/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CFlow;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.CStep;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.MFlowProcedure;
import com.iso.dashboard.dto.MFlowStep;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.frontEnd.ui.ProcedureFlowUI;
import com.iso.dashboard.service.FlowMngtService;
import com.iso.dashboard.service.FlowProcedureService;
import com.iso.dashboard.service.FlowStepService;
import com.iso.dashboard.service.ProcedureMngtService;
import com.iso.dashboard.service.StepMngtService;
import com.iso.dashboard.ui.FlowGraphUI;
import com.iso.dashboard.ui.FlowMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.FlowMngtView;
import com.iso.dashboard.view.FlowProcedureView;
import com.iso.dashboard.view.FlowStepView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowMngtController {

    FlowMngtView view;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Stepname", "Email", "Phone", ""};
    String prefix = "flowMngt.list";//tien to trong file language
    String headerKey = "header.flowMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String stepListLabel = "flowMngt.list";
    Resource resource;

    public FlowMngtController(FlowMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(FlowMngtService.getInstance().listCFlows(null));
        doAction();
    }

    public void initTable(List<CFlow> lstCStep) {
        IndexedContainer container = createContainer(lstCStep);
        pagedTable.genGridCustomButton1(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
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
                            CFlow step = (CFlow) obj;
                            ResultDTO res = FlowMngtService.getInstance().removeCFlow(String.valueOf(step.getFlowId()));
                            ComponentUtils.showNotification("Delete id : " + String.valueOf(step.getFlowId()) + " " + res.getKey() + " " + res.getMessage());
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
                onUpdate((CFlow) obj);
            }
        });
    }

    public IndexedContainer createContainer(List<CFlow> lstStep) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("processDay", String.class, null);
        container.addContainerProperty("description", String.class, null);
        container.addContainerProperty("status", String.class, null);
        for (CFlow u : lstStep) {
            Item item = container.addItem(u);
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("processDay").setValue(u.getProcessDays() + " ngày");
            item.getItemProperty("description").setValue(u.getDescription());
            item.getItemProperty("status").setValue(u.getStatus() != null && u.getStatus() == 1 ? "Hiệu lực" : "Không có hiệu lực");
        }
        return container;
    }

    public void reloadData(List<CFlow> lstCStep) {
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
    }

    public boolean validateData(FlowMngtUI ui) {
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
        createDialog(true, new CFlow());
    }

    private void onUpdate(CFlow dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<CFlow> lstStep = FlowMngtService.getInstance().listCFlows(view.getTxtName().getValue());
        reloadData(lstStep);
    }

    private void initDataDialog(FlowMngtUI ui, boolean isInsert, CFlow dto) {

        List<CatItemDTO> lstSex = new ArrayList<>();
        lstSex.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("common.deactive")));
        lstSex.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("common.active")));
        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbStatus(), Constants.EMPTY_CHARACTER, lstSex);
            setEditable(ui, true);
        } else {
            ui.getTxtName().setValue(dto.getName() == null ? "" : dto.getName());
            ui.getTxtCode().setValue(dto.getCode() == null ? "" : dto.getCode());
            ui.getTxaDescription().setValue(dto.getDescription() == null ? "" : dto.getDescription());
            ComponentUtils.fillDataCombo(ui.getCmbStatus(), Constants.EMPTY_CHARACTER, String.valueOf(dto.getStatus()), lstSex);
            List<MFlowProcedure> flowProcedures = FlowProcedureService.getInstance().listFlowProcedure(String.valueOf(dto.getFlowId()), null);
            if (flowProcedures != null && !flowProcedures.isEmpty()) {
                flowProcedures.stream().forEach((flowProcedure) -> {
                    ui.getWrapper().addProcedure(ProcedureMngtService.getInstance().getProcedureById(String.valueOf(flowProcedure.getProcedureId())));
                });
            }
            setEditable(ui, false);
        }

    }

    public void createDialog(boolean isInsert, CFlow dto) {
        FlowMngtUI ui = new FlowMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 4 / 5;
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
                                List<MFlowProcedure> flowProcedures = new ArrayList<>();
                                getDataFromUI(ui, dto, flowProcedures);
                                if (isInsert) {
                                    res = FlowMngtService.getInstance().addCFlow(dto);
                                    final int flowId = Integer.valueOf(res.getId());
                                    if (flowProcedures != null && !flowProcedures.isEmpty()) {
                                        flowProcedures.stream().forEach((flowProcedure) -> {
                                            flowProcedure.setFlowId(flowId);
                                            FlowProcedureService.getInstance().addFlowProcedure(flowProcedure);
                                        });
                                    }
                                    ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                } else {
                                    res = FlowMngtService.getInstance().updateCFlow(dto);
                                    FlowProcedureService.getInstance().removeProcedureByFlowId(String.valueOf(dto.getFlowId()));
                                    if (flowProcedures != null && !flowProcedures.isEmpty()) {
                                        flowProcedures.stream().forEach((flowProcedure) -> {
                                            FlowProcedureService.getInstance().addFlowProcedure(flowProcedure);
                                        });
                                    }
                                    ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                            + res.getKey() + " " + res.getMessage());
                                }
