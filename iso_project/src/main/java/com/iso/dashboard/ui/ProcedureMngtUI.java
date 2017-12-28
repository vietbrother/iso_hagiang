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
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcedureMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;
    private PopupDateField pdCreatedTime;
    private PopupDateField pdUpdatedTime;
    private TextField txtCode;
    private TextField txtName;
    private TextField txtLevel;
    private TextField txtCost;
    private TextField txtCostDocument;
    private TextField txtProcessTime;
    private TextArea txaDescription;
    private OrgTreeSearchUI trOrgName;
    private ChooseFlowUI cfFlow;
    private Button btnSave;
    private Button btnCancel;

    private CKEditorTextField ckEditorTextField;
    private CKEditorConfig configEditor;
    private Uploader uploadFile;
    private ComboBox cmbType;

    private String caption;

    public ProcedureMngtUI(String caption) {
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
        txtCode.setDescription(BundleUtils.getString("procedureMngt.public.list.code"));
        txtCode.setCaption(BundleUtils.getString("procedureMngt.public.list.code"));
        txtCode.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.code"));

        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setRequired(true);
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtName.setRequired(true);
        txtName.setDescription(BundleUtils.getString("procedureMngt.public.list.name"));
        txtName.setCaption(BundleUtils.getString("procedureMngt.public.list.name"));
        txtName.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.name"));

        txtLevel = new TextField();
        txtLevel.setImmediate(true);
        txtLevel.setRequired(true);
        txtLevel.setWidth("100.0%");
        txtLevel.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtLevel.setRequired(true);
        txtLevel.setDescription(BundleUtils.getString("procedureMngt.public.list.level"));
        txtLevel.setCaption(BundleUtils.getString("procedureMngt.public.list.level"));
        txtLevel.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.level"));

        txtCost = new TextField();
        txtCost.setImmediate(true);
        txtCost.setRequired(true);
        txtCost.setWidth("100.0%");
        txtCost.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtCost.setRequired(true);
        txtCost.setDescription(BundleUtils.getString("procedureMngt.public.list.cost"));
        txtCost.setCaption(BundleUtils.getString("procedureMngt.public.list.cost"));
        txtCost.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.cost"));

        txtProcessTime = new TextField();
        txtProcessTime.setImmediate(true);
        txtProcessTime.setRequired(true);
        txtProcessTime.setWidth("100.0%");
        txtProcessTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtProcessTime.setRequired(true);
        txtProcessTime.setDescription(BundleUtils.getString("procedureMngt.public.list.processTime"));
        txtProcessTime.setCaption(BundleUtils.getString("procedureMngt.public.list.processTime"));
        txtProcessTime.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.processTime"));

        txaDescription = new TextArea();
        txaDescription.setImmediate(true);
        txaDescription.setRequired(true);
        txaDescription.setWidth("100.0%");
        txaDescription.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaDescription.setRequired(true);
        txaDescription.setDescription(BundleUtils.getString("procedureMngt.public.list.description"));
        txaDescription.setCaption(BundleUtils.getString("procedureMngt.public.list.description"));
        txaDescription.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.description"));

        trOrgName = new OrgTreeSearchUI(BundleUtils.getString("procedureMngt.public.list.orgName"));
        trOrgName.setImmediate(true);
        trOrgName.getTxtOrgName().setRequired(true);
        trOrgName.setWidth("100.0%");
        trOrgName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        uploadFile = new Uploader(Uploader.TYPE_UPLOAD.TASK_REPORT, true);
        uploadFile.setCaption(BundleUtils.getString("upload.file.caption"));
        uploadFile.setImmediate(true);
        uploadFile.setWidth("100.0%");
        uploadFile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        cfFlow = new ChooseFlowUI("");
        cfFlow.setCaption(BundleUtils.getString("procedureFlow.addFlow"));
        cfFlow.setImmediate(true);
        cfFlow.setWidth("100.0%");

        txtCostDocument = new TextField();
        txtCostDocument.setImmediate(true);
        txtCostDocument.setRequired(true);
        txtCostDocument.setWidth("100.0%");
        txtCostDocument.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtCostDocument.setRequired(true);
        txtCostDocument.setDescription(BundleUtils.getString("procedureMngt.public.list.costDocument"));
        txtCostDocument.setCaption(BundleUtils.getString("procedureMngt.public.list.costDocument"));
        txtCostDocument.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.costDocument"));

        pdCreatedTime = new PopupDateField();
        pdCreatedTime.setImmediate(true);
        pdCreatedTime.setWidth("100.0%");
        pdCreatedTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdCreatedTime.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdCreatedTime.setResolution(Resolution.SECOND);
        pdCreatedTime.setCaption(BundleUtils.getString("procedureMngt.public.list.createdTime"));
        pdCreatedTime.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.createdTime"));
        pdCreatedTime.setValue(new Date());

        pdUpdatedTime = new PopupDateField();
        pdUpdatedTime.setImmediate(true);
        pdUpdatedTime.setWidth("100.0%");
        pdUpdatedTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdUpdatedTime.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdUpdatedTime.setResolution(Resolution.SECOND);
        pdUpdatedTime.setCaption(BundleUtils.getString("procedureMngt.public.list.updatedTime"));
        pdUpdatedTime.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.updatedTime"));
        pdUpdatedTime.setValue(new Date());

        CKEditorConfig config = new CKEditorConfig();
        config.useCompactTags();
        config.disableElementsPath();
//        config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
        config.disableSpellChecker();
        config.setWidth("100%");
        config.setHeight("300px");

        ckEditorTextField = new CKEditorTextField(config);
        ckEditorTextField.setWidth("100%");
        ckEditorTextField.setHeight("300px");
        ckEditorTextField.setCaption(BundleUtils.getString("procedureMngt.list.content"));
        ckEditorTextField.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
//                Notification.show("CKEditor v" + ckEditorTextField.getVersion() + " - contents: " + event.getProperty().getValue().toString());
            }
        });
        
        
        cmbType = new ComboBox();
        cmbType.setCaption(BundleUtils.getString("procedureMngt.list.type"));
        cmbType.setImmediate(true);
        cmbType.setWidth("100%");
        cmbType.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbType.setRequired(true);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtCode);
        details.addComponent(txtName);
        details.addComponent(txaDescription);
        details.addComponent(txtCost);
        details.addComponent(txtLevel);
        details.addComponent(cmbType);
        details.addComponent(trOrgName);
        details.addComponent(cfFlow);
        details.addComponent(uploadFile);
        details.addComponent(ckEditorTextField);
        details.addComponent(txtProcessTime);
