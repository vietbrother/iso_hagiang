/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MFlowProcedure;
import com.iso.dashboard.service.FlowProcedureService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.ProcedureSearchUI;
import com.iso.dashboard.ui.TaskReportMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.FlowProcedureView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowProcedureController {

    FlowProcedureView view;
    UserMngService service;

    CustomPageTable pagedTable;
    String prefix = "procedure.list";//tien to trong file language
    String headerKey = "header.procedureAssign";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String userListLabel = "procedure.list";
    Resource resource;
    List<CProcedure> cProcedures = new ArrayList<>();

    public FlowProcedureController(FlowProcedureView view, Integer flowId) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        List<MFlowProcedure> flowProcedures = FlowProcedureService.getInstance().listFlowProcedure(String.valueOf(flowId), null);
        if (flowProcedures != null) {
            flowProcedures.forEach((assignee) -> {
                cProcedures.add(ProcedureMngtService.getInstance().getProcedureById(String.valueOf(assignee.getProcedureId())));
            });
        }
        initTable(cProcedures, String.valueOf(flowId));
        doAction(flowId);
    }

    public void initTable(List<CProcedure> lstUsers, String flowId) {

        pagedTable.addGeneratedColumn("action", (Table source, Object itemId, Object columnId) -> {
            Button btnDelete = new Button();
            btnDelete.setIcon(ISOIcons.DELETE);
            btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
            btnDelete.addClickListener((Button.ClickEvent event) -> {
                CProcedure employee = (CProcedure) itemId;
                List<MFlowProcedure> assignees = FlowProcedureService.getInstance().listFlowProcedure(String.valueOf(flowId), String.valueOf(employee.getId()));
                if (assignees != null) {
                    assignees.stream().forEach((assignee) -> {
                        FlowProcedureService.getInstance().removeFlowProcedure(String.valueOf(assignee.getId()));
                    });
                }
                cProcedures.remove(employee);
                reloadData(cProcedures);
            });
            HorizontalLayout hori = new HorizontalLayout();
            hori.addComponent(btnDelete);
            hori.setComponentAlignment(btnDelete, Alignment.MIDDLE_CENTER);
            return hori;
        }//            private static final long serialVersionUID = -5042109683675242407L;
        );
        reloadData(lstUsers);
        pagedTable.setSizeFull();
        pagedTable.setPageLength(10);
        pagedTable.setImmediate(true);
        pagedTable.setSelectable(true);
        pagedTable.setAlwaysRecalculateColumnWidths(true);
        pagedTable.setResponsive(true);
        pagedTable.setColumnHeaders(headerName);
        pagedTable.setVisibleColumns((Object[]) columnVisible);
    }

    public IndexedContainer createContainer(List<CProcedure> lstUser) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        int i = 1;
        for (CProcedure u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("index").setValue(i);
            item.getItemProperty("code").setValue(u.getCode());
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("description").setValue(u.getDescription());
            i++;
        };
        container.sort(new Object[]{"name"}, new boolean[]{true});
        return container;
    }

    public void reloadData(List<CProcedure> lstUsers) {
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

    private void onInsert(Integer flowId) {
        ProcedureSearchUI ui = new ProcedureSearchUI("");
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 3f / 5;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(50.0f, Sizeable.Unit.PERCENTAGE);
        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnSelect().addClickListener((Button.ClickEvent event) -> {
            List<CProcedure> list = ui.getTreeTaskSelected();
            if (list != null) {
                list.stream().filter((emp) -> (!cProcedures.contains(emp))).forEach((emp) -> {
                    MFlowProcedure flowProcedure = new MFlowProcedure();
                    flowProcedure.setFlowId(flowId);
                    flowProcedure.setProcedureId(emp.getId());
                    FlowProcedureService.getInstance().addFlowProcedure(flowProcedure);
                    cProcedures.add(emp);
                });
            }
            reloadData(cProcedures);
            window.close();
        });
        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    private void onSearch(Integer flowId) {
        List<MFlowProcedure> assignees = FlowProcedureService.getInstance().listFlowProcedure(String.valueOf(flowId), null);
        cProcedures = new ArrayList<>();
        if (assignees != null) {
            assignees.forEach((assignee) -> {
                cProcedures.add(ProcedureMngtService.getInstance().getProcedureById(String.valueOf(assignee.getProcedureId())));
            });
        }
        reloadData(cProcedures);
    }
}
