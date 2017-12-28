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
public class DocumentMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private PopupDateField pdBirthday;
    private TextField txtUsername;
    private TextField txtFullname;
    private TextField txtRole;
    private TextField txtEmail;
    private TextField txtMobile;
    private TextField txtparttime;
    private TextField txtdepartmenttime;
    private ComboBox cmbSex;
    private Button btnSave;
    private Button btnCancel;

    public DocumentMngtUI() {
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
        txtUsername.setDescription(BundleUtils.getString("documentMngt.list.code"));
        txtUsername.setCaption(BundleUtils.getString("documentMngt.list.code"));

        txtEmail = new TextField();
        txtEmail.setImmediate(true);
        txtEmail.setRequired(true);
        txtEmail.setWidth("100.0%");
        txtEmail.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmail.setRequired(true);
        txtEmail.setDescription(BundleUtils.getString("documentMngt.list.name"));
        txtEmail.setCaption(BundleUtils.getString("documentMngt.list.name"));

        txtMobile = new TextField();
        txtMobile.setImmediate(true);
        txtMobile.setRequired(true);
        txtMobile.setWidth("100.0%");
        txtMobile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMobile.setRequired(true);
        txtMobile.setDescription(BundleUtils.getString("documentMngt.list.type"));
        txtMobile.setCaption(BundleUtils.getString("documentMngt.list.type"));

        txtFullname = new TextField();
        txtFullname.setImmediate(true);
        txtFullname.setRequired(true);
        txtFullname.setWidth("100.0%");
        txtFullname.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtFullname.setRequired(true);
        txtFullname.setDescription(BundleUtils.getString("documentMngt.list.security"));
        txtFullname.setCaption(BundleUtils.getString("documentMngt.list.security"));               
        
        txtparttime = new TextField();
        txtparttime.setImmediate(true);
        txtparttime.setRequired(true);
        txtparttime.setWidth("100.0%");
        txtparttime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtparttime.setRequired(true);
        txtparttime.setDescription(BundleUtils.getString("documentMngt.list.parttime"));
        txtparttime.setCaption(BundleUtils.getString("documentMngt.list.parttime"));

        txtdepartmenttime = new TextField();
        txtdepartmenttime.setImmediate(true);
        txtdepartmenttime.setRequired(true);
        txtdepartmenttime.setWidth("100.0%");
        txtdepartmenttime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtdepartmenttime.setRequired(true);
        txtdepartmenttime.setDescription(BundleUtils.getString("documentMngt.list.departmenttime"));
        txtdepartmenttime.setCaption(BundleUtils.getString("documentMngt.list.departmenttime"));
        
        txtRole = new TextField();
        txtRole.setImmediate(true);
        txtRole.setRequired(true);
        txtRole.setWidth("100.0%");
        txtRole.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtRole.setRequired(true);
        txtRole.setDescription(BundleUtils.getString("documentMngt.list.status"));
        txtRole.setCaption(BundleUtils.getString("documentMngt.list.status"));

//        pdBirthday = new PopupDateField();
//        pdBirthday.setImmediate(true);
//        pdBirthday.setWidth("100.0%");
//        pdBirthday.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        pdBirthday.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
//        pdBirthday.setResolution(Resolution.SECOND);
//        pdBirthday.setCaption(BundleUtils.getString("userMngt.list.birthDay"));
//
//        
//        cmbSex = new ComboBox();
//        cmbSex.setCaption(BundleUtils.getString("userMngt.list.sex"));
//        cmbSex.setImmediate(true);
//        cmbSex.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        cmbSex.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cmbSex.setRequired(true);
        
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSpacing(true);
        row1.addStyleName("fields");
        row1.addComponents(txtUsername,
                txtEmail,
                txtMobile,
                txtRole
        );
        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSpacing(true);
        row2.addStyleName("fields");
        row2.addComponents(txtFullname,
                txtparttime,
                txtdepartmenttime
        );


        VerticalLayout fields = new VerticalLayout();
        fields.setSizeUndefined();
        fields.setSpacing(true);
        Responsive.makeResponsive(fields);
        fields.addComponent(row1);
        fields.addComponent(row2);
//        fields.addComponent(pdBirthday);
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

    public TextField getTxtparttime() {
        return txtparttime;
    }

    public void setTxtparttime(TextField txtparttime) {
        this.txtparttime = txtparttime;
    }

    public TextField getTxtdepartmenttime() {
        return txtdepartmenttime;
    }

    public void setTxtdepartmenttime(TextField txtdepartmenttime) {
        this.txtdepartmenttime = txtdepartmenttime;
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
