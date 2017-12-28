/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.EmployeeEducation;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.EmployeeEducationMngtService;
import com.iso.dashboard.ui.EmployeeEducationMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.EmployeeEducationMngtView;
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
public class EmployeeEducationMngtController {

    EmployeeEducationMngtView view;
    EmployeeEducationMngtService service;

    CustomGrid pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "employeeEducationMngt.list";//tien to trong file language
    String headerKey = "header.employeeEducationMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String jobListLabel = "employeeEducationMngt.list";
    Resource resource;

    public EmployeeEducationMngtController(EmployeeEducationMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(EmployeeEducationMngtService.getInstance().listEmployeeEducations(null));
        doAction();
    }

    public void initTable(List<EmployeeEducation> lst) {
//        pagedTable.addGeneratedColumn("btnAction", new Table.ColumnGenerator() {
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
//                        String uId = (String) item.getItemProperty("id").getValue();
//                        Notification.show("Edit " + uId);
//                        EmployeeEducation dto = EmployeeEducationMngtService.getInstance().getEmployeeEducationById(uId);
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
//                        ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
//                                BundleUtils.getString("message.warning.title"),
//                                BundleUtils.getString("message.warning.content"),
//                                BundleUtils.getString("common.confirmDelete.yes"),
//                                BundleUtils.getString("common.confirmDelete.no"),
//                                new ConfirmDialog.Listener() {
//
//                            public void onClose(ConfirmDialog dialog) {
//                                if (dialog.isConfirmed()) {
//                                    // Confirmed to continue
//                                    String rId = (String) item.getItemProperty("id").getValue();
//                                    ResultDTO res = EmployeeEducationMngtService.getInstance().removeEmployeeEducation(rId);
//                                    ComponentUtils.showNotification("Delete id : " + rId + " " + res.getKey() + " " + res.getMessage());
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
//        reloadData(lst);
//        pagedTable.setSizeFull();
//        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
////        pagedTable.setWidth("1000px");
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
//        pagedTable.setSelectable(true);
//        pagedTable.setAlwaysRecalculateColumnWidths(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);
        IndexedContainer container = createContainer(lst);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                EmployeeEducation item = (EmployeeEducation) obj;
                String uId = String.valueOf(item.getId());
                Notification.show("Edit " + uId);
                EmployeeEducation dto = EmployeeEducationMngtService.getInstance().getEmployeeEducationById(uId);
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
                                    EmployeeEducation item = (EmployeeEducation) obj;
                                    String rId = String.valueOf(item.getId());
                                    ResultDTO res = EmployeeEducationMngtService.getInstance().removeEmployeeEducation(rId);
                                    ComponentUtils.showNotification("Delete id : " + rId + " " + res.getKey() + " " + res.getMessage());
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

    public void reloadData(List<EmployeeEducation> lst) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lst)));
    }

    public IndexedContainer createContainer(List<EmployeeEducation> lst) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("fromDate", String.class, null);
        container.addContainerProperty("toDate", String.class, null);
        container.addContainerProperty("degreeType", String.class, null);
        container.addContainerProperty("educationType", String.class, null);
        container.addContainerProperty("educationPlace", String.class, null);
        container.addContainerProperty("classification", String.class, null);

        for (EmployeeEducation j : lst) {
            Item item = container.addItem(j);
            item.getItemProperty("id").setValue(String.valueOf(j.getId()));
            item.getItemProperty("fromDate").setValue(j.getFromDate().toString());
            item.getItemProperty("toDate").setValue(j.getToDate().toString());
            item.getItemProperty("degreeType").setValue(j.getDegreeType().getValue());
            item.getItemProperty("educationType").setValue(j.getEducationType().getValue());
            item.getItemProperty("educationPlace").setValue(j.getEducationPlace());
            item.getItemProperty("classification").setValue(j.getClassification());
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

    public boolean validateData(EmployeeEducationMngtUI ui) {
//        if (DataUtil.isNullOrEmpty(ui.getTxtEmployeeType().getValue())) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
//            return false;
//        }
//        if (ui.getTxtEmployeeType().getValue().length() > 20) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
//            return false;
//        }
        return true;
    }

    private void onInsert() {
        createDialog(true, new EmployeeEducation());
    }

    private void onUpdate(EmployeeEducation dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<EmployeeEducation> lstEmployeeEducation = EmployeeEducationMngtService.getInstance().listEmployeeEducations(view.getTxtEmployeeEducation().getValue());
        Notification.show("lstJob : " + lstEmployeeEducation.size());
        reloadData(lstEmployeeEducation);
    }

    private void onExport() {

        try {
            List<EmployeeEducation> lstEmployeeEducation = EmployeeEducationMngtService.getInstance().listEmployeeEducations(view.getTxtEmployeeEducation().getValue());
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

            File fileExport = CommonExport.exportFile(lstEmployeeEducation,//list du lieu
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

    private void initDataDialog(EmployeeEducationMngtUI ui, boolean isInsert, EmployeeEducation dto) {

        if (isInsert) {

        } else {
//            ui.getTxtEmployeeType().setValue(dto.getEmployeeType() == null ? "" : dto.getEmployeeType());
        }
    }

    public void createDialog(boolean isInsert, EmployeeEducation dto) {
//        EmployeeEducationUI ui = new EmployeeEducationUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
//        Window window = new Window(
//                "",
//                ui);
//        //window.setWidth("700px");
//        float height = UI.getCurrent().getWidth() * 1 / 3;
//        window.setWidth(String.valueOf(height) + "%");
////        window.setIcon(VaadinIcons.CALENDAR_USER);
//        initDataDialog(ui, isInsert, dto);
//        ui.getBtnSave().addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                boolean validate = validateData(ui);
//                if (validate) {
//                    ConfirmDialog d = ConfirmDialog.show(
//                            UI.getCurrent(),
//                            BundleUtils.getString("message.warning.title"),
//                            BundleUtils.getString("message.warning.content"),
//                            BundleUtils.getString("common.confirmDelete.yes"),
//                            BundleUtils.getString("common.confirmDelete.no"),
//                            new ConfirmDialog.Listener() {
//
//                        public void onClose(ConfirmDialog dialog) {
//                            if (dialog.isConfirmed()) {
//                                // Confirmed to continue
//                                ResultDTO res = null;
//                                getDataFromUI(ui, dto);
//                                if (isInsert) {
//                                    res = EmployeeTypeMngtService.getInstance().addEmployeeType(dto);
//                                    ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                                } else {
//                                    res = EmployeeTypeMngtService.getInstance().updateEmployeeType(dto);
//                                    ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
//                                            + res.getKey() + " " + res.getMessage());
//                                }
//                                window.close();
//                                view.getBtnSearch().click();
//                            } else {
//                                // User did not confirm
//                                Notification.show("nok");
//                                window.close();
//                            }
//                        }
//                    });
//                    d.setStyleName(Reindeer.LAYOUT_BLUE);
//                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                    d.getOkButton().setIcon(ISOIcons.SAVE);
//                    d.getOkButton().focus();
//                    d.getCancelButton().setIcon(ISOIcons.CANCEL);
//                }
//            }
//
//        });
//        ui.getBtnCancel().addClickListener(new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                window.close();
//            }
//        });
//
//        ui.setWidth("100%");
//        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//
//        window.setModal(true);
//        DataUtil.reloadWindow(window);
//        UI.getCurrent().addWindow(window);
//        ui.getTxtEmployeeType().focus();
    }

    private void getDataFromUI(EmployeeEducationMngtUI ui, EmployeeEducation dto) {
//        dto.setEmployeeType(ui.getTxtEmployeeType().getValue().trim());
    }
}
