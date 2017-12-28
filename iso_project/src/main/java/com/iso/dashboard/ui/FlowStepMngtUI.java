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
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
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
public class FlowStepMngtUI extends VerticalLayout {

    private VerticalLayout mainLayout;

//    private HorizontalLayout horizontalLayout;
//    private TabSheet tabSheet;
    private StepSearchUI txtStep;
    private TreeEmpSearchUI txtDefEmp;
    private TreeEmpSearchUI txtBackupEmp;
    private TreeEmpSearchUI txtSuppportEmp;
    private TextField txtStepOrder;
    private StepSearchUI txtSubBranch;

    private CheckBox cbx1;
    private CheckBox cbx2;
    private CheckBox cbx3;
    private CheckBox cbx4;
    private CheckBox cbx5;
    private CheckBox cbx6;
    private CheckBox cbx7;
    private CheckBox cbx8;
    private CheckBox cbx9;
    private CheckBox cbx10;
    private CheckBox cbx11;
    private CheckBox cbx12;
    private Button btnSave;
    private Button btnCancel;

    private String caption;

    public FlowStepMngtUI(String caption) {
        this.caption = caption;
        
        mainLayout = new VerticalLayout();
        mainLayout.setCaption(this.caption);
        mainLayout.setIcon(FontAwesome.CALENDAR);
        //this.setIcon(FontAwesome.CALENDAR);
        mainLayout.addComponent(buildFieldsInfo());
        mainLayout.addComponent(buildButton());
        
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        //detailsWrapper.setCaption(this.caption);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(mainLayout);
        addComponent(detailsWrapper);
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
        txtStep = new StepSearchUI(BundleUtils.getString("stepMngt.list.name"));
        txtStep.setImmediate(true);
        txtStep.setWidth("100.0%");
        txtStep.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtStep.setDescription(BundleUtils.getString("stepMngt.list.name"));

        txtSubBranch = new StepSearchUI(BundleUtils.getString("flowStep.list.branch"));
        txtSubBranch.setImmediate(true);
        txtSubBranch.setWidth("100.0%");
        txtSubBranch.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtSubBranch.setDescription(BundleUtils.getString("flowStep.list.branch"));

        txtStepOrder = new TextField();
        txtStepOrder.setImmediate(true);
        txtStepOrder.setWidth("100.0%");
        txtStepOrder.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtStepOrder.setRequired(true);
        txtStepOrder.setDescription(BundleUtils.getString("stepMngt.list.position"));
        txtStepOrder.setCaption(BundleUtils.getString("stepMngt.list.position"));
        txtStepOrder.setInputPrompt(BundleUtils.getString("stepMngt.list.position"));

        txtDefEmp = new TreeEmpSearchUI(BundleUtils.getString("flowStep.list.defEmp"));
        txtDefEmp.setImmediate(true);
        txtDefEmp.setWidth("100.0%");
        txtDefEmp.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtDefEmp.setDescription(BundleUtils.getString("flowStep.list.defEmp"));
        
        txtBackupEmp = new TreeEmpSearchUI(BundleUtils.getString("flowStep.list.backEmp"));
        txtBackupEmp.setImmediate(true);
        txtBackupEmp.setWidth("100.0%");
        txtBackupEmp.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtBackupEmp.setDescription(BundleUtils.getString("flowStep.list.backEmp"));
        
        txtSuppportEmp = new TreeEmpSearchUI(BundleUtils.getString("flowStep.list.supEmp"));
        txtSuppportEmp.setImmediate(true);
        txtSuppportEmp.setWidth("100.0%");
        txtSuppportEmp.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtSuppportEmp.setDescription(BundleUtils.getString("flowStep.list.supEmp"));
        
        cbx1 = new CheckBox(BundleUtils.getString("flowStep.function1"));
        cbx2 = new CheckBox(BundleUtils.getString("flowStep.function2"));
        cbx3 = new CheckBox(BundleUtils.getString("flowStep.function3"));
        cbx4 = new CheckBox(BundleUtils.getString("flowStep.function4"));
        cbx5 = new CheckBox(BundleUtils.getString("flowStep.function5"));
        cbx6 = new CheckBox(BundleUtils.getString("flowStep.function6"));
        cbx7 = new CheckBox(BundleUtils.getString("flowStep.function7"));
        cbx8 = new CheckBox(BundleUtils.getString("flowStep.function8"));
        cbx9 = new CheckBox(BundleUtils.getString("flowStep.function9"));
        cbx10 = new CheckBox(BundleUtils.getString("flowStep.function10"));
        cbx11 = new CheckBox(BundleUtils.getString("flowStep.function11"));
        cbx12 = new CheckBox(BundleUtils.getString("flowStep.function12"));

        HorizontalLayout grid = new HorizontalLayout();
        grid.setCaption("Thông tin chung");
        grid.setSizeFull();
        grid.setSpacing(true);
        FormLayout subFrm1 = new FormLayout();
        subFrm1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFrm1.addComponent(txtStep);
        subFrm1.addComponent(txtSubBranch);
        subFrm1.addComponent(txtStepOrder);
        subFrm1.addComponent(cbx1);
        subFrm1.addComponent(cbx2);
        subFrm1.addComponent(cbx3);
        subFrm1.addComponent(cbx4);
        subFrm1.addComponent(cbx5);
        subFrm1.addComponent(cbx6);
        
        grid.addComponent(subFrm1);

        FormLayout subFrm2 = new FormLayout();
        subFrm2.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFrm2.addComponent(txtDefEmp);
        subFrm2.addComponent(txtBackupEmp);
        subFrm2.addComponent(txtSuppportEmp);
        subFrm2.addComponent(cbx7);
        subFrm2.addComponent(cbx8);
        subFrm2.addComponent(cbx9);
        subFrm2.addComponent(cbx10);
        subFrm2.addComponent(cbx11);
        subFrm2.addComponent(cbx12);
        grid.addComponent(subFrm2);

        return grid;
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

    public StepSearchUI getTxtStep() {
        return txtStep;
    }

    public void setTxtStep(StepSearchUI txtStep) {
        this.txtStep = txtStep;
    }

    public TreeEmpSearchUI getTxtDefEmp() {
        return txtDefEmp;
    }

    public void setTxtDefEmp(TreeEmpSearchUI txtDefEmp) {
        this.txtDefEmp = txtDefEmp;
    }

    public TreeEmpSearchUI getTxtBackupEmp() {
        return txtBackupEmp;
    }

    public void setTxtBackupEmp(TreeEmpSearchUI txtBackupEmp) {
        this.txtBackupEmp = txtBackupEmp;
    }

    public TreeEmpSearchUI getTxtSuppportEmp() {
        return txtSuppportEmp;
    }

    public void setTxtSuppportEmp(TreeEmpSearchUI txtSuppportEmp) {
        this.txtSuppportEmp = txtSuppportEmp;
    }

    public TextField getTxtStepOrder() {
        return txtStepOrder;
    }

    public void setTxtStepOrder(TextField txtStepOrder) {
        this.txtStepOrder = txtStepOrder;
    }

    public StepSearchUI getTxtSubBranch() {
        return txtSubBranch;
    }

    public void setTxtSubBranch(StepSearchUI txtSubBranch) {
        this.txtSubBranch = txtSubBranch;
    }

    public CheckBox getCbx1() {
        return cbx1;
    }

    public void setCbx1(CheckBox cbx1) {
        this.cbx1 = cbx1;
    }

    public CheckBox getCbx2() {
        return cbx2;
    }

    public void setCbx2(CheckBox cbx2) {
        this.cbx2 = cbx2;
    }

    public CheckBox getCbx3() {
        return cbx3;
    }

    public void setCbx3(CheckBox cbx3) {
        this.cbx3 = cbx3;
    }

    public CheckBox getCbx4() {
        return cbx4;
    }

    public void setCbx4(CheckBox cbx4) {
        this.cbx4 = cbx4;
    }

    public CheckBox getCbx5() {
        return cbx5;
    }

    public void setCbx5(CheckBox cbx5) {
        this.cbx5 = cbx5;
    }

    public CheckBox getCbx6() {
        return cbx6;
    }

    public void setCbx6(CheckBox cbx6) {
        this.cbx6 = cbx6;
    }

    public CheckBox getCbx7() {
        return cbx7;
    }

    public void setCbx7(CheckBox cbx7) {
        this.cbx7 = cbx7;
    }

    public CheckBox getCbx8() {
        return cbx8;
    }

    public void setCbx8(CheckBox cbx8) {
        this.cbx8 = cbx8;
    }

    public CheckBox getCbx9() {
        return cbx9;
    }

    public void setCbx9(CheckBox cbx9) {
        this.cbx9 = cbx9;
    }

    public CheckBox getCbx10() {
        return cbx10;
    }

    public void setCbx10(CheckBox cbx10) {
        this.cbx10 = cbx10;
    }

    public CheckBox getCbx11() {
        return cbx11;
    }

    public void setCbx11(CheckBox cbx11) {
        this.cbx11 = cbx11;
    }

    public CheckBox getCbx12() {
        return cbx12;
    }

    public void setCbx12(CheckBox cbx12) {
        this.cbx12 = cbx12;
    }
    
    
}
