/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CStep;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.MFlowStep;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.service.FlowStepService;
import com.iso.dashboard.service.StepMngtService;
import com.iso.dashboard.ui.FlowStepMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.FlowStepView;
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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowStepController {

    FlowStepView view;

    CustomPageTable pagedTable;
    String prefix = "procedure.list";//tien to trong file language
    String headerKey = "header.procedureAssign";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String userListLabel = "procedure.list";
    Resource resource;
    List<CStep> cSteps = new ArrayList<>();

    public FlowStepController(FlowStepView view, Integer flowId) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        List<MFlowStep> flowSteps = FlowStepService.getInstance().listTaskAssignee(String.valueOf(flowId), null);
        if (flowSteps != null) {
            flowSteps.forEach((assignee) -> {
                CStep cs = StepMngtService.getInstance().getCStepById(String.valueOf(assignee.getStepId()));
                cs.setPosition(String.valueOf(assignee.getStepIndex()));
                cs.setBranchName(assignee.getStepBranch() != null?StepMngtService.getInstance().getCStepById(String.valueOf(assignee.getStepBranch())).getName():"");
                cSteps.add(cs);
            });
        }
        initTable(cSteps, String.valueOf(flowId));
        doAction(flowId);
    }

    public void initTable(List<CStep> lstUsers, String flowId) {

        pagedTable.addGeneratedColumn("action", (Table source, Object itemId, Object columnId) -> {
            Button btnEdit = new Button();
            btnEdit.setIcon(ISOIcons.EDIT);
            btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnEdit.setDescription(BundleUtils.getString("common.button.delete"));
            btnEdit.addClickListener((Button.ClickEvent event) -> {
                CStep employee = (CStep) itemId;
                List<MFlowStep> assignees = FlowStepService.getInstance().listTaskAssignee(String.valueOf(flowId), String.valueOf(employee.getId()));
                if (assignees != null) {
                    createDialog(false, assignees.get(0), Integer.valueOf(flowId));
                }
            });
            Button btnDelete = new Button();
            btnDelete.setIcon(ISOIcons.DELETE);
            btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
            btnDelete.addClickListener((Button.ClickEvent event) -> {
                CStep employee = (CStep) itemId;
                List<MFlowStep> assignees = FlowStepService.getInstance().listTaskAssignee(String.valueOf(flowId), String.valueOf(employee.getId()));
                if (assignees != null) {
                    assignees.stream().forEach((assignee) -> {
                        FlowStepService.getInstance().removeTaskAssignee(String.valueOf(assignee.getId()));
                    });
                }
                cSteps.remove(employee);
                reloadData(cSteps);
            });
            HorizontalLayout hori = new HorizontalLayout();
            hori.addComponent(btnEdit);
            hori.setComponentAlignment(btnEdit, Alignment.MIDDLE_LEFT);
            hori.addComponent(btnDelete);
            hori.setComponentAlignment(btnDelete, Alignment.MIDDLE_RIGHT);
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

    public IndexedContainer createContainer(List<CStep> lstUser) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        int i = 1;
        for (CStep u : lstUser) {
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

    public void reloadData(List<CStep> lstUsers) {
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

    private void onInsert(Integer flowId) {
        createDialog(true, new MFlowStep(), flowId);
    }

    private void onSearch(Integer flowId) {
        List<MFlowStep> assignees = FlowStepService.getInstance().listTaskAssignee(String.valueOf(flowId), null);
        cSteps = new ArrayList<>();
        if (assignees != null) {
            assignees.forEach((assignee) -> {
                CStep cs = StepMngtService.getInstance().getCStepById(String.valueOf(assignee.getStepId()));
                cs.setPosition(String.valueOf(assignee.getStepIndex()));
                cs.setBranchName(StepMngtService.getInstance().getCStepById(String.valueOf(assignee.getStepBranch())).getName());
                cSteps.add(cs);
            });
        }
        reloadData(cSteps);
    }

    private void initDataDialog(FlowStepMngtUI ui, boolean isInsert, MFlowStep dto) {
        if (!isInsert) {
            CStep cs = StepMngtService.getInstance().getCStepById(String.valueOf(dto.getStepId()));
            ui.getTxtStep().setTreeTaskSelected(cs);
            ui.getTxtStep().getTxtTaskName().setValue(cs.getName());
            ui.getTxtStepOrder().setValue(String.valueOf(dto.getStepIndex()));
            if (dto.getStepBranch() != null) {
                CStep cStep = StepMngtService.getInstance().getCStepById(String.valueOf(dto.getStepBranch()));
                ui.getTxtSubBranch().setTreeTaskSelected(cStep);
                ui.getTxtSubBranch().getTxtTaskName().setValue(cStep.getName());
            }
            if (dto.getBackEmp() != null) {
                Employee backEmp = EmployeeMngtService.getInstance().getEmployeeById(String.valueOf(dto.getBackEmp()));
                ui.getTxtBackupEmp().setTreeTaskSelected(backEmp);
                ui.getTxtBackupEmp().getTxtTaskName().setValue(backEmp.getFirstName());
            }
            if (dto.getDefEmp() != null) {
                Employee backEmp = EmployeeMngtService.getInstance().getEmployeeById(String.valueOf(dto.getDefEmp()));
                ui.getTxtDefEmp().getTxtTaskName().setValue(backEmp.getFirstName());
                ui.getTxtDefEmp().setTreeTaskSelected(backEmp);
            }
            if (dto.getSubEmp() != null) {
                Employee backEmp = EmployeeMngtService.getInstance().getEmployeeById(String.valueOf(dto.getSubEmp()));
                ui.getTxtSuppportEmp().getTxtTaskName().setValue(backEmp.getFirstName());
                ui.getTxtSuppportEmp().setTreeTaskSelected(backEmp);
            }
            String[] temp = null;
            if (dto.getAllowAction() != null) {
                temp = dto.getAllowAction().split(";");
            }
            if (temp != null) {
                List<String> allowAction = Arrays.asList(temp);
                if (allowAction.contains("1")) {
                    ui.getCbx1().setValue(true);
                }
                if (allowAction.contains("2")) {
                    ui.getCbx2().setValue(true);
                }
                if (allowAction.contains("3")) {
                    ui.getCbx3().setValue(true);
                }
                if (allowAction.contains("4")) {
                    ui.getCbx4().setValue(true);
                }
                if (allowAction.contains("5")) {
                    ui.getCbx5().setValue(true);
                }
                if (allowAction.contains("6")) {
                    ui.getCbx6().setValue(true);
                }
                if (allowAction.contains("7")) {
                    ui.getCbx7().setValue(true);
                }
                if (allowAction.contains("8")) {
                    ui.getCbx8().setValue(true);
                }
                if (allowAction.contains("9")) {
                    ui.getCbx9().setValue(true);
                }
                if (allowAction.contains("10")) {
                    ui.getCbx10().setValue(true);
                }
                if (allowAction.contains("11")) {
                    ui.getCbx11().setValue(true);
                }
                if (allowAction.contains("12")) {
                    ui.getCbx12().setValue(true);
                }
            }
        }
    }

    private void createDialog(boolean isInsert, MFlowStep flowStep, Integer flowId) {
        FlowStepMngtUI ui = new FlowStepMngtUI("");
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 4f / 5;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(75.0f, Sizeable.Unit.PERCENTAGE);
        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        initDataDialog(ui, isInsert, flowStep);
        ui.getBtnSave().addClickListener((Button.ClickEvent event) -> {
            CStep cs = ui.getTxtStep().getTreeTaskSelected();
            if (isInsert) {
                if (!cSteps.contains(cs)) {
                    getDateFromUI(flowStep, ui, flowId, cs);
                    FlowStepService.getInstance().addTaskAssignee(flowStep);
                    cSteps.add(cs);
                    reloadData(cSteps);
                    window.close();
                } else {
                    Notification.show("Bước thực hiện đã tồn tại trong quy trình!");
                }
            } else {
                int index = cSteps.indexOf(cs);
                cSteps.remove(cs);
                getDateFromUI(flowStep, ui, flowId, cs);
                FlowStepService.getInstance().updateTaskAssignee(flowStep);
                cSteps.add(index, cs);
                reloadData(cSteps);
                window.close();
            }
        });
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });
        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public void getDateFromUI(MFlowStep flowStep, FlowStepMngtUI ui, Integer flowId, CStep cs) {
        flowStep.setFlowId(flowId);
        flowStep.setBackEmp(ui.getTxtBackupEmp().getTreeTaskSelected() != null ? ui.getTxtBackupEmp().getTreeTaskSelected().getId() : null);
        flowStep.setDefEmp(ui.getTxtDefEmp().getTreeTaskSelected() != null ? ui.getTxtDefEmp().getTreeTaskSelected().getId() : null);
        flowStep.setSubEmp(ui.getTxtSuppportEmp().getTreeTaskSelected() != null ? ui.getTxtSuppportEmp().getTreeTaskSelected().getId() : null);
        flowStep.setStepIndex(Integer.valueOf(ui.getTxtStepOrder().getValue()));
        flowStep.setStepId(cs.getId());
        flowStep.setStepBranch(ui.getTxtSubBranch().getTreeTaskSelected() != null ? ui.getTxtSubBranch().getTreeTaskSelected().getId() : null);
        String actionAllow = "";
        if (ui.getCbx1().getValue()) {
            actionAllow = ";1";
        }
        if (ui.getCbx2().getValue()) {
            actionAllow += ";2";
        }
        if (ui.getCbx3().getValue()) {
            actionAllow += ";3";
        }
        if (ui.getCbx4().getValue()) {
            actionAllow += ";4";
        }
        if (ui.getCbx5().getValue()) {
            actionAllow += ";5";
        }
        if (ui.getCbx6().getValue()) {
            actionAllow += ";6";
        }
        if (ui.getCbx7().getValue()) {
            actionAllow += ";7";
        }
        if (ui.getCbx8().getValue()) {
            actionAllow += ";8";
        }
        if (ui.getCbx9().getValue()) {
            actionAllow += ";9";
        }
        if (ui.getCbx10().getValue()) {
            actionAllow += ";10";
        }
        if (ui.getCbx11().getValue()) {
            actionAllow += ";11";
        }
        if (ui.getCbx12().getValue()) {
            actionAllow += ";12";
        }
        if (actionAllow.length() > 0) {
            flowStep.setAllowAction(actionAllow.substring(1));
        }
        cs.setBranchName(ui.getTxtSubBranch().getTreeTaskSelected() != null ? ui.getTxtSubBranch().getTreeTaskSelected().getName() : "");
        cs.setPosition(ui.getTxtStepOrder().getValue());
    }
}
