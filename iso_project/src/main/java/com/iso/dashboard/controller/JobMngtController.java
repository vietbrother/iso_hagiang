/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.Job;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.service.JobMngtService;
import com.iso.dashboard.ui.JobMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.JobMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class JobMngtController {

    JobMngtView view;
    JobMngtService service;

    CustomGrid pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "jobMngt.list";//tien to trong file language
    String headerKey = "header.jobMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String jobListLabel = "jobMngt.list";
    Resource resource;

    public JobMngtController(JobMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(JobMngtService.getInstance().listJobs(null));
        doAction();
    }

    public void initTable(List<Job> lstJobs) {
//        pagedTable.addGeneratedColumn("btnAction", new Table.ColumnGenerator() {
////            private static final long serialVersionUID = -5042109683675242407L;
//
//            public Component generateCell(Table source, Object itemId, Object columnId) {
//                Item item = source.getItem(itemId);
//
//                Button btnEdit = new Button();
//                btnEdit.setIcon(FontAwesome.EDIT);
//                btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnEdit.setDescription(BundleUtils.getString("common.button.edit"));
//                btnEdit.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        String jobId = (String) item.getItemProperty("id").getValue();
//                        Notification.show("Edit " + jobId);
//                        Job dto = JobMngtService.getInstance().getJobById(jobId);
//                        onUpdate(dto);
//                        view.getBtnSearch().click();
//                    }
//                });
//                Button btnDelete = new Button();
//                btnDelete.setIcon(ISOIcons.DELETE);
//                btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
//                btnDelete.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        ConfirmDialog d = ConfirmDialog.show(
//                                UI.getCurrent(),
//                                BundleUtils.getString("message.warning.title"),
//                                BundleUtils.getString("message.warning.content"),
//                                BundleUtils.getString("common.confirmDelete.yes"),
//                                BundleUtils.getString("common.confirmDelete.no"),
//                                new ConfirmDialog.Listener() {
//
//                            public void onClose(ConfirmDialog dialog) {
//                                if (dialog.isConfirmed()) {
//                                    // Confirmed to continue
//                                    String jobId = (String) item.getItemProperty("id").getValue();
//                                    ResultDTO res = JobMngtService.getInstance().removeJob(jobId);
//                                    ComponentUtils.showNotification("Delete id : " + jobId + " " + res.getKey() + " " + res.getMessage());
//                                    view.getBtnSearch().click();
//                                }
//                            }
//                        });
//                        d.setStyleName(Reindeer.WINDOW_LIGHT);
//                        d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                        d.getOkButton().setIcon(ISOIcons.SAVE);
//                        d.getCancelButton().setIcon(ISOIcons.CANCEL);
//                    }
//                });
//
//                HorizontalLayout hori = new HorizontalLayout();
//                hori.addComponent(btnEdit);
//                hori.addComponent(btnDelete);
//                return hori;
//            }
//        });
//        reloadData(lstJobs);
//        pagedTable.setSizeFull();
//        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
////        pagedTable.setWidth("1000px");
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
//        pagedTable.setSelectable(true);
//        pagedTable.setAlwaysRecalculateColumnWidths(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);

        IndexedContainer container = createContainer(lstJobs);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                Job item = (Job) obj;
                String jobId = String.valueOf(item.getId());
                Notification.show("Edit " + jobId);
                Job dto = JobMngtService.getInstance().getJobById(jobId);
                onUpdate(dto);
                view.getBtnSearch().click();
            }

            @Override
            public void actionDelete(Object obj) {
                ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                        BundleUtils.getString("message.warning.title"),
                        BundleUtils.getString("message.warning.content"),
                        BundleUtils.getString("common.confirmDelete.yes"),
                        BundleUtils.getString("common.confirmDelete.no"),
                        new ConfirmDialog.Listener() {

                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    // Confirmed to continue
                                    Job item = (Job) obj;
                                    String jobId = String.valueOf(item.getId());
                                    ResultDTO res = JobMngtService.getInstance().removeJob(jobId);
                                    ComponentUtils.showNotification("Delete id : " + jobId + " " + res.getKey() + " " + res.getMessage());
                                    view.getBtnSearch().click();
                                }
                            }
                        });
                d.setStyleName(Reindeer.WINDOW_LIGHT);
                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                d.getOkButton().setIcon(ISOIcons.SAVE);
                d.getCancelButton().setIcon(ISOIcons.CANCEL);
            }

            @Override
            public void actionSelect(Object obj) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    public void reloadData(List<Job> lstJobs) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstJobs)));
    }

    public IndexedContainer createContainer(List<Job> lstJobs) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("jobTitle", String.class, null);
        container.addContainerProperty("salaryGlone", String.class, null);
        container.addContainerProperty("salaryWage", String.class, null);
        for (Job j : lstJobs) {
            Item item = container.addItem(j);
            item.getItemProperty("id").setValue(String.valueOf(j.getId()));
            item.getItemProperty("jobTitle").setValue(j.getJobTitle());
            item.getItemProperty("salaryGlone").setValue(j.getSalaryGlone());
            item.getItemProperty("salaryWage").setValue(j.getSalaryWage());
        }
        container.sort(new Object[]{"id"}, new boolean[]{true});
        return container;
    }

    private void doAction() {
        view.getBtnSearch().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onSearch();
            }
        });

        view.getBtnAdd().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onInsert();
            }
        });

        view.getBtnExport().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onExport();
            }
        });
    }

    public boolean validateData(JobMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtJobTitle().getValue())) {
            Notification.show(BundleUtils.getString("jobMngt.list.jobTitle") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtJobTitle().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("jobMngt.list.jobTitle") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtSalaryGlone().getValue())) {
            Notification.show(BundleUtils.getString("jobMngt.list.salaryGlone") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtSalaryGlone().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("jobMngt.list.salaryGlone") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtSalaryWage().getValue())) {
            Notification.show(BundleUtils.getString("jobMngt.list.salaryWage") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtSalaryWage().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("jobMngt.list.salaryWage") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtMinSalary().getValue())) {
            Notification.show(BundleUtils.getString("jobMngt.list.minSalary") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtMinSalary().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("jobMngt.list.minSalary") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtMaxSalary().getValue())) {
            Notification.show(BundleUtils.getString("jobMngt.list.maxSalary") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtMaxSalary().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("jobMngt.list.maxSalary") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        return true;
    }

    private void onInsert() {
        createDialog(true, new Job());
    }

    private void onUpdate(Job dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<Job> lstJob = JobMngtService.getInstance().listJobs(view.getTxtJobtitle().getValue());
        EmployeeMngtService.getInstance().listEmployees("");
        Notification.show("lstJob : " + lstJob.size());
        reloadData(lstJob);
    }

    private void onExport() {

        try {
            List<Job> lstJobs = JobMngtService.getInstance().listJobs(view.getTxtJobtitle().getValue());
            String[] header = new String[]{"export_01", "export_02", "export_03"};
            String[] align = new String[]{"LEFT", "LEFT", "LEFT"};
            List<AbstractMap.SimpleEntry<String, String>> headerAlign = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
            for (int i = 0; i < header.length; i++) {
                headerAlign.add(new AbstractMap.SimpleEntry(header[i], align[i]));
            }
            String fileTemplate = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    //+ File.separator + "WEB-INF"
                    //+ File.separator + "templates"
                    //+ File.separator + "incident"
                    //+ File.separator + "TEMPLATE_EXPORT.xls"
                    + Constants.FILE_CONF.PATH_EXPORT_TEMPLATE_XLSX;

            String subTitle = Constants.EMPTY_CHARACTER;

            File fileExport = CommonExport.exportFile(lstJobs,//list du lieu
                    headerAlign,//header
                    //"userMngt.list",//header prefix
                    jobListLabel,//header prefix
                    fileTemplate,//path template
                    BundleUtils.getString("userMngt.fileName.export"),//fileName out
                    7,//start row
                    subTitle,//sub title
                    4,//cell title Index
                    BundleUtils.getString("userMngt.report")//title
            );
            resource = new FileResource(fileExport);

            Page.getCurrent().open(resource, null, false);
        } catch (Exception e) {
        }
    }

    private void initDataDialog(JobMngtUI ui, boolean isInsert, Job dto) {

        if (isInsert) {

        } else {
            ui.getTxtJobTitle().setValue(dto.getJobTitle() == null ? "" : dto.getJobTitle());
            ui.getTxtSalaryGlone().setValue(dto.getSalaryGlone() == null ? "" : dto.getSalaryGlone());
            ui.getTxtSalaryWage().setValue(dto.getSalaryWage() == null ? "" : dto.getSalaryWage());
        }
    }

    public void createDialog(boolean isInsert, Job dto) {
        JobMngtUI ui = new JobMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 1 / 3;
        window.setWidth(String.valueOf(height) + "%");
//        window.setIcon(VaadinIcons.CALENDAR_USER);
        initDataDialog(ui, isInsert, dto);
        ui.getBtnSave().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean validate = validateData(ui);
                if (validate) {
                    ConfirmDialog d = ConfirmDialog.show(
                            UI.getCurrent(),
                            BundleUtils.getString("message.warning.title"),
                            BundleUtils.getString("message.warning.content"),
                            BundleUtils.getString("common.confirmDelete.yes"),
                            BundleUtils.getString("common.confirmDelete.no"),
                            new ConfirmDialog.Listener() {

                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        // Confirmed to continue
                                        ResultDTO res = null;
                                        getDataFromUI(ui, dto);
                                        if (isInsert) {
                                            res = JobMngtService.getInstance().addJob(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            res = JobMngtService.getInstance().updateJob(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // User did not confirm
                                        Notification.show("nok");
                                        window.close();
                                    }
                                }
                            });
                    d.setStyleName(Reindeer.LAYOUT_BLUE);
                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
                    d.getOkButton().setIcon(ISOIcons.SAVE);
                    d.getOkButton().focus();
                    d.getCancelButton().setIcon(ISOIcons.CANCEL);
                }
            }

        });
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });

        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
        ui.getTxtJobTitle().focus();
    }

    private void getDataFromUI(JobMngtUI ui, Job dto) {
        dto.setJobTitle(ui.getTxtJobTitle().getValue().trim());
        dto.setSalaryGlone(ui.getTxtSalaryGlone().getValue().trim());
        dto.setSalaryWage(ui.getTxtSalaryWage().getValue().trim());
    }
}
