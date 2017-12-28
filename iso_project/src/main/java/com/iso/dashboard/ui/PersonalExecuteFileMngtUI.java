/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.controller.PersonalExecuteFileUIController;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
import com.iso.dashboard.utils.UploaderCustom;
import com.vaadin.data.Property;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import pl.pdfviewer.PdfViewer;
import pl.pdfviewer.listener.AngleChangeListener;
import pl.pdfviewer.listener.PageChangeListener;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalExecuteFileMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;

    private TextField txtName;
    private TextField txtLevel;
    private TextField txtOrgName;
    private TextField txtCost;
    private TextField txtProcessTime;
    private TextArea txaDescription;
    private PopupDateField pdCreatedTime;
    private OptionGroup ogResult;
    private TextArea txaResult;

    private Table lstTemplateFile;
    private Table lstProcessHis;

    private Button btnUploadFile;
    private Button btnSendFile;
    private Button btnExecute;
    private Button btnSubmitApprove;
    private Button btnApprove;
    private Button btnRelease;
    private Button btnSave;
    private Button btnCancel;
    private Component approveContent;

    private String caption;
    private CProcedure procedureDTO;
    private boolean canUpload;
    private Map<MProcedureDocument, Uploader> mapFile = new HashMap<>();
    private Map<MProcedureDocument, UploaderCustom> mapDocFile = new HashMap<>();
    private PersonalExecuteFileUIController controller;

    public PersonalExecuteFileMngtUI(String caption, CProcedure dto, boolean canUpload) {
        this.caption = caption;
        this.procedureDTO = dto;
        this.canUpload = canUpload;
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);

        controller = new PersonalExecuteFileUIController(this);
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
        approveContent = buildApprove();
        mainLayout.addComponent(approveContent);
        mainLayout.addComponent(buildButton());

    }

    public Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.setWidth("100%");
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setWidth("100%");
        subContent.addComponent(buildFieldsInfo());

        VerticalLayout fileLayout = new VerticalLayout();
        fileLayout.setWidth("100%");
        fileLayout.setIcon(FontAwesome.FILES_O);
        fileLayout.setCaption(BundleUtils.getString("processFile.list"));
        lstTemplateFile = new Table();
        lstTemplateFile.setWidth("100%");
        fileLayout.addComponent(lstTemplateFile);
        VerticalLayout hisLayout = new VerticalLayout();
        hisLayout.setWidth("100%");
        hisLayout.setIcon(FontAwesome.HISTORY);
        hisLayout.setCaption(BundleUtils.getString("processHis.list"));
        lstProcessHis = new Table();
        lstProcessHis.setWidth("100%");
        hisLayout.addComponent(lstProcessHis);
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(fileLayout);
        detailsWrapper.addComponent(hisLayout);

        subContent.addComponent(detailsWrapper);

        contenPanel.addComponent(subContent);
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

        txtOrgName = new TextField();
        txtOrgName.setImmediate(true);
        txtOrgName.setRequired(true);
        txtOrgName.setWidth("100.0%");
        txtOrgName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtOrgName.setRequired(true);
        txtOrgName.setDescription(BundleUtils.getString("procedureMngt.public.list.orgName"));
        txtOrgName.setCaption(BundleUtils.getString("procedureMngt.public.list.orgName"));
        txtOrgName.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.orgName"));

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
        txaDescription.setDescription(BundleUtils.getString("procedureMngt.public.list.infoDetail"));
        txaDescription.setCaption(BundleUtils.getString("procedureMngt.public.list.infoDetailHint"));
        txaDescription.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.infoDetail"));

        pdCreatedTime = new PopupDateField();
        pdCreatedTime.setImmediate(true);
        pdCreatedTime.setWidth("100.0%");
        pdCreatedTime.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdCreatedTime.setDateFormat(Constants.DATE.ddMMyyyHHmmss);
        pdCreatedTime.setResolution(Resolution.SECOND);
        pdCreatedTime.setCaption(BundleUtils.getString("procedureMngt.public.list.createdTime"));
        pdCreatedTime.setInputPrompt(BundleUtils.getString("procedureMngt.public.list.createdTime"));
        pdCreatedTime.setValue(new Date());

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.setWidth("100%");
        details.addComponent(txtName);
        details.addComponent(txtOrgName);
        details.addComponent(txtLevel);
        details.addComponent(txtCost);
        details.addComponent(txtProcessTime);
        details.addComponent(txaDescription);
