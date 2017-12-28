/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.view;

//import com.iso.dashboard.component.CommonTableFilterPanel;
import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.PProcessFile;
import com.iso.dashboard.frontEnd.controller.PublicProcedureController;
import com.iso.dashboard.frontEnd.controller.PublicProcedureDetailController;
import com.iso.dashboard.frontEnd.ui.ProcedureFlowUI;
import com.iso.dashboard.frontEnd.ui.ProcedureInfoUI;
import com.iso.dashboard.service.ProcedureDocumentService;
import com.iso.dashboard.service.ProcessFileService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
import com.iso.dashboard.view.BannerView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author VIET_BROTHER
 */
public class PublicProcedureDetailView extends VerticalLayout implements View {

    private TextField txtName;

    private Button btnSearch;
    private Button btnAdd;
    private Button btnExport;
    private Button btnClose;

    private CustomGrid pagedTable;

    private Table menu;
    private Panel content;

    private ProcedureInfoUI procedureInfoUI;
    private ProcedureFlowUI procedureFlowUI;
    private Table proDocumentTable;
    private Table lawTable;

    private String caption;
    private CProcedure procedureDTO;

    public PublicProcedureDetailView(String caption, CProcedure dto) {
        this.caption = caption;
        this.procedureDTO = dto;
        setSizeFull();
        //addComponent(buildContent());
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(buildContent());
        addComponent(detailsWrapper);

        new PublicProcedureDetailController(this);
    }

