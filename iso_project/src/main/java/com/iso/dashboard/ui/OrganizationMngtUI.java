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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class OrganizationMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private TextField txtName;
    private TextField txtCode;
    private TextField txtValue;
    private TextField txtPosition;
    private TextArea txaDescription;
    private ComboBox cmbStatus;
    private ComboBox cmbParent;
    private Button btnSave;
    private Button btnCancel;
    
    private String caption;

    public OrganizationMngtUI(String caption) {
//        buildMainLayout();
//        setCompositionRoot(mainLayout);
        this.caption = caption;
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);
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
        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setRequired(true);
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtName.setRequired(true);
        txtName.setDescription(BundleUtils.getString("orgMngt.list.name"));
        txtName.setCaption(BundleUtils.getString("orgMngt.list.name"));
        txtName.setInputPrompt(BundleUtils.getString("orgMngt.list.name"));

        txtCode = new TextField();
        txtCode.setImmediate(true);
        txtCode.setRequired(true);
        txtCode.setWidth("100.0%");
        txtCode.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtCode.setRequired(true);
        txtCode.setDescription(BundleUtils.getString("orgMngt.list.code"));
        txtCode.setCaption(BundleUtils.getString("orgMngt.list.code"));
        txtCode.setInputPrompt(BundleUtils.getString("orgMngt.list.code"));

        txtValue = new TextField();
        txtValue.setImmediate(true);
        txtValue.setRequired(true);
        txtValue.setWidth("100.0%");
        txtValue.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtValue.setRequired(true);
        txtValue.setDescription(BundleUtils.getString("orgMngt.list.value"));
        txtValue.setCaption(BundleUtils.getString("orgMngt.list.value"));
        txtValue.setInputPrompt(BundleUtils.getString("orgMngt.list.value"));

        txtPosition = new TextField();
        txtPosition.setImmediate(true);
        txtPosition.setRequired(true);
        txtPosition.setWidth("100.0%");
        txtPosition.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtPosition.setRequired(true);
        txtPosition.setDescription(BundleUtils.getString("orgMngt.list.position"));
        txtPosition.setCaption(BundleUtils.getString("orgMngt.list.position"));
        txtPosition.setInputPrompt(BundleUtils.getString("orgMngt.list.position"));

        txaDescription = new TextArea();
        txaDescription.setImmediate(true);
        txaDescription.setRequired(true);
        txaDescription.setWidth("100.0%");
        txaDescription.setHeight(String.valueOf(UI.getCurrent().getWidth() * 0.75));
        txaDescription.setRequired(true);
        txaDescription.setDescription(BundleUtils.getString("orgMngt.list.description"));
        txaDescription.setCaption(BundleUtils.getString("orgMngt.list.description"));
        txaDescription.setInputPrompt(BundleUtils.getString("orgMngt.list.description"));

        cmbParent = new ComboBox();
        cmbParent.setCaption(BundleUtils.getString("orgMngt.list.parent"));
        cmbParent.setImmediate(true);
        cmbParent.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbParent.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbParent.setRequired(true);

        cmbStatus = new ComboBox();
        cmbStatus.setCaption(BundleUtils.getString("orgMngt.list.sex"));
        cmbStatus.setImmediate(true);
        cmbStatus.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbStatus.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbStatus.setRequired(true);

//        HorizontalLayout row1 = new HorizontalLayout();
//        row1.setSpacing(true);
//        row1.addStyleName("fields");
//        row1.addComponents(txtName,
//                txtCode,
//                txtValue
//        );
//        HorizontalLayout row2 = new HorizontalLayout();
//        row2.setSpacing(true);
//        row2.addStyleName("fields");
//        row2.addComponents(txtPosition,
////                cmbParent,
//                cmbStatus
//        );
//        HorizontalLayout row3 = new HorizontalLayout();
//        row3.setSpacing(true);
//        row3.addStyleName("fields");
//        row3.addComponents(txaDescription);
//
//
//        VerticalLayout fields = new VerticalLayout();
//        fields.setSizeUndefined();
//        fields.setSpacing(true);
//        Responsive.makeResponsive(fields);
//        fields.addComponent(row1);
//        fields.addComponent(row2);
//        fields.addComponent(row3);
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtName);
        details.addComponent(txtCode);
        details.addComponent(txtValue);
        details.addComponent(txtPosition);
        details.addComponent(cmbStatus);
        details.addComponent(txaDescription);

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
        btnSave.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSave.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSave.setIcon(ISOIcons.SAVE);
        btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
        // btnCancel
        btnCancel = new Button();
        btnCancel.setCaption(BundleUtils.getString("common.button.cancel"));
        btnCancel.setImmediate(true);
        btnCancel.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setIcon(ISOIcons.CANCEL);

        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setSpacing(true);
        btnLayout.addStyleName("fields");
        btnLayout.addComponents(btnSave,
                btnCancel);
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);
        footer.addComponent(btnLayout);
        footer.setComponentAlignment(btnLayout, Alignment.BOTTOM_RIGHT);

        return footer;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtName) {
        this.txtName = txtName;
    }

    public TextField getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(TextField txtCode) {
        this.txtCode = txtCode;
    }

    public TextField getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(TextField txtValue) {
        this.txtValue = txtValue;
    }

    public TextField getTxtPosition() {
        return txtPosition;
    }

    public void setTxtPosition(TextField txtPosition) {
        this.txtPosition = txtPosition;
    }

    public TextArea getTxaDescription() {
        return txaDescription;
    }

    public void setTxaDescription(TextArea txaDescription) {
        this.txaDescription = txaDescription;
    }

    public ComboBox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(ComboBox cmbStatus) {
        this.cmbStatus = cmbStatus;
    }

    public ComboBox getCmbParent() {
        return cmbParent;
    }

    public void setCmbParent(ComboBox cmbParent) {
        this.cmbParent = cmbParent;
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

}