//                                window.close();
                                setEditable(ui, Boolean.FALSE);
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

        ui.getBtnEdit().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setEditable(ui, Boolean.TRUE);
            }
        });

        ui.getBtnStep().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                FlowStepView flowStepView = new FlowStepView(dto.getFlowId());
                Window window = new Window("", flowStepView);
                float height = UI.getCurrent().getWidth() * 3 / 4;
                window.setWidth(String.valueOf(height) + "%");
                flowStepView.setWidth("100%");
                flowStepView.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
                window.setModal(true);
                DataUtil.reloadWindow(window);
                UI.getCurrent().addWindow(window);
            }
        });

        ui.getBtnGraph().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
//                List<MFlowStep> flowSteps = FlowStepService.getInstance().listFlowProcedure(String.valueOf(dto.getFlowId()), null);
//                List<CStep> cSteps = new ArrayList<>();
//                flowSteps.stream().map((flowStep) -> {
//                    CStep cStep = StepMngtService.getInstance().getCFlowById(String.valueOf(flowStep.getStepId()));
//                    cStep.setPosition(String.valueOf(flowStep.getStepIndex()));
//                    cStep.setBanchId(flowStep.getStepBranch());
//                    return cStep;
//                }).forEach((cStep) -> {
//                    cSteps.add(cStep);
//                });
//                Collections.sort(cSteps, (CStep o1, CStep o2) -> o1.getPosition().compareTo(o2.getPosition()));
                float width = UI.getCurrent().getWidth() * 4 / 5;
                float height = UI.getCurrent().getHeight() * 3 / 4;
//                FlowGraphUI asignMngtView = new FlowGraphUI(cSteps, UI.getCurrent().getPage().getBrowserWindowWidth() / 2, UI.getCurrent().getPage().getBrowserWindowHeight() / 2);
//                asignMngtView.setWidth("100%");
//                asignMngtView.setHeight("100%");
//                
//                Window window = new Window("", asignMngtView);
                Window window = new Window("", new ProcedureFlowUI(String.valueOf(dto.getFlowId()), null));
                window.setWidth(width + "%");
                window.setHeight(height + "%");
                window.setModal(true);
                DataUtil.reloadWindow(window);
                UI.getCurrent().addWindow(window);
            }
        });

        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
        ui.getTxtCode().focus();
    }

    private void getDataFromUI(FlowMngtUI ui, CFlow dto, List<MFlowProcedure> flowProcedures) {
        dto.setCode(ui.getTxtCode().getValue().trim());
        dto.setName(ui.getTxtName().getValue().trim());
        dto.setDescription(ui.getTxaDescription().getValue().trim());
        dto.setStatus(Integer.valueOf(((CatItemDTO) ui.getCmbStatus().getValue()).getItemId()));
        List<CProcedure> cProcedures = ui.getWrapper().getcProcedures();
        if (cProcedures != null && !cProcedures.isEmpty()) {
            cProcedures.stream().map((cProcedure) -> {
                MFlowProcedure flowProcedure = new MFlowProcedure();
                flowProcedure.setFlowId(dto.getFlowId());
                flowProcedure.setProcedureId(cProcedure.getId());
                return flowProcedure;
            }).forEach((flowProcedure) -> {
                flowProcedures.add(flowProcedure);
            });
        }
    }

    private void setEditable(FlowMngtUI ui, Boolean editable) {
        ui.getTxtName().setEnabled(editable);
        ui.getTxtCode().setEnabled(editable);
        ui.getTxaDescription().setEnabled(editable);
        ui.getCmbStatus().setEnabled(editable);
        ui.getWrapper().setEnableToEdit(editable);
        ui.setEditFlow(editable);
    }
}
