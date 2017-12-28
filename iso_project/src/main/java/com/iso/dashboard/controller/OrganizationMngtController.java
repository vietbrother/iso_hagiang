/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.ui.OrganizationMngtUI;
import com.iso.dashboard.ui.UserMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.OrganizationMngtView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class OrganizationMngtController {

    public OrganizationMngtView view;
    public Tree orgTree;

    public List<Organization> lstDataTree = new ArrayList<>();
    public Map<Integer, Organization> mapIdWithOrgObj = new HashMap<>();
    public List<Organization> lstNodeSelected = new ArrayList<>();
    public Organization nodeSelected = null;

    public OrganizationMngtController(OrganizationMngtView view) {
        this.view = view;
        this.orgTree = view.getOrgTree();
        initTree();
        doAction();
    }

    public BeanItemContainer<Organization> reloadData() {
        BeanItemContainer<Organization> container = new BeanItemContainer<>(
                Organization.class);
        getLstDataTree();
        container.addAll(lstDataTree);
        return container;
    }

    public void reloadTree() {
        orgTree.removeAllItems();
        initTree();
//        orgTree.setContainerDataSource(reloadData());
    }

    public void getLstDataTree() {
        nodeSelected = null;
        lstDataTree = new ArrayList<>();
        mapIdWithOrgObj = new HashMap<>();

        lstDataTree = OrganizationMngService.getInstance().listOrganization(null, null);
        if (lstDataTree != null && !lstDataTree.isEmpty()) {
            for (Organization org : lstDataTree) {
                mapIdWithOrgObj.put(org.getId(), org);
            }
        }
    }

    public void initTree() {
        BeanItemContainer<Organization> container = reloadData();
        orgTree.setContainerDataSource(container);
        for (Object itemId : orgTree.getItemIds()) {
            //Here the icon can be set to anything. Just an example.
            Organization temp = (Organization) itemId;
            if (temp != null && temp.getParentId() == 0) {
                orgTree.setItemIcon(itemId, FontAwesome.BANK);
            } else {
                orgTree.setItemIcon(itemId, FontAwesome.FOLDER);
            }
            if (mapIdWithOrgObj.get(temp.getParentId()) != null) {
                orgTree.setParent(itemId, mapIdWithOrgObj.get(temp.getParentId()));
            }
        }
        orgTree.setItemCaptionPropertyId("name");
        orgTree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
        orgTree.addExpandListener(new Tree.ExpandListener() {

            @Override
            public void nodeExpand(Tree.ExpandEvent event) {
                Organization org = (Organization) event.getItemId();
                List<Organization> lstChild = OrganizationMngService.getInstance().listOrganization(null, String.valueOf(org.getId()));
                if (lstChild == null || lstChild.isEmpty()) {
                    orgTree.setChildrenAllowed(org, false);
                }
            }
        });
        orgTree.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Organization org = (Organization) event.getItemId();
                nodeSelected = org;
                view.getDetailTitle().setValue(org.getName());
                view.getDetailContent().setValue(org.getDescription());
            }
        });

    }

    private void doAction() {
        view.getBtnAdd().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (nodeSelected == null) {
                    Notification.show(BundleUtils.getString("orgMngt.validate.nodeParentIsEmpty"));
                    return;
                }
                createDialog(true, nodeSelected);
            }
        });
        view.getBtnUpdate().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (nodeSelected == null) {
                    Notification.show(BundleUtils.getString("orgMngt.validate.nodeIsEmpty"));
                    return;
                }
                createDialog(false, nodeSelected);
            }
        });
        view.getBtnDelete().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (nodeSelected == null) {
                    Notification.show(BundleUtils.getString("orgMngt.validate.nodeIsEmpty"));
                    return;
                }
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
                                    ResultDTO res = OrganizationMngService.getInstance().removeOrganization(String.valueOf(nodeSelected.getId()));
                                    ComponentUtils.showNotification("Delete id : " + nodeSelected.getId() + " " + res.getKey() + " " + res.getMessage());
                                    reloadTree();
                                }
                            }
                        });
                d.setStyleName(Reindeer.WINDOW_LIGHT);
                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                d.getOkButton().setIcon(ISOIcons.SAVE);
                d.getCancelButton().setIcon(ISOIcons.CANCEL);
            }
        });
    }

    private void initDataDialog(OrganizationMngtUI ui, boolean isInsert, Organization dto) {

        List<CatItemDTO> lstStatus = new ArrayList<>();
        lstStatus.add(new CatItemDTO(Constants.ACTIVE_STR, BundleUtils.getString("combo.deault.inactive")));
        lstStatus.add(new CatItemDTO(Constants.DEACTIVE_STR, BundleUtils.getString("combo.deault.active")));

        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbStatus(), Constants.EMPTY_CHARACTER, lstStatus);
        } else {
            ui.getTxtName().setValue(dto.getName() == null ? "" : dto.getName());
            ui.getTxtCode().setValue(dto.getCode() == null ? "" : dto.getCode());
            ui.getTxtValue().setValue(dto.getValue() == null ? "" : dto.getValue());
            ui.getTxtPosition().setValue(dto.getPosition() == null ? "" : dto.getPosition());
            ui.getTxaDescription().setValue(dto.getDescription() == null ? "" : dto.getDescription());
            ComponentUtils.fillDataCombo(ui.getCmbStatus(), Constants.EMPTY_CHARACTER, String.valueOf(dto.getStatus()), lstStatus);
        }

    }

    public void createDialog(boolean isInsert, Organization dto) {
        OrganizationMngtUI ui = new OrganizationMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window("",
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
                                            dto.setParentId(nodeSelected.getId());
                                            dto.setCreateTime(new Date());
                                            dto.setTimeUpdate(new Date());
                                            dto.setCreatedBy("admin");
                                            dto.setLevel(String.valueOf(Integer.valueOf(nodeSelected.getLevel()) + 1));
                                            res = OrganizationMngService.getInstance().addOrganization(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            dto.setTimeUpdate(new Date());
                                            res = OrganizationMngService.getInstance().updateOrganization(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        reloadTree();
                                    } else {
                                        // Organization did not confirm
                                        Notification.show("nok");
                                        window.close();
                                    }
                                }
                            });
                    d.setStyleName(Reindeer.LAYOUT_BLUE);
                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
                    d.getOkButton().setIcon(ISOIcons.SAVE);
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
        ui.getTxtName().focus();
    }

    private void getDataFromUI(OrganizationMngtUI ui, Organization dto) {
        dto.setCode(ui.getTxtCode().getValue().trim());
        dto.setName(ui.getTxtName().getValue().trim());
        dto.setValue(ui.getTxtValue().getValue().trim());
        dto.setPosition(ui.getTxtPosition().getValue().trim());
        dto.setDescription(ui.getTxaDescription().getValue().trim());
        CatItemDTO status = (CatItemDTO) ui.getCmbStatus().getValue();
        if (status != null && !DataUtil.isStringNullOrEmpty(status.getItemId()) && !Constants.DEFAULT_VALUE.equals(status.getItemId())) {
            dto.setStatus(status.getItemCode());
        }
    }

    public boolean validateData(OrganizationMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtName().getValue())) {
            Notification.show(BundleUtils.getString("orgMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtName().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("orgMngt.list.name") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtCode().getValue())) {
            Notification.show(BundleUtils.getString("orgMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtCode().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("orgMngt.list.code") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtValue().getValue())) {
            Notification.show(BundleUtils.getString("orgMngt.list.value") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtValue().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("orgMngt.list.value") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtPosition().getValue())) {
            Notification.show(BundleUtils.getString("orgMngt.list.position") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtPosition().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("orgMngt.list.position") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxaDescription().getValue())) {
            Notification.show(BundleUtils.getString("orgMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxaDescription().getValue().length() > 200) {
            Notification.show(BundleUtils.getString("orgMngt.list.description") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.200"));
            return false;
        }

        return true;
    }
}
