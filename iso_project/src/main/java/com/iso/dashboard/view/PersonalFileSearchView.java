/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

//import com.iso.dashboard.component.CommonTableFilterPanel;
import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.controller.PersonalFileSearchController;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalFileSearchView extends Panel implements View {

    private TextField txtName;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;

    private CustomGrid pagedTable;

    public PersonalFileSearchView() {
        setSizeFull();
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
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

        new PersonalFileSearchController(this);

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
        CustomGrid table = createTable();
        tableLayout.addComponent(table);
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_LEFT);
        return mainLayout;
    }

    public CustomGrid createTable() {
        pagedTable = new CustomGrid();
        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);

        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setInputPrompt(BundleUtils.getString("procedureMngt.list.name"));
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtName);
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

        // btnExport
        btnExport = new Button();
        btnExport.setCaption(BundleUtils.getString("common.button.exportFile"));
        btnExport.setDescription(BundleUtils.getString("common.button.exportFile"));
        btnExport.setIcon(ISOIcons.EXPORT);
        btnExport.setImmediate(true);
        btnExport.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnExport.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        //buttonForm.addComponent(btnExport);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        //buttonForm.addComponent(btnAdd);

        return buttonForm;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtUserName) {
        this.txtName = txtUserName;
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
    public CustomGrid getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGrid pagedTable) {
        this.pagedTable = pagedTable;
    }

}
