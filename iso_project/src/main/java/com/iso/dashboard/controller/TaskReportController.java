/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CTaskAttachment;
import com.iso.dashboard.dto.CTaskAttachmentPK;
import com.iso.dashboard.dto.CTaskHistory;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.TaskAttachmentService;
import com.iso.dashboard.service.TaskHistoryService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.TaskReportMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.TaskReportView;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskReportController {

    TaskReportView view;
    UserMngService service;

    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "task.attach.list";//tien to trong file language
    String headerKey = "header.taskAttachReport";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String userListLabel = "task.attach.list";
    Resource resource;

    public TaskReportController(TaskReportView view, Integer taskId) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(TaskHistoryService.getInstance().listTaskHist(String.valueOf(taskId)));
        doAction(taskId);
    }

    public void initTable(List<CTaskHistory> lstUsers) {

        pagedTable.addGeneratedColumn("action", (Table source, Object itemId, Object columnId) -> {
            Button btnEdit = new Button();
            btnEdit.setIcon(FontAwesome.EDIT);
            btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnEdit.setDescription(BundleUtils.getString("common.button.edit"));
            btnEdit.addClickListener((Button.ClickEvent event) -> {
                onUpdate((CTaskHistory) itemId, false);
            });
            Button btnCheck = new Button();
            btnCheck.setIcon(VaadinIcons.USER_CHECK);
//            btnCheck.setIcon(FontAwesome.COMMENTING);
            btnCheck.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnCheck.setDescription(BundleUtils.getString("common.button.check"));
            btnCheck.addClickListener((Button.ClickEvent event) -> {
                onUpdate((CTaskHistory) itemId, true);
            });
            HorizontalLayout hori = new HorizontalLayout();
            hori.addComponent(btnEdit);
            hori.addComponent(btnCheck);
            return hori;
        });
        reloadData(lstUsers);
        pagedTable.setSizeFull();
        pagedTable.setPageLength(10);
        pagedTable.setImmediate(true);
        //pagedTable.setSelectable(true);
        //pagedTable.setAlwaysRecalculateColumnWidths(true);
        //pagedTable.setResponsive(true);
        pagedTable.setVisibleColumns((Object[]) columnVisible);
        pagedTable.setColumnHeaders(headerName);
    }

    public IndexedContainer createContainer(List<CTaskHistory> lstUser) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("time", String.class, null);
        container.addContainerProperty("contentAction", String.class, null);
        container.addContainerProperty("status", String.class, null);
        container.addContainerProperty("timeCheck", String.class, null);
        container.addContainerProperty("contentCheck", String.class, null);
        container.addContainerProperty("statusCheck", String.class, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        int i = 1;
        for (CTaskHistory u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("index").setValue(i);
            item.getItemProperty("time").setValue(u.getReportTime() != null ? sdf.format(u.getReportTime()) : "");
            item.getItemProperty("contentAction").setValue(u.getReportContent() != null ? u.getReportContent() : "");
            item.getItemProperty("status").setValue(u.getReportRate() != null ? u.getReportRate() + "%" : "0%");
            item.getItemProperty("timeCheck").setValue(u.getCheckTime() != null ? sdf.format(u.getCheckTime()) : "");
            item.getItemProperty("contentCheck").setValue(u.getCheckResult() != null ? u.getCheckResult() : "");
            item.getItemProperty("statusCheck").setValue(u.getCheckRate() != null ? u.getCheckRate() + "%" : "0%");
            i++;
        }
        //container.sort(new Object[]{"contentAction"}, new boolean[]{true});
        return container;
    }

    public void reloadData(List<CTaskHistory> lstUsers) {
        pagedTable.setContainerDataSource(createContainer(lstUsers));
    }

    private void doAction(Integer taskId) {
        view.getBtnRefresh().addClickListener((Button.ClickEvent event) -> {
            onSearch(taskId);
        });

        view.getBtnAdd().addClickListener((Button.ClickEvent event) -> {
            onInsert(taskId);
        });

    }

    public boolean validateData(TaskReportMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxaTaskContent().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.mobile") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }

        if (ui.getPbTimeAttach().getValue() == null) {
            Notification.show(BundleUtils.getString("task.attach.list.time") + Constants.SPACE_CHARACTER
                    + BundleUtils.getString("common.notnull"));
            return false;
        }
        return true;
    }

    private void onInsert(Integer taskId) {
        CTaskHistory cTaskHistory = new CTaskHistory();
        cTaskHistory.setTaskId(taskId);
        createDialog(true, cTaskHistory, false);
    }

    private void onUpdate(CTaskHistory dto, boolean isCheck) {
        createDialog(false, dto, isCheck);
    }

    private void onSearch(Integer taskId) {
        List<CTaskHistory> lstUser = TaskHistoryService.getInstance().listTaskHist(String.valueOf(taskId));
        reloadData(lstUser);
    }

    private void initDataDialog(TaskReportMngtUI ui, boolean isInsert, CTaskHistory dto, boolean isCheck) {

        if (!isInsert) {
            ui.getTxaTaskContent().setValue(dto.getReportContent());
            ui.getPbTimeAttach().setValue(dto.getReportTime());
            ComponentUtils.fillPercentCombo(ui.getCmbStatus(), String.valueOf(dto.getReportRate()));
            Map<Object, Object> map = new HashMap<>();
            map.put("id.taskId", dto.getTaskId());
            map.put("histSeq", dto.getSeq());
            map.put("id.attachmentType", Constants.ATTACHMENT_TYPE_REPORT);
            List<CTaskAttachment> attachments = TaskAttachmentService.getInstance().listTaskByCondition(map);
            if (attachments != null) {
                attachments.stream().forEach((attachment) -> {
                    ui.getUploadImport().addFileAttack(attachment.getAttachmentUrl(), attachment.getFileName());
                });
            }
        } else {
            ComponentUtils.fillPercentCombo(ui.getCmbStatus(), null);
        }
        if (isCheck) {
            ui.getTxaContentCheck().setValue(dto.getCheckResult() != null ? dto.getCheckResult() : "");
            ui.getTxaContentCheck().setVisible(true);
            ui.getPbTimeCheck().setValue(dto.getCheckTime());
            ui.getPbTimeCheck().setVisible(true);
            ComponentUtils.fillPercentCombo(ui.getCmbCheck(), String.valueOf(dto.getCheckRate()));
            ui.getCmbCheck().setVisible(true);
            ui.getTxaTaskContent().setReadOnly(true);
            ui.getPbTimeAttach().setReadOnly(true);
            ui.getCmbStatus().setReadOnly(true);
            ui.getUploadImport().setEnableUpload(false);
            ui.getUploadImport().setEnableToDelete(false);
        }

    }

    public void createDialog(boolean isInsert, CTaskHistory dto, boolean isCheck) {
        TaskReportMngtUI ui = new TaskReportMngtUI(BundleUtils.getString("common.button.add"));
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 1 / 2;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(80.0f, Sizeable.Unit.PERCENTAGE);
        initDataDialog(ui, isInsert, dto, isCheck);
        ui.getBtnSave().addClickListener((Button.ClickEvent event) -> {
            if (validateData(ui)) {
                ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                        BundleUtils.getString("message.warning.title"),
                        BundleUtils.getString("message.warning.content"),
                        BundleUtils.getString("common.confirmDelete.yes"),
                        BundleUtils.getString("common.confirmDelete.no"), (ConfirmDialog dialog) -> {
                    if (dialog.isConfirmed()) {
                        ResultDTO res = null;
                        List<CTaskAttachment> attachments = new ArrayList<>();
                        getDataFromUI(ui, dto, attachments, isCheck);
                        if (isInsert) {
                            res = TaskHistoryService.getInstance().addTaskHist(dto);
                            String seq = res.getId();
                            if (seq != null && !"".equals(seq)) {
                                attachments.forEach((attachment) -> {
                                    CTaskAttachmentPK attachmentPK = new CTaskAttachmentPK();
                                    attachmentPK.setTaskId(dto.getTaskId());
                                    attachmentPK.setAttachmentType(Constants.ATTACHMENT_TYPE_REPORT);
                                    attachment.setId(attachmentPK);
                                    attachment.setHistSeq(Integer.valueOf(seq));
                                    TaskAttachmentService.getInstance().addTaskAttachment(attachment);
                                });
                            }
                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                        } else {
                            dto.setUpdatedTime(new Date());
                            res = TaskHistoryService.getInstance().updateTaskHist(dto);
                            TaskAttachmentService.getInstance().deleteAttachmentsByTask(String.valueOf(dto.getTaskId()), String.valueOf(Constants.ATTACHMENT_TYPE_REPORT), String.valueOf(dto.getSeq()));
                            attachments.forEach((attachment) -> {
                                CTaskAttachmentPK attachmentPK = new CTaskAttachmentPK();
                                attachmentPK.setTaskId(dto.getTaskId());
                                attachmentPK.setAttachmentType(Constants.ATTACHMENT_TYPE_REPORT);
                                attachment.setId(attachmentPK);
                                attachment.setHistSeq(dto.getSeq());
                                TaskAttachmentService.getInstance().addTaskAttachment(attachment);
                            });
                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                    + res.getKey() + " " + res.getMessage());
                        }
                        window.close();
                        view.getBtnRefresh().click();
                    } else {
                        // User did not confirm
                        Notification.show("nok");
                        window.close();
                    }
                });
                d.setStyleName(Reindeer.LAYOUT_BLUE);
                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                d.getOkButton().setIcon(ISOIcons.SAVE);
                d.getOkButton().focus();
                d.getCancelButton().setIcon(ISOIcons.CANCEL);
            }
        });
        ui.getBtnCancel().addClickListener((Button.ClickEvent event) -> {
            window.close();
        });

        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    private void getDataFromUI(TaskReportMngtUI ui, CTaskHistory dto, List<CTaskAttachment> attachments, boolean isCheck) {
        List<String> urls = ui.getUploadImport().getUrl();
        String path = ui.getUploadImport().getPath();
        urls.forEach((url) -> {
            CTaskAttachment attachment = new CTaskAttachment();
            attachment.setAttachmentUrl(path);
            attachment.setFileName(url);
            attachments.add(attachment);
        });
        dto.setReportContent(ui.getTxaTaskContent().getValue());
        dto.setReportTime(ui.getPbTimeAttach().getValue());
        dto.setReportRate(Integer.parseInt(((CatItemDTO) ui.getCmbStatus().getValue()).getItemId()));
        if (isCheck) {
            dto.setCheckRate(Integer.parseInt(((CatItemDTO) ui.getCmbCheck().getValue()).getItemId()));
            dto.setCheckTime(ui.getPbTimeCheck().getValue());
            dto.setCheckResult(ui.getTxaContentCheck().getValue());
        }
    }
}
