/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.dto.PProcess;
import com.iso.dashboard.dto.PProcessHis;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.service.ProcessHisService;
import com.iso.dashboard.service.ProcessService;
import com.iso.dashboard.ui.PersonalExecuteFileMngtUI;
import com.iso.dashboard.ui.ProcedureMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.view.PersonalFileInfoView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.HtmlRenderer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalFileInfoController {

    PersonalFileInfoView view;

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

    Employee user = (Employee) VaadinSession.getCurrent()
            .getAttribute(Employee.class.getName());

    public PersonalFileInfoController(PersonalFileInfoView view) {
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
        pagedTable.getColumn("view").setHeaderCaption(BundleUtils.getString("personal.fileInfo.list.view"));
//        pagedTable.getColumn("view").setWidth(120);
//        pagedTable.getColumn("view").setHeaderCaption(BundleUtils.getString("common.button.info"));

        RendererClickListener nameClickListener = new RendererClickListener() {

            @Override
            public void click(RendererClickEvent event) {
                createViewDialog((PProcess) event.getItemId());
            }
        };
        pagedTable.getColumn("procedureName").setRenderer(new ButtonRenderer(nameClickListener));

    }

    public IndexedContainer createContainer(List<PProcess> lstProcess) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("procedureName", String.class, null);
        container.addContainerProperty("createTime", String.class, null);
        container.addContainerProperty("level", String.class, null);
        for (PProcess u : lstProcess) {
            Item item = container.addItem(u);
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
        PersonalExecuteFileMngtUI ui = new PersonalExecuteFileMngtUI(
                BundleUtils.getString("procedureMngt.public.list.sendFile"), procedure, true);
        Organization org = OrganizationMngService.getInstance().getOrganizationById(String.valueOf(procedure.getOrgId()));
        ui.getTxtName().setValue(procedure.getName());
        ui.getTxtLevel().setValue(procedure.getLevel());
        ui.getTxtCost().setValue(procedure.getCost());
        ui.getTxtOrgName().setValue(org == null ? "" : org.getName());
        ui.getTxtProcessTime().setValue(procedure.getProcessTime());
        ui.getTxaDescription().setValue(dtoProcess.getDescription() == null ? "" : dtoProcess.getDescription());

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
        ui.getBtnExecute().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (dtoProcess != null) {
                    PProcessHis his = createProcessHisDTO(dtoProcess);

                    dtoProcess.setState(Constants.PROCESS_STATE_ID.EXECUTE);
                    dtoProcess.setStateName(BundleUtils.getString("process.action.3.execute"));
                    boolean result = (boolean) ui.getOgResult().getValue();
                    if (result) {
//                        his.setResult(BundleUtils.getString("SUCCESS"));
                        his.setDescription(BundleUtils.getString("approve.list.result.yes"));
                    } else {
//                        his.setResult(Constants.FAIL);
                        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
                            ComponentUtils.showNotification(BundleUtils.getString("procedureMngt.public.list.infoDetail")
                                    + " " + BundleUtils.getString("common.notnull"));
                            return;
                        }
                        his.setDescription(BundleUtils.getString("approve.list.result.no") + ": "
                                + ui.getTxaDescription().getValue()
                        );
                    }
                    ResultDTO res = ProcessService.getInstance().updateProcess(dtoProcess);
                    his.setActionName(BundleUtils.getString("process.action.3.execute"));
                    if (res != null && Constants.SUCCESS.equals(res.getKey())) {
                        //his.setDescription(Constants.SUCCESS);
                        his.setResult(BundleUtils.getString("SUCCESS"));
                        ProcessHisService.getInstance().addProcessHis(his);

                        ComponentUtils.showNotification(BundleUtils.getString("process.action.3.execute")
                                + " "
                                + BundleUtils.getString("common.success"));
                        window.close();
                    } else {
                        his.setResult(Constants.FAIL);
                        //ComponentUtils.showNotification(res != null ? res.getMessage() : Constants.FAIL);
                        ComponentUtils.showNotification(BundleUtils.getString("process.action.3.execute")
                                + " "
                                + BundleUtils.getString("common.error"));
                    }
                }
            }
        });
        ui.getBtnSubmitApprove().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (dtoProcess != null) {
                    PProcessHis his = createProcessHisDTO(dtoProcess);
                    dtoProcess.setState(Constants.PROCESS_STATE_ID.SUBMIT_APPROVE);
                    dtoProcess.setStateName(BundleUtils.getString("process.action.4.submitApprove"));
                    boolean result = (boolean) ui.getOgResult().getValue();
                    if (result) {
//                        his.setResult(Constants.SUCCESS);
                        his.setDescription(BundleUtils.getString("approve.list.result.yes"));
                    } else {
//                        his.setResult(Constants.FAIL);
                        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
                            ComponentUtils.showNotification(BundleUtils.getString("procedureMngt.public.list.infoDetail")
                                    + " " + BundleUtils.getString("common.notnull"));
                            return;
                        }
                        his.setDescription(BundleUtils.getString("approve.list.result.yes") + ": "
                                + ui.getTxaDescription().getValue()
                        );
                    }
                    ResultDTO res = ProcessService.getInstance().updateProcess(dtoProcess);

                    his.setActionName(BundleUtils.getString("process.action.4.submitApprove"));
                    if (res != null && Constants.SUCCESS.equals(res.getKey())) {
//                        his.setDescription(Constants.SUCCESS);
                        his.setResult(BundleUtils.getString("SUCCESS"));
                        ProcessHisService.getInstance().addProcessHis(his);

                        ComponentUtils.showNotification(BundleUtils.getString("process.action.4.submitApprove")
                                + " "
                                + BundleUtils.getString("common.success"));
                        window.close();
                    } else {
                        his.setResult(Constants.FAIL);
//                        ComponentUtils.showNotification(res != null ? res.getMessage() : Constants.FAIL);
                        ComponentUtils.showNotification(BundleUtils.getString("process.action.4.submitApprove")
                                + " "
                                + BundleUtils.getString("common.error"));
                    }
                }
            }
        });
        ui.getBtnApprove().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (dtoProcess != null) {
                    dtoProcess.setState(Constants.PROCESS_STATE_ID.APPROVE);
                    dtoProcess.setStateName(BundleUtils.getString("process.action.5.approve"));
                    PProcessHis his = createProcessHisDTO(dtoProcess);
                    boolean result = (boolean) ui.getOgResult().getValue();
                    if (result) {
//                        his.setResult(Constants.SUCCESS);
                        his.setDescription(BundleUtils.getString("approve.list.result.yes"));
                    } else {
//                        his.setResult(Constants.FAIL);
                        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
                            ComponentUtils.showNotification(BundleUtils.getString("procedureMngt.public.list.infoDetail")
                                    + " " + BundleUtils.getString("common.notnull"));
                            return;
                        }
                        his.setDescription(BundleUtils.getString("approve.list.result.yes") + ": "
                                + ui.getTxaDescription().getValue()
                        );
                    }
                    ResultDTO res = ProcessService.getInstance().updateProcess(dtoProcess);

                    his.setActionName(BundleUtils.getString("process.action.5.approve"));
                    if (res != null && Constants.SUCCESS.equals(res.getKey())) {
//                        his.setDescription(Constants.SUCCESS);
                        his.setResult(BundleUtils.getString("SUCCESS"));
                        ProcessHisService.getInstance().addProcessHis(his);

                        ComponentUtils.showNotification(BundleUtils.getString("process.action.5.approve")
                                + " "
                                + BundleUtils.getString("common.success"));
                        window.close();
                    } else {
                        his.setResult(Constants.FAIL);
//                        ComponentUtils.showNotification(res != null ? res.getMessage() : Constants.FAIL);
                        ComponentUtils.showNotification(BundleUtils.getString("process.action.5.approve")
                                + " "
                                + BundleUtils.getString("common.error"));
                    }
                }
            }
        });
        ui.getBtnRelease().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (dtoProcess != null) {
                    dtoProcess.setState(Constants.PROCESS_STATE_ID.RELEASE);
                    dtoProcess.setStateName(BundleUtils.getString("process.action.6.release"));
                    PProcessHis his = createProcessHisDTO(dtoProcess);
                    boolean result = (boolean) ui.getOgResult().getValue();
                    if (result) {
//                        his.setResult(Constants.SUCCESS);
                        his.setDescription(BundleUtils.getString("approve.list.result.yes"));
                    } else {
//                        his.setResult(Constants.FAIL);
                        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
                            ComponentUtils.showNotification(BundleUtils.getString("procedureMngt.public.list.infoDetail")
                                    + " " + BundleUtils.getString("common.notnull"));
                            return;
                        }
                        his.setDescription(BundleUtils.getString("approve.list.result.yes") + ": "
                                + ui.getTxaDescription().getValue()
                        );
                    }
                    ResultDTO res = ProcessService.getInstance().updateProcess(dtoProcess);
                    his.setActionName(BundleUtils.getString("process.action.6.release"));
                    if (res != null && Constants.SUCCESS.equals(res.getKey())) {
//                        his.setDescription(Constants.SUCCESS);
                        his.setResult(BundleUtils.getString("SUCCESS"));
                        ProcessHisService.getInstance().addProcessHis(his);

                        ComponentUtils.showNotification(BundleUtils.getString("process.action.6.release")
                                + " "
                                + BundleUtils.getString("common.success"));
                        window.close();
                    } else {
                        his.setResult(Constants.FAIL);
//                        ComponentUtils.showNotification(res != null ? res.getMessage() : Constants.FAIL);
                        ComponentUtils.showNotification(BundleUtils.getString("process.action.6.release")
                                + " "
                                + BundleUtils.getString("common.error"));
                    }
                }
            }
        });
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });

        if (dtoProcess.getState() != null) {
            if(dtoProcess.getState() == Constants.PROCESS_STATE_ID.SUBMIT_FILE){
                ui.getBtnSubmitApprove().setVisible(false);
                ui.getBtnApprove().setVisible(false);
                ui.getBtnRelease().setVisible(false);
            } else if(dtoProcess.getState() == Constants.PROCESS_STATE_ID.EXECUTE){
                ui.getBtnExecute().setVisible(false);
                ui.getBtnApprove().setVisible(false);
                ui.getBtnRelease().setVisible(false);
            } else if(dtoProcess.getState() == Constants.PROCESS_STATE_ID.SUBMIT_APPROVE){
                ui.getBtnExecute().setVisible(false);
                ui.getBtnSubmitApprove().setVisible(false);
                ui.getBtnRelease().setVisible(false);
            } else if(dtoProcess.getState() == Constants.PROCESS_STATE_ID.APPROVE){
                ui.getBtnExecute().setVisible(false);
                ui.getBtnSubmitApprove().setVisible(false);
                ui.getBtnApprove().setVisible(false);
            } else {
                ui.getBtnExecute().setVisible(false);
                ui.getBtnSubmitApprove().setVisible(false);
                ui.getBtnApprove().setVisible(false);
                ui.getBtnRelease().setVisible(false);
            }
        }

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public PProcessHis createProcessHisDTO(PProcess processDto) {
        PProcessHis his = new PProcessHis();
        his.setpProcessId(processDto.getId());
        his.setProId(processDto.getProcedureId());
        his.setCreateBy(DataUtil.isNullOrEmpty(user.getUserName()) ? "admin" : user.getUserName());
        his.setCreatedTime(new Date());
        his.setUpdateBy(DataUtil.isNullOrEmpty(user.getUserName()) ? "admin" : user.getUserName());
        his.setUpdatedTime(new Date());
        his.setProcessStateId(processDto.getState());
        his.setProcessStateName(processDto.getStateName());
        return his;
    }

}
