/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class UserMngtUI extends CustomComponent {

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
    
    private OptionGroup viewFunction;

    private String caption;
    public UserMngtUI(String caption) {
//        addStyleName("profile-window");
//        Responsive.makeResponsive(this);
////        setResizable(false);
////        setClosable(true);
//        setHeight(90.0f, Unit.PERCENTAGE);
        this.caption = caption;
        setSizeFull();
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);
//        content.addComponent(detailsWrapper);
//        content.setExpandRatio(detailsWrapper, 1f);
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setCaption(this.caption);
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        Component mainContent = buildContent();
        mainLayout.addComponent(mainContent);
        mainLayout.setExpandRatio(mainContent, 1.0f);
        mainLayout.addComponent(buildButton());

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
//        contenPanel.addComponent(buildButton());
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
        txtUsername.setDescription(BundleUtils.getString("userMngt.list.username"));
        txtUsername.setCaption(BundleUtils.getString("userMngt.list.username"));
        txtUsername.setInputPrompt(BundleUtils.getString("userMngt.list.username"));

        txtEmail = new TextField();
        txtEmail.setImmediate(true);
        txtEmail.setRequired(true);
        txtEmail.setWidth("100.0%");
        txtEmail.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmail.setRequired(true);
        txtEmail.setDescription(BundleUtils.getString("userMngt.list.email"));
        txtEmail.setCaption(BundleUtils.getString("userMngt.list.email"));
        txtEmail.setInputPrompt(BundleUtils.getString("userMngt.list.email"));

        txtMobile = new TextField();
        txtMobile.setImmediate(true);
        txtMobile.setRequired(true);
        txtMobile.setWidth("100.0%");
        txtMobile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMobile.setRequired(true);
        txtMobile.setDescription(BundleUtils.getString("userMngt.list.mobile"));
        txtMobile.setCaption(BundleUtils.getString("userMngt.list.mobile"));
        txtMobile.setInputPrompt(BundleUtils.getString("userMngt.list.mobile"));

        txtFullname = new TextField();
        txtFullname.setImmediate(true);
        txtFullname.setRequired(true);
        txtFullname.setWidth("100.0%");
        txtFullname.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtFullname.setRequired(true);
        txtFullname.setDescription(BundleUtils.getString("userMngt.list.fullname"));
        txtFullname.setCaption(BundleUtils.getString("userMngt.list.fullname"));
        txtFullname.setInputPrompt(BundleUtils.getString("userMngt.list.fullname"));

        txtRole = new TextField();
        txtRole.setImmediate(true);
        txtRole.setRequired(true);
        txtRole.setWidth("100.0%");
        txtRole.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtRole.setRequired(true);
        txtRole.setDescription(BundleUtils.getString("userMngt.list.role"));
        txtRole.setCaption(BundleUtils.getString("userMngt.list.role"));
        txtRole.setInputPrompt(BundleUtils.getString("userMngt.list.role"));

        pdBirthday = new PopupDateField();
        pdBirthday.setImmediate(true);
        pdBirthday.setWidth("100.0%");
        pdBirthday.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdBirthday.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdBirthday.setResolution(Resolution.SECOND);
        pdBirthday.setCaption(BundleUtils.getString("userMngt.list.birthDay"));
        pdBirthday.setInputPrompt(BundleUtils.getString("userMngt.list.birthDay"));

        cmbSex = new ComboBox();
        cmbSex.setCaption(BundleUtils.getString("userMngt.list.sex"));
        cmbSex.setImmediate(true);
        cmbSex.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setRequired(true);
        
        viewFunction = new OptionGroup(BundleUtils.getString("userMngt.list.role"));
        viewFunction.setMultiSelect(true);

        FormLayout details = new FormLayout();
        details.setWidth("100%");
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtUsername);
        details.addComponent(txtFullname);
        details.addComponent(txtEmail);
        details.addComponent(txtMobile);
        details.addComponent(txtRole);
        details.addComponent(pdBirthday);
        details.addComponent(cmbSex);
        details.addComponent(viewFunction);

        return details;
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
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);
        footer.addComponent(temp);
        footer.setComponentAlignment(temp, Alignment.BOTTOM_RIGHT);
//        footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);
//        footer.addComponent(btnCancel);
//        footer.setComponentAlignment(btnSave, Alignment.TOP_LEFT);
//        btnLayout.setSpacing(true);
//        btnLayout.setMargin(true);
//        btnLayout.addStyleName("fields");
//        btnLayout.addComponents(btnSave,
//                btnCancel);

        return footer;
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

    public OptionGroup getViewFunction() {
        return viewFunction;
    }

    public void setViewFunction(OptionGroup viewFunction) {
        this.viewFunction = viewFunction;
    }

    
}
