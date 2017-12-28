/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.controller.TaskPersonalMngtController;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.annotations.JavaScript;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
@JavaScript({"jquery.min.js", "bootstrap.min.js"})
public class TaskPersonalMngtView extends Panel implements View {

    public List<Organization> lstData = new ArrayList<>();
    public Map<String, Organization> map = new HashMap<>();

    private TextField txtTaskName;
    private Button btnSearch;
    private Button btnAdd;

    private CustomGrid pagedTable;

    public TaskPersonalMngtView() {
        UI.getCurrent().setPollInterval(1000);
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.taskPersonalMngt"));
        root.setSizeFull();
        root.setSpacing(true);
        root.addStyleName("dashboard-view");
        Responsive.makeResponsive(root);

        Component content = buildContent();
        TabSheet tabSheet = new TabSheet();
        tabSheet.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        setContent(tabSheet);
//        addComponent(new Trees());
        tabSheet.addComponent(root);

        root.addComponent(buildHeader());
        root.addComponent(content);
//        root.setExpandRatio(content, 1);

//        addComponent(buildToolbar());
        new TaskPersonalMngtController(this);
    }

    private Component buildHeader() {
        HorizontalLayout searchForm = new HorizontalLayout();
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);
        txtTaskName = new TextField();
        txtTaskName.setImmediate(true);
        txtTaskName.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.name"));
        txtTaskName.setWidth("100.0%");
        txtTaskName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtTaskName);
        

        HorizontalLayout buttonForm = new HorizontalLayout();
        buttonForm.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        Responsive.makeResponsive(buttonForm);
        btnSearch = new Button();
        btnSearch.setCaption(BundleUtils.getString("common.button.search"));
        btnSearch.setDescription(BundleUtils.getString("common.button.search"));
        btnSearch.setIcon(ISOIcons.SEARCH);
        btnSearch.setImmediate(true);
        btnSearch.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSearch.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSearch.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonForm.addComponent(btnSearch);
        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        buttonForm.addComponent(btnAdd);


        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        searchForm.addStyleName("toolbar");
        header.addComponent(searchForm);
        header.setExpandRatio(searchForm, 6.0f);

        buttonForm.addStyleName("toolbar");
        header.addComponent(buttonForm);
        header.setExpandRatio(buttonForm, 3.0f);
        return header;
    }

    private Component buildContent() {

        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        HorizontalSplitPanel contentLayout = new HorizontalSplitPanel();
        contentLayout.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        contentLayout.addStyleName("profile-form");

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        pagedTable = new CustomGrid();
        tableLayout.addComponent(pagedTable);
        contentLayout.setSecondComponent(tableLayout);
        contentLayout.setSplitPosition(0, Sizeable.Unit.PERCENTAGE);
//        contentLayout.addComponent(tableLayout);
//        contentLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_RIGHT);
//        contentLayout.setExpandRatio(tableLayout, 7.5f);

        contenPanel.addComponent(contentLayout);
        return contenPanel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    public List<Organization> getLstData() {
        return lstData;
    }

    public void setLstData(List<Organization> lstData) {
        this.lstData = lstData;
    }

    public Map<String, Organization> getMap() {
        return map;
    }

    public void setMap(Map<String, Organization> map) {
        this.map = map;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public CustomGrid getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGrid pagedTable) {
        this.pagedTable = pagedTable;
    }

    public TextField getTxtTaskName() {
        return txtTaskName;
    }

    public void setTxtTaskName(TextField txtTaskName) {
        this.txtTaskName = txtTaskName;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

}

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.iso.dashboard.view;
//
//import com.iso.dashboard.component.CustomPageGrid;
//import com.iso.dashboard.dto.Organization;
//import com.iso.dashboard.utils.BundleUtils;
//import com.iso.dashboard.utils.Constants;
//import com.iso.dashboard.utils.DataUtil;
//import com.iso.dashboard.utils.ISOIcons;
//import com.vaadin.data.Property;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.server.Responsive;
//import com.vaadin.shared.ui.slider.SliderOrientation;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.Slider;
//import com.vaadin.ui.TabSheet;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.themes.ValoTheme;
//import com.vaadin.ui.Tree;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.Window;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
////import org.vaadin.easyuploads.MultiFileUpload;
////import org.vaadin.easyuploads.UploadField;
//
///**
// *
// * @author VIET_BROTHER
// */
//public class TaskPersonalMngtView extends Panel implements View {
//
//    public List<Organization> lstData = new ArrayList<>();
//    public Map<String, Organization> map = new HashMap<>();
//
//    private Button btnAdd;
//    private Button btnUpdate;
//    private Button btnDelete;
//
//    private Tree orgTree;
//
//    public TaskPersonalMngtView() {
//        setSizeFull();
//        addStyleName(ValoTheme.PANEL_BORDERLESS);
//        setSizeFull();
//        VerticalLayout root = new VerticalLayout();
//        root.setCaption(BundleUtils.getStringCas("menu.organizationMngt"));
//        root.setSizeFull();
//        root.setSpacing(true);
//        root.addStyleName("dashboard-view");
//        Responsive.makeResponsive(root);
//
//        // Create a vertical slider
//        Slider vertslider = new Slider(1, 100);
//        vertslider.setOrientation(SliderOrientation.HORIZONTAL);
//        Label vertvalue = new Label("123");
//        vertvalue.setSizeUndefined();
//
//        // Handle changes in slider value.
//        vertslider.addValueChangeListener(
//                new Property.ValueChangeListener() {
//
//                    @Override
//                    public void valueChange(Property.ValueChangeEvent event) {
//                        double value = (Double) vertslider.getValue();
//
//                        // Use the value
//                        vertvalue.setValue(String.valueOf(value));
//                    }
//
//                });
//
//        // The slider has to be immediate to send the changes
//        // immediately after the user drags the handle.
//        vertslider.setImmediate(true);
//        root.addComponent(vertslider);
//        root.addComponent(vertvalue);
//
//        Component content = buildContent();
//        TabSheet tabSheet = new TabSheet();
//        tabSheet.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        tabSheet.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
//        setContent(tabSheet);
////        addComponent(new Trees());
//        tabSheet.addComponent(root);
//        root.addComponent(new CustomPageGrid());
//        // Build the transactional container and grid
//
////        Grid grid = new Grid();
////        grid.setSizeFull();
////        List<CTaskGroup> taskGroupsRes = TaskGroupMgntService.getInstance().getTaskGroups();
////        BeanItemContainer<CTaskGroup> container = new BeanItemContainer<CTaskGroup>(
////                CTaskGroup.class);
////        container.addAll(taskGroupsRes);
////        GeneratedPropertyContainer wrappingContainer = new GeneratedPropertyContainer(container);
////        wrappingContainer.addGeneratedProperty("stt", new PropertyValueGenerator<Long>() {
////            private long index = 1;
////
////            @Override
////            public Long getValue(Item item, Object itemId, Object propertyId) {
////                return (long) (container.indexOfId(itemId) + 1);
////            }
////
////            @Override
////            public Class<Long> getType() {
////                return Long.class;
////            }
////        });
////        wrappingContainer.addGeneratedProperty("action", new PropertyValueGenerator<String>() {
////
////            @Override
////            public String getValue(Item item, Object itemId, Object propertyId) {
////                return "";
////            }
////
////            @Override
////            public Class<String> getType() {
////                return String.class;
////            }
////
////        });
////        grid.setContainerDataSource(wrappingContainer);
////
////        /*
////         * Changing the column order and adjusting column headers
////         */
////        String prefix = "taskGroupMgnt.list";
////        String headerKey = "header.taskGroupMgnt";//lay trong file cas
////        String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
////        String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
////        String[] columnVisible = BundleUtils.getHeaderColumnVisible(headerKey);
////        java.lang.reflect.Field[] fields = container.getBeanType().getDeclaredFields();
////        List<String> lstValView = Arrays.asList(columnVisible);
////        for (java.lang.reflect.Field f : fields) {
////            if (!lstValView.contains(f.getName())) {
////                grid.removeColumn(f.getName());
////            }
////        }
////        grid.setColumns(columnVisible);
////
////        //set column headername
////        String headerNameTemp = null;
////        for (String columnsVisible1 : columnVisible) {
////            headerNameTemp = BundleUtils.getString(prefix + "." + columnsVisible1);
////            grid.getColumn(columnsVisible1).setHeaderCaption(headerNameTemp);
////        }
////        grid.getColumn("action").setHeaderCaption(BundleUtils.getString("common.table.action"));
////
////        Map<Integer, HandlerButtonCustomRenderer> mapHandler = new HashMap<>();
////        mapHandler.put(Constants.BUTTON_RENDERER.EDIT_BITM, new HandlerButtonCustomRenderer() {
////
////            @Override
////            public void action(ClickableRenderer.RendererClickEvent event) {
////                Notification.show("abc");
////            }
////        });
////
////        grid.getColumn("action")
////                .setRenderer(new CustomButtonValueRenderer(mapHandler))
////                .setWidth(100);
////        grid.setEditorSaveCaption(BundleUtils.getString("common.button.save"));
////        grid.setEditorCancelCaption(BundleUtils.getString("common.button.cancel"));
////
////        /*
////         * Removing unwanted columns
////         */
////        // grid.removeColumn("customer");
////        // grid.removeColumn("customized");
////        // grid.removeColumn("priority");
////        // grid.removeColumn("orderTime");
////
////        /*
////         * Adjusting column sizes
////         */
//////        grid.getColumn("action").setRenderer(new ButtonRenderer());
////
////        /*
////         * Keep some columns in view all the time
////         */
//////        grid.getColumn("action").setLastFrozenColumn();
////
////        /*
////         * Various ways of tweaking how data is shown
////         */
////        /*
////         * Footer with various types of content
////         */
////        FooterRow footerRow = grid.appendFooterRow();
////        footerRow.join(columnVisible);
////
////        /*
////         * Enable editing
////         */
////        grid.setEditorEnabled(true);
////        grid.setFrozenColumnCount(0);
////
////        grid.getColumn("stt").setEditable(false);
////        grid.getColumn("action").setEditable(false);
////
//////        Field<?> customerField = grid.getColumn("customer").getEditorField();
//////        customerField.setRequired(true);
//////        customerField.setRequiredError("Value is required");
//////        ComboBox cmb = new ComboBox();
//////        List<String> lst = new ArrayList<>();
//////        lst.add("1");
//////        lst.add("2");
//////        lst.add("3");
//////        cmb.addItems(lst);
////        CheckBox cb = new CheckBox();
////        grid.getColumn("status").setEditorField(cb);
////
////        ComboBox colorEditor = new ComboBox();
////        colorEditor.setImmediate(true);
////        colorEditor.setWidth("100%");
////        colorEditor.setItemStyleGenerator(new ComboBox.ItemStyleGenerator() {
////
////            @Override
////            public String getStyle(ComboBox source, Object itemId) {
////                String style = (itemId == null) ? "white" : itemId.toString().toLowerCase();
////                colorEditor.setStyleName(style);
////                return style;
////            }
////        });
////        colorEditor.addValueChangeListener(new Property.ValueChangeListener() {
////
////            @Override
////            public void valueChange(Property.ValueChangeEvent event) {
////                String style = (event.getProperty().getValue() == null) ? "white" : ((String) event.getProperty().getValue()).toLowerCase();
////                colorEditor.setStyleName(style);
////            }
////        });
////        colorEditor.addItems(ColorService.getInstance().listColor());
////        colorEditor.setNullSelectionAllowed(false);
////        grid.getColumn("color").setEditorField(colorEditor);
////        grid.getColumn("color").setRenderer(new HtmlRenderer(), new Converter<String, String>() {
////
////            @Override
////            public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
////                return "not implemented";
////            }
////
////            @Override
////            public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
////                return "<div style=\"width: 100%; text-align: center; background-color:"
////                        + (ColorService.getInstance().getMapCodeColor().get(value) == null
////                                ? "white" : ("#" + ColorService.getInstance().getMapCodeColor().get(value)))
////                        + "\" >"
////                        + value
////                        + " </div>";
////            }
////
////            @Override
////            public Class<String> getModelType() {
////                return String.class;
////            }
////
////            @Override
////            public Class<String> getPresentationType() {
////                return String.class;
////            }
////        });
////
////        /*
////         * Get an event when the users saves in the editor
////         */
////        grid.getEditorFieldGroup().addCommitHandler(new CommitHandler() {
////            @Override
////            public void preCommit(CommitEvent commitEvent)
////                    throws CommitException {
////                // Do nothing
////                Notification.show("preCommit saved");
////            }
////
////            @Override
////            public void postCommit(CommitEvent commitEvent)
////                    throws CommitException {
////                commitEvent.getFieldBinder().getItemDataSource();
////                Notification.show("Changes saved");
////            }
////        });
////
////
////        /*
////         * New feature going into Vaadin 7.5: Column reordering
////         */
////        grid.setColumnReorderingAllowed(true);
////
////        /*
////         * New feature going into Vaadin 7.5: Row details
////         */
////        grid.getColumn("status").setConverter(
////                new Converter<String, Boolean>() {
////
////                    @Override
////                    public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale) throws Converter.ConversionException {
////                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////                    }
////
////                    @Override
////                    public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
////                        if (value) {
////                            return FontAwesome.CHECK_SQUARE_O.getHtml();
////                        } else {
////                            return FontAwesome.SQUARE_O.getHtml();
////                        }
////                    }
////
////                    @Override
////                    public Class<Boolean> getModelType() {
////                        return Boolean.class;
////                    }
////
////                    @Override
////                    public Class<String> getPresentationType() {
////                        return String.class;
////                    }
////                });
////        grid.getColumn("status").setRenderer(new HtmlRenderer());
////        grid.setEditorEnabled(true);
////        grid.setHeightMode(HeightMode.ROW);
////        grid.setHeightByRows(container.size());
////        root.addComponent(grid);
//    }
//
//    private Component buildHeader() {
//
//        CssLayout headerPanel = new CssLayout();
//        headerPanel.addStyleName("dashboard-panels");
//        Responsive.makeResponsive(headerPanel);
//        // btnAdd
//        btnAdd = new Button();
//        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
//        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        btnAdd.setIcon(ISOIcons.ADD);
//        btnAdd.setStyleName(ValoTheme.BUTTON_PRIMARY);
//        btnAdd.addClickListener(new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                Component ui = new TaskAttachmentView();
//                Window window = new Window("", ui);
//                //window.setWidth("700px");
//                float height = UI.getCurrent().getWidth() * 3 / 4;
//                window.setWidth(String.valueOf(height) + "%");
//                window.setHeight(70.0f, Unit.PERCENTAGE);
////        window.setIcon(VaadinIcons.CALENDAR_USER);
//
//                ui.setWidth("100%");
//                ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//
//                window.setModal(true);
//                DataUtil.reloadWindow(window);
//                UI.getCurrent().addWindow(window);
//            }
//        });
//        // btnAdd
//        btnUpdate = new Button();
//        btnUpdate.setCaption(BundleUtils.getString("common.button.edit"));
//        btnUpdate.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnUpdate.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        btnUpdate.setIcon(ISOIcons.EDIT);
//        // btnDelete
//        btnDelete = new Button();
//        btnDelete.setCaption(BundleUtils.getString("common.button.delete"));
//        btnDelete.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnDelete.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        btnDelete.setIcon(ISOIcons.CANCEL);
//
//        headerPanel.addComponent(btnAdd);
//        headerPanel.addComponent(btnUpdate);
//        headerPanel.addComponent(btnDelete);
//        return headerPanel;
//    }
//
//    private Component buildContent() {
//        CssLayout contenPanel = new CssLayout();
//        contenPanel.addStyleName("dashboard-panels");
//        Responsive.makeResponsive(contenPanel);
//
////        loadTreeData();
//        orgTree = new Tree();
//
//        contenPanel.addComponent(orgTree);
//
//        return contenPanel;
//    }
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//    }
//
//    public List<Organization> getLstData() {
//        return lstData;
//    }
//
//    public void setLstData(List<Organization> lstData) {
//        this.lstData = lstData;
//    }
//
//    public Map<String, Organization> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, Organization> map) {
//        this.map = map;
//    }
//
//    public Button getBtnAdd() {
//        return btnAdd;
//    }
//
//    public void setBtnAdd(Button btnAdd) {
//        this.btnAdd = btnAdd;
//    }
//
//    public Button getBtnUpdate() {
//        return btnUpdate;
//    }
//
//    public void setBtnUpdate(Button btnUpdate) {
//        this.btnUpdate = btnUpdate;
//    }
//
//    public Button getBtnDelete() {
//        return btnDelete;
//    }
//
//    public void setBtnDelete(Button btnDelete) {
//        this.btnDelete = btnDelete;
//    }
//
//    public Tree getOrgTree() {
//        return orgTree;
//    }
//
//    public void setOrgTree(Tree orgTree) {
//        this.orgTree = orgTree;
//    }
//
//}
