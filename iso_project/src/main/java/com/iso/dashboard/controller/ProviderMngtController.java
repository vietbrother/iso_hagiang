/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.Provider;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.ProviderMngtService;
import com.iso.dashboard.ui.ProviderMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.ProviderMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class ProviderMngtController {
    
    ProviderMngtView view;
    ProviderMngtService service;

    CustomGrid pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "providerMngt.list";//tien to trong file language
    String headerKey = "header.providerMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String jobListLabel = "providerMngt.list";
    Resource resource;

    public ProviderMngtController(ProviderMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(ProviderMngtService.getInstance().listProviders(null));
        doAction();
    }

    public void initTable(List<Provider> lst) {
        IndexedContainer container = createContainer(lst);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                Provider item = (Provider) obj;
                String uId = String.valueOf(item.getId());
                Notification.show("Edit " + uId);
                Provider dto = ProviderMngtService.getInstance().getProviderById(uId);
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
                                    Provider item = (Provider) obj;
                                    String delId = String.valueOf(item.getId());
                                    ResultDTO res = ProviderMngtService.getInstance().removeProvider(delId);
                                    ComponentUtils.showNotification("Delete id : " + delId + " " + res.getKey() + " " + res.getMessage());
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

    public void reloadData(List<Provider> lst) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lst)));
    }

    public IndexedContainer createContainer(List<Provider> lst) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("name", String.class, null);
         container.addContainerProperty("description", String.class, null);
        for (Provider j : lst) {
            Item item = container.addItem(j);
            item.getItemProperty("id").setValue(String.valueOf(j.getId()));
            item.getItemProperty("code").setValue(j.getCode());
            item.getItemProperty("name").setValue(j.getName());
            item.getItemProperty("description").setValue(j.getDescription());
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

    public boolean validateData(ProviderMngtUI ui) {
//        if (DataUtil.isNullOrEmpty(ui.getTxtUnit().getValue())) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
//            return false;
//        }
//        if (ui.getTxtUnit().getValue().length() > 20) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
//            return false;
//        }
        return true;
    }

    private void onInsert() {
        createDialog(true, new Provider());
    }

    private void onUpdate(Provider dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<Provider> lst = ProviderMngtService.getInstance().listProviders(view.getTxtProvider().getValue());
        Notification.show("lstUnits : " + lst.size());
        reloadData(lst);
    }

    private void onExport() {

        try {
            List<Provider> lst = ProviderMngtService.getInstance().listProviders(view.getTxtProvider().getValue());
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

            File fileExport = CommonExport.exportFile(lst,//list du lieu
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

    private void initDataDialog(ProviderMngtUI ui, boolean isInsert, Provider dto) {

        if (isInsert) {

        } else {
//            ui.getTxtUnit().setValue(dto.getUnit() == null ? "" : dto.getUnit());
        }
    }

    public void createDialog(boolean isInsert, Provider dto) {
//        ProviderMngtUI ui = new ProviderMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
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
//                                public void onClose(ConfirmDialog dialog) {
//                                    if (dialog.isConfirmed()) {
//                                        // Confirmed to continue
//                                        ResultDTO res = null;
//                                        getDataFromUI(ui, dto);
//                                        if (isInsert) {
//                                            res = UnitMngtService.getInstance().addUnit(dto);
//                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                                        } else {
//                                            res = UnitMngtService.getInstance().updateUnit(dto);
//                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
//                                                    + res.getKey() + " " + res.getMessage());
//                                        }
//                                        window.close();
//                                        view.getBtnSearch().click();
//                                    } else {
//                                        // User did not confirm
//                                        Notification.show("nok");
//                                        window.close();
//                                    }
//                                }
//                            });
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
//        ui.getTxtUnit().focus();
    }

    private void getDataFromUI(ProviderMngtUI ui, Provider dto) {
//        dto.setUnit(ui.getTxtUnit().getValue().trim());
    }
}
