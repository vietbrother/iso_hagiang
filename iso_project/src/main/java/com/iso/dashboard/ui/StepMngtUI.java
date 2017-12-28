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
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author VIET_BROTHER
 */
public class StepMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private PopupDateField pdCreatedTime;
    private PopupDateField pdUpdatedTime;
    private TextField txtCode;
    private TextField txtName;
    private TextArea txaDescription;
    private TextField txtTimeExecute;
    private TextField txtPosition;
    private TextField txtCreatedBy;
    private TextField txtUpdatedBy;
    private Button btnSave;
    private Button btnCancel;

    private String caption;
    public StepMngtUI(String caption) {
        this.caption = caption;
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
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
        txtCode = new TextField();
        txtCode.setImmediate(true);
        txtCode.setRequired(true);
        txtCode.setWidth("100.0%");
        txtCode.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtCode.setRequired(true);
        txtCode.setDescription(BundleUtils.getString("stepMngt.list.code"));
        txtCode.setCaption(BundleUtils.getString("stepMngt.list.code"));
        txtCode.setInputPrompt(BundleUtils.getString("stepMngt.list.code"));
        
        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setRequired(true);
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtName.setRequired(true);
        txtName.setDescription(BundleUtils.getString("stepMngt.list.name"));
        txtName.setCaption(BundleUtils.getString("stepMngt.list.name"));
        txtName.setInputPrompt(BundleUtils.getString("stepMngt.list.name"));


        txaDescription = new TextArea();
        txaDescription.setImmediate(true);
        txaDescription.setRequired(true);
        txaDescription.setWidth("100.0%");
        txaDescription.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaDescription.setRequired(true);
        txaDescription.setDescription(BundleUtils.getString("stepMngt.list.description"));
        txaDescription.setCaption(BundleUtils.getString("stepMngt.list.description"));
        txaDescription.setInputPrompt(BundleUtils.getString("stepMngt.list.description"));

        txtTimeExecute = new TextField();
        txtTimeExecute.setImmediate(true);
        txtTimeExecute.setRequired(true);
        txtTimeExecute.setWidth("100.0%");
        txtTimeExecute.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtTimeExecute.setRequired(true);
        txtTimeExecute.setDescription(BundleUtils.getString("stepMngt.list.timeExecute"));
        txtTimeExecute.setCaption(BundleUtils.getString("stepMngt.list.timeExecute"));
        txtTimeExecute.setInputPrompt(BundleUtils.getString("stepMngt.list.timeExecute"));

        txtPosition = new TextField();
        txtPosition.setImmediate(true);
        txtPosition.setRequired(true);
        txtPosition.setWidth("100.0%");
        txtPosition.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtPosition.setRequired(true);
        txtPosition.setDescription(BundleUtils.getString("stepMngt.list.position"));
        txtPosition.setCaption(BundleUtils.getString("stepMngt.list.position"));
        txtPosition.setInputPrompt(BundleUtils.getString("stepMngt.list.position"));

        txtCreatedBy = new TextField();
        txtCreatedBy.setImmediate(true);
        txtCreatedBy.setRequired(true);
        txtCreatedBy.setWidth("100.0%");
        txtCreatedBy.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtCreatedBy.setRequired(true);
        txtCreatedBy.setDescription(BundleUtils.getString("stepMngt.list.createdBy"));
        txtCreatedBy.setCaption(BundleUtils.getString("stepMngt.list.createdBy"));
        txtCreatedBy.setInputPrompt(BundleUtils.getString("stepMngt.list.createdBy"));

        txtUpdatedBy = new TextField();
        txtUpdatedBy.setImmediate(true);
        txtUpdatedBy.setRequired(true);
        txtUpdatedBy.setWidth("100.0%");
        txtUpdatedBy.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtUpdatedBy.setRequired(true);
        txtUpdatedBy.setDescription(BundleUtils.getString("stepMngt.list.updatedBy"));
        txtUpdatedBy.setCaption(BundleUtils.getString("stepMngt.list.updatedBy"));
        txtUpdatedBy.setInputPrompt(BundleUtils.getString("stepMngt.list.updatedBy"));

        pdCreatedTime = new PopupDateField();
        pdCreatedTime.setImmediate(true);
        pdCreatedTime.setWidth("100.0%");
        pdCreatedTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdCreatedTime.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdCreatedTime.setResolution(Resolution.SECOND);
        pdCreatedTime.setCaption(BundleUtils.getString("stepMngt.list.createdTime"));
        pdCreatedTime.setInputPrompt(BundleUtils.getString("stepMngt.list.createdTime"));
        pdCreatedTime.setValue(new Date());

        pdUpdatedTime = new PopupDateField();
        pdUpdatedTime.setImmediate(true);
        pdUpdatedTime.setWidth("100.0%");
        pdUpdatedTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdUpdatedTime.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdUpdatedTime.setResolution(Resolution.SECOND);
        pdUpdatedTime.setCaption(BundleUtils.getString("stepMngt.list.updatedTime"));
        pdUpdatedTime.setInputPrompt(BundleUtils.getString("stepMngt.list.updatedTime"));
        pdUpdatedTime.setValue(new Date());


        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtCode);
        details.addComponent(txtName);
        details.addComponent(txaDescription);
        details.addComponent(txtTimeExecute);
        details.addComponent(txtPosition);
        details.addComponent(txtCreatedBy);
        details.addComponent(pdCreatedTime);
        details.addComponent(txtUpdatedBy);
        details.addComponent(pdUpdatedTime);

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

    public PopupDateField getPdCreatedTime() {
        return pdCreatedTime;
    }

    public void setPdCreatedTime(PopupDateField pdCreatedTime) {
        this.pdCreatedTime = pdCreatedTime;
    }

    public PopupDateField getPdUpdatedTime() {
        return pdUpdatedTime;
    }

    public void setPdUpdatedTime(PopupDateField pdUpdatedTime) {
        this.pdUpdatedTime = pdUpdatedTime;
    }

    public TextField getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(TextField txtCode) {
        this.txtCode = txtCode;
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtName) {
        this.txtName = txtName;
    }

    public TextArea getTxaDescription() {
        return txaDescription;
    }

    public void setTxaDescription(TextArea txaDescription) {
        this.txaDescription = txaDescription;
    }

    public TextField getTxtTimeExecute() {
        return txtTimeExecute;
    }

    public void setTxtTimeExecute(TextField txtTimeExecute) {
        this.txtTimeExecute = txtTimeExecute;
    }

    public TextField getTxtPosition() {
        return txtPosition;
    }

    public void setTxtPosition(TextField txtPosition) {
        this.txtPosition = txtPosition;
    }

    public TextField getTxtCreatedBy() {
        return txtCreatedBy;
    }

    public void setTxtCreatedBy(TextField txtCreatedBy) {
        this.txtCreatedBy = txtCreatedBy;
    }

    public TextField getTxtUpdatedBy() {
        return txtUpdatedBy;
    }

    public void setTxtUpdatedBy(TextField txtUpdatedBy) {
        this.txtUpdatedBy = txtUpdatedBy;
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
