/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.dto.PProcess;
import com.iso.dashboard.dto.PProcessFile;
import com.iso.dashboard.dto.PProcessHis;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.service.ProcessFileService;
import com.iso.dashboard.service.ProcessHisService;
import com.iso.dashboard.service.ProcessService;
import com.iso.dashboard.ui.PersonalSendFileMngtUI;
import com.iso.dashboard.ui.ProcedureMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.DateUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.UploaderCustom;
import com.iso.dashboard.view.PersonalSendFileView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalSendFileController {

    PersonalSendFileView view;
    ProcedureMngtService service;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Procedurename", "Email", "Phone", ""};
    String prefix = "procedureMngt.public.list";//tien to trong file language
    String headerKey = "header.personalSendFileMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String procedureListLabel = "procedureMngt.list";
    Resource resource;

    Employee user = (Employee) VaadinSession.getCurrent()
            .getAttribute(Employee.class.getName());

    public PersonalSendFileController(PersonalSendFileView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(ProcedureMngtService.getInstance().listProcedures(null));
        doAction();
    }

    public void initTable(List<CProcedure> lstCProcedure) {
        IndexedContainer container = createContainer(lstCProcedure);
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
        pagedTable.getColumn("level").setMaximumWidth(250);
        pagedTable.getColumn("level").setMinimumWidth(150);
//        pagedTable.getColumn("view").setWidth(120);
//        pagedTable.getColumn("view").setHeaderCaption(BundleUtils.getString("common.button.info"));

        RendererClickListener nameClickListener = new RendererClickListener() {

            @Override
            public void click(RendererClickEvent event) {
                createViewDialog((CProcedure) event.getItemId());
            }
        };
        pagedTable.getColumn("name").setRenderer(new ButtonRenderer(nameClickListener));

        RendererClickListener sendFileClickListener = new RendererClickListener() {

            @Override
            public void click(RendererClickEvent event) {
                createViewDialog((CProcedure) event.getItemId());
            }
        };
        pagedTable.getColumn("sendFile").setRenderer(new ButtonRenderer(sendFileClickListener));
    }

    public IndexedContainer createContainer(List<CProcedure> lstProcedure) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("level", String.class, null);
        container.addContainerProperty("orgName", String.class, null);
        container.addContainerProperty("sendFile", String.class, null);
        for (CProcedure u : lstProcedure) {
            Item item = container.addItem(u);
            item.getItemProperty("name").setValue(u.getName());
            Organization org = OrganizationMngService.getInstance().getOrganizationById(String.valueOf(u.getOrgId()));
            u.setOrgName(org == null ? "" : org.getName());
            item.getItemProperty("orgName").setValue(org == null ? "" : org.getName());
            item.getItemProperty("level").setValue(u.getLevel());
            item.getItemProperty("sendFile").setValue(BundleUtils.getString("procedureMngt.public.list.sendFile"));
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
        List<CProcedure> lstProcedure = ProcedureMngtService.getInstance().listProcedures(view.getTxtName().getValue());
        reloadData(lstProcedure);
    }

    public void createViewDialog(CProcedure dto) {
        PersonalSendFileMngtUI ui = new PersonalSendFileMngtUI(
                BundleUtils.getString("procedureMngt.public.list.sendFile"), dto, true);
        ui.getTxtName().setValue(dto.getName());
        ui.getTxtLevel().setValue(dto.getLevel());
        ui.getTxtCost().setValue(dto.getCost());
        ui.getTxtOrgName().setValue(dto.getOrgName());
        ui.getTxtProcessTime().setValue(dto.getProcessTime());

        ui.getTxtName().setReadOnly(true);
        ui.getTxtLevel().setReadOnly(true);
        ui.getTxtCost().setReadOnly(true);
        ui.getTxtOrgName().setReadOnly(true);
        ui.getTxtProcessTime().setReadOnly(true);

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
        ui.getBtnUploadFile().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                for (Map.Entry<MProcedureDocument, UploaderCustom> entry : ui.getMapDocFile().entrySet()) {
                    UploaderCustom upload = entry.getValue();
                    if (upload.getUploader().getUploadedFiles().length == 0) {
                        Notification.show("Chua du file");
                        ui.getController().setFileTable(new Table());
                        ui.getController().initTable();
                        return;
                    }
                }

                PProcess processDto = new PProcess();
                createProcessDTO(ui, processDto);
                ResultDTO res = ProcessService.getInstance().addProcess(processDto);
                if (res != null && Constants.SUCCESS.equals(res.getKey())) {
                    dto.setProcessDto(processDto);
                    String processId = res.getId();

                    for (Map.Entry<MProcedureDocument, UploaderCustom> entry : ui.getMapDocFile().entrySet()) {
                        MProcedureDocument procedureDoc = entry.getKey();
                        UploaderCustom upload = entry.getValue();

                        PProcessFile pFile = new PProcessFile();
                        pFile.setCreatedBy(user.getUserName());
                        pFile.setCreatedTime(new Date());
                        // ui.getUploadImport().getPath() + lst.get(lst.size() - 1);
                        pFile.setFileName(upload.getUploader().getUploadedFiles()[0].getName());
                        pFile.setFilePath(((File) upload.getUploader().getUploadedFiles()[0].getUploadedFile()).getPath());

                        pFile.setProcedureDocumentId(procedureDoc.getId());
                        pFile.setProcessId(Integer.valueOf(processId));
                        ProcessFileService.getInstance().addProcessFile(pFile);
                    }
                    Notification.show(BundleUtils.getString("process.action.1.uploadFile") + " "
                            + BundleUtils.getString("common.success"));

                    PProcessHis his = createProcessHisDTO(processDto);
                    his.setActionName(BundleUtils.getString("process.action.1.uploadFile"));
                    his.setDescription(BundleUtils.getString("approve.list.result.yes"));
                    his.setResult(BundleUtils.getString("SUCCESS"));
                    ProcessHisService.getInstance().addProcessHis(his);

                    ui.getBtnUploadFile().setVisible(false);
                    ui.getBtnSendFile().setVisible(true);
                } else {
                    Notification.show(BundleUtils.getString("process.action.1.uploadFile") + " "
                            + BundleUtils.getString("common.error"));;
                }
            }
        });
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });
        ui.getBtnSendFile().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                PProcess processDto = dto.getProcessDto();
                if (processDto != null) {
                    processDto.setState(Constants.PROCESS_STATE_ID.SUBMIT_FILE);
                    processDto.setStateName(BundleUtils.getString("process.action.2.sendFile"));
                    processDto.setDescription(ui.getTxaDescription().getValue());

                    ResultDTO res = ProcessService.getInstance().updateProcess(processDto);
                    PProcessHis his = createProcessHisDTO(processDto);
                    his.setActionName(BundleUtils.getString("process.action.2.sendFile"));
                    if (res != null && Constants.SUCCESS.equals(res.getKey())) {
                        his.setResult(Constants.SUCCESS);
                        his.setDescription(Constants.SUCCESS);
                        ProcessHisService.getInstance().addProcessHis(his);

                        Notification.show(BundleUtils.getString("process.action.2.sendFile") + " "
                                + BundleUtils.getString("common.success"));

                        window.close();
                    } else {
                        his.setResult(Constants.FAIL);
                        his.setDescription(res != null ? res.getMessage() : Constants.FAIL);
                        Notification.show(BundleUtils.getString("process.action.2.sendFile") + " "
                                + BundleUtils.getString("common.error"));;
                    }
                }
            }
        });
        ui.getBtnSendFile().setVisible(false);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public void createProcessDTO(PersonalSendFileMngtUI ui, PProcess processDto) {
        String createBy = user.getUserName() == null ? "admin" : user.getUserName();
        processDto.setCode(ui.getProcedureDTO().getCode() + "_"
                + DateUtil.dateTime2StringNoSlash(new Date()) + "_"
                + createBy.replace(" ", "")
        );
        processDto.setCreateBy(createBy);
        processDto.setCreatedTime(new Date());
        processDto.setLevel(ui.getProcedureDTO().getLevel());
        processDto.setName(user.getUserName() == null ? "admin" : user.getUserName());
        processDto.setProcedureId(ui.getProcedureDTO().getId());
        processDto.setProcedureName(ui.getProcedureDTO().getName());
        processDto.setProgress(Constants.PROCESS_STATE_ID.DRAFT_STR);// "0");
        processDto.setPrice(ui.getProcedureDTO().getCost());

        processDto.setDescription(ui.getTxaDescription().getValue());
        processDto.setState(Constants.PROCESS_STATE_ID.UPLOAD_FILE);
        processDto.setStateName(BundleUtils.getString("process.action.1.uploadFile"));
    }

    public PProcessHis createProcessHisDTO(PProcess processDto) {
        PProcessHis his = new PProcessHis();
        his.setpProcessId(processDto.getId());
        his.setProId(processDto.getProcedureId());
        his.setCreateBy(user.getUserName() == null ? "admin" : user.getUserName());
        his.setCreatedTime(new Date());
        his.setProcessStateId(processDto.getState());
        his.setProcessStateName(processDto.getStateName());
        return his;
    }
}
