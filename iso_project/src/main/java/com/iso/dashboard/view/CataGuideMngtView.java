/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

//import com.iso.dashboard.component.CommonTableFilterPanel;
import com.iso.dashboard.component.CustomGridEditRow;
import com.iso.dashboard.controller.ProcessGuideController;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class CataGuideMngtView extends Panel implements View {

    private TextField txtUserName;
    private TextField txtStaffCode;
    private ComboBox cmbStatus;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;
    private Button btnRefresh;

//    private CommonTableFilterPanel commonTableFilterPanel;
    private CustomGridEditRow pagedTable;
//    private CustomPageTable pagedTable;


    public CataGuideMngtView() {
        setSizeFull();
//        addStyleName("transactions");
        addStyleName(ValoTheme.PANEL_BORDERLESS);
//        setMargin(false);
//        setSpacing(false);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.userMngt"));
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
//        root.addStyleName("transactions");
//        setContent(root);
        Responsive.makeResponsive(root);

        Component content = buildContent();
        TabSheet tabSheet = new TabSheet();
//        tabSheet.setCaption(BundleUtils.getStringCas("menu.userMngt"));
        tabSheet.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        setContent(tabSheet);
        tabSheet.addComponent(root);

        root.addComponent(content);
        root.setExpandRatio(content, 1);

//        addComponent(buildToolbar());
        new ProcessGuideController(this);
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildToolbar());
        contenPanel.addComponent(buildResultLayout());

        return contenPanel;
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        HorizontalLayout condition = buildSearchForm();
        condition.addStyleName("toolbar");
        header.addComponent(condition);
        header.setExpandRatio(condition, 6.0f);

        HorizontalLayout tools = buildButtonForm();
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        header.setExpandRatio(tools, 3.0f);

        return header;
    }

    private Component buildResultLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
//        CustomPageTable table = createTable();
//        tableLayout.addComponent(table);
//        tableLayout.addComponent(table.createControls("10"));
        CustomGridEditRow table = createTable();
        tableLayout.addComponent(table);
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_LEFT);

        return mainLayout;
    }

//    public CustomPageTable createTable() {
    public CustomGridEditRow createTable() {
        pagedTable = new CustomGridEditRow();
        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
//        searchForm.setWidth("100.0%");
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);

        txtUserName = new TextField();
        txtUserName.setImmediate(true);
        txtUserName.setInputPrompt(BundleUtils.getString("guideMgnt.list.processGuideName"));
        txtUserName.setWidth("100.0%");
        txtUserName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtUserName);

        return searchForm;
    }

    private HorizontalLayout buildButtonForm() {
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

//        // btnExport
//        btnExport = new Button();
//        btnExport.setCaption(BundleUtils.getString("common.button.exportFile"));
//        btnExport.setDescription(BundleUtils.getString("common.button.exportFile"));
//        btnExport.setIcon(ISOIcons.EXPORT);
//        btnExport.setImmediate(true);
//        btnExport.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnExport.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        buttonForm.addComponent(btnExport);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        buttonForm.addComponent(btnAdd);
        
//        
//        btnRefresh = new Button();
//        btnRefresh.setCaption(BundleUtils.getString("common.button.refresh"));
//        btnRefresh.setDescription(BundleUtils.getString("common.button.add"));
//        btnRefresh.setIcon(ISOIcons.REFRESH);
//        btnRefresh.setImmediate(true);
//        btnRefresh.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnRefresh.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        buttonForm.addComponent(btnRefresh);

        return buttonForm;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public TextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(TextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public ComboBox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(ComboBox cmbStatus) {
        this.cmbStatus = cmbStatus;
    }

    public TextField getTxtStaffCode() {
        return txtStaffCode;
    }

    public void setTxtStaffCode(TextField txtStaffCode) {
        this.txtStaffCode = txtStaffCode;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

    public Button getBtnExport() {
        return btnExport;
    }

    public void setBtnExport(Button btnExport) {
        this.btnExport = btnExport;
    }



    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

//    public CustomPageTable getPagedTable() {
//        return pagedTable;
//    }
//
//    public void setPagedTable(CustomPageTable pagedTable) {
//        this.pagedTable = pagedTable;
//    }

    public CustomGridEditRow getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGridEditRow pagedTable) {
        this.pagedTable = pagedTable;
    }

}
