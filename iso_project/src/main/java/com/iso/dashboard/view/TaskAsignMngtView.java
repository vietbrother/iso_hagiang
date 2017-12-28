/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;

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
public class TaskAsignMngtView extends Panel implements View {

    public List<Organization> lstData = new ArrayList<>();
    public Map<String, Organization> map = new HashMap<>();
    
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    
    private Tree orgTree;

    public TaskAsignMngtView() {
        setSizeFull();
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.organizationMngt"));
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
//        new OrganizationMngtController(this);
    }

    private void loadTreeData() {
        for (int i = 0; i < 15; i++) {
            Organization org = new Organization("name " + i, 
                    "code_" + i, "value_" + i, String.valueOf(i), 0);
            org.setId(i);
            if (i == 3 || i == 6 || i == 9) {
                org.setParentId(1);
            }
            if (i == 2) {
                org.setParentId(3);
            }
            if (i > 3 && i < 6) {
                org.setParentId(3);
            }
            if (i > 6 && i < 9) {
                org.setParentId(6);
            }
            if (i > 9) {
                org.setParentId(9);
            }
            map.put(String.valueOf(i), org);
            lstData.add(org);
        }
    }

    private Component buildHeader() {
        CssLayout headerPanel = new CssLayout();
        headerPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(headerPanel);
         // btnAdd
        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setStyleName(ValoTheme.BUTTON_PRIMARY);
         // btnAdd
        btnUpdate = new Button();
        btnUpdate.setCaption(BundleUtils.getString("common.button.edit"));
        btnUpdate.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnUpdate.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnUpdate.setIcon(ISOIcons.EDIT);
        // btnDelete
        btnDelete = new Button();
        btnDelete.setCaption(BundleUtils.getString("common.button.delete"));
        btnDelete.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnDelete.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnDelete.setIcon(ISOIcons.CANCEL);

        headerPanel.addComponent(btnAdd);
        headerPanel.addComponent(btnUpdate);
        headerPanel.addComponent(btnDelete);
        return headerPanel;
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

//        loadTreeData();
        orgTree = new Tree();

        contenPanel.addComponent(orgTree);

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

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public Tree getOrgTree() {
        return orgTree;
    }

    public void setOrgTree(Tree orgTree) {
        this.orgTree = orgTree;
    }

    
}
