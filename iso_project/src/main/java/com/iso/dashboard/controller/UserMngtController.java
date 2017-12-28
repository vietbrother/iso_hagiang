/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CMenu;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Student;
import com.iso.dashboard.dto.User_;
import com.iso.dashboard.dto.User_;
import com.iso.dashboard.service.MenuMgntService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.UserMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.DateUtil;
import com.iso.dashboard.utils.HibernateUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.UserMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
import org.hibernate.Session;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class UserMngtController {

    UserMngtView view;
    UserMngService service;

    CustomGrid pagedTable;
//    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "userMngt.list";//tien to trong file language
    String headerKey = "header.userMngt";//lay trong file cas
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String userListLabel = "userMngt.list";
    Resource resource;

    public UserMngtController(UserMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(UserMngService.getInstance().listUsers(null));
        doAction();
//        reloadData(UserMngService.getInstance().listUsers(null));
    }

    public void initTable(List<User_> lstUsers) {
////        pagedTable.addGeneratedColumn("stt", new Table.ColumnGenerator() {
////
////            @Override
////            public Object generateCell(Table source, Object itemId, Object columnId) {
////                List lstObj = (List) source.getItemIds();
////                int i = lstObj.indexOf(itemId);
////                return i + 1;
////            }
////        });
//        pagedTable.addGeneratedColumn("action", new Table.ColumnGenerator() {
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
//                        User_ dto = UserMngService.getInstance().getUserById(userId);
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
//                                            ResultDTO res = UserMngService.getInstance().removeUser(userId);
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
//                hori.setSizeFull();
//                hori.addComponent(btnEdit);
//                hori.setComponentAlignment(btnEdit, Alignment.MIDDLE_RIGHT);
//                hori.addComponent(btnDelete);
//                hori.setComponentAlignment(btnDelete, Alignment.MIDDLE_LEFT);
//                return hori;
//            }
//        });
//        reloadData(lstUsers);
//        pagedTable.setSizeFull();
//        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
////        pagedTable.setWidth("1000px");
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
////        pagedTable.setSelectable(true);
//        pagedTable.setAlwaysRecalculateColumnWidths(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);
//        pagedTable.setVisibleColumns(BundleUtils.getStringCas("header.userMngt").split("#"));

        IndexedContainer container = createContainer(lstUsers);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                onUpdate((User_) obj);
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
                                    User_ user = (User_) obj;
                                    ResultDTO res = UserMngService.getInstance().removeUser(String.valueOf(user.getId()));
                                    ComponentUtils.showNotification("Delete id : " + String.valueOf(user.getId()) + " " + res.getKey() + " " + res.getMessage());
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

    public IndexedContainer createContainer(List<User_> lstUser) {
        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
//        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("username", String.class, null);
        container.addContainerProperty("email", String.class, null);
        container.addContainerProperty("phone", String.class, null);
        container.addContainerProperty("birthday", String.class, null);
        for (User_ u : lstUser) {
            Item item = container.addItem(u);
//            item.getItemProperty("id").setValue(String.valueOf(u.getId()));
            item.getItemProperty("username").setValue(u.getUserName());
            item.getItemProperty("email").setValue(u.getEmail());
            item.getItemProperty("phone").setValue(u.getPhone());
            item.getItemProperty("birthday").setValue(DateUtil.date2ddMMyyyyHHMMss(u.getBirthDay()));
        }
        //container.sort(new Object[]{"id"}, new boolean[]{true});
        return container;
    }

    public void reloadData(List<User_> lstUsers) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstUsers)));
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
        createDialog(true, new User_());
    }

    private void onUpdate(User_ dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<User_> lstUser = UserMngService.getInstance().listUsers(view.getTxtUserName().getValue());
        Notification.show("lstUser : " + lstUser.size());
        reloadData(lstUser);
    }

    private void onExport() {

        try {
            List<User_> lstUser = UserMngService.getInstance().listUsers(view.getTxtUserName().getValue());
            String[] header = new String[]{"username", "birthDay", "email"};
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

            File fileExport = CommonExport.exportFile(lstUser,//list du lieu
                    headerAlign,//header
                    //"userMngt.list",//header prefix
                    userListLabel,//header prefix
                    fileTemplate,//path template
                    BundleUtils.getString("userMngt.fileName.export"),//fileName out
                    7,//start row
                    subTitle,//sub title
                    4,//cell title Index
                    BundleUtils.getString("userMngt.report")//title
            );
            resource = new FileResource(fileExport);

            Page.getCurrent().open(resource, null, false);
        } catch (Exception e) {
        }
    }

    private void initDataDialog(UserMngtUI ui, boolean isInsert, User_ dto) {

        List<CatItemDTO> lstSex = new ArrayList<>();
        lstSex.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("userMngt.list.sex.male")));
        lstSex.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("userMngt.list.sex.female")));

        //filling the combobox with User_'s by BeanContainer
        BeanItemContainer<CMenu> beanContainer = new BeanItemContainer<CMenu>(CMenu.class);
        List<CMenu> lstMenu = MenuMgntService.getInstance().listCMenu(null);
        beanContainer.addAll(lstMenu);
        ui.getViewFunction().setContainerDataSource(beanContainer);
        ui.getViewFunction().setItemCaptionPropertyId("name");
        ui.getViewFunction().setItemCaptionMode(ItemCaptionMode.PROPERTY);
        
        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbSex(), Constants.EMPTY_CHARACTER, lstSex);
        } else {
            ui.getTxtUsername().setValue(dto.getUserName() == null ? "" : dto.getUserName());
            ui.getTxtEmail().setValue(dto.getEmail() == null ? "" : dto.getEmail());
            ui.getTxtFullname().setValue(dto.getLastName() == null ? "" : dto.getLastName());
            ui.getTxtMobile().setValue(dto.getPhone() == null ? "" : dto.getPhone());
            ui.getTxtRole().setValue(dto.getRole() == null ? "" : dto.getRole());
            ui.getPdBirthday().setValue(dto.getBirthDay());
            ComponentUtils.fillDataCombo(ui.getCmbSex(), Constants.EMPTY_CHARACTER, String.valueOf(dto.isMale()), lstSex);
        }

    }

    public void createDialog(boolean isInsert, User_ dto) {
        UserMngtUI ui = new UserMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 2 / 3;
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
                                            res = UserMngService.getInstance().addUser(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                        } else {
                                            res = UserMngService.getInstance().updateUser(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // User_ did not confirm
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
        ui.getTxtUsername().focus();
    }

    private void getDataFromUI(UserMngtUI ui, User_ dto) {
        dto.setRole(ui.getTxtRole().getValue().trim());
        dto.setEmail(ui.getTxtEmail().getValue().trim());
        dto.setFirstName(ui.getTxtFullname().getValue().trim());
        dto.setLastName(ui.getTxtFullname().getValue().trim());
        dto.setUserName(ui.getTxtUsername().getValue().trim());
        dto.setPhone(ui.getTxtMobile().getValue().trim());
        dto.setBirthDay(ui.getPdBirthday().getValue());
        CatItemDTO sex = (CatItemDTO) ui.getCmbSex().getValue();
        if (sex != null && !DataUtil.isStringNullOrEmpty(sex.getItemId()) && !Constants.DEFAULT_VALUE.equals(sex.getItemId())) {
            dto.setMale(Boolean.getBoolean(sex.getItemId()));
        }

    }
}