//        details.addComponent(pdCreatedTime);
//        details.addComponent(pdUpdatedTime);

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
        footer.setSpacing(true);
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

    public CKEditorTextField getCkEditorTextField() {
        return ckEditorTextField;
    }

    public void setCkEditorTextField(CKEditorTextField ckEditorTextField) {
        this.ckEditorTextField = ckEditorTextField;
    }

    public CKEditorConfig getConfigEditor() {
        return configEditor;
    }

    public void setConfigEditor(CKEditorConfig configEditor) {
        this.configEditor = configEditor;
    }

    public TextField getTxtLevel() {
        return txtLevel;
    }

    public void setTxtLevel(TextField txtLevel) {
        this.txtLevel = txtLevel;
    }

    public TextField getTxtCost() {
        return txtCost;
    }

    public void setTxtCost(TextField txtCost) {
        this.txtCost = txtCost;
    }

    public TextField getTxtCostDocument() {
        return txtCostDocument;
    }

    public void setTxtCostDocument(TextField txtCostDocument) {
        this.txtCostDocument = txtCostDocument;
    }

    public TextField getTxtProcessTime() {
        return txtProcessTime;
    }

    public void setTxtProcessTime(TextField txtProcessTime) {
        this.txtProcessTime = txtProcessTime;
    }

    public OrgTreeSearchUI getTrOrgName() {
        return trOrgName;
    }

    public void setTrOrgName(OrgTreeSearchUI trOrgName) {
        this.trOrgName = trOrgName;
    }

    public ChooseFlowUI getCfFlow() {
        return cfFlow;
    }

    public void setCfFlow(ChooseFlowUI cfFlow) {
        this.cfFlow = cfFlow;
    }

    public Uploader getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(Uploader uploadFile) {
        this.uploadFile = uploadFile;
    }

    public ComboBox getCmbType() {
        return cmbType;
    }

    public void setCmbType(ComboBox cmbType) {
        this.cmbType = cmbType;
    }

    
}