//        details.addComponent(pdCreatedTime);
//        details.addComponent(pdUpdatedTime);

        return details;
    }

    public Component buildApprove() {

        txaResult = new TextArea();
        txaResult.setImmediate(true);
        txaResult.setRequired(true);
        txaResult.setWidth("100.0%");
        txaResult.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaResult.setRequired(true);
        txaResult.setDescription(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setCaption(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setInputPrompt(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setVisible(false);

        ogResult = new OptionGroup(BundleUtils.getString("approve.list.result"));  // Pass a string used as caption (title) of the group of radio buttons.
        ogResult.addItems(Boolean.TRUE, Boolean.FALSE);  // Pass item ids to be used in constructing Item objects on our behalf.
        ogResult.setItemCaption(Boolean.TRUE, BundleUtils.getString("approve.list.result.yes")); // Specify a textual label rather than default generated value "true" & "false".
        ogResult.setItemCaption(Boolean.FALSE, BundleUtils.getString("approve.list.result.no"));
        ogResult.setValue(Boolean.TRUE);  // Specify which radio button is selected by default.
        // Add a listener to react to user selection.
        ogResult.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                txaResult.setVisible(!((boolean) event.getProperty().getValue()));
            }
        });

        FormLayout details = new FormLayout();
        details.setCaption(BundleUtils.getString("approve.list.result"));
        details.setIcon(FontAwesome.FILE_TEXT_O);
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.setWidth("100%");
        details.addComponent(ogResult);
        details.addComponent(txaResult);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(details);
        return detailsWrapper;
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
        // btnSendFile
        btnSendFile = new Button();
        btnSendFile.setCaption(BundleUtils.getString("procedureMngt.public.list.sendFile"));
        btnSendFile.setImmediate(true);
        btnSendFile.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSendFile.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSendFile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSendFile.setIcon(FontAwesome.SEND_O);
        // btnSendFile
        btnUploadFile = new Button();
        btnUploadFile.setCaption(BundleUtils.getString("common.button.upload"));
        btnUploadFile.setImmediate(true);
        btnUploadFile.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnUploadFile.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnUploadFile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnUploadFile.setIcon(FontAwesome.UPLOAD);

        // btnExecute
        btnExecute = new Button();
        btnExecute.setCaption(BundleUtils.getString("process.action.3.execute"));
        btnExecute.setImmediate(true);
        btnExecute.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnExecute.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnExecute.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnExecute.setIcon(FontAwesome.PLAY);
        // btnSubmitApprove
        btnSubmitApprove = new Button();
        btnSubmitApprove.setCaption(BundleUtils.getString("process.action.4.submitApprove"));
        btnSubmitApprove.setImmediate(true);
        btnSubmitApprove.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSubmitApprove.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSubmitApprove.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSubmitApprove.setIcon(FontAwesome.SEND);
        // btnApprove
        btnApprove = new Button();
        btnApprove.setCaption(BundleUtils.getString("process.action.5.approve"));
        btnApprove.setImmediate(true);
        btnApprove.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnApprove.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnApprove.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnApprove.setIcon(FontAwesome.CHECK);
        // btnRelease
        btnRelease = new Button();
        btnRelease.setCaption(BundleUtils.getString("process.action.6.release"));
        btnRelease.setImmediate(true);
        btnRelease.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnRelease.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnRelease.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnRelease.setIcon(FontAwesome.CERTIFICATE);

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
        temp.addComponents(//btnUploadFile,
                btnExecute,
                btnSubmitApprove,
                btnApprove,
                btnRelease,
                btnCancel
        );
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(true);
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

    public PopupDateField getPdCreatedTime() {
        return pdCreatedTime;
    }

    public void setPdCreatedTime(PopupDateField pdCreatedTime) {
        this.pdCreatedTime = pdCreatedTime;
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

    public TextField getTxtProcessTime() {
        return txtProcessTime;
    }

    public void setTxtProcessTime(TextField txtProcessTime) {
        this.txtProcessTime = txtProcessTime;
    }

    public TextField getTxtOrgName() {
        return txtOrgName;
    }

    public void setTxtOrgName(TextField txtOrgName) {
        this.txtOrgName = txtOrgName;
    }

    public Table getLstTemplateFile() {
        return lstTemplateFile;
    }

    public void setLstTemplateFile(Table lstTemplateFile) {
        this.lstTemplateFile = lstTemplateFile;
    }

    public Button getBtnSendFile() {
        return btnSendFile;
    }

    public void setBtnSendFile(Button btnSendFile) {
        this.btnSendFile = btnSendFile;
    }

    public CProcedure getProcedureDTO() {
        return procedureDTO;
    }

    public void setProcedureDTO(CProcedure procedureDTO) {
        this.procedureDTO = procedureDTO;
    }

    public boolean isCanUpload() {
        return canUpload;
    }

    public void setCanUpload(boolean canUpload) {
        this.canUpload = canUpload;
    }

    public Map<MProcedureDocument, Uploader> getMapFile() {
        return mapFile;
    }

    public void setMapFile(Map<MProcedureDocument, Uploader> mapFile) {
        this.mapFile = mapFile;
    }

    public Button getBtnUploadFile() {
        return btnUploadFile;
    }

    public void setBtnUploadFile(Button btnUploadFile) {
        this.btnUploadFile = btnUploadFile;
    }

    public PersonalExecuteFileUIController getController() {
        return controller;
    }

    public void setController(PersonalExecuteFileUIController controller) {
        this.controller = controller;
    }

    public Map<MProcedureDocument, UploaderCustom> getMapDocFile() {
        return mapDocFile;
    }

    public void setMapDocFile(Map<MProcedureDocument, UploaderCustom> mapDocFile) {
        this.mapDocFile = mapDocFile;
    }

    public OptionGroup getOgResult() {
        return ogResult;
    }

    public void setOgResult(OptionGroup ogResult) {
        this.ogResult = ogResult;
    }

    public TextArea getTxaResult() {
        return txaResult;
    }

    public void setTxaResult(TextArea txaResult) {
        this.txaResult = txaResult;
    }

    public Table getLstProcessHis() {
        return lstProcessHis;
    }

    public void setLstProcessHis(Table lstProcessHis) {
        this.lstProcessHis = lstProcessHis;
    }

    public Button getBtnExecute() {
        return btnExecute;
    }

    public void setBtnExecute(Button btnExecute) {
        this.btnExecute = btnExecute;
    }

    public Button getBtnSubmitApprove() {
        return btnSubmitApprove;
    }

    public void setBtnSubmitApprove(Button btnSubmitApprove) {
        this.btnSubmitApprove = btnSubmitApprove;
    }

    public Button getBtnApprove() {
        return btnApprove;
    }

    public void setBtnApprove(Button btnApprove) {
        this.btnApprove = btnApprove;
    }

    public Component getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(Component approveContent) {
        this.approveContent = approveContent;
    }

    public Button getBtnRelease() {
        return btnRelease;
    }

    public void setBtnRelease(Button btnRelease) {
        this.btnRelease = btnRelease;
    }

    
}
