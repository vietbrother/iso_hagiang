/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.AssetGroup;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.AssetGroupMngtService;
import com.iso.dashboard.service.AssetGroupMngtService;
import com.iso.dashboard.ui.AssetGroupMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.AssetGroupMngtView;
import com.iso.dashboard.view.AssetGroupMngtView;
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
public class AssetGroupMngtController {
    
    AssetGroupMngtView view;
    AssetGroupMngtService service;

    CustomGrid pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "assetGroupMngt.list";//tien to trong file language
    String headerKey = "header.assetGroupMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String jobListLabel = "assetGroupMngt.list";
    Resource resource;

    public AssetGroupMngtController(AssetGroupMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(AssetGroupMngtService.getInstance().listAssetGroups(null));
        doAction();
    }

    public void initTable(List<AssetGroup> lst) {
        IndexedContainer container = createContainer(lst);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                AssetGroup item = (AssetGroup) obj;
                String uId = String.valueOf(item.getId());
                Notification.show("Edit " + uId);
                AssetGroup dto = AssetGroupMngtService.getInstance().getAssetGroupById(uId);
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
                                    AssetGroup item = (AssetGroup) obj;
                                    String delId = String.valueOf(item.getId());
                                    ResultDTO res = AssetGroupMngtService.getInstance().removeAssetGroup(delId);
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

    public void reloadData(List<AssetGroup> lstAssetGroups) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstAssetGroups)));
    }

    public IndexedContainer createContainer(List<AssetGroup> lstAssetGroups) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("name", String.class, null);
         container.addContainerProperty("description", String.class, null);
        for (AssetGroup j : lstAssetGroups) {
            Item item = container.addItem(j);
            item.getItemProperty("id").setValue(String.valueOf(j.getId()));
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

    public boolean validateData(AssetGroupMngtUI ui) {
//        if (DataUtil.isNullOrEmpty(ui.getTxtAssetGroup().getValue())) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
//            return false;
//        }
//        if (ui.getTxtAssetGroup().getValue().length() > 20) {
//            Notification.show(BundleUtils.getString("employeeTypeMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
//            return false;
//        }
        return true;
    }

    private void onInsert() {
        createDialog(true, new AssetGroup());
    }

    private void onUpdate(AssetGroup dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<AssetGroup> lstAssetGroup = AssetGroupMngtService.getInstance().listAssetGroups(view.getTxtAssetGroup().getValue());
        Notification.show("lstJob : " + lstAssetGroup.size());
        reloadData(lstAssetGroup);
    }

    private void onExport() {

        try {
            List<AssetGroup> lstAssetGroups = AssetGroupMngtService.getInstance().listAssetGroups(view.getTxtAssetGroup().getValue());
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

            File fileExport = CommonExport.exportFile(lstAssetGroups,//list du lieu
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

    private void initDataDialog(AssetGroupMngtUI ui, boolean isInsert, AssetGroup dto) {

        if (isInsert) {

        } else {
//            ui.getTxtAssetGroup().setValue(dto.getAssetGroup() == null ? "" : dto.getAssetGroup());
        }
    }

    public void createDialog(boolean isInsert, AssetGroup dto) {
//        AssetGroupMngtUI ui = new AssetGroupMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
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
//                                            res = AssetGroupMngtService.getInstance().addAssetGroup(dto);
//                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                                        } else {
//                                            res = AssetGroupMngtService.getInstance().updateAssetGroup(dto);
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
//        ui.getTxtAssetGroup().focus();
    }

    private void getDataFromUI(AssetGroupMngtUI ui, AssetGroup dto) {
//        dto.setAssetGroup(ui.getTxtAssetGroup().getValue().trim());
    }
}
