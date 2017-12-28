/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.PProcessFile;
import com.iso.dashboard.service.ProcedureDocumentService;
import com.iso.dashboard.service.ProcessFileService;
import com.iso.dashboard.ui.PersonalSendFileMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.utils.Uploader;
import com.iso.dashboard.utils.UploaderCustom;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.List;
import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalSendFileUIController {

    private PersonalSendFileMngtUI view;

    final CProcedure dto;
    Resource resource;

    String proDocumentPrefix = "processFile.list";//tien to trong file language
    String proDocumentHeaderKey = "header.personalSendFileMngt.lstFileSend";//lay trong file cas
    String[] proDocumentHeaderColumn = BundleUtils.getHeaderColumn(proDocumentHeaderKey);
    String[] proDocumentHeaderName = BundleUtils.getHeaderColumnName(proDocumentPrefix, proDocumentHeaderKey);

    private Table fileTable;

    public PersonalSendFileUIController(PersonalSendFileMngtUI view) {
        this.view = view;
        this.dto = view.getProcedureDTO();
        this.fileTable = view.getLstTemplateFile();
        initTable();
    }

    public IndexedContainer createContainer(List<MProcedureDocument> listProcedureDocuments) {
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
        return container;
    }

    public void reloadData(List<MProcedureDocument> listProcedureDocuments) {
        fileTable.setContainerDataSource(createContainer(listProcedureDocuments));
    }

    public void initTable() {

//        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(null);
        List<MProcedureDocument> listProcedureDocuments = ProcedureDocumentService.getInstance().listProcedureDocuments(String.valueOf(dto.getId()));
        IndexedContainer container = createContainer(listProcedureDocuments);
        fileTable.setContainerDataSource(container);
        fileTable.addGeneratedColumn("stt", (final Table source, final Object itemId, final Object columnId) -> {
            List lstObj = (List) source.getItemIds();
            int i = lstObj.indexOf(itemId);
            return i + 1;
        });
//        fileTable.addGeneratedColumn("action", new Table.ColumnGenerator() {
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
        fileTable.addGeneratedColumn("name", new Table.ColumnGenerator() {

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

//        fileTable.addGeneratedColumn("status", new Table.ColumnGenerator() {
//
//            @Override
//            public Object generateCell(Table source, Object itemId, Object columnId) {
//                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;
//
////                PProcessFile temp = new PProcessFile();
////                temp.setProcessId(dto.getId());
////                temp.setProcedureDocumentId(prodecureDoc.getId());
////                List<PProcessFile> lstFile = ProcessFileService.getInstance().listPProcessFile(temp);
////                Label status = new Label(
////                        (lstFile == null || lstFile.isEmpty())
////                                ? ("<b style=\"color:red;\">" + BundleUtils.getString("processFile.list.status.no") + " </b>")
////                                : BundleUtils.getString("processFile.list.status.yes"), ContentMode.HTML);
//                String desc = "<b style=\"color:red;\">" + BundleUtils.getString("processFile.list.status.no") + " </b>";
//                if (dto.getProcessDto() != null) {
//                    PProcessFile temp = new PProcessFile();
//                    temp.setProcessId(dto.getId());
//                    temp.setProcedureDocumentId(prodecureDoc.getId());
//                    temp.setProcessId(dto.getProcessDto().getId());
//                    List<PProcessFile> lstFile = ProcessFileService.getInstance().listPProcessFile(temp);
//                    if (lstFile != null && !lstFile.isEmpty()) {
//                        desc = "<b style=\"color:green;\">" + BundleUtils.getString("processFile.list.status.yes") + " </b>";
//                    }
//                } else {
//                    desc = (view.getMapFile().get(prodecureDoc) == null
//                            || view.getMapFile().get(prodecureDoc).getUrl().size() == 0)
//                                    ? ("<b style=\"color:red;\">" + BundleUtils.getString("processFile.list.status.no") + " </b>")
//                                    : ("<b style=\"color:green;\">" + BundleUtils.getString("processFile.list.status.yes") + " </b>");
//                }
//
//                Label status = new Label(desc, ContentMode.HTML);
//
//                return status;
//            }
//        });
//        fileTable.addGeneratedColumn("upload", new Table.ColumnGenerator() {
//
//            @Override
//            public Object generateCell(Table source, Object itemId, Object columnId) {
//                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;
//                Uploader upload = new Uploader(Uploader.TYPE_UPLOAD.TASK, false);
//                if (view.getMapFile().get(prodecureDoc) == null) {
//                    view.getMapFile().put(prodecureDoc, upload);
//                } else {
//                    upload = view.getMapFile().get(prodecureDoc);
//                }
//                upload.setEnableUpload(view.isCanUpload());
//
//                return upload;
//            }
//        });
        fileTable.addGeneratedColumn("upload", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;
                if (view.isCanUpload()) {
                    UploaderCustom upload = new UploaderCustom("", UploaderCustom.TYPE_UPLOAD.TASK);
                    view.getMapDocFile().put(prodecureDoc, upload);
                    return upload;
                } else {
                    if (dto.getProcessDto() == null) {
                        return new Label("");
                    } else {
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

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ComponentUtils.showNotification("Download Error");
                                }
                            }
                        });

                        return btnDownload;
                    }
                }

            }
        });
        fileTable.setSizeFull();
        fileTable.setPageLength(fileTable.getContainerDataSource().size() == 0 ? 1 : fileTable.getContainerDataSource().size());
        fileTable.setImmediate(true);
        fileTable.setResponsive(true);
//        fileTable.setVisibleColumns(BundleUtils.getStringCas("proDocumentHeaderKey").split("#"));
        fileTable.setVisibleColumns(BundleUtils.getStringCas(proDocumentHeaderKey).split("#"));
        fileTable.setColumnHeaders(proDocumentHeaderName);

    }

    public Table getFileTable() {
        return fileTable;
    }

    public void setFileTable(Table fileTable) {
        this.fileTable = fileTable;
    }

}
