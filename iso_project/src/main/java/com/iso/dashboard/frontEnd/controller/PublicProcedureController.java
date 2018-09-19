/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.controller;

import com.iso.dashboard.controller.*;
import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.frontEnd.view.PublicProcedureDetailView;
import com.iso.dashboard.frontEnd.view.PublicProcedureView;
import com.iso.dashboard.service.ColorService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.ui.ProcedureMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class PublicProcedureController {

    PublicProcedureView view;
    ProcedureMngtService service;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Procedurename", "Email", "Phone", ""};
    String prefix = "procedureMngt.public.list";//tien to trong file language
    String headerKey = "header.procedureMngt.public";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String procedureListLabel = "procedureMngt.list";
    Resource resource;

    public PublicProcedureController(PublicProcedureView view) {
        this.view = view;
        this.pagedTable = view.getProcedurePage().getPagedTable();
        initTable(ProcedureMngtService.getInstance().listProcedures(null));
        doAction();
    }

    public void initTable(List<CProcedure> lstCProcedure) {
        IndexedContainer container = createContainer(lstCProcedure);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                onUpdate((CProcedure) obj);
                view.getProcedurePage().getBtnSearch().click();
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
                                    CProcedure procedure = (CProcedure) obj;
                                    ResultDTO res = ProcedureMngtService.getInstance().removeProcedure(String.valueOf(procedure.getId()));
                                    ComponentUtils.showNotification("Delete id : " + String.valueOf(procedure.getId()) + " " + res.getKey() + " " + res.getMessage());
                                    view.getProcedurePage().getBtnSearch().click();
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
                createViewDialog((CProcedure) obj);
            }
        });
        pagedTable.getColumn("level").setRenderer(new HtmlRenderer(), new Converter<String, String>() {

            @Override
            public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                return value;
            }

            @Override
            public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                String backgroundColor = "#00bcd4 !important";
                if (value != null) {
                    if (value.contains("1")) {
                        backgroundColor = "#00bcd4 !important";
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
        pagedTable.getColumn("level").setMaximumWidth(250);
        pagedTable.getColumn("level").setMinimumWidth(150);

        pagedTable.getColumn("type").setRenderer(new HtmlRenderer(), new Converter<String, String>() {

            @Override
            public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                return value;
            }

            @Override
            public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                String backgroundColor = "#green !important";
                String valueStr = "";
                if (value != null) {
                    if (value.contains("1")) {
                        backgroundColor = "green !important";
                        valueStr = BundleUtils.getString("procedureMngt.list.type.1");
                    } else if (value.contains("2")) {
                        backgroundColor = "red";
                        valueStr = BundleUtils.getString("procedureMngt.list.type.2");
                    } else {
                        backgroundColor = "#00bcd4";
                        valueStr = BundleUtils.getString("procedureMngt.list.type.3");
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
                        + valueStr
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
        pagedTable.getColumn("type").setMaximumWidth(250);
        pagedTable.getColumn("type").setMinimumWidth(150);

        pagedTable.getColumn("view").setWidth(120);
        pagedTable.getColumn("view").setHeaderCaption(BundleUtils.getString("common.button.info"));

        RendererClickListener nameClickListener = new RendererClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void click(RendererClickEvent event) {
                createViewDialog((CProcedure) event.getItemId());
            }
        };
        pagedTable.getColumn("name").setRenderer(new ButtonRenderer(nameClickListener));
        pagedTable.setSelectionMode(Grid.SelectionMode.NONE);
    }

    public IndexedContainer createContainer(List<CProcedure> lstProcedure) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("type", String.class, null);
        container.addContainerProperty("level", String.class, null);
        for (CProcedure u : lstProcedure) {
            Item item = container.addItem(u);
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("type").setValue(u.getType());
            item.getItemProperty("level").setValue(u.getLevel());
        }
        return container;
    }

    public void reloadData(List<CProcedure> lstCProcedure) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstCProcedure)));
    }

    private void doAction() {
        view.getProcedurePage().getBtnSearch().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onSearch();
            }
        });

        view.getProcedurePage().getBtnAdd().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onInsert();
            }
        });

        view.getProcedurePage().getBtnExport().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onExport();
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

    private void onSearch() {
        List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getProcedurePage().getTxtName().getValue());
        reloadData(lstProcedure);
    }

    private void onExport() {

        try {
            List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getProcedurePage().getTxtName().getValue());
            String[] header = new String[]{"procedurename", "birthDay", "email"};
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

            File fileExport = CommonExport.exportFile(lstProcedure,//list du lieu
                    headerAlign,//header
                    //"procedureMngt.list",//header prefix
                    procedureListLabel,//header prefix
                    fileTemplate,//path template
                    BundleUtils.getString("procedureMngt.fileName.export"),//fileName out
                    7,//start row
                    subTitle,//sub title
                    4,//cell title Index
                    BundleUtils.getString("procedureMngt.report")//title
            );
            resource = new FileResource(fileExport);

            Page.getCurrent().open(resource, null, false);
        } catch (Exception e) {
        }
    }

    public void createViewDialog(CProcedure dto) {
        PublicProcedureDetailView ui = new PublicProcedureDetailView(BundleUtils.getString("procedureMngt.public.list.view"), dto);
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
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnClose().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

}
