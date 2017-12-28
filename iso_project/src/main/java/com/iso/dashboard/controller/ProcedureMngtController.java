/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.DocumentDTO;
import com.iso.dashboard.dto.MFlowProcedure;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.frontEnd.view.PublicProcedureDetailView;
import com.iso.dashboard.service.DocumentMngService;
import com.iso.dashboard.service.FlowMngtService;
import com.iso.dashboard.service.FlowProcedureService;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.ProcedureDocumentService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.ui.ProcedureMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.ProcedureMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcedureMngtController {

    ProcedureMngtView view;
    ProcedureMngtService service;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Procedurename", "Email", "Phone", ""};
    String prefix = "procedureMngt.list";//tien to trong file language
    String headerKey = "header.procedureMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String procedureListLabel = "procedureMngt.list";
    Resource resource;

    Employee user = (Employee) VaadinSession.getCurrent().getAttribute(Employee.class.getName());

    public ProcedureMngtController(ProcedureMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(ProcedureMngtService.getInstance().listProcedures(null));
        doAction();
    }

    public void initTable(List<CProcedure> lstCProcedure) {
        IndexedContainer container = createContainer(lstCProcedure);
//        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {
//
//            @Override
//            public void actionEdit(Object obj) {
//                onUpdate((CProcedure) obj);
//                view.getBtnSearch().click();
//            }
//
//            @Override
//            public void actionDelete(Object obj) {
//                ConfirmDialog d = ConfirmDialog.show(
//                        UI.getCurrent(),
//                        BundleUtils.getString("message.warning.title"),
//                        BundleUtils.getString("message.warning.content"),
//                        BundleUtils.getString("common.confirmDelete.yes"),
//                        BundleUtils.getString("common.confirmDelete.no"),
//                        new ConfirmDialog.Listener() {
//
//                            public void onClose(ConfirmDialog dialog) {
//                                if (dialog.isConfirmed()) {
//                                    // Confirmed to continue
//                                    CProcedure procedure = (CProcedure) obj;
//                                    ResultDTO res = ProcedureMngtService.getInstance().removeProcedure(String.valueOf(procedure.getId()));
//                                    ComponentUtils.showNotification("Delete id : " + String.valueOf(procedure.getId()) + " " + res.getKey() + " " + res.getMessage());
//                                    view.getBtnSearch().click();
//                                }
//                            }
//                        });
//                d.setStyleName(Reindeer.WINDOW_LIGHT);
//                d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                d.getOkButton().setIcon(ISOIcons.SAVE);
//                d.getCancelButton().setIcon(ISOIcons.CANCEL);
//            }
//
//            @Override
//            public void actionSelect(Object obj) {
//                createViewDialog((CProcedure) obj);
//            }
//        });
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                onUpdate((CProcedure) obj);
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
                                    CProcedure procedure = (CProcedure) obj;
                                    ResultDTO res = ProcedureMngtService.getInstance().removeProcedure(String.valueOf(procedure.getId()));
                                    ComponentUtils.showNotification("Delete id : " + String.valueOf(procedure.getId()) + " " + res.getKey() + " " + res.getMessage());
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
                String backgroundColor = "##00bcd4 !important";
                if (value != null) {
                    if (value.contains("1")) {
                        //backgroundColor = "#e67e22 !important";
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

        ClickableRenderer.RendererClickListener nameClickListener = new ClickableRenderer.RendererClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void click(ClickableRenderer.RendererClickEvent event) {
                createViewDialog((CProcedure) event.getItemId());
            }
        };
        pagedTable.getColumn("name").setRenderer(new ButtonRenderer(nameClickListener));
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

    public boolean validateData(ProcedureMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtName().getValue())) {
            Notification.show(BundleUtils.getString("procedureMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtName().getValue().length() > 2000) {
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
        createDialog(true, new CProcedure());
    }

    private void onUpdate(CProcedure dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getTxtName().getValue());
        reloadData(lstProcedure);
    }

    private void onExport() {

        try {
            List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getTxtName().getValue());
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

    private void initDataDialog(ProcedureMngtUI ui, boolean isInsert, CProcedure dto) {

        List<CatItemDTO> lstProcedureType = new ArrayList<>();
        lstProcedureType.add(new CatItemDTO(Constants.PROCEDURE_TYPE_ID.INTERNAL_STR, BundleUtils.getString("procedureMngt.list.type.1")));
        lstProcedureType.add(new CatItemDTO(Constants.PROCEDURE_TYPE_ID.ISO_STR, BundleUtils.getString("procedureMngt.list.type.2")));
        lstProcedureType.add(new CatItemDTO(Constants.PROCEDURE_TYPE_ID.OTHER_STR, BundleUtils.getString("procedureMngt.list.type.3")));

        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbType(), Constants.EMPTY_CHARACTER, lstProcedureType);
        } else {
            ui.getTxtName().setValue(dto.getName() == null ? "" : dto.getName());
            ui.getTxtCode().setValue(dto.getCode() == null ? "" : dto.getCode());
            ui.getTxaDescription().setValue(dto.getDescription() == null ? "" : dto.getDescription());
            ui.getCkEditorTextField().setValue(dto.getContent() == null ? "" : dto.getContent());
            ui.getTxtCost().setValue(dto.getCost() == null ? "" : dto.getCost());
            ui.getTxtLevel().setValue(dto.getLevel() == null ? "" : dto.getLevel());

            if (dto.getOrgId() != 0) {
                Organization org = OrganizationMngService.getInstance().getOrganizationById(String.valueOf(dto.getOrgId()));
                ui.getTrOrgName().getTxtOrgName().setValue(org.getName());
            }
//            ui.getTxtTimeExecute().setValue(dto.getTimeExecute()== null ? "" : dto.getTimeExecute());
//            ui.getTxtPosition().setValue(dto.getPosition()== null ? "" : dto.getPosition());
//            ui.getTxtCreatedBy().setValue(dto.getCreatedBy()== null ? "" : dto.getCreatedBy());
//            ui.getTxtUpdatedBy().setValue(dto.getUpdatedBy()== null ? "" : dto.getUpdatedBy());
//            ui.getPdCreatedTime().setValue(dto.getCreatedTime());
//            ui.getPdUpdatedTime().setValue(dto.getUpdatedTime());

            List<MFlowProcedure> flowProcedures = FlowProcedureService.getInstance().listFlowProcedure(null, String.valueOf(dto.getId()));
            if (flowProcedures != null && !flowProcedures.isEmpty()) {
                flowProcedures.stream().forEach((flowProcedure) -> {
                    ui.getCfFlow().addFlow(FlowMngtService.getInstance().getCFlowById(String.valueOf(flowProcedure.getFlowId())));
                });
            }

            List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(String.valueOf(dto.getId()));
            if (listProcedureDocuments != null) {
                for (MProcedureDocument proDoc : listProcedureDocuments) {
                    ui.getUploadFile().addFileAttack(proDoc.getDocument().getUrl(), proDoc.getDocument().getFileName());
                }
            }

            ComponentUtils.fillDataObjectCombo(ui.getCmbType(), "-1", dto.getType(), lstProcedureType,
                    CatItemDTO.class, "itemId", "itemName");
        }

    }

    public void createDialog(boolean isInsert, CProcedure dto) {
        ProcedureMngtUI ui = new ProcedureMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 4 / 5;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(90.0f, Sizeable.Unit.PERCENTAGE);
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
                                            res = ProcedureMngtService.getInstance().addProcedure(dto);
                                            if (Constants.SUCCESS.equals(res.getKey())) {
                                                dto.setId(Integer.valueOf(res.getId()));
                                                MFlowProcedure flowProcedure = new MFlowProcedure();
                                                flowProcedure.setFlowId(ui.getCfFlow().getcFlows().get(0).getFlowId());
                                                flowProcedure.setProcedureId(Integer.valueOf(res.getId()));
                                                FlowProcedureService.getInstance().addFlowProcedure(flowProcedure);

                                                List<String> urls = ui.getUploadFile().getUrl();
                                                String path = ui.getUploadFile().getPath();
                                                for (String url : urls) {
                                                    DocumentDTO documentDTO = new DocumentDTO();
                                                    documentDTO.setCreatedBy(user.getUserName());
                                                    documentDTO.setFileCode(url);
                                                    documentDTO.setFileName(url);
                                                    documentDTO.setFileType(Constants.DOCUMENT_TYPE.PROCEDURE);
                                                    documentDTO.setSecurityLevel(BundleUtils.getString("documentMngt.list.security.high"));
                                                    documentDTO.setStatus(Constants.DOCUMENT_STATUS.ACTIVE);
//                                                    documentDTO.setUrl(path + File.separator + url);
                                                    documentDTO.setUrl(path);
                                                    ResultDTO resAddDoc = DocumentMngService.getInstance().addDocument(documentDTO);
                                                    if (Constants.SUCCESS.equals(resAddDoc.getKey())) {
                                                        documentDTO.setId(Integer.valueOf(resAddDoc.getId()));

                                                        MProcedureDocument proDoc = new MProcedureDocument();
                                                        proDoc.setCreated(new Date());
                                                        proDoc.setCreatedBy(user.getUserName());
                                                        proDoc.setDescription(dto.getName());
                                                        proDoc.setIsActive(Constants.ACTIVE);
                                                        proDoc.setUpdated(new Date());
                                                        proDoc.setUpdatedBy(user.getUserName());
                                                        proDoc.setDocument(documentDTO);
                                                        proDoc.setProcedure(dto);
                                                        ProcedureDocumentService.getInstance().addProcedureDocument(proDoc);
                                                    }
                                                }
                                                ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                            } else {
                                                ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                            }
                                        } else {
                                            res = ProcedureMngtService.getInstance().updateProcedure(dto);

                                            FlowProcedureService.getInstance().removeProcedureByProcedureId(String.valueOf(dto.getId()));
                                            MFlowProcedure flowProcedure = new MFlowProcedure();
                                            flowProcedure.setFlowId(ui.getCfFlow().getcFlows().get(0).getFlowId());
                                            flowProcedure.setProcedureId(dto.getId());
                                            FlowProcedureService.getInstance().addFlowProcedure(flowProcedure);

                                            ProcedureDocumentService.getInstance().removeDocumentByProcedureId(String.valueOf(dto.getId()));
                                            List<String> urls = ui.getUploadFile().getUrl();
                                            String path = ui.getUploadFile().getPath();
                                            for (String url : urls) {
                                                DocumentDTO documentDTO = new DocumentDTO();
                                                documentDTO.setCreatedBy(user.getUserName());
                                                documentDTO.setFileCode(url);
                                                documentDTO.setFileName(url);
                                                documentDTO.setFileType(Constants.DOCUMENT_TYPE.PROCEDURE);
                                                documentDTO.setSecurityLevel(BundleUtils.getString("documentMngt.list.security.high"));
                                                documentDTO.setStatus(Constants.DOCUMENT_STATUS.ACTIVE);
//                                                documentDTO.setUrl(path + File.separator + url);
                                                documentDTO.setUrl(path);
                                                ResultDTO resAddDoc = DocumentMngService.getInstance().addDocument(documentDTO);
                                                if (Constants.SUCCESS.equals(resAddDoc.getKey())) {
                                                    documentDTO.setId(Integer.valueOf(resAddDoc.getId()));

                                                    MProcedureDocument proDoc = new MProcedureDocument();
                                                    proDoc.setCreated(new Date());
                                                    proDoc.setCreatedBy(user.getUserName());
                                                    proDoc.setDescription(dto.getName());
                                                    proDoc.setIsActive(Constants.ACTIVE);
                                                    proDoc.setUpdated(new Date());
                                                    proDoc.setUpdatedBy(user.getUserName());
                                                    proDoc.setDocument(documentDTO);
                                                    proDoc.setProcedure(dto);
                                                    ProcedureDocumentService.getInstance().addProcedureDocument(proDoc);
                                                }
                                            }
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // Procedure did not confirm
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

    private void getDataFromUI(ProcedureMngtUI ui, CProcedure dto) {
        dto.setCode(ui.getTxtCode().getValue().trim());
        dto.setName(ui.getTxtName().getValue().trim());
        dto.setDescription(ui.getTxaDescription().getValue().trim());
        dto.setCost(ui.getTxtCost().getValue() == null ? "" : ui.getTxtCost().getValue().trim());
        dto.setLevel(ui.getTxtLevel().getValue() == null ? "" : ui.getTxtLevel().getValue().trim());
        if (ui.getTrOrgName().getOrgSelected() != null) {
            dto.setOrgId(ui.getTrOrgName().getOrgSelected().getId());
        }
        dto.setContent(ui.getCkEditorTextField().getValue());
        dto.setCreated(new Date());
        dto.setCreatedBy(user.getUserName());
        dto.setProcessTime(ui.getTxtProcessTime().getValue() == null ? "" : ui.getTxtProcessTime().getValue().trim());
        dto.setUpdated(new Date());

        CatItemDTO type = (CatItemDTO) ui.getCmbType().getValue();
        if (type != null && !DataUtil.isStringNullOrEmpty(type.getItemId()) && !Constants.DEFAULT_VALUE.equals(type.getItemId())) {
            dto.setType(type.getItemId());
        }
//        dto.setTimeExecute(ui.getTxtTimeExecute().getValue().trim());
//        dto.setPosition(ui.getTxtPosition().getValue().trim());
//        dto.setCreatedBy(ui.getTxtCreatedBy().getValue().trim());
//        dto.setUpdatedBy(ui.getTxtUpdatedBy().getValue().trim());
//        dto.setUpdatedTime(ui.getPdUpdatedTime().getValue());
//        dto.setCreatedTime(ui.getPdCreatedTime().getValue());
    }
}
