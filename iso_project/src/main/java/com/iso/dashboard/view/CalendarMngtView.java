/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

//import com.iso.dashboard.component.CommonTableFilterPanel;
import com.iso.dashboard.component.CustomGridEditRow;
import com.iso.dashboard.controller.CalendarController;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Container;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class CalendarMngtView extends Panel implements View {

    private TextField txtStaffCode;
    private ComboBox cmbStatus;

    private Button btnAdd;

//    private CommonTableFilterPanel commonTableFilterPanel;
    private CustomGridEditRow pagedTable;

    public List<Employee> userList = new ArrayList<>();

    public CalendarMngtView() {
        setSizeFull();
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.calendar"));
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
        Responsive.makeResponsive(root);

        Component content = buildContent();
        TabSheet tabSheet = new TabSheet();
        tabSheet.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        setContent(tabSheet);
        tabSheet.addComponent(root);
        root.addComponent(content);
        root.setExpandRatio(content, 1);
        
        new CalendarController(this);
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

        contenPanel.addComponent(buildToolbar());
        contenPanel.addComponent(buildResultLayout());

        return contenPanel;
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        HorizontalLayout tools = buildButtonForm();
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        header.setExpandRatio(tools, 3.0f);

        return header;
    }

    private Component buildResultLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");

        for (int i = 0; i < 240; i++) {
            Employee user = new Employee();
            user.setEmail(i + "_@native.com");
            user.setUserName(i + "_native");
            user.setId(i + 1);
            userList.add(user);
        }
        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        CustomGridEditRow table = createTable();
        tableLayout.addComponent(table);
//        tableLayout.addComponent(table.createControls("10"));
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_LEFT);

        mainLayout.setMargin(true);
        return mainLayout;
    }

    public CustomGridEditRow createTable() {
        pagedTable = new CustomGridEditRow();
        
        return pagedTable;
    }

    private HorizontalLayout buildButtonForm() {
        HorizontalLayout buttonForm = new HorizontalLayout();
        buttonForm.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        Responsive.makeResponsive(buttonForm);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        buttonForm.addComponent(btnAdd);

        return buttonForm;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public ComboBox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(ComboBox cmbStatus) {
        this.cmbStatus = cmbStatus;
    }

    public TextField getTxtStaffCode() {
        return txtStaffCode;
    }

    public void setTxtStaffCode(TextField txtStaffCode) {
        this.txtStaffCode = txtStaffCode;
    }



    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public CustomGridEditRow getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGridEditRow pagedTable) {
        this.pagedTable = pagedTable;
    }

}
