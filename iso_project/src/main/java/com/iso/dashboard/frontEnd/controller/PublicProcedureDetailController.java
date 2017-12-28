/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.controller;

import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.DocumentDTO;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.PProcessFile;
import com.iso.dashboard.frontEnd.ui.ProcedureFlowUI;
import com.iso.dashboard.frontEnd.view.PublicProcedureDetailView;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.ProcedureDocumentService;
import com.iso.dashboard.service.ProcessFileService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.UploaderCustom;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class PublicProcedureDetailController {

    private PublicProcedureDetailView view;

    private CProcedure dto;
    Resource resource;

    String proDocumentPrefix = "procedureDocument.public.list";//tien to trong file language
    String proDocumentHeaderKey = "header.procedureDocument.public";//lay trong file cas
    String[] proDocumentHeaderColumn = BundleUtils.getHeaderColumn(proDocumentHeaderKey);
    String[] proDocumentHeaderName = BundleUtils.getHeaderColumnName(proDocumentPrefix, proDocumentHeaderKey);

    public PublicProcedureDetailController(PublicProcedureDetailView view) {
        this.view = view;
        this.dto = view.getProcedureDTO();
        initData();
    }

    private void initData() {
        initMenu();
        initProInfo();
        initProFlowInfo();
        initProDocumentTable();
        initLawTable();
    }

    private void initMenu() {
        view.getMenu().addContainerProperty("Action", String.class, null);
        view.getMenu().addGeneratedColumn("Action", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Item item = source.getItem(itemId);
                item.getItemProperty("STT").getValue();
                Button btn = new Button((String) item.getItemProperty("STT").getValue());
                btn.setStyleName("btn-procedure");
                return btn;
            }
        });
        view.getMenu().addContainerProperty("Name", String.class, null);
        view.getMenu().addContainerProperty("STT", String.class, null);

// Add a row the hard way
        Object newItemId = view.getMenu().addItem();
        Item row1 = view.getMenu().getItem(newItemId);
        row1.getItemProperty("Name").setValue(BundleUtils.getString("procedureMenu.public.info"));
        row1.getItemProperty("STT").setValue("1");

// Add a few other rows using shorthand addItem()
        view.getMenu().addItem(new Object[]{BundleUtils.getString("procedureMenu.public.step"), "2"}, 2);
        view.getMenu().addItem(new Object[]{BundleUtils.getString("procedureMenu.public.documentPart"), "3"}, 3);
        view.getMenu().addItem(new Object[]{BundleUtils.getString("procedureMenu.public.law"), "4"}, 4);

        // Show 5 rows
        view.getMenu().setPageLength(4);
        // Allow selecting
        view.getMenu().setSelectable(true);
        view.getMenu().setColumnCollapsingAllowed(true);
