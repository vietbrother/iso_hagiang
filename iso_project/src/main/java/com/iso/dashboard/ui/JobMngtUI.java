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
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class JobMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private TextField txtJobTitle;
    private TextField txtSalaryGlone;
    private TextField txtSalaryWage;
    private TextField txtMinSalary;
    private TextField txtMaxSalary;
    private Button btnSave;
    private Button btnCancel;

    private String caption;

    public JobMngtUI(String caption) {
        this.caption = caption;
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
        mainLayout.setSizeUndefined();
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
        txtJobTitle = new TextField();
        txtJobTitle.setImmediate(true);
        txtJobTitle.setRequired(true);
        txtJobTitle.setWidth("100.0%");
        txtJobTitle.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtJobTitle.setRequired(true);
        txtJobTitle.setDescription(BundleUtils.getString("jobMngt.list.jobTitle"));
        txtJobTitle.setCaption(BundleUtils.getString("jobMngt.list.jobTitle"));
        txtJobTitle.setInputPrompt(BundleUtils.getString("jobMngt.list.jobTitle"));

        txtSalaryGlone = new TextField();
        txtSalaryGlone.setImmediate(true);
        txtSalaryGlone.setRequired(true);
        txtSalaryGlone.setWidth("100.0%");
        txtSalaryGlone.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtSalaryGlone.setRequired(true);
        txtSalaryGlone.setDescription(BundleUtils.getString("jobMngt.list.salaryGlone"));
        txtSalaryGlone.setCaption(BundleUtils.getString("jobMngt.list.salaryGlone"));
        txtSalaryGlone.setInputPrompt(BundleUtils.getString("jobMngt.list.salaryGlone"));

        txtSalaryWage = new TextField();
        txtSalaryWage.setImmediate(true);
        txtSalaryWage.setRequired(true);
        txtSalaryWage.setWidth("100.0%");
        txtSalaryWage.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtSalaryWage.setRequired(true);
        txtSalaryWage.setDescription(BundleUtils.getString("jobMngt.list.salaryWage"));
        txtSalaryWage.setCaption(BundleUtils.getString("jobMngt.list.salaryWage"));
        txtSalaryWage.setInputPrompt(BundleUtils.getString("jobMngt.list.salaryWage"));

        txtMinSalary = new TextField();
        txtMinSalary.setImmediate(true);
        txtMinSalary.setWidth("100.0%");
        txtMinSalary.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMinSalary.setDescription(BundleUtils.getString("jobMngt.list.minSalary"));
        txtMinSalary.setCaption(BundleUtils.getString("jobMngt.list.minSalary"));
        txtMinSalary.setInputPrompt(BundleUtils.getString("jobMngt.list.minSalary"));

        txtMaxSalary = new TextField();
        txtMaxSalary.setImmediate(true);
        txtMaxSalary.setWidth("100.0%");
        txtMaxSalary.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMaxSalary.setDescription(BundleUtils.getString("jobMngt.list.maxSalary"));
        txtMaxSalary.setCaption(BundleUtils.getString("jobMngt.list.maxSalary"));
        txtMaxSalary.setInputPrompt(BundleUtils.getString("jobMngt.list.maxSalary"));

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtJobTitle);
        details.addComponent(txtSalaryGlone);
        details.addComponent(txtSalaryWage);
        details.addComponent(txtMinSalary);
        details.addComponent(txtMaxSalary);

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

        return footer;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public TextField getTxtJobTitle() {
        return txtJobTitle;
    }

    public void setTxtJobTitle(TextField txtJobTitle) {
        this.txtJobTitle = txtJobTitle;
    }

    public TextField getTxtSalaryGlone() {
        return txtSalaryGlone;
    }

    public void setTxtSalaryGlone(TextField txtSalaryGlone) {
        this.txtSalaryGlone = txtSalaryGlone;
    }

    public TextField getTxtSalaryWage() {
        return txtSalaryWage;
    }

    public void setTxtSalaryWage(TextField txtSalaryWage) {
        this.txtSalaryWage = txtSalaryWage;
    }

    public TextField getTxtMinSalary() {
        return txtMinSalary;
    }

    public void setTxtMinSalary(TextField txtMinSalary) {
        this.txtMinSalary = txtMinSalary;
    }

    public TextField getTxtMaxSalary() {
        return txtMaxSalary;
    }

    public void setTxtMaxSalary(TextField txtMaxSalary) {
        this.txtMaxSalary = txtMaxSalary;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
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
