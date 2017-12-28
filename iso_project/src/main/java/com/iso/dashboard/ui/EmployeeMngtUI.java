/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.GroupBoxUI;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.EmployeeEducationMngtView;
import com.iso.dashboard.view.EmployeeProcessMngtView;
import com.iso.dashboard.view.EmployeeRewardMngtView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeMngtUI extends VerticalLayout {

    private VerticalLayout mainLayout;

//    private HorizontalLayout horizontalLayout;
//    private TabSheet tabSheet;
    private TextField txtEmployeeCode;

    private TextField txtEmployeeName;
    private PopupDateField pdBirthday;
    private TextField txtEmail;
    private TextField txtMobile;
    private ComboBox cmbJobTitle;
    private ComboBox cmbEmployeeType;
    private TextField txtDepartment;

    private CheckBox cbxActive;

    private TextField txtAddress;
    private ComboBox cmbIsActive;
    private ComboBox cmbSex;
    private TextField username;
    private PasswordField password;
    private GroupBoxUI viewFunction;

    private Button btnSave;
    private Button btnCancel;

    private String caption;

    private TabSheet detailsWrapper;
    private EmployeeProcessMngtView employeeProcessMngtView;
    private EmployeeEducationMngtView employeeEducationMngtView;
    private EmployeeRewardMngtView rewardMngtView;

    public EmployeeMngtUI(String caption) {
        this.setSizeFull();
        this.caption = caption;
        this.setIcon(FontAwesome.CALENDAR);
        //addComponent(buildFieldsInfo());

        mainLayout = new VerticalLayout();
        mainLayout.setCaption(this.caption);
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setSizeFull();
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);
        mainLayout.addComponent(buildFieldsInfo());

        TabSheet mainWrapper = new TabSheet();
        mainWrapper.setSizeFull();
        mainWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        mainWrapper.addComponent(mainLayout);
        addComponent(mainWrapper);

        employeeProcessMngtView = new EmployeeProcessMngtView();
        employeeProcessMngtView.setWidth("100%");
        employeeProcessMngtView.getBtnAdd().setVisible(false);
        employeeProcessMngtView.getBtnExport().setVisible(false);
        employeeProcessMngtView.getBtnSearch().setVisible(false);
        employeeProcessMngtView.getTxtEmployeeProcess().setVisible(false);
        employeeProcessMngtView.getHeader().setVisible(false);
        employeeProcessMngtView.setCaption("Quá trình công tác");

        detailsWrapper = new TabSheet(employeeProcessMngtView);
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        mainLayout.addComponent(detailsWrapper);
        mainLayout.addComponent(buildButton());

        // add new tab
        employeeEducationMngtView = new EmployeeEducationMngtView();
        employeeEducationMngtView.setWidth("100%");
        employeeEducationMngtView.getBtnAdd().setVisible(false);
        employeeEducationMngtView.getBtnExport().setVisible(false);
        employeeEducationMngtView.getBtnSearch().setVisible(false);
        employeeEducationMngtView.getTxtEmployeeEducation().setVisible(false);
        employeeEducationMngtView.setCaption("Văn bằng/ Chứng chỉ");
        employeeEducationMngtView.getHeader().setVisible(false);
        detailsWrapper.addTab(employeeEducationMngtView);

        // add new tab
        rewardMngtView = new EmployeeRewardMngtView();
        rewardMngtView.setWidth("100%");
        rewardMngtView.getBtnAdd().setVisible(false);
        rewardMngtView.getBtnExport().setVisible(false);
        rewardMngtView.getBtnSearch().setVisible(false);
        rewardMngtView.getTxtEmployeeReward().setVisible(false);
        rewardMngtView.setCaption("Khen thưởng");
        rewardMngtView.getHeader().setVisible(false);
        detailsWrapper.addTab(rewardMngtView);

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
        txtEmployeeCode = new TextField();
        txtEmployeeCode.setImmediate(true);
        txtEmployeeCode.setRequired(true);
        txtEmployeeCode.setWidth("100.0%");
        txtEmployeeCode.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmployeeCode.setRequired(true);
        txtEmployeeCode.setDescription(BundleUtils.getString("employeeMngt.list.employeeCode"));
        txtEmployeeCode.setCaption(BundleUtils.getString("employeeMngt.list.employeeCode"));
        txtEmployeeCode.setInputPrompt(BundleUtils.getString("employeeMngt.list.employeeCode"));

        txtEmployeeName = new TextField();
        txtEmployeeName.setImmediate(true);
        txtEmployeeName.setRequired(true);
        txtEmployeeName.setWidth("100.0%");
        txtEmployeeName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmployeeName.setRequired(true);
        txtEmployeeName.setDescription(BundleUtils.getString("employeeMngt.list.employeeName"));
        txtEmployeeName.setCaption(BundleUtils.getString("employeeMngt.list.employeeName"));
        txtEmployeeName.setInputPrompt(BundleUtils.getString("employeeMngt.list.employeeName"));

        pdBirthday = new PopupDateField();
        pdBirthday.setImmediate(true);
        pdBirthday.setWidth("100.0%");
        pdBirthday.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        pdBirthday.setDateFormat(Constants.DATE.ddMMyyy);
        pdBirthday.setResolution(Resolution.DAY);
        pdBirthday.setCaption(BundleUtils.getString("employeeMngt.list.birthday"));
        pdBirthday.setInputPrompt(BundleUtils.getString("employeeMngt.list.birthday"));
        pdBirthday.setValue(new Date());

        txtEmail = new TextField();
        txtEmail.setImmediate(true);
        txtEmail.setRequired(true);
        txtEmail.setWidth("100.0%");
        txtEmail.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtEmail.setRequired(true);
        txtEmail.setDescription(BundleUtils.getString("employeeMngt.list.email"));
        txtEmail.setCaption(BundleUtils.getString("employeeMngt.list.email"));
        txtEmail.setInputPrompt(BundleUtils.getString("employeeMngt.list.email"));

        txtAddress = new TextField();
        txtAddress.setImmediate(true);
        txtAddress.setRequired(true);
        txtAddress.setWidth("100.0%");
        txtAddress.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtAddress.setRequired(true);
        txtAddress.setDescription(BundleUtils.getString("employeeMngt.list.address"));
        txtAddress.setCaption(BundleUtils.getString("employeeMngt.list.address"));
        txtAddress.setInputPrompt(BundleUtils.getString("employeeMngt.list.address"));

        txtMobile = new TextField();
        txtMobile.setImmediate(true);
        txtMobile.setRequired(true);
        txtMobile.setWidth("100.0%");
        txtMobile.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtMobile.setRequired(true);
        txtMobile.setDescription(BundleUtils.getString("employeeMngt.list.mobile"));
        txtMobile.setCaption(BundleUtils.getString("employeeMngt.list.mobile"));
        txtMobile.setInputPrompt(BundleUtils.getString("employeeMngt.list.mobile"));

        cmbJobTitle = new ComboBox();
        cmbJobTitle.setCaption(BundleUtils.getString("employeeMngt.list.jobTitle"));
        cmbJobTitle.setImmediate(true);
        cmbJobTitle.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbJobTitle.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbJobTitle.setRequired(true);

        cmbEmployeeType = new ComboBox();
        cmbEmployeeType.setCaption(BundleUtils.getString("employeeTypeMngt.list.employeeType"));
        cmbEmployeeType.setImmediate(true);
        cmbEmployeeType.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbEmployeeType.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbEmployeeType.setRequired(true);

        txtDepartment = new TextField();
        txtDepartment.setImmediate(true);
        txtDepartment.setRequired(true);
        txtDepartment.setWidth("100.0%");
        txtDepartment.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txtDepartment.setRequired(true);
        txtDepartment.setDescription(BundleUtils.getString("employeeMngt.list.department"));
        txtDepartment.setCaption(BundleUtils.getString("employeeMngt.list.department"));
        txtDepartment.setInputPrompt(BundleUtils.getString("employeeMngt.list.department"));

        cbxActive = new CheckBox();
        cbxActive.setImmediate(true);
        cbxActive.setWidth("100.0%");
        cbxActive.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cbxActive.setDescription(BundleUtils.getString("employeeMngt.list.active"));
        cbxActive.setCaption(BundleUtils.getString("employeeMngt.list.active"));

        username = new TextField(BundleUtils.getString("login.username"));
        username.setRequired(true);
        username.setInputPrompt(BundleUtils.getString("login.username"));
        //username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        password = new PasswordField(BundleUtils.getString("login.password"));
        password.setRequired(true);
        password.setInputPrompt(BundleUtils.getString("login.password"));
        //password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        cmbIsActive = new ComboBox();
        cmbIsActive.setCaption(BundleUtils.getString("employeeMngt.list.status"));
        cmbIsActive.setImmediate(true);
        cmbIsActive.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbIsActive.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbIsActive.setRequired(true);

        cmbSex = new ComboBox();
        cmbSex.setCaption(BundleUtils.getString("userMngt.list.sex"));
        cmbSex.setImmediate(true);
        cmbSex.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        cmbSex.setRequired(true);

//        viewFunction = new OptionGroup(BundleUtils.getString("userMngt.list.role"));
//        viewFunction.setMultiSelect(true);
//        viewFunction.setHeight("200px");
        viewFunction = new GroupBoxUI(BundleUtils.getString("userMngt.list.role"), true);
        viewFunction.setRequired(true);
        viewFunction.getOgMain().setRequired(true);

        HorizontalLayout grid = new HorizontalLayout();
//        grid.setCaption("Thông tin chung");
        grid.setWidth("100%");
        grid.setSpacing(true);
        FormLayout subFrm1 = new FormLayout();
        subFrm1.setWidth("100%");
        subFrm1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFrm1.addComponent(txtEmployeeCode);
        subFrm1.addComponent(cmbJobTitle);
        subFrm1.addComponent(pdBirthday);
        subFrm1.addComponent(txtAddress);
        subFrm1.addComponent(username);
        subFrm1.addComponent(password);

        grid.addComponent(subFrm1);
//        grid.setComponentAlignment(subFl1, Alignment.MIDDLE_LEFT);
//        grid.setExpandRatio(subFl1, 4.0f);

        FormLayout subFrm2 = new FormLayout();
        subFrm2.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFrm2.setWidth("100%");
        subFrm2.addComponent(txtEmployeeName);
        subFrm2.addComponent(cmbEmployeeType);
        subFrm2.addComponent(txtEmail);
        subFrm2.addComponent(txtMobile);
        subFrm2.addComponent(cmbSex);
        subFrm2.addComponent(cmbIsActive);
        grid.addComponent(subFrm2);

        FormLayout subFrm3 = new FormLayout();
        subFrm3.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subFrm3.addComponent(viewFunction);
        grid.addComponent(subFrm3);

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

    public TextField getTxtEmployeeCode() {
        return txtEmployeeCode;
    }

    public void setTxtEmployeeCode(TextField txtEmployeeCode) {
        this.txtEmployeeCode = txtEmployeeCode;
    }

    public TextField getTxtEmployeeName() {
        return txtEmployeeName;
    }

    public void setTxtEmployeeName(TextField txtEmployeeName) {
        this.txtEmployeeName = txtEmployeeName;
    }



    public TextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(TextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public TextField getTxtMobile() {
        return txtMobile;
    }

    public void setTxtMobile(TextField txtMobile) {
        this.txtMobile = txtMobile;
    }



    public TextField getTxtDepartment() {
        return txtDepartment;
    }

    public void setTxtDepartment(TextField txtDepartment) {
        this.txtDepartment = txtDepartment;
    }

    public CheckBox getCbxActive() {
        return cbxActive;
    }

    public void setCbxActive(CheckBox cbxActive) {
        this.cbxActive = cbxActive;
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

    public ComboBox getCmbSex() {
        return cmbSex;
    }

    public void setCmbSex(ComboBox cmbSex) {
        this.cmbSex = cmbSex;
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public GroupBoxUI getViewFunction() {
        return viewFunction;
    }

    public void setViewFunction(GroupBoxUI viewFunction) {
        this.viewFunction = viewFunction;
    }

    public TabSheet getDetailsWrapper() {
        return detailsWrapper;
    }

    public void setDetailsWrapper(TabSheet detailsWrapper) {
        this.detailsWrapper = detailsWrapper;
    }

    public EmployeeEducationMngtView getEmployeeEducationMngtView() {
        return employeeEducationMngtView;
    }

    public void setEmployeeEducationMngtView(EmployeeEducationMngtView employeeEducationMngtView) {
        this.employeeEducationMngtView = employeeEducationMngtView;
    }

    public EmployeeRewardMngtView getRewardMngtView() {
        return rewardMngtView;
    }

    public void setRewardMngtView(EmployeeRewardMngtView rewardMngtView) {
        this.rewardMngtView = rewardMngtView;
    }

    public EmployeeProcessMngtView getEmployeeProcessMngtView() {
        return employeeProcessMngtView;
    }

    public void setEmployeeProcessMngtView(EmployeeProcessMngtView employeeProcessMngtView) {
        this.employeeProcessMngtView = employeeProcessMngtView;
    }

    public PopupDateField getPdBirthday() {
        return pdBirthday;
    }

    public void setPdBirthday(PopupDateField pdBirthday) {
        this.pdBirthday = pdBirthday;
    }

    public ComboBox getCmbJobTitle() {
        return cmbJobTitle;
    }

    public void setCmbJobTitle(ComboBox cmbJobTitle) {
        this.cmbJobTitle = cmbJobTitle;
    }

    public ComboBox getCmbEmployeeType() {
        return cmbEmployeeType;
    }

    public void setCmbEmployeeType(ComboBox cmbEmployeeType) {
        this.cmbEmployeeType = cmbEmployeeType;
    }

    public TextField getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(TextField txtAddress) {
        this.txtAddress = txtAddress;
    }

    public ComboBox getCmbIsActive() {
        return cmbIsActive;
    }

    public void setCmbIsActive(ComboBox cmbIsActive) {
        this.cmbIsActive = cmbIsActive;
    }

    
}
