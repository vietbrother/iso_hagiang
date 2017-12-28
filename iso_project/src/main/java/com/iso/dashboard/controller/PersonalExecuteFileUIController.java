/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.PProcessFile;
import com.iso.dashboard.dto.PProcessHis;
import com.iso.dashboard.service.ProcedureDocumentService;
import com.iso.dashboard.service.ProcessFileService;
import com.iso.dashboard.service.ProcessHisService;
import com.iso.dashboard.ui.ApproveUI;
import com.iso.dashboard.ui.PersonalExecuteFileMngtUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class PersonalExecuteFileUIController {

    private PersonalExecuteFileMngtUI view;

    final CProcedure dto;
    Resource resource;

    String proDocumentPrefix = "processFile.list";//tien to trong file language
    String proDocumentHeaderKey = "header.personalExecuteFileMngt.lstFileSend";//lay trong file cas
    String[] proDocumentHeaderColumn = BundleUtils.getHeaderColumn(proDocumentHeaderKey);
    String[] proDocumentHeaderName = BundleUtils.getHeaderColumnName(proDocumentPrefix, proDocumentHeaderKey);

    String processHisPrefix = "processHis.list";//tien to trong file language
    String processHisHeaderKey = "header.processHis";//lay trong file cas
    String[] processHisHeaderColumn = BundleUtils.getHeaderColumn(processHisHeaderKey);
    String[] processHisHeaderName = BundleUtils.getHeaderColumnName(processHisPrefix, processHisHeaderKey);

    private Table fileTable;
    private Table processHisTable;
    SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public PersonalExecuteFileUIController(PersonalExecuteFileMngtUI view) {
        this.view = view;
        this.dto = view.getProcedureDTO();
        this.fileTable = view.getLstTemplateFile();
        this.processHisTable = view.getLstProcessHis();
        initTableFile();
        initTableProcessHis();
    }

    public IndexedContainer createContainer(List<MProcedureDocument> listProcedureDocuments) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("stt", String.class, null);
//        container.addContainerProperty("action", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        container.addContainerProperty("upload", String.class, null);
        container.addContainerProperty("execute", String.class, null);
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

    public void initTableFile() {

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
                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;

                Button btnDownload = new Button(prodecureDoc.getDocument() == null
                        ? "N/A" : prodecureDoc.getDocument().getFileName());
                btnDownload.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnDownload.addStyleName(ValoTheme.BUTTON_LINK);
                btnDownload.setDescription(BundleUtils.getString("common.button.download"));
                btnDownload.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        try {
                            String fileName = prodecureDoc.getDocument().getUrl();
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

                Button btnView = new Button();
                btnView.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnView.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
                btnView.setIcon(ISOIcons.VIEW);
                btnView.setCaption(BundleUtils.getString("approve.list.view"));
                btnView.setDescription(BundleUtils.getString("approve.list.view"));
                btnView.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        try {
                            createApproveDialog(prodecureDoc);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ComponentUtils.showNotification("Error");
                        }
                    }
                });

                HorizontalLayout hori = new HorizontalLayout();
                hori.setSpacing(true);
                hori.addComponents(btnDownload, btnView);
                return hori;

            }
        });
