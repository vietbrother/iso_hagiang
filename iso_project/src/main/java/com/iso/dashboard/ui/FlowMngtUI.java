/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataWrapper;
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private TextField txtCode;
    private TextField txtName;
    private TextArea txaDescription;
    private ComboBox cmbStatus;
    private Button btnSave;
    private Button btnEdit;
    private Button btnStep;
    private Button btnGraph;
    private Button btnCancel;
    private DataWrapper wrapper;

    private String caption;

    public FlowMngtUI(String caption) {
        setSizeFull();
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
        mainLayout.setWidth("100%");
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
        contenPanel.setWidth("100%");
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
        txtCode.setDescription(BundleUtils.getString("flowMngt.list.code"));
        txtCode.setCaption(BundleUtils.getString("flowMngt.list.code"));
        txtCode.setInputPrompt(BundleUtils.getString("flowMngt.list.code"));

        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setRequired(true);
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtName.setRequired(true);
        txtName.setDescription(BundleUtils.getString("flowMngt.list.name"));
        txtName.setCaption(BundleUtils.getString("flowMngt.list.name"));
        txtName.setInputPrompt(BundleUtils.getString("flowMngt.list.name"));


        wrapper = new DataWrapper("");
        wrapper.setCaption(BundleUtils.getString("flowProcedure.addProc"));
        wrapper.setImmediate(true);
        wrapper.setWidth("100.0%");
        wrapper.setHeight(Constants.STYLE_CONF.AUTO_VALUE);


        txaDescription = new TextArea();
        txaDescription.setImmediate(true);
        txaDescription.setRequired(true);
        txaDescription.setWidth("100.0%");
        txaDescription.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaDescription.setRequired(true);
        txaDescription.setDescription(BundleUtils.getString("flowMngt.list.description"));
        txaDescription.setCaption(BundleUtils.getString("flowMngt.list.description"));
        txaDescription.setInputPrompt(BundleUtils.getString("flowMngt.list.description"));

        cmbStatus = new ComboBox();
        cmbStatus.setCaption(BundleUtils.getString("flowMngt.list.status"));
        cmbStatus.setImmediate(true);
        cmbStatus.setRequired(true);
        cmbStatus.setNullSelectionAllowed(false);

        FormLayout details = new FormLayout();
        details.setWidth("100%");
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtCode);
        details.addComponent(txtName);
        details.addComponent(txaDescription);
        details.addComponent(wrapper);
        details.addComponent(cmbStatus);

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

        btnEdit = new Button();
        btnEdit.setCaption(BundleUtils.getString("common.button.edit"));
        btnEdit.setImmediate(true);
        btnEdit.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnEdit.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnEdit.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnEdit.setIcon(ISOIcons.EDIT);

        btnStep = new Button();
        btnStep.setCaption(BundleUtils.getString("flowStep.list"));
        btnStep.setImmediate(true);
        btnStep.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnStep.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnStep.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnStep.setIcon(ISOIcons.SAVE);

        btnGraph = new Button();
        btnGraph.setCaption(BundleUtils.getString("flowStep.graph"));
        btnGraph.setImmediate(true);
        btnGraph.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnGraph.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnGraph.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnGraph.setIcon(ISOIcons.SAVE);
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
        temp.addComponents(btnEdit, btnSave, btnStep, btnGraph, btnCancel);
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
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

    public ComboBox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(ComboBox cmbStatus) {
        this.cmbStatus = cmbStatus;
    }

    public DataWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(DataWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(Button btnEdit) {
        this.btnEdit = btnEdit;
    }

    public Button getBtnStep() {
        return btnStep;
    }

    public void setBtnStep(Button btnStep) {
        this.btnStep = btnStep;
    }

    public void setEditFlow(Boolean editable) {
        btnGraph.setVisible(!editable);
        btnEdit.setVisible(!editable);
        btnStep.setVisible(!editable);
        btnSave.setVisible(editable);
    }

    public Button getBtnGraph() {
        return btnGraph;
    }

    public void setBtnGraph(Button btnGraph) {
        this.btnGraph = btnGraph;
    }

}
