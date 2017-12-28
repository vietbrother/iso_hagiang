/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CStep;
import com.iso.dashboard.service.StepMngtService;
import com.iso.dashboard.ui.StepMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.DateUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.StepMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class StepMngtController {

    StepMngtView view;
    StepMngtService service;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Stepname", "Email", "Phone", ""};
    String prefix = "stepMngt.list";//tien to trong file language
    String headerKey = "header.stepMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String stepListLabel = "stepMngt.list";
    Resource resource;

    public StepMngtController(StepMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(StepMngtService.getInstance().listCSteps(null));
        doAction();
    }

    public void initTable(List<CStep> lstCStep) {
        IndexedContainer container = createContainer(lstCStep);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                onUpdate((CStep) obj);
                view.getBtnSearch().click();
            }

            @Override
            public void actionDelete(Object obj) {
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
                                    CStep step = (CStep) obj;
                                    ResultDTO res = StepMngtService.getInstance().removeCStep(String.valueOf(step.getId()));
                                    ComponentUtils.showNotification("Delete id : " + String.valueOf(step.getId()) + " " + res.getKey() + " " + res.getMessage());
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

    public IndexedContainer createContainer(List<CStep> lstStep) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        for (CStep u : lstStep) {
            Item item = container.addItem(u);
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("description").setValue(u.getDescription());
        }
        return container;
    }

    public void reloadData(List<CStep> lstCStep) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstCStep)));
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

    public boolean validateData(StepMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtName().getValue())) {
            Notification.show(BundleUtils.getString("stepMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtName().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("stepMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtCode().getValue())) {
            Notification.show(BundleUtils.getString("stepMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtCode().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("stepMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
            Notification.show(BundleUtils.getString("stepMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxaDescription().getValue().length() > 2000) {
            Notification.show(BundleUtils.getString("stepMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        return true;
    }

    private void onInsert() {
        createDialog(true, new CStep());
    }

    private void onUpdate(CStep dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<CStep> lstStep = StepMngtService.getInstance().listCSteps(view.getTxtName().getValue());
        reloadData(lstStep);
    }

    private void onExport() {

        try {
            List<CStep> lstStep = StepMngtService.getInstance().listCSteps(view.getTxtName().getValue());
            String[] header = new String[]{"stepname", "birthDay", "email"};
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

            File fileExport = CommonExport.exportFile(lstStep,//list du lieu
                    headerAlign,//header
                    //"stepMngt.list",//header prefix
                    stepListLabel,//header prefix
                    fileTemplate,//path template
                    BundleUtils.getString("stepMngt.fileName.export"),//fileName out
                    7,//start row
                    subTitle,//sub title
                    4,//cell title Index
                    BundleUtils.getString("stepMngt.report")//title
            );
            resource = new FileResource(fileExport);

            Page.getCurrent().open(resource, null, false);
        } catch (Exception e) {
        }
    }

    private void initDataDialog(StepMngtUI ui, boolean isInsert, CStep dto) {

        if (isInsert) {
            
        } else {
            ui.getTxtName().setValue(dto.getName() == null ? "" : dto.getName());
            ui.getTxtCode().setValue(dto.getCode()== null ? "" : dto.getCode());
            ui.getTxaDescription().setValue(dto.getDescription()== null ? "" : dto.getDescription());
            ui.getTxtTimeExecute().setValue(dto.getTimeExecute()== null ? "" : dto.getTimeExecute());
            ui.getTxtPosition().setValue(dto.getPosition()== null ? "" : dto.getPosition());
            ui.getTxtCreatedBy().setValue(dto.getCreatedBy()== null ? "" : dto.getCreatedBy());
            ui.getTxtUpdatedBy().setValue(dto.getUpdatedBy()== null ? "" : dto.getUpdatedBy());
            ui.getPdCreatedTime().setValue(dto.getCreatedTime());
            ui.getPdUpdatedTime().setValue(dto.getUpdatedTime());
        }

    }

    public void createDialog(boolean isInsert, CStep dto) {
        StepMngtUI ui = new StepMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
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
                                            res = StepMngtService.getInstance().addCStep(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            res = StepMngtService.getInstance().updateCStep(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // Step did not confirm
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
        ui.getTxtCode().focus();
    }

    private void getDataFromUI(StepMngtUI ui, CStep dto) {
        dto.setCode(ui.getTxtCode().getValue().trim());
        dto.setName(ui.getTxtName().getValue().trim());
        dto.setDescription(ui.getTxaDescription().getValue().trim());
        dto.setTimeExecute(ui.getTxtTimeExecute().getValue().trim());
        dto.setPosition(ui.getTxtPosition().getValue().trim());
        dto.setCreatedBy(ui.getTxtCreatedBy().getValue().trim());
        dto.setUpdatedBy(ui.getTxtUpdatedBy().getValue().trim());
        dto.setUpdatedTime(ui.getPdUpdatedTime().getValue());
        dto.setCreatedTime(ui.getPdCreatedTime().getValue());
    }
}
