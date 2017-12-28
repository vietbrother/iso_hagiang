/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.controller.FileMngtController;
import com.iso.dashboard.controller.UserMngtController;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author 
 */
public class FileMngtView extends Panel implements View {

    private TextField txtUserName;
    private TextField txtStaffCode;
    private ComboBox cmbStatus;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;

    private CustomGrid pagedTable;

    public List<Employee> userList = new ArrayList<>();

    public FileMngtView() {
        setSizeFull();
//        addStyleName("transactions");
        addStyleName(ValoTheme.PANEL_BORDERLESS);
//        setMargin(false);
//        setSpacing(false);
        setSizeFull();
        VerticalLayout root = new VerticalLayout();
        root.setCaption(BundleUtils.getStringCas("menu.fileMngt"));
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
//        root.addStyleName("transactions");
//        setContent(root);
        Responsive.makeResponsive(root);

        Component content = buildContent();
        TabSheet tabSheet = new TabSheet();
//        tabSheet.setCaption(BundleUtils.getStringCas("menu.userMngt"));
        tabSheet.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.setStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        setContent(tabSheet);
        tabSheet.addComponent(root);
//        tabSheet.setStyleName(ValoTheme.LABEL_COLORED);
//        tabSheet.setStyleName(ValoTheme.LABEL_BOLD);
//
//        VerticalLayout title = new VerticalLayout();
//        Responsive.makeResponsive(title);
//        title.setCaption(BundleUtils.getStringCas("menu.userMngt"));
//        title.setMargin(false);
//        title.setSpacing(false);
//        title.addComponent(content);
//        tabSheet.addComponent(title);
//
//        tabSheet.addComponent(content);
        root.addComponent(content);
        root.setExpandRatio(content, 1);

//        addComponent(buildToolbar());
        new FileMngtController(this);
    }

    private Component buildContent() {
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);

//        Label titleLabel = new Label(BundleUtils.getStringCas("menu.userMngt"));
//        titleLabel.setSizeUndefined();
//        titleLabel.addStyleName(ValoTheme.LABEL_H1);
//        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//        contenPanel.addComponent(titleLabel);
        contenPanel.addComponent(buildToolbar());
        contenPanel.addComponent(buildResultLayout());

        return contenPanel;
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        HorizontalLayout condition = buildSearchForm();
        condition.addStyleName("toolbar");
        header.addComponent(condition);
        header.setExpandRatio(condition, 6.0f);

        HorizontalLayout tools = buildButtonForm();
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        header.setExpandRatio(tools, 3.0f);

        return header;
    }

    private Component buildResultLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
//        mainLayout.addComponent(buildSearchForm());
//        mainLayout.addComponent(buildButtonForm());
//        mainLayout.addComponent(buildResultForm());

        for (int i = 0; i < 240; i++) {
            Employee user = new Employee();
            user.setEmail(i + "_@native.com");
            user.setUserName(i + "_native");
            user.setId(i + 1);
            userList.add(user);
        }
        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        CustomGrid table = createTable();
        tableLayout.addComponent(table);
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_LEFT);

//        Table table = new Table();
//        table.setSizeFull();
//        table.setContainerDataSource(new BeanItemContainer<>(Employee.class, userList));
//        table.setVisibleColumns("id", "userName", "email");
//        table.setColumnHeaders("ID", "Name", "Email");
//        table.setWidth(100, Unit.PERCENTAGE);
//        table.setHeightUndefined();
//        table.setPageLength(10);
//        com.vaadin.addon.pagination.PaginationResource paginationResource = com.vaadin.addon.pagination.PaginationResource.newBuilder()
//                .setTotal((long) userList.size()).setPage(1).setLimit(20).build();
//        com.vaadin.addon.pagination.Pagination pagination = new com.vaadin.addon.pagination.Pagination(paginationResource);
//        pagination.setItemsPerPage(10, 20, 50, 100);
//        pagination.addPageChangeListener(new com.vaadin.addon.pagination.PaginationChangeListener() {
//            @Override
//            public void changed(com.vaadin.addon.pagination.PaginationResource event) {
//                table.removeAllItems();
//                for (Employee user : userList.subList(event.fromIndex(), event.toIndex())) {
//                    table.addItem(user);
//                }
//            }
//        });
//        tableLayout.addComponent(table);
//        mainLayout.addComponent(tableLayout);
        mainLayout.setMargin(true);
        return mainLayout;
    }

    public CustomGrid createTable() {
        pagedTable = new CustomGrid();

        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
//        searchForm.setWidth("100.0%");
        searchForm.setSpacing(true);
//        searchForm.setStyleName(Constants.STYLE_CONF.CUSTOM_FEILDSET);
//        searchForm.setCaption(MakeURL.makeURLForGrid(BundleUtils.getString("common.search.condition")));
//        searchForm.setCaptionAsHtml(true);
        Responsive.makeResponsive(searchForm);

        txtUserName = new TextField();
        txtUserName.setImmediate(true);
        txtUserName.setInputPrompt(BundleUtils.getString("userMngt.list.username"));
        txtUserName.setWidth("100.0%");
        txtUserName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtUserName);
//        searchForm.setExpandRatio(txtUserName, 3.0f);
//        searchForm.setComponentAlignment(txtUserName, Alignment.MIDDLE_CENTER);

//        cmbStatus = new ComboBox();
//        cmbStatus.setImmediate(true);
//        cmbStatus.setCaption(BundleUtils.getString("userMngt.list.status"));
//        cmbStatus.setWidth("100.0%");
//        cmbStatus.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        searchForm.addComponent(cmbStatus);
////        searchForm.setExpandRatio(cmbStatus, 3.0f);
////        searchForm.setComponentAlignment(cmbStatus, Alignment.MIDDLE_CENTER);
//
//        txtStaffCode = new TextField();
//        txtStaffCode.setImmediate(true);
//        txtStaffCode.setCaption(BundleUtils.getString("userMngt.list.staffCode"));
//        txtStaffCode.setWidth("100.0%");
//        txtStaffCode.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        searchForm.addComponent(txtStaffCode);
//        searchForm.setExpandRatio(txtStaffCode, 3.0f);
//        searchForm.setComponentAlignment(txtStaffCode, Alignment.MIDDLE_CENTER);
        return searchForm;
    }

    private HorizontalLayout buildButtonForm() {
        HorizontalLayout buttonForm = new HorizontalLayout();
        buttonForm.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        Responsive.makeResponsive(buttonForm);

        btnSearch = new Button();
        btnSearch.setCaption(BundleUtils.getString("common.button.search"));
        btnSearch.setDescription(BundleUtils.getString("common.button.search"));
        btnSearch.setIcon(ISOIcons.SEARCH);
        btnSearch.setImmediate(true);
        btnSearch.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSearch.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSearch.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonForm.addComponent(btnSearch);

//        // btnExport
//        btnExport = new Button();
//        btnExport.setCaption(BundleUtils.getString("common.button.exportFile"));
//        btnExport.setDescription(BundleUtils.getString("common.button.exportFile"));
//        btnExport.setIcon(ISOIcons.EXPORT);
//        btnExport.setImmediate(true);
//        btnExport.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
//        btnExport.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        buttonForm.addComponent(btnExport);

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

    public TextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(TextField txtUserName) {
        this.txtUserName = txtUserName;
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

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

//    public Button getBtnExport() {
//        return btnExport;
//    }
//
//    public void setBtnExport(Button btnExport) {
//        this.btnExport = btnExport;
//    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public CustomGrid getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGrid pagedTable) {
        this.pagedTable = pagedTable;
    }

}