    private Component buildContent() {

        //CssLayout mainLayout = new CssLayout();
        //mainLayout.addStyleName("dashboard-panels");
        //Responsive.makeResponsive(mainLayout);
        HorizontalLayout main = new HorizontalLayout();
//        main.setCaption(this.caption);
//        main.setIcon(FontAwesome.CALENDAR);
        main.addStyleName("dashboard-panels");
        main.setWidth("100%");
        main.setHeight("100%");
        //mainLayout.addComponent(main);
        //Responsive.makeResponsive(main);

        content = new Panel();
        content.addStyleName(ValoTheme.PANEL_BORDERLESS);
        content.setSizeFull();
        content.setContent(buildSubContent());

        menu = new Table();
        menu.setWidth("100%");

        // Define two columns for the built-in container
        VerticalLayout ver = new VerticalLayout();
        ver.setWidth("100%");
        ver.addComponent(new Label(""));
        ver.addComponent(menu);
        main.addComponent(ver);
        main.setExpandRatio(ver, 3.0f);

        main.addComponent(content);
        main.setExpandRatio(content, 8.0f);
        //main.addComponent(main);

        VerticalLayout temp = new VerticalLayout();
        temp.setWidth("100%");
        temp.setSpacing(true);
        temp.setCaption(this.caption);
        temp.setIcon(FontAwesome.CALENDAR);

        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);
        btnClose = new Button(BundleUtils.getString("common.button.cancel"));
        btnClose.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnClose.setIcon(FontAwesome.CLOSE);
        footer.addComponent(btnClose);
        temp.addComponent(main);
        temp.addComponent(footer);
//        return main;
        return temp;
    }

    private Component buildSubContent() {
        VerticalLayout subContent = new VerticalLayout();
        subContent.setWidth("100%");
        procedureInfoUI = new ProcedureInfoUI();
        subContent.addComponent(procedureInfoUI);
        procedureFlowUI = new ProcedureFlowUI(String.valueOf(this.procedureDTO.getId()));
        subContent.addComponent(procedureFlowUI);

        proDocumentTable = new Table();
        proDocumentTable.setHeightUndefined();
        proDocumentTable.setWidth("100%");
        subContent.addComponent(proDocumentTable);

        lawTable = new Table();
        lawTable.setHeightUndefined();
        lawTable.setWidth("100%");

        subContent.addComponent(lawTable);
        

        return subContent;
    }

    public Table test() {
        Table table = new Table();
        //        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(null);
        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(String.valueOf(procedureDTO.getId()));

        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        container.addContainerProperty("upload", String.class, null);
        container.addContainerProperty("status", String.class, null);
        for (MProcedureDocument mpd : listProcedureDocuments) {
            Item item = container.addItem(mpd);
            item.getItemProperty("name").setValue(mpd.getDocument() == null ? "N/A" : mpd.getDocument().getFileName());
            item.getItemProperty("description").setValue(mpd.getDescription());
        }
        table.setContainerDataSource(container);
        table.addGeneratedColumn("stt", (final Table source, final Object itemId, final Object columnId) -> {
            List lstObj = (List) source.getItemIds();
            int i = lstObj.indexOf(itemId);
            return i + 1;
        });
//        table.addGeneratedColumn("action", new Table.ColumnGenerator() {
//
//            @Override
//            public Object generateCell(Table source, Object itemId, Object columnId) {
//                MProcedureDocument dto = (MProcedureDocument) itemId;
//
//                Button btnDownload = new Button();
//                btnDownload.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnDownload.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
//                btnDownload.setIcon(ISOIcons.DOWNLOAD);
//                btnDownload.setDescription(BundleUtils.getString("common.button.download"));
//                btnDownload.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        try {
//                            String fileName = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
//                                    + File.separator
//                                    + dto.getDocument().getUrl();
//                            File file = new File(fileName);
//                            Resource resource = new FileResource(file);
//                            Page.getCurrent().open(resource, null, false);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ComponentUtils.showNotification("Download Error");
//                        }
//                    }
//                });
//
//                return btnDownload;
//            }
//        });
        table.addGeneratedColumn("name", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                MProcedureDocument dto = (MProcedureDocument) itemId;

                Button btnDownload = new Button(dto.getDocument() == null ? "N/A" : dto.getDocument().getFileName());
                btnDownload.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnDownload.addStyleName(ValoTheme.BUTTON_LINK);
                btnDownload.setDescription(BundleUtils.getString("common.button.download"));
                btnDownload.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        try {
                            String fileName = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                                    + File.separator
                                    + dto.getDocument().getUrl();
                            File file = new File(fileName); 
                            Resource resource = new FileResource(file);
                            Page.getCurrent().open(resource, null, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ComponentUtils.showNotification("Download Error");
                        }
                    }
                });

                return btnDownload;
            }
        });
        table.addGeneratedColumn("upload", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                MProcedureDocument dto = (MProcedureDocument) itemId;
                Uploader upload = new Uploader(Uploader.TYPE_UPLOAD.TASK, false);
                return upload;
            }
        });
        table.addGeneratedColumn("status", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;

                PProcessFile temp = new PProcessFile();
                temp.setProcessId(procedureDTO.getId());
                temp.setProcedureDocumentId(prodecureDoc.getId());
                List<PProcessFile> lstFile = ProcessFileService.getInstance().listPProcessFile(temp);
                Label status = new Label(
                        (lstFile == null || lstFile.isEmpty()) ? 
                                ("<b style=\"color:red;\">" + BundleUtils.getString("processFile.list.status.no") + " </b>")
                                    : BundleUtils.getString("processFile.list.status.yes")
                        , ContentMode.HTML);

                return status;
            }
        });

        table.setColumnWidth("upload", 94);
        table.setSizeFull();
        table.setPageLength(table.getContainerDataSource().size() == 0 ? 1 : table.getContainerDataSource().size());
        table.setImmediate(true);
        table.setResponsive(true);
