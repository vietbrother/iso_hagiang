/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.controller.EmployeeEducationMngtController;
import com.iso.dashboard.dto.EmployeeEducation;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeEducationMngtView extends VerticalLayout implements View {

    private TextField txtEmployeeEducation;
//    private TextField txtStaffCode;
//    private ComboBox cmbStatus;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;

    private HorizontalLayout header;
    private CustomGrid pagedTable;

    public List<EmployeeEducation> employeeEducations = new ArrayList<>();

    public EmployeeEducationMngtView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
        root.addStyleName(ValoTheme.PANEL_BORDERLESS);
        Responsive.makeResponsive(root);

        Component content = buildContent();
        setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        addComponent(root);
        new EmployeeEducationMngtController(this);
    }
    
    public EmployeeEducationMngtView(int employeeId) {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
        root.addStyleName(ValoTheme.PANEL_BORDERLESS);
        Responsive.makeResponsive(root);

        Component content = buildContent();
        setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        addComponent(root);
        new EmployeeEducationMngtController(this);
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        header = buildToolbar();
        contenPanel.addComponent(header);
        contenPanel.addComponent(buildResultLayout());

        return contenPanel;
    }

    private HorizontalLayout buildToolbar() {
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

        for (int i = 0; i < 240; i++) {
            EmployeeEducation et = new EmployeeEducation();
            et.setId(i + 1);
            employeeEducations.add(et);
        }
        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        CustomGrid table = createTable();
        tableLayout.addComponent(table);
//        tableLayout.addComponent(table.createControls("10"));
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_CENTER);

        return mainLayout;
    }

    private CustomGrid createTable() {
        pagedTable = new CustomGrid();

        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);

        txtEmployeeEducation = new TextField();
        txtEmployeeEducation.setImmediate(true);
        txtEmployeeEducation.setInputPrompt(BundleUtils.getString("common.button.search"));
        txtEmployeeEducation.setWidth("100.0%");
        txtEmployeeEducation.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtEmployeeEducation);

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

    public TextField getTxtEmployeeEducation() {
        return txtEmployeeEducation;
    }

    public void setTxtEmployeeEducation(TextField txtEmployeeEducation) {
        this.txtEmployeeEducation = txtEmployeeEducation;
    }

    public List<EmployeeEducation> getEmployeeEducations() {
        return employeeEducations;
    }

    public void setEmployeeEducations(List<EmployeeEducation> employeeEducations) {
        this.employeeEducations = employeeEducations;
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

    private Component searchLayout = buildSearchForm();
    private Component buttonLayout = buildButtonForm();
    private Component toolbarLayout = buildToolbar();

    public Component getSearchLayout() {
        return searchLayout;
    }

    public void setSearchLayout(Component searchLayout) {
        this.searchLayout = searchLayout;
    }

    public Component getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(Component buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

    public Component getToolbarLayout() {
        return toolbarLayout;
    }

    public void setToolbarLayout(Component toolbarLayout) {
        this.toolbarLayout = toolbarLayout;
    }

    public HorizontalLayout getHeader() {
        return header;
    }

    public void setHeader(HorizontalLayout header) {
        this.header = header;
    }

}
