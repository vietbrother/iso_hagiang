/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
import com.iso.dashboard.view.TaskReportView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
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
public class TaskAddUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private TreeTaskSearchUI txtTaskReference;
    private TextField txtTaskName;
    private ComboBox cmbTashGroup;
    private TextField txtDepartment;
    private PopupDateField pbTimeStart;
    private PopupDateField pbTimeEnd;
    private TextField txtBudget;
    private ComboBox cmbTashPriority;
    private TextArea txaTaskContent;
    private Uploader attachFile;
    private TextField txtAsignTo;

    private Button btnReceiver;
    private Button btnSave;
    private Button btnCancel;

    private String caption;
    private String widthCustom;

    private TabSheet tabReport;

    public TaskAddUI(String caption, Integer taskId, Boolean isView) {
        setSizeFull();
        this.caption = caption;
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setWidth("100%");
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);

        if (isView) {
            tabReport = new TaskReportView(taskId);
            detailsWrapper.addTab(tabReport, BundleUtils.getString("task.attach.list.report"), ISOIcons.TASKS_RECEIVE);
        }
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setCaption(this.caption);
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setWidth("99%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        Component mainContent = buildFieldsInfo();
        mainLayout.addComponent(mainContent);
        mainLayout.setExpandRatio(mainContent, 1.0f);
        mainLayout.addComponent(buildButton());

    }

    public Component buildContent() {
        CssLayout contenPanel = new CssLayout();
//        contenPanel.addStyleName("dashboard-panels");
        contenPanel.setWidth("100%");
//        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildFieldsInfo());
        return contenPanel;
    }

    public Component buildFieldsInfo() {
        /**
         * create fields infomation
         */
        txtTaskReference = new TreeTaskSearchUI(BundleUtils.getString("taskOrgMngt.list.referenceTask"));
        txtTaskReference.setImmediate(true);
        txtTaskReference.setWidth("100.0%");
        txtTaskReference.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        txtTaskReference.setRequired(true);
        txtTaskReference.setDescription(BundleUtils.getString("taskOrgMngt.list.referenceTask"));
//        txtTaskReference.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.referenceTask"));

        txtTaskName = new TextField();
        txtTaskName.setImmediate(true);
        txtTaskName.setRequired(true);
        txtTaskName.setWidth("100.0%");
        txtTaskName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtTaskName.setRequired(true);
        txtTaskName.setDescription(BundleUtils.getString("taskOrgMngt.list.name"));
        txtTaskName.setCaption(BundleUtils.getString("taskOrgMngt.list.name"));
        txtTaskName.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.name"));

        cmbTashGroup = new ComboBox();
        cmbTashGroup.setCaption(BundleUtils.getString("taskOrgMngt.list.group"));
        cmbTashGroup.setImmediate(true);
        cmbTashGroup.setRequired(true);

        txtDepartment = new TextField();
        txtDepartment.setImmediate(true);
        txtDepartment.setRequired(true);
        txtDepartment.setWidth("100.0%");
        txtDepartment.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtDepartment.setRequired(true);
        txtDepartment.setDescription(BundleUtils.getString("taskOrgMngt.list.department"));
        txtDepartment.setCaption(BundleUtils.getString("taskOrgMngt.list.department"));
        txtDepartment.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.department"));

        pbTimeStart = new PopupDateField();
        pbTimeStart.setImmediate(true);
        pbTimeStart.setWidth("100.0%");
        pbTimeStart.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pbTimeStart.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pbTimeStart.setResolution(Resolution.SECOND);
        pbTimeStart.setCaption(BundleUtils.getString("taskOrgMngt.list.startTime"));
        pbTimeStart.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.startTime"));

        pbTimeEnd = new PopupDateField();
        pbTimeEnd.setImmediate(true);
        pbTimeEnd.setWidth("100.0%");
        pbTimeEnd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pbTimeEnd.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pbTimeEnd.setResolution(Resolution.SECOND);
        pbTimeEnd.setCaption(BundleUtils.getString("taskOrgMngt.list.endTime"));
        pbTimeEnd.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.endTime"));

        txtBudget = new TextField();
        txtBudget.setImmediate(true);
        txtBudget.setRequired(true);
        txtBudget.setWidth("100.0%");
        txtBudget.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtBudget.setRequired(true);
        txtBudget.setDescription(BundleUtils.getString("taskOrgMngt.list.budget"));
        txtBudget.setCaption(BundleUtils.getString("taskOrgMngt.list.budget"));
        txtBudget.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.budget"));

        cmbTashPriority = new ComboBox();
        cmbTashPriority.setCaption(BundleUtils.getString("taskOrgMngt.list.priority"));
        cmbTashPriority.setWidth("100%");
        cmbTashPriority.setImmediate(true);
        cmbTashPriority.setRequired(true);

        txaTaskContent = new TextArea();
        txaTaskContent.setImmediate(true);
        txaTaskContent.setRequired(true);
        txaTaskContent.setWidth("100.0%");
        txaTaskContent.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaTaskContent.setRequired(true);
        txaTaskContent.setDescription(BundleUtils.getString("taskOrgMngt.list.content"));
        txaTaskContent.setCaption(BundleUtils.getString("taskOrgMngt.list.content"));
        txaTaskContent.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.content"));

        attachFile = new Uploader(Uploader.TYPE_UPLOAD.TASK, false);
//        attachFile = new Uploader();
        attachFile.setCaption(BundleUtils.getString("upload.file.caption"));
        attachFile.setImmediate(true);
        attachFile.setWidth("100.0%");
        attachFile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        txtAsignTo = new TextField();
        txtAsignTo.setImmediate(true);
        txtAsignTo.setRequired(true);
        txtAsignTo.setWidth("100.0%");
        txtAsignTo.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtAsignTo.setRequired(true);
        txtAsignTo.setDescription(BundleUtils.getString("taskOrgMngt.list.asignTo"));
        txtAsignTo.setCaption(BundleUtils.getString("taskOrgMngt.list.asignTo"));
        txtAsignTo.setInputPrompt(BundleUtils.getString("taskOrgMngt.list.asignTo"));

        VerticalLayout details = new VerticalLayout();
        details.setWidth("100%");

        FormLayout details1 = new FormLayout();
        details1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details1.setWidth(widthCustom == null ? "100%" : widthCustom);
        details1.addComponent(txtTaskReference);
        details1.addComponent(txtTaskName);
        details1.addComponent(cmbTashGroup);
        details1.addComponent(txaTaskContent);
        details1.addComponent(attachFile);

        HorizontalLayout grid = new HorizontalLayout();
        grid.setWidth("100%");
        grid.setSpacing(true);
        FormLayout subFl1 = new FormLayout();
        subFl1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFl1.setWidth(widthCustom == null ? "100%" : widthCustom);
        subFl1.addComponent(pbTimeStart);
        subFl1.addComponent(txtBudget);
        grid.addComponent(subFl1);

        FormLayout subFl2 = new FormLayout();
        subFl2.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFl2.setWidth(widthCustom == null ? "100%" : widthCustom);
        subFl2.addComponent(pbTimeEnd);
        subFl2.addComponent(cmbTashPriority);
        grid.addComponent(subFl2);
        details.addComponent(details1);
        details.addComponent(grid);

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
        btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSave.addStyleName(ValoTheme.BUTTON_SMALL);
        // btnCancel
        btnCancel = new Button();
        btnCancel.setCaption(BundleUtils.getString("common.button.cancel"));
        btnCancel.setImmediate(true);
        btnCancel.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnCancel.setIcon(ISOIcons.CANCEL);
        btnCancel.addStyleName(ValoTheme.BUTTON_SMALL);

        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setSpacing(true);
        btnLayout.addStyleName("fields");
        btnLayout.addComponents(btnSave,
                btnCancel);
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth("100%");
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

    public TreeTaskSearchUI getTxtTaskReference() {
        return txtTaskReference;
    }

    public void setTxtTaskReference(TreeTaskSearchUI txtTaskReference) {
        this.txtTaskReference = txtTaskReference;
    }

    public TextField getTxtTaskName() {
        return txtTaskName;
    }

    public void setTxtTaskName(TextField txtTaskName) {
        this.txtTaskName = txtTaskName;
    }

    public ComboBox getCmbTashGroup() {
        return cmbTashGroup;
    }

    public void setCmbTashGroup(ComboBox cmbTashGroup) {
        this.cmbTashGroup = cmbTashGroup;
    }

    public TextField getTxtDepartment() {
        return txtDepartment;
    }

    public void setTxtDepartment(TextField txtDepartment) {
        this.txtDepartment = txtDepartment;
    }

    public PopupDateField getPbTimeStart() {
        return pbTimeStart;
    }

    public void setPbTimeStart(PopupDateField pbTimeStart) {
        this.pbTimeStart = pbTimeStart;
    }

    public PopupDateField getPbTimeEnd() {
        return pbTimeEnd;
    }

    public void setPbTimeEnd(PopupDateField pbTimeEnd) {
        this.pbTimeEnd = pbTimeEnd;
    }

    public TextField getTxtBudget() {
        return txtBudget;
    }

    public void setTxtBudget(TextField txtBudget) {
        this.txtBudget = txtBudget;
    }

    public ComboBox getCmbTashPriority() {
        return cmbTashPriority;
    }

    public void setCmbTashPriority(ComboBox cmbTashPriority) {
        this.cmbTashPriority = cmbTashPriority;
    }

    public TextArea getTxaTaskContent() {
        return txaTaskContent;
    }

    public void setTxaTaskContent(TextArea txaTaskContent) {
        this.txaTaskContent = txaTaskContent;
    }

    public Uploader getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(Uploader attachFile) {
        this.attachFile = attachFile;
    }

    public TextField getTxtAsignTo() {
        return txtAsignTo;
    }

    public void setTxtAsignTo(TextField txtAsignTo) {
        this.txtAsignTo = txtAsignTo;
    }

    public Button getBtnReceiver() {
        return btnReceiver;
    }

    public void setBtnReceiver(Button btnReceiver) {
        this.btnReceiver = btnReceiver;
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

    public String getWidthCustom() {
        return widthCustom;
    }

    public void setWidthCustom(String widthCustom) {
        this.widthCustom = widthCustom;
    }

}