//        fileTable.setVisibleColumns(BundleUtils.getStringCas("proDocumentHeaderKey").split("#"));
//        table.setVisibleColumns(BundleUtils.getStringCas(proDocumentHeaderKey).split("#"));
        table.setVisibleColumns("stt#name#description#upload#status".split("#"));

        String lstColumnsVal = "stt#name#description#upload#status";
        String[] lstColumnsKey = lstColumnsVal.split("#");
        String[] lstColumns = new String[lstColumnsKey.length];
        for (int i = 0; i < lstColumnsKey.length; i++) {
            lstColumns[i] = BundleUtils.getString("processFile.list" + "." + lstColumnsKey[i]);
        }
        table.setColumnHeaders(lstColumns);
        
        return table;
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

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        CustomGrid table = createTable();
        tableLayout.addComponent(table);
        mainLayout.addComponent(tableLayout);
        mainLayout.setComponentAlignment(tableLayout, Alignment.MIDDLE_LEFT);
        return mainLayout;
    }

    public CustomGrid createTable() {
        pagedTable = new CustomGrid();
        return pagedTable;
    }

    private HorizontalLayout buildSearchForm() {
        HorizontalLayout searchForm = new HorizontalLayout();
        searchForm.setSpacing(true);
        Responsive.makeResponsive(searchForm);

        txtName = new TextField();
        txtName.setImmediate(true);
        txtName.setInputPrompt(BundleUtils.getString("procedureMngt.list.name"));
        txtName.setWidth("100.0%");
        txtName.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        searchForm.addComponent(txtName);
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

        // btnExport
        btnExport = new Button();
        btnExport.setCaption(BundleUtils.getString("common.button.exportFile"));
        btnExport.setDescription(BundleUtils.getString("common.button.exportFile"));
        btnExport.setIcon(ISOIcons.EXPORT);
        btnExport.setImmediate(true);
        btnExport.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnExport.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        //buttonForm.addComponent(btnExport);

        btnAdd = new Button();
        btnAdd.setCaption(BundleUtils.getString("common.button.add"));
        btnAdd.setDescription(BundleUtils.getString("common.button.add"));
        btnAdd.setIcon(ISOIcons.ADD);
        btnAdd.setImmediate(true);
        btnAdd.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnAdd.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        //buttonForm.addComponent(btnAdd);

        return buttonForm;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtUserName) {
        this.txtName = txtUserName;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

    public Button getBtnExport() {
        return btnExport;
    }

    public void setBtnExport(Button btnExport) {
        this.btnExport = btnExport;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

//    public CustomPageTable getPagedTable() {
//        return pagedTable;
//    }
//
//    public void setPagedTable(CustomPageTable pagedTable) {
//        this.pagedTable = pagedTable;
//    }
    public CustomGrid getPagedTable() {
        return pagedTable;
    }

    public void setPagedTable(CustomGrid pagedTable) {
        this.pagedTable = pagedTable;
    }

    public Table getMenu() {
        return menu;
    }

    public void setMenu(Table menu) {
        this.menu = menu;
    }

    public Panel getContent() {
        return content;
    }

    public void setContent(Panel content) {
        this.content = content;
    }

    public ProcedureInfoUI getProcedureInfoUI() {
        return procedureInfoUI;
    }

    public void setProcedureInfoUI(ProcedureInfoUI procedureInfoUI) {
        this.procedureInfoUI = procedureInfoUI;
    }

    public ProcedureFlowUI getProcedureFlowUI() {
        return procedureFlowUI;
    }

    public void setProcedureFlowUI(ProcedureFlowUI procedureFlowUI) {
        this.procedureFlowUI = procedureFlowUI;
    }

    public Table getLawTable() {
        return lawTable;
    }

    public void setLawTable(Table lawTable) {
        this.lawTable = lawTable;
    }

    public Table getProDocumentTable() {
        return proDocumentTable;
    }

    public void setProDocumentTable(Table proDocumentTable) {
        this.proDocumentTable = proDocumentTable;
    }

    public CProcedure getProcedureDTO() {
        return procedureDTO;
    }

    public void setProcedureDTO(CProcedure procedureDTO) {
        this.procedureDTO = procedureDTO;
    }

    public Button getBtnClose() {
        return btnClose;
    }

    public void setBtnClose(Button btnClose) {
        this.btnClose = btnClose;
    }

}
