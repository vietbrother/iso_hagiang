/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.FileLevel;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.service.FileMngService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.FileMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.FileMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author
 */
public class FileMngtController {

    FileMngtView view;
    FileMngService service;

    CustomGrid pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "fileMngt.list";//tien to trong file language
    String headerKey = "header.fileMngt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String userListLabel = "userMngt.list";
    Resource resource;

    public FileMngtController(FileMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(FileMngService.getInstance().listUsers(null));
        doAction();
    }

    public void initTable(List<FileLevel> lstUsers) {
////        pagedTable.addGeneratedColumn("stt", new Table.ColumnGenerator() {
////
////            @Override
////            public Object generateCell(Table source, Object itemId, Object columnId) {
////                List lstObj = (List) source.getItemIds();
////                int i = lstObj.indexOf(itemId);
////                return i + 1;
////            }
////        });
//        pagedTable.addGeneratedColumn("btnAction", new Table.ColumnGenerator() {
////            private static final long serialVersionUID = -5042109683675242407L;
//
//            public Component generateCell(Table source, Object itemId, Object columnId) {
//                Item item = source.getItem(itemId);
//
//                Button btnEdit = new Button();
//                btnEdit.setIcon(FontAwesome.EDIT);
//                btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnEdit.setDescription(BundleUtils.getString("common.button.edit"));
//                btnEdit.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        String userId = (String) item.getItemProperty("id").getValue();
//                        Notification.show("Edit " + userId);
//                        FileLevel dto = FileMngService.getInstance().getUserById(userId);
//                        onUpdate(dto);
//                        view.getBtnSearch().click();
//                    }
//                });
//                Button btnDelete = new Button();
//                btnDelete.setIcon(ISOIcons.DELETE);
//                btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
//                btnDelete.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        ConfirmDialog d = ConfirmDialog.show(
//                                UI.getCurrent(),
//                                BundleUtils.getString("message.warning.title"),
//                                BundleUtils.getString("message.warning.content"),
//                                BundleUtils.getString("common.confirmDelete.yes"),
//                                BundleUtils.getString("common.confirmDelete.no"),
//                                new ConfirmDialog.Listener() {
//
//                                    public void onClose(ConfirmDialog dialog) {
//                                        if (dialog.isConfirmed()) {
//                                            // Confirmed to continue
//                                            String userId = (String) item.getItemProperty("id").getValue();
//                                            ResultDTO res = FileMngService.getInstance().removeUser(userId);
//                                            ComponentUtils.showNotification("Delete id : " + userId + " " + res.getKey() + " " + res.getMessage());
//                                            view.getBtnSearch().click();
//                                        }
//                                    }
//                                });
//                        d.setStyleName(Reindeer.WINDOW_LIGHT);
//                        d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                        d.getOkButton().setIcon(ISOIcons.SAVE);
//                        d.getCancelButton().setIcon(ISOIcons.CANCEL);
//                    }
//                });
//
//                HorizontalLayout hori = new HorizontalLayout();
//                hori.addComponent(btnEdit);
//                hori.addComponent(btnDelete);
//                return hori;
//            }
//        });
//        reloadData(lstUsers);
//        pagedTable.setSizeFull();
//        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
////        pagedTable.setWidth("1000px");
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
//        pagedTable.setSelectable(true);
//        pagedTable.setAlwaysRecalculateColumnWidths(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);
        IndexedContainer container = createContainer(lstUsers);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                FileLevel item = (FileLevel) obj;
                String userId = String.valueOf(item.getId());
                Notification.show("Edit " + userId);
                FileLevel dto = FileMngService.getInstance().getUserById(userId);
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
                                    FileLevel item = (FileLevel) obj;
                                    String userId = String.valueOf(item.getId());
                                    ResultDTO res = FileMngService.getInstance().removeUser(userId);
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

            @Override
            public void actionSelect(Object obj) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    public void reloadData(List<FileLevel> lstUsers) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstUsers)));
    }

    public IndexedContainer createContainer(List<FileLevel> lstUser) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("parttime", Integer.class, null);
        container.addContainerProperty("departmenttime", Integer.class, null);
        container.addContainerProperty("type", String.class, null);
        container.addContainerProperty("security", String.class, null);
