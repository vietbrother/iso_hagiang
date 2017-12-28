/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dao.SecurityLevelDAO;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.SecurityLevel;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.SecurityLevelMngService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.SecurityLevelMngtUI;
import com.iso.dashboard.ui.UserMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.SecurityLevelMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author 
 */
public class SecurityLevelMngtController {

    SecurityLevelMngtView view;
    SecurityLevelMngService service;

    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "securityLevelMngt.list";//tien to trong file language
    String headerKey = "header.securityLevelMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String userListLabel = "userMngt.list";
    Resource resource;

    public SecurityLevelMngtController(SecurityLevelMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(SecurityLevelMngService.getInstance().listUsers(null));
        doAction();
    }

    public void initTable(List<SecurityLevel> lstUsers) {
//        pagedTable.addGeneratedColumn("stt", new Table.ColumnGenerator() {
//
//            @Override
//            public Object generateCell(Table source, Object itemId, Object columnId) {
//                List lstObj = (List) source.getItemIds();
//                int i = lstObj.indexOf(itemId);
//                return i + 1;
//            }
//        });
        pagedTable.addGeneratedColumn("btnAction", new Table.ColumnGenerator() {
//            private static final long serialVersionUID = -5042109683675242407L;

            public Component generateCell(Table source, Object itemId, Object columnId) {
                Item item = source.getItem(itemId);

                Button btnEdit = new Button();
                btnEdit.setIcon(FontAwesome.EDIT);
                btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnEdit.setDescription(BundleUtils.getString("common.button.edit"));
                btnEdit.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String userId = (String) item.getItemProperty("id").getValue();
                        Notification.show("Edit " + userId);
                        Employee dto = SecurityLevelMngService.getInstance().getUserById(userId);
                        onUpdate(dto);
                        view.getBtnSearch().click();
                    }
                });
                Button btnDelete = new Button();
                btnDelete.setIcon(ISOIcons.DELETE);
                btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
                btnDelete.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
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
                                            String userId = (String) item.getItemProperty("id").getValue();
                                            ResultDTO res = SecurityLevelMngService.getInstance().removeUser(userId);
                                            ComponentUtils.showNotification("Delete id : " + userId + " " + res.getKey() + " " + res.getMessage());
                                            view.getBtnSearch().click();
                                        }
                                    }
                                });
                        d.setStyleName(Reindeer.WINDOW_LIGHT);
                        d.setContentMode(ConfirmDialog.ContentMode.HTML);
                        d.getOkButton().setIcon(ISOIcons.SAVE);
                        d.getCancelButton().setIcon(ISOIcons.CANCEL);
                    }
                });

                HorizontalLayout hori = new HorizontalLayout();
                hori.addComponent(btnEdit);
                hori.addComponent(btnDelete);
                return hori;
            }
        });
        reloadData(lstUsers);
        pagedTable.setSizeFull();
        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
//        pagedTable.setWidth("1000px");
        pagedTable.setPageLength(10);
        pagedTable.setImmediate(true);
        pagedTable.setSelectable(true);
        pagedTable.setAlwaysRecalculateColumnWidths(true);
        pagedTable.setResponsive(true);
        pagedTable.setColumnHeaders(headerName);
    }

    public void reloadData(List<SecurityLevel> lstUsers) {
        pagedTable.setContainerDataSource(createContainer(lstUsers));
    }

    public IndexedContainer createContainer(List<SecurityLevel> lstUser) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("color", String.class, null);
        container.addContainerProperty("description", String.class, null);
        for (SecurityLevel u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("id").setValue(String.valueOf(u.getId()));
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("color").setValue(u.getColor());
            item.getItemProperty("description").setValue(u.getDescription());
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
        
//        view.getBtnExport().addClickListener(new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                onExport();
//            }
//        });
    }

    public boolean validateData(UserMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtUsername().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.username") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtUsername().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("userMngt.list.username") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtFullname().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.fullname") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtFullname().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("userMngt.list.username") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtMobile().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.mobile") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtMobile().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("userMngt.list.mobile") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtRole().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.role") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtRole().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("userMngt.list.role") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getTxtEmail().getValue())) {
            Notification.show(BundleUtils.getString("userMngt.list.email") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtEmail().getValue().length() > 20) {
            Notification.show(BundleUtils.getString("userMngt.list.email") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }

        if (ui.getPdBirthday().getValue() != null && ui.getPdBirthday().getValue().getTime() - new Date().getTime() > 0) {
            Notification.show(BundleUtils.getString("userMngt.list.birthday") + Constants.SPACE_CHARACTER
                    + BundleUtils.getString("common.less")
                    + Constants.SPACE_CHARACTER + BundleUtils.getString("common.now"));
            return false;
        }
        return true;
    }

    private void onInsert() {
        createDialog(true, new Employee());
    }

    private void onUpdate(Employee dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<SecurityLevel> lstUser = SecurityLevelDAO.getInstance().listUsers(view.getTxtUserName().getValue());
        Notification.show("lstUser : " + lstUser.size());
        reloadData(lstUser);
    }


    private void initDataDialog(UserMngtUI ui, boolean isInsert, Employee dto) {

        List<CatItemDTO> lstSex = new ArrayList<>();
        lstSex.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("userMngt.list.sex.male")));
        lstSex.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("userMngt.list.sex.female")));

        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbSex(), Constants.EMPTY_CHARACTER, lstSex);
        } else {
            ui.getTxtUsername().setValue(dto.getUserName() == null ? "" : dto.getUserName());
            ui.getTxtEmail().setValue(dto.getEmail() == null ? "" : dto.getEmail());
            ui.getTxtFullname().setValue(dto.getLastName() == null ? "" : dto.getLastName());
            ui.getTxtMobile().setValue(dto.getMobile()== null ? "" : dto.getMobile());
            ui.getTxtRole().setValue(dto.getRole() == null ? "" : dto.getRole());
            ui.getPdBirthday().setValue(dto.getBirthday());
            ComponentUtils.fillDataCombo(ui.getCmbSex(), Constants.EMPTY_CHARACTER, String.valueOf(dto.isMale()), lstSex);
        }

    }

    public void createDialog(boolean isInsert, Employee dto) {
        UserMngtUI ui = new UserMngtUI("abc");
        Window window = new Window(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"), 
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 3 / 4;
        window.setWidth(String.valueOf(height) + "%");
        window.setIcon(VaadinIcons.CALENDAR_USER);
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
                                            //res = UserMngService.getInstance().addUser(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            //res = UserMngService.getInstance().updateUser(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // Employee did not confirm
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
    }

    private void getDataFromUI(UserMngtUI ui, Employee dto) {
        dto.setRole(ui.getTxtRole().getValue().trim());
        dto.setEmail(ui.getTxtEmail().getValue().trim());
        dto.setFirstName(ui.getTxtFullname().getValue().trim());
        dto.setLastName(ui.getTxtFullname().getValue().trim());
        dto.setUserName(ui.getTxtUsername().getValue().trim());
        dto.setMobile(ui.getTxtMobile().getValue().trim());
        dto.setBirthday(ui.getPdBirthday().getValue());
        CatItemDTO sex = (CatItemDTO) ui.getCmbSex().getValue();
        if (sex != null && !DataUtil.isStringNullOrEmpty(sex.getItemId()) && !Constants.DEFAULT_VALUE.equals(sex.getItemId())) {
            dto.setMale(Boolean.getBoolean(sex.getItemId()));
        }
    }
}
