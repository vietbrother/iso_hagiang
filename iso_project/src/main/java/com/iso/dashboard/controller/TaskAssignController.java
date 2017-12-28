/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.CTaskAssignee;
import com.iso.dashboard.dto.CTaskAssigneePK;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.service.TaskAssigneeService;
import com.iso.dashboard.service.TaskOrgMngService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.TaskReportMngtUI;
import com.iso.dashboard.ui.TreeEmployeeSearchUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.TaskAssignView;
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
public class TaskAssignController {

    TaskAssignView view;
    UserMngService service;

    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "task.assign.list";//tien to trong file language
    String headerKey = "header.taskAssign";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String userListLabel = "task.attach.list";
    Resource resource;
    List<Employee> employees = new ArrayList<>();

    public TaskAssignController(TaskAssignView view, Integer taskId) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        List<CTaskAssignee> assignees = TaskAssigneeService.getInstance().listTaskAssignee(String.valueOf(taskId), null);
        if (assignees != null) {
            assignees.forEach((assignee) -> {
                employees.add(EmployeeMngtService.getInstance().getEmployeeById(String.valueOf(assignee.getAssignedId())));
            });
        }
        initTable(employees, String.valueOf(taskId));
        doAction(taskId);
    }

    public void initTable(List<Employee> lstUsers, String taskId) {

        pagedTable.addGeneratedColumn("action", (Table source, Object itemId, Object columnId) -> {
            Button btnDelete = new Button();
            btnDelete.setIcon(ISOIcons.DELETE);
            btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
            btnDelete.addClickListener((Button.ClickEvent event) -> {
                Employee employee = (Employee) itemId;
                List<CTaskAssignee> assignees = TaskAssigneeService.getInstance().listTaskAssignee(String.valueOf(taskId), String.valueOf(employee.getId()));
                if (assignees != null) {
                    assignees.stream().forEach((assignee) -> {
                        TaskAssigneeService.getInstance().removeTaskAssignee(assignee.getId());
                    });
                }
                employees.remove(employee);
                reloadData(employees);
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
//        pagedTable.setSelectable(true);
        pagedTable.setAlwaysRecalculateColumnWidths(true);
        //pagedTable.setResponsive(true);
        pagedTable.setColumnHeaders(headerName);
        pagedTable.setVisibleColumns((Object[]) columnVisible);
    }

    public IndexedContainer createContainer(List<Employee> lstUser) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("title", String.class, null);
        container.addContainerProperty("department", String.class, null);
        int i = 1;
        for (Employee u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("index").setValue(i);
            item.getItemProperty("name").setValue(u.getFirstName());
            item.getItemProperty("title").setValue(u.getJob().getJobTitle());
            item.getItemProperty("department").setValue(u.getDepartment().getName());
            i++;
        };
        container.sort(new Object[]{"name"}, new boolean[]{true});
        return container;
    }

    public void reloadData(List<Employee> lstUsers) {
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
        TreeEmployeeSearchUI ui = new TreeEmployeeSearchUI("");
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 8.5f / 10;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(80.0f, Sizeable.Unit.PERCENTAGE);
        ui.setWidth("100%");
//        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnSelect().addClickListener((Button.ClickEvent event) -> {
            List<Employee> list = ui.getTreeTaskSelected();
            if (list != null) {
                list.stream().filter((emp) -> (!employees.contains(emp))).forEach((emp) -> {
                    CTaskAssignee cTaskAssignee = new CTaskAssignee();
                    CTaskAssigneePK assigneePK = new CTaskAssigneePK();
                    assigneePK.setTaskId(taskId);
                    cTaskAssignee.setId(assigneePK);
                    cTaskAssignee.setAssignedId(emp.getId());
                    TaskAssigneeService.getInstance().addTaskAssignee(cTaskAssignee);
                    employees.add(emp);
                });
            }
            reloadData(employees);
            CTask cTask = TaskOrgMngService.getInstance().getTaskOrgById(String.valueOf(taskId));
            if (cTask != null && (cTask.getStatus() == null || cTask.getStatus() == 1)) {
                cTask.setStatus(2);
                TaskOrgMngService.getInstance().updateTaskOrg(cTask);
            }
            window.close();
        });
        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    private void onSearch(Integer taskId) {
        List<CTaskAssignee> assignees = TaskAssigneeService.getInstance().listTaskAssignee(String.valueOf(taskId), null);
        employees = new ArrayList<>();
        if (assignees != null) {
            assignees.forEach((assignee) -> {
                employees.add(EmployeeMngtService.getInstance().getEmployeeById(String.valueOf(assignee.getAssignedId())));
            });
        }
        reloadData(employees);
    }
}
