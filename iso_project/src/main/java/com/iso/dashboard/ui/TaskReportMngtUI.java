/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskReportMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private TextArea txaTaskContent;
    private PopupDateField pbTimeAttach;
    private Uploader uploadImport;
    private ComboBox cmbStatus;
    private TextArea txaContentCheck;
    private PopupDateField pbTimeCheck;
    private ComboBox cmbCheck;
    private Button btnSave;
    private Button btnCancel;

    private String caption;
    private String widthCustom;

    public TaskReportMngtUI(String caption) {
        setSizeFull();
//        buildMainLayout();
//        setCompositionRoot(mainLayout);
        this.caption = caption;
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setWidth("100%");
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);
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
//        contenPanel.addStyleName("dashboard-panels");
        contenPanel.setWidth("100%");
//        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildFieldsInfo());
//        contenPanel.addComponent(buildButton());
        return contenPanel;
    }

    public Component buildFieldsInfo() {
        /**
         * create fields infomation
         */
        txaTaskContent = new TextArea();
        txaTaskContent.setImmediate(true);
        txaTaskContent.setRequired(true);
        txaTaskContent.setWidth("100.0%");
        txaTaskContent.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaTaskContent.setRequired(true);
        txaTaskContent.setDescription(BundleUtils.getString("task.attach.list.contentAction"));
        txaTaskContent.setCaption(BundleUtils.getString("task.attach.list.contentAction"));
        txaTaskContent.setInputPrompt(BundleUtils.getString("task.attach.list.contentAction"));

        pbTimeAttach = new PopupDateField();
        pbTimeAttach.setImmediate(true);
        pbTimeAttach.setWidth("100.0%");
        pbTimeAttach.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pbTimeAttach.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pbTimeAttach.setResolution(Resolution.SECOND);
        pbTimeAttach.setCaption(BundleUtils.getString("task.attach.list.time"));
        pbTimeAttach.setInputPrompt(BundleUtils.getString("task.attach.list.time"));

        uploadImport = new Uploader(Uploader.TYPE_UPLOAD.TASK_REPORT, false);
//        uploadImport = new Uploader();
        uploadImport.setCaption(BundleUtils.getString("upload.file.caption"));
        uploadImport.setImmediate(true);
        uploadImport.setWidth("100.0%");
        uploadImport.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        txaContentCheck = new TextArea();
        txaContentCheck.setImmediate(true);
        txaContentCheck.setRequired(true);
        txaContentCheck.setWidth("100.0%");
        txaContentCheck.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaContentCheck.setRequired(true);
        txaContentCheck.setDescription(BundleUtils.getString("task.attach.list.contentCheck"));
        txaContentCheck.setCaption(BundleUtils.getString("task.attach.list.contentCheck"));
        txaContentCheck.setInputPrompt(BundleUtils.getString("task.attach.list.contentCheck"));
        txaContentCheck.setVisible(false);

        cmbStatus = new ComboBox();
        cmbStatus.setCaption(BundleUtils.getString("task.attach.list.status"));
        cmbStatus.setImmediate(true);
        cmbStatus.setRequired(true);

        pbTimeCheck = new PopupDateField();
        pbTimeCheck.setImmediate(true);
        pbTimeCheck.setWidth("100.0%");
        pbTimeCheck.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pbTimeCheck.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pbTimeCheck.setResolution(Resolution.SECOND);
        pbTimeCheck.setCaption(BundleUtils.getString("task.attach.list.timeCheck"));
        pbTimeCheck.setInputPrompt(BundleUtils.getString("task.attach.list.timeCheck"));
        pbTimeCheck.setVisible(false);

        cmbCheck = new ComboBox();
        cmbCheck.setCaption(BundleUtils.getString("task.attach.list.status"));
        cmbCheck.setImmediate(true);
        cmbCheck.setRequired(true);
        cmbCheck.setVisible(false);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.setWidth(widthCustom == null ? "100%" : widthCustom);
        details.addComponent(txaTaskContent);
        details.addComponent(pbTimeAttach);
        details.addComponent(uploadImport);
        details.addComponent(cmbStatus);
        details.addComponent(txaContentCheck);
        details.addComponent(pbTimeCheck);
        details.addComponent(cmbCheck);

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

    public TextArea getTxaTaskContent() {
        return txaTaskContent;
    }

    public void setTxaTaskContent(TextArea txaTaskContent) {
        this.txaTaskContent = txaTaskContent;
    }

    public PopupDateField getPbTimeAttach() {
        return pbTimeAttach;
    }

    public void setPbTimeAttach(PopupDateField pbTimeAttach) {
        this.pbTimeAttach = pbTimeAttach;
    }

    public Uploader getUploadImport() {
        return uploadImport;
    }

    public void setUploadImport(Uploader uploadImport) {
        this.uploadImport = uploadImport;
    }

    public TextArea getTxaContentCheck() {
        return txaContentCheck;
    }

    public void setTxaContentCheck(TextArea txaContentCheck) {
        this.txaContentCheck = txaContentCheck;
    }

    public ComboBox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(ComboBox cmbStatus) {
        this.cmbStatus = cmbStatus;
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

    public PopupDateField getPbTimeCheck() {
        return pbTimeCheck;
    }

    public void setPbTimeCheck(PopupDateField pbTimeCheck) {
        this.pbTimeCheck = pbTimeCheck;
    }

    public ComboBox getCmbCheck() {
        return cmbCheck;
    }

    public void setCmbCheck(ComboBox cmbCheck) {
        this.cmbCheck = cmbCheck;
    }

}
