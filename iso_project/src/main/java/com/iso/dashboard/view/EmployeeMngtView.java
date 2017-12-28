/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.controller.EmployeeMngtController;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeMngtView extends Panel implements View {

    private TextField txtEmployeeType;
//    private TextField txtStaffCode;
//    private ComboBox cmbStatus;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;

    private CustomGrid pagedTable;

    public List<Employee> lstEmployees = new ArrayList<>();

    private Button btnHideTree;
    private Tree orgTree;
    boolean isViewTree;

    public EmployeeMngtView() {
        setSizeFull();
//        addStyleName("transactions");
        addStyleName(ValoTheme.PANEL_BORDERLESS);
//        setMargin(false);
//        setSpacing(false);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.employeeMngt"));
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
        new EmployeeMngtController(this);
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
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidth(100.0f, Unit.PERCENTAGE);
        headerLayout.addStyleName("profile-form");

        // btnHide
        isViewTree = true;
        btnHideTree = new Button();
        btnHideTree.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnHideTree.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnHideTree.setIcon(FontAwesome.ARROW_CIRCLE_O_LEFT);
//        btnHideTree.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        btnHideTree.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnHideTree.addStyleName(ValoTheme.BUTTON_SMALL);
        btnHideTree.setDescription(BundleUtils.getString("common.collspan"));
        btnHideTree.setCaption(BundleUtils.getString("common.collspan"));
        btnHideTree.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (isViewTree) {
                    btnHideTree.setIcon(FontAwesome.ARROW_CIRCLE_O_LEFT);
                    btnHideTree.setDescription(BundleUtils.getString("common.expand"));
                    btnHideTree.setCaption(BundleUtils.getString("common.expand"));
                } else {
                    btnHideTree.setIcon(FontAwesome.ARROW_CIRCLE_O_RIGHT);
                    btnHideTree.setCaption(BundleUtils.getString("common.collspan"));
                }
                isViewTree = !isViewTree;
                orgTree.setVisible(isViewTree);
            }
        });

        HorizontalLayout headerPanel = new HorizontalLayout();
        headerPanel.addStyleName("viewheader");
        headerPanel.setSpacing(true);
        Responsive.makeResponsive(headerPanel);

        HorizontalLayout condition = buildSearchForm();
        condition.addStyleName("toolbar");
        headerPanel.addComponent(condition);
        headerPanel.setExpandRatio(condition, 6.5f);

        HorizontalLayout tools = buildButtonForm();
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        headerPanel.addComponent(tools);
        headerPanel.setExpandRatio(tools, 3.0f);

        headerLayout.addComponent(btnHideTree);
        headerLayout.setExpandRatio(btnHideTree, 3.0f);
        headerLayout.setComponentAlignment(btnHideTree, Alignment.TOP_LEFT);
        headerLayout.addComponent(headerPanel);
        headerLayout.setExpandRatio(headerPanel, 7.0f);
        headerLayout.setComponentAlignment(headerPanel, Alignment.TOP_RIGHT);
        return headerLayout;
    }

    private Component buildResultLayout() {
        //HorizontalLayout contentLayout = new HorizontalLayout();
        HorizontalSplitPanel contentLayout = new HorizontalSplitPanel();
        contentLayout.addStyleName(ValoTheme.SPLITPANEL_LARGE);
        contentLayout.setWidth(100.0f, Unit.PERCENTAGE);
        contentLayout.addStyleName("profile-form");

//        loadTreeData();
        orgTree = new Tree();
        orgTree.setCaption(BundleUtils.getString("orgMngt.tree"));
        contentLayout.setFirstComponent(orgTree);
//        contentLayout.addComponent(orgTree);
//        contentLayout.setComponentAlignment(orgTree, Alignment.MIDDLE_LEFT);
//        contentLayout.setExpandRatio(orgTree, 2.5f);

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        pagedTable = new CustomGrid();
        tableLayout.addComponent(pagedTable);
        contentLayout.setSecondComponent(tableLayout);
        contentLayout.setSplitPosition(25, Unit.PERCENTAGE);
//        contentLayout.addComponent(tableLayout);
//        contentLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_RIGHT);
//        contentLayout.setExpandRatio(tableLayout, 7.5f);

        return contentLayout;
    }

    public CustomGrid createTable() {
        pagedTable = new CustomGrid();

        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);

        txtEmployeeType = new TextField();
        txtEmployeeType.setImmediate(true);
        txtEmployeeType.setInputPrompt(BundleUtils.getString("employeeMngt.list.employee"));
        txtEmployeeType.setWidth("100.0%");
        txtEmployeeType.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtEmployeeType);

        return searchForm;
    }

    private HorizontalLayout buildButtonForm() {
        HorizontalLayout buttonForm = new HorizontalLayout();
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
        buttonForm.addComponent(btnExport);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        buttonForm.addComponent(btnAdd);

        return buttonForm;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public TextField getTxtEmployeeType() {
        return txtEmployeeType;
    }

    public void setTxtEmployeeType(TextField txtEmployeeType) {
        this.txtEmployeeType = txtEmployeeType;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public Button getBtnExport() {
        return btnExport;
    }

    public void setBtnExport(Button btnExport) {
        this.btnExport = btnExport;
    }

    public CustomGrid getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGrid pagedTable) {
        this.pagedTable = pagedTable;
    }

    public List<Employee> getLstEmployees() {
        return lstEmployees;
    }

    public void setLstEmployees(List<Employee> lstEmployees) {
        this.lstEmployees = lstEmployees;
    }

    public Button getBtnHideTree() {
        return btnHideTree;
    }

    public void setBtnHideTree(Button btnHideTree) {
        this.btnHideTree = btnHideTree;
    }

    public Tree getOrgTree() {
        return orgTree;
    }

    public void setOrgTree(Tree orgTree) {
        this.orgTree = orgTree;
    }

    public boolean isIsViewTree() {
        return isViewTree;
    }

    public void setIsViewTree(boolean isViewTree) {
        this.isViewTree = isViewTree;
    }

    
}
