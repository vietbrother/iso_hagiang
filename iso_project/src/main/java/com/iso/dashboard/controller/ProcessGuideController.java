/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGridEditRow;
import com.iso.dashboard.dto.CProcessGuide;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.ProcessGuideMgntService;
import com.iso.dashboard.service.ColorService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.CataGuideMngtView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.Reindeer;
import java.util.List;
import java.util.Locale;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessGuideController {

    CataGuideMngtView view;

    CustomGridEditRow pagedTable;
    String prefix = "guideMgnt.list";//tien to trong file language
    String headerKey = "header.guideMgnt";//lay trong file cas
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
    Resource resource;
    public static Object propertyID = null;
    public static Object itemID = null;

    String styleSelect = "";

    public ProcessGuideController(CataGuideMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        initTable(ProcessGuideMgntService.getInstance().getProcessGuides());
        doAction();
    }

    public void initTable(List<CProcessGuide> takGroups) {
 
        BeanItemContainer container = createContainer(takGroups);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
//                CProcessGuide processGuideItem = (CProcessGuide) obj;
//                //luu thong tin cap nhat
//                String name = processGuideItem.getProcessGuideName();
//                String color = processGuideItem.getColor();
//                Boolean status = processGuideItem.getStatus();
//                int id = processGuideItem.getProcessGuideId();
//                String desc = processGuideItem.getDescription();
//                CProcessGuide processGuide = new CProcessGuide();
//                processGuide.setProcessGuideName(name);
//                processGuide.setColor(color);
//                processGuide.setDescription(desc);
//                processGuide.setStatus(status);
//                if (id != 0) {
//                    processGuide.setProcessGuideId(id);
//                    ResultDTO res = ProcessGuideMgntService.getInstance().updateProcessGuide(processGuide);
//                    ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " " + res.getKey() + " " + res.getMessage());
//                } else {
//                    ResultDTO res = ProcessGuideMgntService.getInstance().addProcessGuide(processGuide);
//                    ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                }
//                view.getBtnRefresh().click();
                Notification.show("Edit");
            }

            @Override
            public void actionDelete(Object obj) {
                CProcessGuide processGuideItem = (CProcessGuide) obj;
                String id = processGuideItem != null ? processGuideItem.getProcessGuideId().toString() : null;
                if (id != null) {
                    ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                            BundleUtils.getString("message.warning.title"),
                            BundleUtils.getString("message.warning.content"),
                            BundleUtils.getString("common.confirmDelete.yes"),
                            BundleUtils.getString("common.confirmDelete.no"), (ConfirmDialog dialog) -> {
                                if (dialog.isConfirmed()) {
                                    ResultDTO res = ProcessGuideMgntService.getInstance().removeProcessGuide(id);
                                    ComponentUtils.showNotification("Delete id : " + id + " " + res.getKey() + " " + res.getMessage());
                                    reloadData(ProcessGuideMgntService.getInstance().getProcessGuides());
                                }
                            });
                    d.setStyleName(Reindeer.WINDOW_LIGHT);
                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
                    d.getOkButton().setIcon(ISOIcons.SAVE);
                    d.getCancelButton().setIcon(ISOIcons.CANCEL);
                } else {
                    pagedTable.getContainerDataSource().removeItem(obj);
                    pagedTable.refreshAllRows();
                }
            }

            @Override
            public void actionSelect(Object obj) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        pagedTable.setEditorEnabled(true);

        TextField nameEditor = new TextField();
        nameEditor.focus();
        nameEditor.setWidth("100%");
        pagedTable.getColumn("processGuideName").setEditorField(nameEditor);

        pagedTable.getColumn("status").setConverter(
                new Converter<String, Boolean>() {

                    @Override
                    public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale) throws Converter.ConversionException {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
                        if (value != null && value) {
                            return FontAwesome.CHECK_SQUARE_O.getHtml();
                        } else {
                            return FontAwesome.SQUARE_O.getHtml();
                        }
                    }

                    @Override
                    public Class<Boolean> getModelType() {
                        return Boolean.class;
                    }

                    @Override
                    public Class<String> getPresentationType() {
                        return String.class;
                    }
                });
        pagedTable.getColumn("status").setRenderer(new HtmlRenderer());
//        pagedTable.getColumn("status").getEditorField().setCaption("");
        CheckBox cb = new CheckBox();
        pagedTable.getColumn("status").setEditorField(cb);
        
        pagedTable.initFilter(pagedTable);
        pagedTable.getFilter().setTextFilter("processGuideName", true, false, BundleUtils.getString("guideMgnt.list.processGuideName"));
    }

    public void reloadData(List<CProcessGuide> processGuides) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(processGuides)));
    }

    public BeanItemContainer createContainer(List<CProcessGuide> processGuides) {
        BeanItemContainer<CProcessGuide> container = new BeanItemContainer<CProcessGuide>(
                CProcessGuide.class);
        container.addAll(processGuides);
        return container;
    }


    private void doAction() {
        view.getBtnAdd().addClickListener((Button.ClickEvent event) -> {
            List<CProcessGuide> processGuides = ProcessGuideMgntService.getInstance().getProcessGuides();
            processGuides.add(new CProcessGuide());
            reloadData(processGuides);
        });
        view.getBtnSearch().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                reloadData(ProcessGuideMgntService.getInstance().getProcessGuides());
            }
        });
    }

}