//        container.addContainerProperty("createby", String.class, null);
        container.addContainerProperty("status", String.class, null);
        for (FileLevel u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("id").setValue(String.valueOf(u.getId()));
            item.getItemProperty("code").setValue(u.getFileCode());
            item.getItemProperty("name").setValue(u.getFileName());
            item.getItemProperty("parttime").setValue(u.getPartStorageTime());
            item.getItemProperty("departmenttime").setValue(u.getDepartmentStorageTime());
            item.getItemProperty("type").setValue(u.getFileType());
            item.getItemProperty("security").setValue(u.getSecurityLevel());
//            item.getItemProperty("createby").setValue(u.getCreatedBy());
            item.getItemProperty("status").setValue(u.getStatus());
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

    public boolean validateData(FileMngtUI ui) {
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

//        if (ui.getPdBirthday().getValue() != null && ui.getPdBirthday().getValue().getTime() - new Date().getTime() > 0) {
//            Notification.show(BundleUtils.getString("userMngt.list.birthday") + Constants.SPACE_CHARACTER
//                    + BundleUtils.getString("common.less")
//                    + Constants.SPACE_CHARACTER + BundleUtils.getString("common.now"));
//            return false;
//        }
        return true;
    }

    private void onInsert() {
        createDialog(true, new FileLevel());
    }

    private void onUpdate(FileLevel dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<FileLevel> lstUser = FileMngService.getInstance().listUsers(view.getTxtUserName().getValue());
        Notification.show("lstUser : " + lstUser.size());
        reloadData(lstUser);
    }

//    private void onExport() {
//
//        try {
//            List<Users> lstUser = UserMngService.getInstance().listUsers(view.getTxtUserName().getValue());
//            String[] header = new String[]{"username", "birthDay", "email"};
//            String[] align = new String[]{"LEFT", "LEFT", "LEFT"};
//            List<AbstractMap.SimpleEntry<String, String>> headerAlign = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
//            for (int i = 0; i < header.length; i++) {
//                headerAlign.add(new AbstractMap.SimpleEntry(header[i], align[i]));
//            }
//            String fileTemplate = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
//                    //+ File.separator + "WEB-INF"
//                    //+ File.separator + "templates"
//                    //+ File.separator + "incident"
//                    //+ File.separator + "TEMPLATE_EXPORT.xls"
//                    + Constants.FILE_CONF.PATH_EXPORT_TEMPLATE_XLSX;
//
//            String subTitle = Constants.EMPTY_CHARACTER;
//
//            File fileExport = CommonExport.exportFile(lstUser,//list du lieu
//                    headerAlign,//header
//                    //"userMngt.list",//header prefix
//                    userListLabel,//header prefix
//                    fileTemplate,//path template
//                    BundleUtils.getString("userMngt.fileName.export"),//fileName out
//                    7,//start row
//                    subTitle,//sub title
//                    4,//cell title Index
//                    BundleUtils.getString("userMngt.report")//title
//            );
//            resource = new FileResource(fileExport);
//
//            Page.getCurrent().open(resource, null, false);
//        } catch (Exception e) {
//        }
//    }
    private void initDataDialog(FileMngtUI ui, boolean isInsert, FileLevel dto) {

//        List<CatItemDTO> lstSex = new ArrayList<>();
//        lstSex.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("userMngt.list.sex.male")));
//        lstSex.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("userMngt.list.sex.female")));
        if (isInsert) {
//            ComponentUtils.fillDataComboNoDefault(ui.getCmbSex(), Constants.EMPTY_CHARACTER, lstSex);
        } else {
            ui.getTxtUsername().setValue(dto.getFileCode() == null ? "" : dto.getFileCode());
            ui.getTxtEmail().setValue(dto.getFileName() == null ? "" : dto.getFileName());
            ui.getTxtFullname().setValue(dto.getSecurityLevel() == null ? "" : dto.getSecurityLevel());
            ui.getTxtMobile().setValue(dto.getFileType() == null ? "" : dto.getFileType());
            ui.getTxtRole().setValue(dto.getStatus() == null ? "" : dto.getStatus());
            ui.getTxtparttime().setValue(dto.getPartStorageTime() == 0 ? "" : String.valueOf(dto.getPartStorageTime()));
            ui.getTxtdepartmenttime().setValue(dto.getDepartmentStorageTime() == 0 ? "" : String.valueOf(dto.getDepartmentStorageTime()));
//            ui.getPdBirthday().setValue(dto.getBirthDay());
//            ComponentUtils.fillDataCombo(ui.getCmbSex(), Constants.EMPTY_CHARACTER, String.valueOf(dto.isMale()), lstSex);
        }

    }

    public void createDialog(boolean isInsert, FileLevel dto) {
        FileMngtUI ui = new FileMngtUI();
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
                                            res = FileMngService.getInstance().addUser(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            res = FileMngService.getInstance().updateUser(dto);
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

    private void getDataFromUI(FileMngtUI ui, FileLevel dto) {
        dto.setFileCode(ui.getTxtUsername().getValue().trim());
        dto.setFileName(ui.getTxtEmail().getValue().trim());
        dto.setPartStorageTime(Integer.parseInt(ui.getTxtparttime().getValue().trim()));
        dto.setDepartmentStorageTime(Integer.parseInt(ui.getTxtdepartmenttime().getValue().trim()));
        dto.setFileType(ui.getTxtMobile().getValue().trim());
        dto.setSecurityLevel(ui.getTxtFullname().getValue().trim());
        dto.setStatus(ui.getTxtRole().getValue());
//        CatItemDTO sex = (CatItemDTO) ui.getCmbSex().getValue();
//        if (sex != null && !DataUtil.isStringNullOrEmpty(sex.getItemId()) && !Constants.DEFAULT_VALUE.equals(sex.getItemId())) {
//            dto.setMale(Boolean.getBoolean(sex.getItemId()));
//        }
    }
}
