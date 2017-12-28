/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.CTaskAssignee;
import com.iso.dashboard.dto.CTaskAttachment;
import com.iso.dashboard.dto.CTaskAttachmentPK;
import com.iso.dashboard.dto.CTaskGroup;
import com.iso.dashboard.dto.CTaskPriority;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.TaskAssigneeService;
import com.iso.dashboard.service.TaskAttachmentService;
import com.iso.dashboard.service.TaskGroupMgntService;
import com.iso.dashboard.service.TaskOrgMngService;
import com.iso.dashboard.service.TaskPriorityMgntService;
import com.iso.dashboard.ui.TaskAddUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.TaskAssignView;
import com.iso.dashboard.view.TaskPersonalMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.Reindeer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskPersonalMngtController {

    TaskPersonalMngtView view;
    CustomGrid pagedTable;

    String prefix = "taskOrgMngt.list";//tien to trong file language
    String headerKey = "header.taskOrgMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String orgTaskListLabel = "taskOrgMngt.list";

    Employee user = (Employee) VaadinSession.getCurrent().getAttribute(Employee.class.getName());
    List<Integer> lstTaskId = new ArrayList<>();

    public TaskPersonalMngtController(TaskPersonalMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        if (user != null) {
            List<CTaskAssignee> lstAssignees = TaskAssigneeService.getInstance().listTaskAssignee(null, String.valueOf(user.getId()));
            if (lstAssignees != null && !lstAssignees.isEmpty()) {
                lstAssignees.stream().forEach((assignee) -> {
                    lstTaskId.add(assignee.getId().getTaskId());
                });
            }
        }
        initTable(TaskOrgMngService.getInstance().getTasksByName(view.getTxtTaskName().getValue(), 
                    user.getDepartment() != null ? String.valueOf(user.getDepartment().getId()) : null));
        doAction();
    }

    public void initTable(List<CTask> cTasks) {

        IndexedContainer container = createContainer(cTasks);
        pagedTable.genGridCustomButton(container, prefix, headerColumn, null, new HandlerActionGrid() {
            @Override
            public void actionEdit(Object obj) {
                CTask cTask = (CTask) obj;
                createDialog(false, cTask, cTask.getDepartmentId(), false, true);
            }

            @Override
            public void actionDelete(Object obj) {
                ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                        BundleUtils.getString("message.warning.title"),
                        BundleUtils.getString("message.warning.content"),
                        BundleUtils.getString("common.confirmDelete.yes"),
                        BundleUtils.getString("common.confirmDelete.no"), (ConfirmDialog dialog) -> {
                    if (dialog.isConfirmed()) {
                        CTask cTask = (CTask) obj;
                        TaskOrgMngService.getInstance().removeTaskOrg(String.valueOf(cTask.getTaskId()));
                        view.getBtnSearch().click();
                    }
                });
                d.setStyleName(Reindeer.WINDOW_LIGHT);
                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                d.getOkButton().setIcon(ISOIcons.SAVE);
                d.getCancelButton().setIcon(ISOIcons.CANCEL);
            }

            @Override
            public void actionSelect(Object obj) {
                CTask cTask = (CTask) obj;
                createDialog(false, cTask, cTask.getDepartmentId(), false, false);
            }

            @Override
            public void actionAssign(Object obj) {
                CTask cTask = (CTask) obj;
                TaskAssignView asignMngtView = new TaskAssignView(cTask.getTaskId());
                Window window = new Window("", asignMngtView);
                float height = UI.getCurrent().getWidth() * 3 / 4;
                window.setWidth(String.valueOf(height) + "%");
                window.setHeight("80%");
                asignMngtView.setWidth("100%");
                asignMngtView.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
                window.setModal(true);
                DataUtil.reloadWindow(window);
                UI.getCurrent().addWindow(window);
            }

            @Override
            public void actionReport(Object obj) {
                CTask cTask = (CTask) obj;
                createDialog(false, cTask, cTask.getDepartmentId(), true, false);
            }
        });
//        pagedTable.getColumn("progress").setRenderer(new ProgressBarRenderer());
        pagedTable.getColumn("progress").setRenderer(new HtmlRenderer(), new Converter<String, String>() {

            @Override
            public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                return "not implemented";
            }

            @Override
            public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                return "<div class=\"progress\" "
                        //                        + "style=\"background-color: #D5D5C9;border-radius: 8px;width: 100%;height: 5px;vertical-align: middle;\""
                        + ">\n"
                        + "    <div class=\"progress-bar\" "
                        //                        + "style=\"background-color: #29D110; border-radius: 8px; height: 100%;\" "
                        + "role=\"progressbar\" "
                        + "aria-valuenow=\"" + value + "\" "
                        + "aria-valuemin=\"0\" "
                        + "aria-valuemax=\"100\" "
                        + "style=\"width:" + value + "%\">\n"
                        //                        + "      <p style=\"padding-left: 16px; margin-top: 0px;\">" + value + "%</p>\n"
                        + "      <p style=\"margin-top: 0px;\">" + value + "%</p>\n"
                        + "    </div>\n"
                        + "  </div>";
            }

            @Override
            public Class<String> getModelType() {
                return String.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        
        
        pagedTable.getColumn("name").setMinimumWidth(130);
        pagedTable.getColumn("startTime").setMinimumWidth(160);
        pagedTable.getColumn("endTime").setMinimumWidth(160);
        pagedTable.getColumn("progress").setMinimumWidth(80);
        pagedTable.getColumn("status").setMinimumWidth(110);
    }

    public IndexedContainer createContainer(List<CTask> cTasks) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("startTime", String.class, null);
        container.addContainerProperty("endTime", String.class, null);
//        container.addContainerProperty("progress", Double.class, null);
        container.addContainerProperty("progress", String.class, null);
        container.addContainerProperty("status", String.class, null);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (CTask u : cTasks) {
            if (!lstTaskId.contains(u.getTaskId())) {
                break;
            }
            Item item = container.addItem(u);
            item.getItemProperty("name").setValue(u.getTaskName() != null ? u.getTaskName() : "");
            item.getItemProperty("startTime").setValue(u.getStartTime() != null ? sdf.format(u.getStartTime()) : "");
            item.getItemProperty("endTime").setValue(u.getEndTime() != null ? sdf.format(u.getEndTime()) : "");
//            item.getItemProperty("progress").setValue(u.getRate() != null ? Float.intBitsToFloat(u.getRate()) : 0f);
            item.getItemProperty("progress").setValue(u.getRate() != null ? String.valueOf(u.getRate()) : "0");
//            item.getItemProperty("progress").setValue(u.getRate() != null ? Double.valueOf((double) u.getRate() / 100.00) : 0f);
            if (u.getStatus() != null) {
                if (null != u.getStatus()) {
                    switch (u.getStatus()) {
                        case 4:
                            item.getItemProperty("status").setValue("Đã hủy");
                            break;
                        case 3:
                            item.getItemProperty("status").setValue("Hoàn thành");
                            break;
                        case 2:
                            item.getItemProperty("status").setValue("Thực hiện");
                            break;
                        case 1:
                            item.getItemProperty("status").setValue("Mới");
                            break;
                        default:
                            item.getItemProperty("status").setValue("Mới");
                            break;
                    }
                }
            } else {
                item.getItemProperty("status").setValue("Mới");
            }
        }
        return container;
    }

    public void reloadData(List<CTask> cTasks) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(cTasks)));
    }

    public void doAction() {

        view.getBtnAdd().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                createDialog(true, new CTask(), user.getDepartment() != null ? -1 : user.getDepartment().getId(), false, true);
            }
        });

        view.getBtnSearch().addClickListener((Button.ClickEvent event) -> {
            reloadData(TaskOrgMngService.getInstance().getTasksByName(view.getTxtTaskName().getValue(), 
                    user.getDepartment() != null ? String.valueOf(user.getDepartment().getId()) : null));
        });
    }

    public void createDialog(boolean isInsert, CTask dto, Integer departmentId, Boolean isView, boolean isEditable) {
        TaskAddUI ui = new TaskAddUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"), dto.getTaskId(), isView);
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 4 / 5;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(92.0f, Sizeable.Unit.PERCENTAGE);
//        window.setIcon(VaadinIcons.CALENDAR_USER);
        initDataDialog(ui, isInsert, dto, isEditable);
        ui.getBtnSave().addClickListener((Button.ClickEvent event) -> {
            boolean validate = validateData(ui);
            if (validate) {
                ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                        BundleUtils.getString("message.warning.title"),
                        BundleUtils.getString("message.warning.content"),
                        BundleUtils.getString("common.confirmDelete.yes"),
                        BundleUtils.getString("common.confirmDelete.no"), (ConfirmDialog dialog) -> {
                    if (dialog.isConfirmed()) {
                        // Confirmed to continue
                        ResultDTO res = null;
                        List<CTaskAttachment> attachments = new ArrayList<>();
                        getDataFromUI(ui, dto, departmentId, attachments);
                        if (isInsert) {
                            res = TaskOrgMngService.getInstance().addTaskOrg(dto);
                            String taskId = res.getId();
                            if (taskId != null && !"".equals(taskId)) {
                                attachments.forEach((attachment) -> {
                                    CTaskAttachmentPK attachmentPK = new CTaskAttachmentPK();
                                    attachmentPK.setTaskId(Integer.valueOf(taskId));
                                    attachmentPK.setAttachmentType(Constants.ATTACHMENT_TYPE_TASK);
                                    attachment.setId(attachmentPK);
                                    TaskAttachmentService.getInstance().addTaskAttachment(attachment);
                                });
                            }
                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                        } else {
                            dto.setUpdateTime(new Date());
                            res = TaskOrgMngService.getInstance().updateTaskOrg(dto);
                            TaskAttachmentService.getInstance().deleteAttachmentsByTask(String.valueOf(dto.getTaskId()), String.valueOf(Constants.ATTACHMENT_TYPE_TASK), null);
                            attachments.forEach((attachment) -> {
                                CTaskAttachmentPK attachmentPK = new CTaskAttachmentPK();
                                attachmentPK.setTaskId(dto.getTaskId());
                                attachmentPK.setAttachmentType(Constants.ATTACHMENT_TYPE_TASK);
                                attachment.setId(attachmentPK);
                                TaskAttachmentService.getInstance().addTaskAttachment(attachment);
                            });
                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                    + res.getKey() + " " + res.getMessage());
                        }
                        window.close();
                        view.getBtnSearch().click();
                    } else {
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
        ui.getTxtTaskName().focus();
    }

    private void initDataDialog(TaskAddUI ui, boolean isInsert, CTask dto, boolean isEditable) {
        List<CTaskGroup> cTaskGroups = TaskGroupMgntService.getInstance().getTaskGroups();
        List<CTaskPriority> cTaskPioritys = TaskPriorityMgntService.getInstance().getTaskPrioritys();
        List<CatItemDTO> listTaskGroups = new ArrayList<>();
        listTaskGroups.add(0, new CatItemDTO("-1", BundleUtils.getString("common.select")));
        List<CatItemDTO> listTaskPiorities = new ArrayList<>();
        listTaskPiorities.add(0, new CatItemDTO("-1", BundleUtils.getString("common.select")));
        cTaskGroups.stream().forEach((cTaskGroup) -> {
            listTaskGroups.add(new CatItemDTO(String.valueOf(cTaskGroup.getTaskGroupId()), cTaskGroup.getTaskGroupName()));
        });
        cTaskPioritys.stream().forEach((cTaskPiority) -> {
            listTaskPiorities.add(new CatItemDTO(String.valueOf(cTaskPiority.getTaskPiorityId()), cTaskPiority.getTaskPiorityName()));
        });
        ui.getCmbTashGroup().setNullSelectionAllowed(false);
        ui.getCmbTashPriority().setNullSelectionAllowed(false);
        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbTashGroup(), Constants.EMPTY_CHARACTER, listTaskGroups);
            ComponentUtils.fillDataComboNoDefault(ui.getCmbTashPriority(), Constants.EMPTY_CHARACTER, listTaskPiorities);
        } else {
            ui.getTxtTaskName().setValue(dto.getTaskName());
//            ui.getTxtTaskReference().setValue(TaskOrgMngService.getInstance().getTaskOrgById(String.valueOf(dto.getTaskId())).getTaskName());
            if (dto.getTaskParentId() != null) {
                CTask taskParent = TaskOrgMngService.getInstance().getTaskOrgById(String.valueOf(dto.getTaskParentId()));
                ui.getTxtTaskReference().setTreeTaskSelected(taskParent);
                ui.getTxtTaskReference().getTxtTaskName().setValue(taskParent != null ? taskParent.getTaskName() : "");
            }
            ui.getTxtDepartment().setValue(OrganizationMngService.getInstance().getOrganizationById(String.valueOf(dto.getDepartmentId())).getName());
            ui.getPbTimeStart().setValue(dto.getStartTime());
            ui.getPbTimeEnd().setValue(dto.getEndTime());
            ui.getTxtBudget().setValue(String.valueOf(dto.getBudget()));
            ComponentUtils.fillDataCombo(ui.getCmbTashGroup(), Constants.EMPTY_CHARACTER, String.valueOf(dto.getTaskGroupId()), listTaskGroups);
            ComponentUtils.fillDataCombo(ui.getCmbTashPriority(), Constants.EMPTY_CHARACTER, String.valueOf(dto.getTaskPiorityId()), listTaskPiorities);
            ui.getTxaTaskContent().setValue(dto.getTaskContent());
            List<CTaskAttachment> attachments
                    = TaskAttachmentService.getInstance().listTaskAttachment(
                            String.valueOf(dto.getTaskId()), String.valueOf(Constants.ATTACHMENT_TYPE_TASK));
            if (attachments != null) {
                attachments.stream().forEach((attachment) -> {
                    ui.getAttachFile().addFileAttack(attachment.getAttachmentUrl(), attachment.getFileName());
                });
            }
        }
        if (!isEditable) {
            ui.getTxtTaskReference().setEnabled(false);
            ui.getTxtTaskName().setEnabled(false);
            ui.getCmbTashGroup().setEnabled(false);
            ui.getCmbTashPriority().setEnabled(false);
            ui.getPbTimeStart().setEnabled(false);
            ui.getPbTimeEnd().setEnabled(false);
            ui.getTxtBudget().setEnabled(false);
            ui.getTxaTaskContent().setEnabled(false);
            ui.getBtnSave().setVisible(false);
            ui.getAttachFile().setEnableUpload(false);
            ui.getAttachFile().setEnableToDelete(false);
        }
    }

    public boolean validateData(TaskAddUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtTaskName().getValue())) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getTxaTaskContent().getValue())) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.content") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }

        if (ui.getCmbTashGroup().getValue() == null || ((CatItemDTO) ui.getCmbTashGroup().getValue()).getItemId().equals(("-1"))) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.group") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getCmbTashPriority().getValue() == null || ((CatItemDTO) ui.getCmbTashPriority().getValue()).getItemId().equals(("-1"))) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.priority") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getTxtBudget().getValue())) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.budget") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getPbTimeStart().getValue() == null) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.startTime") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getPbTimeEnd().getValue() == null) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.endTime") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getPbTimeEnd().getValue().getTime() < ui.getPbTimeStart().getValue().getTime()) {
            ComponentUtils.showNotification(BundleUtils.getString("taskOrgMngt.list.endTime") + Constants.SPACE_CHARACTER
                    + BundleUtils.getString("common.less")
                    + BundleUtils.getString("taskOrgMngt.list.startTime"));
            return false;
        }

        return true;
    }

    private void getDataFromUI(TaskAddUI ui, CTask dto, Integer depId, List<CTaskAttachment> attachments) {
        CTask taskParent = ui.getTxtTaskReference().getTreeTaskSelected();
        dto.setTaskParentId(taskParent != null ? taskParent.getTaskId() : null);
        dto.setDepartmentId(depId);
        dto.setTaskName(ui.getTxtTaskName().getValue());
        dto.setTaskContent(ui.getTxaTaskContent().getValue());
        dto.setTaskGroupId(Integer.valueOf(((CatItemDTO) ui.getCmbTashGroup().getValue()).getItemId()));
        dto.setTaskPiorityId(Integer.valueOf(((CatItemDTO) ui.getCmbTashPriority().getValue()).getItemId()));
        dto.setStartTime(ui.getPbTimeStart().getValue());
        dto.setEndTime(ui.getPbTimeEnd().getValue());
        dto.setBudget(Float.parseFloat(ui.getTxtBudget().getValue()));
        List<String> urls = ui.getAttachFile().getUrl();
        String path = ui.getAttachFile().getPath();
        urls.forEach((url) -> {
            CTaskAttachment attachment = new CTaskAttachment();
            attachment.setAttachmentUrl(path);
            attachment.setFileName(url);
            attachments.add(attachment);
        });
    }
}
