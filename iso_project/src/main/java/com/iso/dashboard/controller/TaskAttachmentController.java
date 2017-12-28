/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.User_;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.ui.TaskAttachmentMngtUI;
import com.iso.dashboard.ui.UserMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.TaskAttachmentView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAttachmentController {

    TaskAttachmentView view;
    UserMngService service;

    CustomPageTable pagedTable;
//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "task.attach.list";//tien to trong file language
    String headerKey = "header.taskAttachReport";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    String userListLabel = "task.attach.list";
    Resource resource;

    public TaskAttachmentController(TaskAttachmentView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(UserMngService.getInstance().listUsers(null));
        doAction();
    }

    public void initTable(List<User_> lstUsers) {

        pagedTable.addGeneratedColumn("action", new Table.ColumnGenerator() {
//            private static final long serialVersionUID = -5042109683675242407L;

            public Component generateCell(Table source, Object itemId, Object columnId) {
                Item item = source.getItem(itemId);

                Button btnView = new Button();
                btnView.setIcon(ISOIcons.VIEW);
                btnView.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnView.setDescription(BundleUtils.getString("common.button.edit"));
                btnView.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String userId = (String) item.getItemProperty("id").getValue();
                        Notification.show("Edit " + userId);
                        User_ dto = UserMngService.getInstance().getUserById(userId);
                        onUpdate(dto);
                        view.getBtnRefresh().click();
                    }
                });
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

                HorizontalLayout hori = new HorizontalLayout();
                hori.addComponent(btnView);
//                hori.addComponent(btnDelete);
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
//        pagedTable.setColumnCollapsingAllowed(true);
        pagedTable.setResponsive(true);
//        pagedTable.setColumnCollapsed("id", true);
        pagedTable.setColumnHeaders(headerName);
        pagedTable.setVisibleColumns(columnVisible);
    }

    public IndexedContainer createContainer(List<User_> lstUser) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
//        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("username", String.class, null);
        container.addContainerProperty("email", String.class, null);
        container.addContainerProperty("phone", String.class, null);

        int i = 1;
        for (User_ u : lstUser) {
            Item item = container.addItem(u);
            item.getItemProperty("stt").setValue(String.valueOf(i));
            i++;
//            item.getItemProperty("id").setValue(String.valueOf(u.getId()));
            item.getItemProperty("username").setValue(u.getUserName());
            item.getItemProperty("email").setValue(u.getEmail());
            item.getItemProperty("phone").setValue(u.getPhone());
        }
//        container.sort(new Object[]{"id"}, new boolean[]{true});
        return container;
    }

    public void reloadData(List<User_> lstUsers) {
        pagedTable.setContainerDataSource(createContainer(lstUsers));
    }

    private void doAction() {
        view.getBtnRefresh().addClickListener(new Button.ClickListener() {

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
        List<User_> lstUser = UserMngService.getInstance().listUsers(null);
        Notification.show("lstUser : " + lstUser.size());
        reloadData(lstUser);
    }

    public void createDialog(boolean isInsert, User_ dto) {
//        UserMngtUI ui = new UserMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        TaskAttachmentMngtUI ui = new TaskAttachmentMngtUI(BundleUtils.getString("common.button.add"));
        Window window = new Window("", ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 3 / 4;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(80.0f, Sizeable.Unit.PERCENTAGE);
//        window.setIcon(VaadinIcons.CALENDAR_USER);
//        initDataDialog(ui, isInsert, dto);
        ui.getBtnSave().addClickListener((Button.ClickEvent event) -> {
            //                boolean validate = validateData(ui);
            boolean validate = true;
            ArrayList<String> lst = ui.getUploadImport().getUrl();
            if (DataUtil.isListNullOrEmpty(lst)) {
                ComponentUtils.showNotification("File is empty");
            } else {
                String pathFileImport = ui.getUploadImport().getPath() + lst.get(lst.size() - 1);
                
                ComponentUtils.showNotification(pathFileImport);
            }
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
//                                        // Confirmed to continue
//                                        ResultDTO res = null;
//                                        getDataFromUI(ui, dto);
//                                        if (isInsert) {
//                                            res = UserMngService.getInstance().addUser(dto);
//                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                                        } else {
//                                            res = UserMngService.getInstance().updateUser(dto);
//                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
//                                                    + res.getKey() + " " + res.getMessage());
//                                        }
window.close();
view.getBtnRefresh().click();
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

}
