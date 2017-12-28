/*
 * To change this license header, choose License Headers in Project Properties.
 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.component.CustomPageTable;
import com.iso.dashboard.controller.TaskAssignController;
import com.iso.dashboard.controller.TaskReportController;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAssignView extends TabSheet implements View {

    private Button btnAdd;
    private Button btnRefresh;

    private CustomPageTable pagedTable;

    public TaskAssignView(Integer taskId) {
        setSizeFull();
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.taskAssignMngt"));
        root.setWidth("98%");
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
        Responsive.makeResponsive(root);

        Component content = buildContent();
        addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        addComponent(root);

        root.addComponent(content);
        root.setExpandRatio(content, 1);
        new TaskAssignController(this, taskId);
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
//        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

//        Label titleLabel = new Label(BundleUtils.getStringCas("menu.userMngt"));
//        titleLabel.setSizeUndefined();
//        titleLabel.addStyleName(ValoTheme.LABEL_H1);
//        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//        contenPanel.addComponent(titleLabel);
        contenPanel.addComponent(buildToolbar());
        contenPanel.addComponent(buildResultLayout());

        return contenPanel;
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);


        HorizontalLayout tools = buildButtonForm();
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        header.setExpandRatio(tools, 3.0f);
        header.setComponentAlignment(tools, Alignment.BOTTOM_RIGHT);

        return header;
    }

    private Component buildResultLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");


        CustomPageTable table = createTable();
        mainLayout.addComponent(table);
        mainLayout.addComponent(table.createControls(Constants.DEFAULT_PAGING));

        return mainLayout;
    }

    public CustomPageTable createTable() {
        pagedTable = new CustomPageTable(
                BundleUtils.getString("common.result"));
        pagedTable.setWidth("100%");

        return pagedTable;
    }

    private HorizontalLayout buildButtonForm() {
        HorizontalLayout buttonForm = new HorizontalLayout();
        buttonForm.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        Responsive.makeResponsive(buttonForm);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAdd.addStyleName(ValoTheme.BUTTON_SMALL);
        buttonForm.addComponent(btnAdd);

        btnRefresh = new Button();
        btnRefresh.setCaption(BundleUtils.getString("common.button.refresh"));
        btnRefresh.setDescription(BundleUtils.getString("common.button.refresh"));
        btnRefresh.setIcon(ISOIcons.REFRESH);
        btnRefresh.setImmediate(true);
        btnRefresh.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnRefresh.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnRefresh.addStyleName(ValoTheme.BUTTON_SMALL);
        buttonForm.addComponent(btnRefresh);

        return buttonForm;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public Button getBtnRefresh() {
        return btnRefresh;
    }

    public void setBtnRefresh(Button btnRefresh) {
        this.btnRefresh = btnRefresh;
    }

 

    public CustomPageTable getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomPageTable pagedTable) {
        this.pagedTable = pagedTable;
    }

  
}
