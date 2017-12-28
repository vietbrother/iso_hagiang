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
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author 
 */
public class SecurityLevelMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private PopupDateField pdBirthday;
    private TextField txtUsername;
    private TextField txtFullname;
    private TextField txtRole;
    private TextField txtEmail;
    private TextField txtMobile;
    private ComboBox cmbSex;
    private Button btnSave;
    private Button btnCancel;

    public SecurityLevelMngtUI() {
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        mainLayout.addComponent(buildContent());

//        // top-level component properties
//        setWidth("100.0%");
//        setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        //ver.addComponent(label);
//        CssLayout clFiels = new CssLayout();
//        clFiels.setWidth("100%");
//        Responsive.makeResponsive(clFiels);
//        clFiels.addComponent(fields);
//        mainLayout.addComponent(clFiels);
//        CssLayout clBtnLayout = new CssLayout();
//        clBtnLayout.setWidth("100%");
//        Responsive.makeResponsive(clBtnLayout);
//        clBtnLayout.addComponent(btnLayout);
//        mainLayout.addComponent(clBtnLayout);
////        mainLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
    }

    public Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildFieldsInfo());
        contenPanel.addComponent(buildButton());
        return contenPanel;
    }

    public Component buildFieldsInfo() {
        /**
         * create fields infomation
         */
        txtUsername = new TextField();
        txtUsername.setImmediate(true);
        txtUsername.setRequired(true);
        txtUsername.setWidth("100.0%");
        txtUsername.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtUsername.setRequired(true);
        txtUsername.setDescription(BundleUtils.getString("securityLevelMngt.list.name"));
        txtUsername.setCaption(BundleUtils.getString("securityLevelMngt.list.name"));

        txtEmail = new TextField();
        txtEmail.setImmediate(true);
        txtEmail.setRequired(true);
        txtEmail.setWidth("100.0%");
        txtEmail.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmail.setRequired(true);
        txtEmail.setDescription(BundleUtils.getString("securityLevelMngt.list.color"));
        txtEmail.setCaption(BundleUtils.getString("securityLevelMngt.list.color"));

        txtMobile = new TextField();
        txtMobile.setImmediate(true);
        txtMobile.setRequired(true);
        txtMobile.setWidth("100.0%");
        txtMobile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMobile.setRequired(true);
        txtMobile.setDescription(BundleUtils.getString("securityLevelMngt.list.description"));
        txtMobile.setCaption(BundleUtils.getString("securityLevelMngt.list.description"));

        txtFullname = new TextField();
        txtFullname.setImmediate(true);
        txtFullname.setRequired(true);
        txtFullname.setWidth("100.0%");
        txtFullname.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtFullname.setRequired(true);
        txtFullname.setDescription(BundleUtils.getString("securityLevelMngt.list.isActive"));
        txtFullname.setCaption(BundleUtils.getString("securityLevelMngt.list.isActive"));

        txtRole = new TextField();
        txtRole.setImmediate(true);
        txtRole.setRequired(true);
        txtRole.setWidth("100.0%");
        txtRole.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtRole.setRequired(true);
        txtRole.setDescription(BundleUtils.getString("userMngt.list.role"));
        txtRole.setCaption(BundleUtils.getString("userMngt.list.role"));

        pdBirthday = new PopupDateField();
        pdBirthday.setImmediate(true);
        pdBirthday.setWidth("100.0%");
        pdBirthday.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdBirthday.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdBirthday.setResolution(Resolution.SECOND);
        pdBirthday.setCaption(BundleUtils.getString("userMngt.list.birthDay"));

        
        cmbSex = new ComboBox();
        cmbSex.setCaption(BundleUtils.getString("userMngt.list.sex"));
        cmbSex.setImmediate(true);
        cmbSex.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setRequired(true);
        
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSpacing(true);
        row1.addStyleName("fields");
        row1.addComponents(txtUsername,
                txtEmail,
                txtMobile
        );
        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSpacing(true);
        row2.addStyleName("fields");
        row2.addComponents(txtFullname,
                txtRole,
                cmbSex
        );


        VerticalLayout fields = new VerticalLayout();
        fields.setSizeUndefined();
        fields.setSpacing(true);
        Responsive.makeResponsive(fields);
        fields.addComponent(row1);
        fields.addComponent(row2);
        fields.addComponent(pdBirthday);
        return fields;
    }

    public Component buildButton() {
        /**
         * Create button action
         */
        // btnSave
        btnSave = new Button();
        btnSave.setCaption(BundleUtils.getString("common.button.save"));
        btnSave.setImmediate(true);
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

        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setSpacing(true);
        btnLayout.setMargin(true);
        btnLayout.addStyleName("fields");
        btnLayout.addComponents(btnSave,
                btnCancel);

        return btnLayout;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public PopupDateField getPdBirthday() {
        return pdBirthday;
    }

    public void setPdBirthday(PopupDateField pdBirthday) {
        this.pdBirthday = pdBirthday;
    }

    public TextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(TextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public TextField getTxtFullname() {
        return txtFullname;
    }

    public void setTxtFullname(TextField txtFullname) {
        this.txtFullname = txtFullname;
    }

    public TextField getTxtRole() {
        return txtRole;
    }

    public void setTxtRole(TextField txtRole) {
        this.txtRole = txtRole;
    }



    public TextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(TextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public TextField getTxtMobile() {
        return txtMobile;
    }

    public void setTxtMobile(TextField txtMobile) {
        this.txtMobile = txtMobile;
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

    public ComboBox getCmbSex() {
        return cmbSex;
    }

    public void setCmbSex(ComboBox cmbSex) {
        this.cmbSex = cmbSex;
    }

    
}