//        fileTable.addGeneratedColumn("execute", new Table.ColumnGenerator() {
//
//            @Override
//            public Object generateCell(Table source, Object itemId, Object columnId) {
//                MProcedureDocument prodecureDoc = (MProcedureDocument) itemId;
//                Button btnExecute = new Button();
//                btnExecute.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnExecute.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
//                btnExecute.setIcon(ISOIcons.CONFIG);
//                btnExecute.setCaption(BundleUtils.getString("approve.list.execute"));
//                btnExecute.setDescription(BundleUtils.getString("approve.list.execute"));
//                btnExecute.addClickListener(new Button.ClickListener() {
//
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        try {
//                            createApproveDialog(prodecureDoc);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ComponentUtils.showNotification("Error");
//                        }
//                    }
//                });
//
//                return btnExecute;
//
//            }
//        });
        fileTable.setSizeFull();
        fileTable.setPageLength(fileTable.getContainerDataSource().size() == 0 ? 1 : fileTable.getContainerDataSource().size());
        fileTable.setImmediate(true);
        fileTable.setResponsive(true);
//        fileTable.setVisibleColumns(BundleUtils.getStringCas("proDocumentHeaderKey").split("#"));
        fileTable.setVisibleColumns(BundleUtils.getStringCas(proDocumentHeaderKey).split("#"));
        fileTable.setColumnHeaders(proDocumentHeaderName);

    }

    public void initTableProcessHis() {
        PProcessHis processHisSearch = new PProcessHis();
        processHisSearch.setpProcessId(dto.getProcessDto().getId());
        List<PProcessHis> listProcessHis = ProcessHisService.getInstance().listPProcessHis(processHisSearch);
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("stt", String.class, null);
        container.addContainerProperty("createTime", String.class, null);
        container.addContainerProperty("actionName", String.class, null);
        container.addContainerProperty("createBy", String.class, null);
        container.addContainerProperty("result", String.class, null);
        container.addContainerProperty("description", String.class, null);
        for (PProcessHis pHis : listProcessHis) {
            Item item = container.addItem(pHis);
            item.getItemProperty("createTime").setValue(spd.format(pHis.getCreatedTime()));
            item.getItemProperty("actionName").setValue(pHis.getActionName());
            item.getItemProperty("createBy").setValue(pHis.getCreateBy());
            item.getItemProperty("result").setValue(pHis.getResult());
            item.getItemProperty("description").setValue(pHis.getDescription());
        }
        processHisTable.setContainerDataSource(container);
        processHisTable.addGeneratedColumn("stt", (final Table source, final Object itemId, final Object columnId) -> {
            List lstObj = (List) source.getItemIds();
            int i = lstObj.indexOf(itemId);
            return i + 1;
        });

        processHisTable.setSizeFull();
        processHisTable.setPageLength(processHisTable.getContainerDataSource().size() == 0 ? 1 : processHisTable.getContainerDataSource().size());
        processHisTable.setImmediate(true);
        processHisTable.setResponsive(true);
//        fileTable.setVisibleColumns(BundleUtils.getStringCas("proDocumentHeaderKey").split("#"));
        processHisTable.setVisibleColumns(BundleUtils.getStringCas(processHisHeaderKey).split("#"));
        processHisTable.setColumnHeaders(processHisHeaderName);

    }

    public void createApproveDialog(MProcedureDocument prodecureDoc) {
        PProcessFile search = new PProcessFile();
        search.setProcessId(dto.getProcessDto().getId());
        search.setProcedureDocumentId(prodecureDoc.getId());
        List<PProcessFile> lstFile = ProcessFileService.getInstance().listPProcessFile(search);
        if (lstFile != null && !lstFile.isEmpty()) {
            ApproveUI ui = new ApproveUI(
                    BundleUtils.getString("processFile.list.upload"), lstFile.get(0).getFilePath());

            Window window = new Window(
                    "",
                    ui);
            //window.setWidth("700px");
            float height = UI.getCurrent().getWidth() * 4 / 5;
            window.setWidth(String.valueOf(height) + "%");
            window.setHeight(92.0f, Sizeable.Unit.PERCENTAGE);
            window.setResizeLazy(true);
            window.setResizable(true);

            ui.setWidth("100%");
//        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
            ui.getBtnSave().addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Notification.show("SAVE");
                }
            });
            
            ui.getBtnCancel().addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    window.close();
                }
            });

            window.setModal(true);
            DataUtil.reloadWindow(window);
            UI.getCurrent().addWindow(window);
        } else {
            Notification.show("No file");
        }

    }

    public Table getFileTable() {
        return fileTable;
    }

    public void setFileTable(Table fileTable) {
        this.fileTable = fileTable;
    }

    public Table getProcessHisTable() {
        return processHisTable;
    }

    public void setProcessHisTable(Table processHisTable) {
        this.processHisTable = processHisTable;
    }
    
    

}
