/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeProcessMngtUI extends VerticalLayout {

    private VerticalLayout mainLayout;

//    private HorizontalLayout horizontalLayout;
//    private TabSheet tabSheet;
    private TextField txtEmployeeType;
    private CheckBox cbxActive;
    private Button btnSave;
    private Button btnCancel;

    private String caption;

    public EmployeeProcessMngtUI(String caption) {
        this.caption = caption;
        buildMainLayout();
 
        
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(mainLayout);
        addComponent(horizontalLayout);
        
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        Component mainContent = buildContent();
        mainLayout.addComponent(mainContent);
        mainLayout.setExpandRatio(mainContent, 1.0f);

    }

    public Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildFieldsInfo());
        return contenPanel;
    }

    public Component buildFieldsInfo() {
        /**
         * create fields infomation
         */
        txtEmployeeType = new TextField();
        txtEmployeeType.setImmediate(true);
        txtEmployeeType.setRequired(true);
        txtEmployeeType.setWidth("100.0%");
        txtEmployeeType.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmployeeType.setRequired(true);
        txtEmployeeType.setDescription(BundleUtils.getString("employeeTypeMngt.list.employeeType"));
        txtEmployeeType.setCaption(BundleUtils.getString("employeeTypeMngt.list.employeeType"));
        txtEmployeeType.setInputPrompt(BundleUtils.getString("employeeTypeMngt.list.employeeType"));

        cbxActive = new CheckBox();
        cbxActive.setImmediate(true);
        cbxActive.setWidth("100.0%");
        cbxActive.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cbxActive.setDescription(BundleUtils.getString("employeeTypeMngt.list.active"));
        cbxActive.setCaption(BundleUtils.getString("employeeTypeMngt.list.active"));

        
        FormLayout formLayout = new FormLayout();
        formLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        formLayout.addComponent(txtEmployeeType);
        formLayout.addComponent(cbxActive);

        return formLayout;
    }

    public Component buildButton() {
        /**
         * Create button action
         */
        // btnSave
        btnSave = new Button();
        btnSave.setCaption(BundleUtils.getString("common.button.save"));
        btnSave.setImmediate(true);
        btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSave.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSave.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSave.setIcon(ISOIcons.SAVE);
        // btnCancel
        btnCancel = new Button();
        btnCancel.setCaption(BundleUtils.getString("common.button.cancel"));
        btnCancel.setImmediate(true);
        btnCancel.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setIcon(ISOIcons.CANCEL);

        HorizontalLayout temp = new HorizontalLayout();
        temp.setSpacing(true);
        temp.addStyleName("fields");
        temp.addComponents(btnSave,
                btnCancel
        );
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        footer.setSpacing(false);
        footer.addComponent(temp);
        footer.setComponentAlignment(temp, Alignment.BOTTOM_RIGHT);

        return footer;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public TextField getTxtEmployeeType() {
        return txtEmployeeType;
    }

    public void setTxtEmployeeType(TextField txtEmployeeType) {
        this.txtEmployeeType = txtEmployeeType;
    }

    public CheckBox getCbxActive() {
        return cbxActive;
    }

    public void setCbxActive(CheckBox cbxActive) {
        this.cbxActive = cbxActive;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(Button btnSave) {
        this.btnSave = btnSave;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