//<editor-fold defaultstate="collapsed" desc="table vaadin 7">
        //        pagedTable.addGeneratedColumn("index", (final Table source, final Object itemId, final Object columnId) -> {
//            Container.Indexed container = (Container.Indexed) source.getContainerDataSource();
//            return Integer.toString(container.indexOfId(itemId) + 1);
//        });
//        pagedTable.addGeneratedColumn("action", (Table source, Object itemId, Object columnId) -> {
//            Button btnSave = new Button();
//            btnSave.setIcon(FontAwesome.SAVE);
//            btnSave.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//            btnSave.setDescription(BundleUtils.getString("common.save"));
//            btnSave.addClickListener(new Button.ClickListener() {
//
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    CProcessGuide processGuideItem = (CProcessGuide) itemId;
//                    //luu thong tin cap nhat
//                    Item item = source.getItem(itemId);
//                    String name = item.getItemProperty("name").getValue().toString();
//                    String color = item.getItemProperty("color").getValue().toString();
//                    String status = item.getItemProperty("status").getValue().toString();
//                    String id = (processGuideItem == null || processGuideItem.getProcessGuideId() == null || "".equals(processGuideItem.getProcessGuideId().toString()) || "0".equals(processGuideItem.getProcessGuideId().toString())) ? null : processGuideItem.getProcessGuideId().toString();
//                    String desc = item.getItemProperty("desc").getValue().toString();
//                    CProcessGuide processGuide = new CProcessGuide();
//                    processGuide.setProcessGuideName(name);
//                    processGuide.setColor(color);
//                    processGuide.setDescription(desc);
//                    processGuide.setStatus(status.equals("false") ? 1 : 0);
//                    if (id != null) {
//                        processGuide.setProcessGuideId(Integer.parseInt(id));
//                        ResultDTO res = ProcessGuideMgntService.getInstance().updateProcessGuide(processGuide);
//                        ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " " + res.getKey() + " " + res.getMessage());
//                    } else {
//                        ResultDTO res = ProcessGuideMgntService.getInstance().addProcessGuide(processGuide);
//                        ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
//                    }
//                }
//            });
//            Button btnDelete = new Button();
//            btnDelete.setIcon(ISOIcons.DELETE);
//            btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//            btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
//            btnDelete.addClickListener((Button.ClickEvent event) -> {
//                Item item = source.getItem(itemId);
//                CProcessGuide processGuideItem = (CProcessGuide) itemId;
//                String id = processGuideItem != null ? processGuideItem.getProcessGuideId().toString() : null;
//                if (id != null) {
//                    ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
//                            BundleUtils.getString("message.warning.title"),
//                            BundleUtils.getString("message.warning.content"),
//                            BundleUtils.getString("common.confirmDelete.yes"),
//                            BundleUtils.getString("common.confirmDelete.no"), (ConfirmDialog dialog) -> {
//                                if (dialog.isConfirmed()) {
//                                    ResultDTO res = ProcessGuideMgntService.getInstance().removeProcessGuide(id);
//                                    ComponentUtils.showNotification("Delete id : " + id + " " + res.getKey() + " " + res.getMessage());
//                                    pagedTable.removeItem(itemId);
//                                }
//                            });
//                    d.setStyleName(Reindeer.WINDOW_LIGHT);
//                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                    d.getOkButton().setIcon(ISOIcons.SAVE);
//                    d.getCancelButton().setIcon(ISOIcons.CANCEL);
//                } else {
//                    pagedTable.removeItem(itemId);
//                }
//            });
//
//            HorizontalLayout hori = new HorizontalLayout();
//            hori.addComponent(btnSave);
//            hori.addComponent(btnDelete);
//            return hori;
//        });
//
//        pagedTable.setTableFieldFactory(new DefaultFieldFactory() {
//            private static final long serialVersionUID = 8585461394836108250L;
//
//            @Override
//            public Field<?> createField(Container container, Object itemId,
//                    Object propertyId, Component uiContext) {
//                if ("name".equals(propertyId)) {
//                    if (pagedTable.isEditable() && propertyId.equals(ProcessGuideController.propertyID) && itemId.equals(ProcessGuideController.itemID)) {
//                        TextField tf = new TextField();
//                        tf.focus();
//                        tf.addBlurListener((FieldEvents.BlurEvent event) -> {
//                            ProcessGuideController.propertyID = null;
//                            ProcessGuideController.itemID = null;
//                            if (pagedTable.isEditable()) {
//                                pagedTable.setEditable(false);
//                            }
//                        });
//                        tf.setWidth("100%");
//                        return tf;
//                    } else {
//                        return null;
//                    }
//                }
//                if ("color".equals(propertyId)) {
//                    if (pagedTable.isEditable() && propertyId.equals(ProcessGuideController.propertyID) && itemId.equals(ProcessGuideController.itemID)) {
//                        ComboBox cb = new ComboBox();
//                        cb.setImmediate(true);
//                        cb.focus();
//                        cb.addBlurListener((FieldEvents.BlurEvent event) -> {
//                            ProcessGuideController.propertyID = null;
//                            ProcessGuideController.itemID = null;
//                            if (pagedTable.isEditable()) {
//                                pagedTable.setEditable(false);
//                            }
////                            String style = ((CProcessGuide) itemId).getColor().toLowerCase();
//                            //cb.setStyleName(styleSelect);
//                        });
//                        cb.setWidth("100%");
////                        cb.setItemStyleGenerator((ComboBox source, Object itemId1) -> 
////                                itemId1 == null ? "white" : itemId1.toString().toLowerCase());
//                        cb.setItemStyleGenerator(new ComboBox.ItemStyleGenerator() {
//
//                            @Override
//                            public String getStyle(ComboBox source, Object itemId) {
//                                String style = (itemId == null) ? "white" : itemId.toString().toLowerCase();
//                                cb.setStyleName(style);
//                                styleSelect = style;
//                                return style;
//                            }
//                        });
//
//                        cb.addValueChangeListener(new Property.ValueChangeListener() {
//
//                            @Override
//                            public void valueChange(Property.ValueChangeEvent event) {
//                                String style = (event.getProperty().getValue() == null) ? "white" : ((String) event.getProperty().getValue()).toLowerCase();
//                                cb.setStyleName(style);
//                            }
//                        });
//                        cb.addItems(ColorService.getInstance().listColor());
//                        cb.setNullSelectionAllowed(false);
//
//                        return cb;
//                    } else {
//                        return null;
//                    }
//                }
//                if ("desc".equals(propertyId)) {
//                    if (pagedTable.isEditable() && propertyId.equals(ProcessGuideController.propertyID) && itemId.equals(ProcessGuideController.itemID)) {
//                        TextField tf = new TextField();
//                        tf.focus();
//                        tf.addBlurListener((FieldEvents.BlurEvent event) -> {
//                            ProcessGuideController.propertyID = null;
//                            ProcessGuideController.itemID = null;
//                            if (pagedTable.isEditable()) {
//                                pagedTable.setEditable(false);
//                            }
//                        });
//                        tf.setWidth("100%");
//                        return tf;
//                    } else {
//                        return null;
//                    }
//                }
//                // Otherwise use the default field factory 
//                return super.createField(container, itemId, propertyId, uiContext);
//            }
//        });
//
//        pagedTable.addGeneratedColumn("status", (Table source, Object itemId, Object columnId) -> {
//            Property prop = source.getItem(itemId).getItemProperty(columnId);
//            CheckBox checkBox = new CheckBox(null, prop);
//            // don't allow user to change value
//            if (ProcessGuideController.propertyID != null && ProcessGuideController.itemID != null && ProcessGuideController.propertyID.equals("status") && ProcessGuideController.itemID.equals(itemId)) {
//                checkBox.setEnabled(source.isEditable());
//            }
//            checkBox.addBlurListener((FieldEvents.BlurEvent event) -> {
//                ProcessGuideController.propertyID = null;
//                ProcessGuideController.itemID = null;
//                if (pagedTable.isEditable()) {
//                    pagedTable.setEditable(false);
//                }
//            });
//            return checkBox;
//        });
////        pagedTable.setCellStyleGenerator(new Table.CellStyleGenerator() {
////
////            @Override
////            public String getStyle(Table source, Object itemId, Object propertyId) {
////                Item item = source.getItem(itemId);
////                String color = item.getItemProperty("color").getValue().toString();
////                if (color != null) {
////                    if (color.equalsIgnoreCase("yellow")) {
////                        return "yellow";
////                    }
////                }
////                return "";
////            }
////        });
//
////        pagedTable.addGeneratedColumn("color", new Table.ColumnGenerator() {
////
////            @Override
////            public Object generateCell(Table source, Object itemId, Object columnId) {
////                Item item = source.getItem(itemId);
////                String color = item.getItemProperty("color").getValue().toString();
////                Label lb = new Label(color);
////                lb.setStyleName(color.toLowerCase());
////                return lb;
////            }
////        });
//        reloadData(takGroups);
//        pagedTable.setSizeFull();
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
//        pagedTable.setSelectable(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);
//        pagedTable.setVisibleColumns(columnVisible);
//        pagedTable.addItemClickListener((ItemClickEvent event) -> {
//            if (event.isDoubleClick()) {
//                propertyID = event.getPropertyId();
//                itemID = event.getItemId();
//                if (!pagedTable.isEditable()) {
//                    pagedTable.setEditable(true);
//                }
//            }
//        });
        //</editor-fold>
