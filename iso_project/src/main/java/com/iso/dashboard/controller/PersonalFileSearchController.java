/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.PProcess;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.service.ProcessService;
import com.iso.dashboard.ui.PersonalSendFileMngtUI;
import com.iso.dashboard.ui.ProcedureMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.view.PersonalFileSearchView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.HtmlRenderer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalFileSearchController {

    PersonalFileSearchView view;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Procedurename", "Email", "Phone", ""};
    String prefix = "personal.fileInfo.list";//tien to trong file language
    String headerKey = "header.personalFileInfoMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String procedureListLabel = "procedureMngt.list";
    Resource resource;
    SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public PersonalFileSearchController(PersonalFileSearchView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
//        initTable(ProcedureMngtService.getInstance().listProcedures(null));
        initTable(getListData());
        doAction();
    }

    public void initTable(List<PProcess> lstProcess) {
        IndexedContainer container = createContainer(lstProcess);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {

            }

            @Override
            public void actionDelete(Object obj) {

            }

            @Override
            public void actionSelect(Object obj) {
                createViewDialog((PProcess) obj);
            }
        });
        pagedTable.getColumn("level").setRenderer(new HtmlRenderer(), new Converter<String, String>() {

            @Override
            public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                return value;
            }

            @Override
            public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                String backgroundColor = "#e67e22 !important";
                if (value != null) {
                    if (value.contains("1")) {
                        backgroundColor = "#e67e22 !important";
                    } else if (value.contains("2")) {
                        backgroundColor = "green";
                    } else if (value.contains("3")) {
                        backgroundColor = "red";
                    } else {
                        backgroundColor = "#00bcd4";
                    }
                }
//                return "<div style=\"width: 100%; color: white; font-weight: bold; text-align: center; font-family: \'Arial\';"
//                        + "background-color:" + backgroundColor
//                        + "\" >"
//                        + value
//                        + " </div>";
                return "<span style=\"    display: inline-block;\n"
                        + "    font-weight: bold;\n"
                        + "    padding: 2px 5px 1px 5px;\n"
                        + "    line-height: 1.5384616;\n"
                        + "    border: 1px solid transparent;\n"
                        + "    text-transform: uppercase;\n"
                        + "    font-size: 12px;\n"
                        + "    letter-spacing: .1px;\n"
                        + "    border-radius: 2px; \n"
                        + "    color: #fff; font-family: \'Arial\';\n"
                        + "    text-align: center;\n"
                        + "    white-space: nowrap;\n"
                        + "    vertical-align: baseline;\n"
                        + "    background-color: " + backgroundColor + ";"
                        + "\">"
                        + value
                        + "</span>";
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
        pagedTable.getColumn("level").setWidth(150);
//        pagedTable.getColumn("view").setWidth(120);
//        pagedTable.getColumn("view").setHeaderCaption(BundleUtils.getString("common.button.info"));

        RendererClickListener nameClickListener = new RendererClickListener() {

            @Override
            public void click(RendererClickEvent event) {
                createViewDialog((PProcess) event.getItemId());
            }
        };
        pagedTable.getColumn("procedureName").setRenderer(new ButtonRenderer(nameClickListener));
        pagedTable.getColumn("code").setRenderer(new ButtonRenderer(nameClickListener));

    }

    public IndexedContainer createContainer(List<PProcess> lstProcess) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("procedureName", String.class, null);
        container.addContainerProperty("createTime", String.class, null);
        container.addContainerProperty("level", String.class, null);
        for (PProcess u : lstProcess) {
            Item item = container.addItem(u);
            item.getItemProperty("code").setValue(u.getCode());
            item.getItemProperty("procedureName").setValue(u.getProcedureName());
            item.getItemProperty("level").setValue(u.getLevel());
            item.getItemProperty("createTime").setValue(sp.format(u.getCreatedTime()));
        }
        return container;
    }

//    public void reloadData(List<CProcedure> lstCProcedure) {
//        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstCProcedure)));
//    }
    public void reloadData(List<PProcess> lstProcess) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstProcess)));
    }

    private void doAction() {
        view.getBtnSearch().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onSearch();
            }
        });

    }

    public boolean validateData(ProcedureMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtName().getValue())) {
            Notification.show(BundleUtils.getString("procedureMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtName().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("procedureMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtCode().getValue())) {
            Notification.show(BundleUtils.getString("procedureMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtCode().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("procedureMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
            Notification.show(BundleUtils.getString("procedureMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxaDescription().getValue().length() > 2000) {
            Notification.show(BundleUtils.getString("procedureMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        return true;
    }

    private void onInsert() {
    }

    private void onUpdate(CProcedure dto) {
    }

//    private void onSearch() {
//        List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getTxtName().getValue());
//        reloadData(lstProcedure);
//    }
//    
    private void onSearch() {

        reloadData(getListData());
    }

    private List<PProcess> getListData() {
        PProcess searchDto = new PProcess();
        searchDto.setProcedureName(view.getTxtName().getValue());
        List<PProcess> lstProcess = ProcessService.getInstance().listPProcess(searchDto);
        return lstProcess;
    }

    public void createViewDialog(PProcess dtoProcess) {
        CProcedure procedure = ProcedureMngtService.getInstance().getProcedureById(String.valueOf(dtoProcess.getProcedureId()));
        procedure.setProcessDto(dtoProcess);
        PersonalSendFileMngtUI ui = new PersonalSendFileMngtUI(
                BundleUtils.getString("procedureMngt.public.list.infoDetail"), procedure, false);
        ui.getTxtName().setValue(procedure.getName());
        ui.getTxtLevel().setValue(procedure.getLevel());
        ui.getTxtCost().setValue(procedure.getCost());
        ui.getTxtOrgName().setValue(procedure.getOrgName());
        ui.getTxtProcessTime().setValue(procedure.getProcessTime());
        ui.getTxaDescription().setValue(procedure.getDescription());

        ui.getTxtName().setReadOnly(true);
        ui.getTxtLevel().setReadOnly(true);
        ui.getTxtCost().setReadOnly(true);
        ui.getTxtOrgName().setReadOnly(true);
        ui.getTxtProcessTime().setReadOnly(true);
        ui.getTxaDescription().setReadOnly(true);

        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 4 / 5;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(92.0f, Sizeable.Unit.PERCENTAGE);
        window.setResizeLazy(true);
        window.setResizable(true);

        ui.setWidth("100%");
//        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });
        ui.getBtnSave().setVisible(false);
        ui.getBtnSendFile().setVisible(false);
        ui.getBtnUploadFile().setVisible(false);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

}
