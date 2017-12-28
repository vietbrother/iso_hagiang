/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.controller.PersonalSendFileUIController;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
import com.iso.dashboard.utils.UploaderCustom;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
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
public class PersonalSendFileMngtUI extends CustomComponent {

    private VerticalLayout mainLayout;

    private TextField txtName;
    private TextField txtLevel;
    private TextField txtOrgName;
    private TextField txtCost;
    private TextField txtProcessTime;
    private TextArea txaDescription;
    private PopupDateField pdCreatedTime;

    private Table lstTemplateFile;

    private Button btnUploadFile;
    private Button btnSendFile;
    private Button btnSave;
    private Button btnCancel;

    private String caption;
    private CProcedure procedureDTO;
    private boolean canUpload;
    private Map<MProcedureDocument, Uploader> mapFile = new HashMap<>();
    private Map<MProcedureDocument, UploaderCustom> mapDocFile = new HashMap<>();
    private PersonalSendFileUIController controller;

    public PersonalSendFileMngtUI(String caption, CProcedure dto, boolean canUpload) {
        this.caption = caption;
        this.procedureDTO = dto;
        this.canUpload = canUpload;
        buildMainLayout();
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);

        controller = new PersonalSendFileUIController(this);
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
        contenPanel.setWidth("100%");
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setWidth("100%");
        subContent.addComponent(buildFieldsInfo());

        lstTemplateFile = new Table();
        lstTemplateFile.setWidth("100%");
        subContent.addComponent(lstTemplateFile);

//        contenPanel.addComponent(buildButton());
        contenPanel.addComponent(subContent);
//        subContent.addComponent(test());
        return contenPanel;
    }

    public Component test() {
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");

        PdfViewer c = new PdfViewer(new File("C:\\Users\\VIET_BROTHER\\Desktop\\Lập trình Java cơ bản - nâng cao - FPT Polytechnic\\Java\\Giáo trình FPT\\Java 1\\Lý thuyết\\MOB1013-Slide 7 - Ke thua.pdf"));
        c.setHeight("100%");
        c.setWidth("100%");
        c.setBackAngleButtonCaption(VaadinIcons.ROTATE_LEFT.getHtml());
        c.setNextAngleButtonCaption(VaadinIcons.ROTATE_RIGHT.getHtml());
        c.setIncreaseButtonCaption(VaadinIcons.SEARCH_PLUS.getHtml());
        c.setDecreaseButtonCaption(VaadinIcons.SEARCH_MINUS.getHtml());
        c.setPreviousPageCaption(VaadinIcons.ANGLE_LEFT.getHtml() + " Back");
        c.setNextPageCaption("Next " + VaadinIcons.ANGLE_RIGHT.getHtml());
        c.addPageChangeListener(new PageChangeListener() {
            @Override
            public void pageChange(Integer page) {
                System.out.println(page + " PAGE CHANGE");
            }
        });
        c.addAngleChangeListener(new AngleChangeListener() {
            @Override
            public void angleChange(Integer page) {
                System.out.println("Angle change");
            }
        });
        layout.addComponent(c);
        Button b = new Button("dupa");
        b.addClickListener(new ClickListener() {
            int b = 0;

            @Override
            public void buttonClick(ClickEvent event) {
                Window window = new Window();
                window.setWidth("90%");
                window.setHeight("90%");
                BrowserFrame e = new BrowserFrame("PDF File", new ExternalResource("C:\\Users\\VIET_BROTHER\\Desktop\\Lập trình Java cơ bản - nâng cao - FPT Polytechnic\\Java\\Giáo trình FPT\\Java 1\\Lý thuyết\\MOB1013-Slide 7 - Ke thua.pdf"));
                e.setWidth("100%");
                e.setHeight("100%");
                window.setContent(e);
                window.center();
                window.setModal(true);
                UI.getCurrent().addWindow(window);
            }
        });
        layout.addComponent(b);
        return layout;
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
        temp.addComponents(btnUploadFile,
                btnSendFile,
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

    public PersonalSendFileUIController getController() {
        return controller;
    }

    public void setController(PersonalSendFileUIController controller) {
        this.controller = controller;
    }

    public Map<MProcedureDocument, UploaderCustom> getMapDocFile() {
        return mapDocFile;
    }

    public void setMapDocFile(Map<MProcedureDocument, UploaderCustom> mapDocFile) {
        this.mapDocFile = mapDocFile;
    }

}