//        view.getMenu().setColumnCollapsed("Name", true);
        view.getMenu().setColumnCollapsed("STT", true);

        // Trigger selection change events immediately
        view.getMenu().setImmediate(true);

        // Handle selection changes
        view.getMenu().addListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                //Notification.show("ab : "+ event.getProperty().getValue().toString());
                if (event.getProperty().getValue() == null || "1".equals(event.getProperty().getValue().toString())) {
                    view.getProcedureInfoUI().setVisible(true);
                    view.getProcedureFlowUI().setVisible(false);
                    view.getProDocumentTable().setVisible(false);
                    view.getLawTable().setVisible(false);
                } else if ("2".equals(event.getProperty().getValue().toString())) {
                    view.getProcedureInfoUI().setVisible(false);
                    view.getProcedureFlowUI().setVisible(true);
                    view.getProDocumentTable().setVisible(false);
                    view.getLawTable().setVisible(false);
                } else if ("3".equals(event.getProperty().getValue().toString())) {
                    view.getProcedureInfoUI().setVisible(false);
                    view.getProcedureFlowUI().setVisible(false);
                    view.getProDocumentTable().setVisible(true);
                    view.getLawTable().setVisible(false);
                } else {
                    view.getProcedureInfoUI().setVisible(false);
                    view.getProcedureFlowUI().setVisible(false);
                    view.getProDocumentTable().setVisible(false);
                    view.getLawTable().setVisible(true);
                }
            }
        });
        view.getMenu().setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        view.getMenu().select(newItemId);
    }

    private void initProInfo() {
        view.getProcedureInfoUI().getTxtName().setValue(dto.getName());
        view.getProcedureInfoUI().getTxtCondition().setValue("Doanh nghiệp");
        view.getProcedureInfoUI().getTxtCost().setValue(dto.getCost());
        view.getProcedureInfoUI().getTxtCostDocument().setValue(dto.getCost());
        view.getProcedureInfoUI().getTxtOrgName().setValue(OrganizationMngService.getInstance().getOrganizationById(String.valueOf(dto.getOrgId())).getName());
        view.getProcedureInfoUI().getTxtResult().setValue("Cấp giấy phép bưu chính");
        view.getProcedureInfoUI().getTxtProcessTime().setValue(dto.getProcessTime());
        view.getProcedureInfoUI().getTxtType().setValue(dto.getType());
    }

    private void initProFlowInfo() {
        //Component c = view.getProcedureFlowUI().getContent();
        //c = view.getProcedureFlowUI().buildContent(null, String.valueOf(dto.getId()));
        //view.setProcedureFlowUI(new ProcedureFlowUI(null, String.valueOf(dto.getId())));
    }

    private void initProDocumentTable() {
        //List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(null);
//        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(String.valueOf(dto.getId()));
//
//        IndexedContainer container = new IndexedContainer();
//        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
//        container.addContainerProperty("name", String.class, null);
//        container.addContainerProperty("description", String.class, null);
//        for (MProcedureDocument mpd : listProcedureDocuments) {
//            Item item = container.addItem(mpd);
//            item.getItemProperty("name").setValue(mpd.getDocument() == null ? "N/A" : mpd.getDocument().getFileName());
//            item.getItemProperty("description").setValue(mpd.getDescription());
//        }
//        view.getProDocumentTable().setContainerDataSource(container);
//        view.getProDocumentTable().addGeneratedColumn("stt", (final Table source, final Object itemId, final Object columnId) -> {
//            List lstObj = (List) source.getItemIds();
//            int i = lstObj.indexOf(itemId);
//            return i + 1;
//        });
//        view.getProDocumentTable().addGeneratedColumn("action", new Table.ColumnGenerator() {
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
//                            resource = new FileResource(file);
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
//
//        view.getProDocumentTable().setSizeFull();
//        view.getProDocumentTable().setPageLength(view.getProDocumentTable().getContainerDataSource().size() == 0 ? 1 : view.getProDocumentTable().getContainerDataSource().size());
//        view.getProDocumentTable().setImmediate(true);
//        view.getProDocumentTable().setResponsive(true);
//        view.getProDocumentTable().setVisibleColumns(BundleUtils.getStringCas("header.procedureDocument.public").split("#"));
//        view.getProDocumentTable().setColumnHeaders(proDocumentHeaderName);

        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(String.valueOf(dto.getId()));
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        container.addContainerProperty("upload", String.class, null);
//        container.addContainerProperty("status", String.class, null);
        for (MProcedureDocument mpd : listProcedureDocuments) {
            Item item = container.addItem(mpd);
            item.getItemProperty("name").setValue(mpd.getDocument() == null ? "N/A" : mpd.getDocument().getFileName());
            item.getItemProperty("description").setValue(mpd.getDescription());
        }
        view.getProDocumentTable().setContainerDataSource(container);
        view.getProDocumentTable().addGeneratedColumn("stt", (final Table source, final Object itemId, final Object columnId) -> {
            List lstObj = (List) source.getItemIds();
            int i = lstObj.indexOf(itemId);
            return i + 1;
        });

        view.getProDocumentTable().addGeneratedColumn("name", new Table.ColumnGenerator() {

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
                            String fileName = dto.getDocument().getUrl();
                            File file = new File(fileName);
                            resource = new FileResource(file);
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

        view.getProDocumentTable().addGeneratedColumn("action", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;

                Button btnDownload = new Button();
                btnDownload.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnDownload.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
                btnDownload.setIcon(ISOIcons.DOWNLOAD);
                btnDownload.setDescription(BundleUtils.getString("common.button.download"));
                btnDownload.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        try {
                            PProcessFile search = new PProcessFile();
                            search.setProcessId(dto.getProcessDto().getId());
                            search.setProcedureDocumentId(prodecureDoc.getId());
                            List<PProcessFile> lstFile = ProcessFileService.getInstance().listPProcessFile(search);
                            if (lstFile != null && !lstFile.isEmpty()) {
                                File file = new File(lstFile.get(0).getFilePath());
                                resource = new FileResource(file);
                                Page.getCurrent().open(resource, null, false);
                            }

                            DocumentDTO document = prodecureDoc.getDocument();
                            if (document != null && !DataUtil.isNullOrEmpty(document.getUrl())) {
                                File file = new File(document.getUrl() + File.separator + document.getFileName());
                                resource = new FileResource(file);
                                Page.getCurrent().open(resource, null, false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ComponentUtils.showNotification("Download Error");
                        }
                    }
                });

                return btnDownload;

            }
        }
        );
        view.getProDocumentTable()
                .setSizeFull();
        view.getProDocumentTable()
                .setPageLength(view.getProDocumentTable().getContainerDataSource().size() == 0 ? 1 : view.getProDocumentTable().getContainerDataSource().size());
        view.getProDocumentTable()
                .setImmediate(true);
        view.getProDocumentTable()
                .setResponsive(true);
//        view.getProDocumentTable().setVisibleColumns(BundleUtils.getStringCas("proDocumentHeaderKey").split("#"));
        view.getProDocumentTable()
                .setVisibleColumns(BundleUtils.getStringCas(proDocumentHeaderKey).split("#"));
        view.getProDocumentTable()
                .setColumnHeaders(proDocumentHeaderName);
    }

    private void initLawTable() {
        // Define two columns for the built-in container
        view.getLawTable().addContainerProperty("STT", String.class, null);
        view.getLawTable()
                .addContainerProperty("Tên tài liệu pháp lý", String.class, null);
        view.getLawTable()
                .addContainerProperty("Đính kèm", String.class, null);
        view.getLawTable()
                .addGeneratedColumn("Đính kèm", new Table.ColumnGenerator() {

                    @Override
                    public Object generateCell(Table source, Object itemId, Object columnId
                    ) {
                        Item item = source.getItem(itemId);
                        //item.getItemProperty("name").getValue();
                        HorizontalLayout hori = new HorizontalLayout();
                        hori.setSpacing(true);
                        Button btn = new Button("1");
                        btn.setStyleName("btn-procedure");
                        Label label = new Label("Quy trinh", ContentMode.HTML);
                        label.addStyleName("label-procedure");
                        hori.addComponents(btn, label);
                        return hori;
                    }
                }
                );

        view.getLawTable()
                .setSizeFull();
        view.getLawTable()
                .setPageLength(1);
        view.getLawTable()
                .setImmediate(true);
        view.getLawTable()
                .setResponsive(true);
    }
}
