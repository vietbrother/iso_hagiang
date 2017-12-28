/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 *
 * Khai bao: Uploader uploader = new Uploader();
 *
 * Doc cac url: uploader.getUrl();
 *
 */
public class Uploader extends CustomComponent {

    Logger log = Logger.getLogger(Uploader.class);

    public static String PATH_UPLOAD = BundleUtils.getStringCas("path_upload");

    MultiFileUpload multiUpload;
    String folder = "VAADIN/UPLOAD";
    String prefixName = Constants.EMPTY_CHARACTER;
    final VerticalLayout layout = new VerticalLayout();
    Table table = new Table();
    BeanItemContainer<String> containerLink;
    final ArrayList<Link> links = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    String rootPath = Constants.EMPTY_CHARACTER;
    String prefixPath = Constants.EMPTY_CHARACTER;

    private String path;
    String extensionInoge;
    String extensionAllow = ".doc,.docx,.pdf,.xls,.xlsx,.ppt,.pptx,.csv,.txt,.rar,.zip,.7z,.jpg,.gif,.png,.bmp,.sql";
    VerticalLayout vLayoutAttach;
    private String pathDownload;

    public static interface TYPE_UPLOAD {

        public static final String TASK = "TASK";
        public static final String AVATAR = "AVATAR";
        public static final String TASK_REPORT = "TASK_REPORT";
        public static final String PROCEDURE = "PROCEDURE";
    }

    public Uploader(String caption) {
        this.path = PATH_UPLOAD + "/" + DateUtil.date2String(new Date()) + "/";
        // Create the upload with a caption and set receiver later
        Upload upload = new Upload(caption, null);
        HandlerUpload uploader = new HandlerUpload();
        upload.setReceiver(uploader);
        url.clear();
        links.clear();
//        upload.addListener(uploader);
        setCompositionRoot(upload);
    }

    public Uploader(Date date) {
        rootPath = BundleUtils.getStringCas("path_upload");
        path = BundleUtils.getStringCas("path_upload") + DateUtil.date2String(date) + "/";
        initGUI();
        addUpload(true);
    }

//    public Uploader(String typeUpload) {
//        if (typeUpload != null && typeUpload.equals(TYPE_UPLOAD.AVATAR)) {
//            path = BundleUtils.getStringCas("path_upload_avatar") + DateUtil.date2String(new Date()) + "/";
//        } else if (typeUpload != null && typeUpload.equals(TYPE_UPLOAD.TASK)) {
//            path = BundleUtils.getStringCas("path_upload_tast") + DateUtil.date2String(new Date()) + "/";
//        } else if (typeUpload != null && typeUpload.equals(TYPE_UPLOAD.TASK_REPORT)) {
//            path = BundleUtils.getStringCas("path_upload_task_report") + DateUtil.date2String(new Date()) + "/";
//        } else {
//        }
//        initGUI();
//        addUpload(true);
//    }
    /**
     *
     * @param pathUpload
     * @param isMultiFile
     */
    public Uploader(String pathUpload, boolean isMultiFile) {
        if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.AVATAR)) {
            this.path = BundleUtils.getStringCas("path_upload_avatar") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK)) {
            this.path = BundleUtils.getStringCas("path_upload_task") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK_REPORT)) {
            this.path = BundleUtils.getStringCas("path_upload_task_report") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.PROCEDURE)) {
            this.path = BundleUtils.getStringCas("path_upload_procedure") + DateUtil.date2String(new Date()) + "/";
        } else {
            this.path = PATH_UPLOAD + "/" + DateUtil.date2String(new Date()) + "/";
        }
        initGUI();
        addUpload(isMultiFile);

    }

    private void initGUI() {
        table.setImmediate(true);
        table.setWidth("100%");
        table.setHeight("100%");
        table.setVisible(false);
        table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        table.addContainerProperty("button", String.class, null);
        table.addContainerProperty("File", String.class, null);
        containerLink = new BeanItemContainer<>(String.class);
        table.setColumnWidth("button", 30);
        table.addGeneratedColumn("button", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, final Object itemId, Object columnId) {
                Resource iconVi = ISOIcons.DELETE;
                Button btnDelete = new Button();
//                btnDelete.setStyleName(Constants.STYLE_CONF.BUTTON_LINK_LARGE);
                btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
                btnDelete.setIcon(iconVi);
                btnDelete.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Item row1 = table.getItem(itemId);
                        String fileName = (String) row1.getItemProperty("File").getValue();
                        url.remove(prefixName + fileName);
                        table.removeItem(itemId);
                        table.setPageLength(table.getItemIds().size());
                        table.refreshRowCache();
                        Collection coll = table.getItemIds();
                        if (coll == null || coll.isEmpty()) {
                            table.setVisible(false);
                        }
                    }
                });
                return btnDelete;
            }
        });

        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                if (DataUtil.isNullOrEmpty(prefixPath)) {
                    Link l = links.get(Integer.valueOf(event.getItemId().toString()) - 1);
                    Resource resource = l.getResource();
                    Page.getCurrent().open(resource, "_blank", false);//Mo file trong tab moi 
                } else {
                    Item row = event.getItem();
                    String fileName = (String) row.getItemProperty("File").getValue();
                    File fileDownload = new File(rootPath + prefixPath + "/" + fileName);
                    Resource resource = new FileResource(fileDownload);
                    Page.getCurrent().open(resource, "_blank", false);
                }

            }
        });

        // tooltip view chi tiết
        table.setItemDescriptionGenerator(new AbstractSelect.ItemDescriptionGenerator() {
            @Override
            public String generateDescription(Component source, Object itemId, Object propertyId) {
                //khai bao Item de get item thu itemId
                Item row = table.getItem(itemId);
                if (propertyId == "File" && row.getItemProperty("File").getValue() != null) {
                    return row.getItemProperty("File").getValue().toString();
                }
                return null;
            }
        });
        buildLayout();
    }

    private void buildLayout() {
        setCompositionRoot(layout);
        layout.setWidth("100%");
    }

    public MultiFileUpload getMultiUpload() {
        return multiUpload;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    private void addUpload(boolean multifile) {
        UploadStateWindow window = new UploadStateWindow();

        window.setCancelIconResource(ISOIcons.CANCEL);
        window.setImmediate(true);
        UploadFinishedHandler handler;
        handler = new UploadFinishedHandler() {

            @Override
            public void handleFile(InputStream inputStream, String fileName, String mimeType, long length, int filesLeftInQueue) {
                try {
                    boolean checkExtend = false;
                    if (extensionAllow != null) {
                        String[] extendArr = extensionAllow.split(",");
                        for (String e : extendArr) {
                            if (fileName.toLowerCase().endsWith(e.toLowerCase())) {
                                checkExtend = true;
                                break;
                            }
                        }
                    }
                    if (checkExtend) {
                        //_ replace ky tu ";","&",... ky tu dac biet cua URL trong ten file
                        fileName = fileName
                                .replaceAll("[~!@#$%^&*()+`=;,\\-\\s+\\[\\]{}']", "_")
                                .replaceAll("(_)+", "_");
                        fileName = DateUtil.date2HHMMssNoSlash(new Date()) + "_" + fileName;
                        final String saltPath = buildPath();
                        File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        File file = new File(path + prefixName + fileName);
						file.getAbsoluteFile();
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(IOUtils.toByteArray(inputStream));
                            fos.close();
                        }

                        Link link = new Link(fileName, new FileResource(file));
                        link.setTargetName("_blank");
                        link.setDescription(fileName);
//                    layout.addComponent(link);
                        links.add(link);
                        if (!url.contains(prefixName + fileName)) {
                            
                            // Add a row the hard way
                            table.setVisible(true);
                            table.clear();
                            if (!multifile) {
                                url.clear();
                                table.removeAllItems();
                            }
							url.add(prefixName + fileName);
                            Object newItemId = table.addItem();
                            Item row1 = table.getItem(newItemId);
                            row1.getItemProperty("File").setValue(fileName);
                            table.setPageLength(table.getItemIds().size());
//                            Container con = table.getContainerDataSource();
//                            table.setContainerDataSource(con);
                            //table.setHeightMode(HeightMode.ROW);
                            //Page.getCurrent().getJavaScript().execute("$('.v-table-column-selector').remove()");
                        } else {
                            Notification.show(BundleUtils.getString("fileExist") + fileName, Notification.Type.TRAY_NOTIFICATION);
                        }
                    } else {
                        Notification.show(BundleUtils.getString("fileTypeInvalid") + ": " + fileName);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    PrintLog.printLog(ex);
                }
            }
        };
        multiUpload = new MultiFileUpload(handler, window);
//        multiUpload.setPanelCaption("Upload panel");
//        multiUpload.setAcceptedMimeTypes(DataUtil.getListAcceptFile());
        multiUpload.setAcceptFilter(extensionAllow);
        multiUpload.setMaxFileSize(Constants.FILE_CONF.MAX_FILE_SIZE_UPLOAD);
        multiUpload.getSmartUpload().setUploadButtonCaptions(BundleUtils.getString("chooseAttachFile"), BundleUtils.getString("chooseAttachFile"));
        layout.addComponent(multiUpload);

//        vLayoutAttach = new VerticalLayout();
//        vLayoutAttach.setImmediate(true);
//        vLayoutAttach.setWidth("100%");
//        vLayoutAttach.setHeight("60px");
//        vLayoutAttach.setMargin(false);
//        vLayoutAttach.setSpacing(false);
//        vLayoutAttach.setStyleName("verticalLayOut-Scroll");
//        vLayoutAttach.addComponent(table);
//        layout.addComponent(vLayoutAttach);
        
        Panel panel = new Panel();
        panel.setWidth("100%");
        panel.setHeight("100px");
        panel.setContent(table);

        layout.addComponent(panel);

    }

    // get List file save db 
    public String getLstFileSave() {
        String strLstFileName = Constants.EMPTY_CHARACTER;
        Collection<?> itemIDS = table.getItemIds();
        if (!itemIDS.isEmpty()) {
            for (Object itemID : itemIDS) {
                Property property = table.getContainerProperty(itemID, "File");
                Object data = property.getValue();
                strLstFileName = strLstFileName + prefixName + DateUtil.date2HHMMssNoSlash(new Date()) + "_" + data.toString() + ";";
            }
            strLstFileName = strLstFileName.substring(0, strLstFileName.length() - 1);
        }
        return strLstFileName;
    }

    // get List file save db not use prefix_
    public String getLstFileSaveNotPrefix() {
        String strLstFileName = Constants.EMPTY_CHARACTER;
        Collection<?> itemIDS = table.getItemIds();
        if (!itemIDS.isEmpty()) {
            for (Object itemID : itemIDS) {
                Property property = table.getContainerProperty(itemID, "File");
                Object data = property.getValue();
                strLstFileName = strLstFileName + DateUtil.date2HHMMssNoSlash(new Date()) + "_" + data.toString() + ";";
            }
            strLstFileName = strLstFileName.substring(0, strLstFileName.length() - 1);
        }
        return strLstFileName;
    }

    public String buildPath() {
        return RandomStringUtils.randomAlphabetic(15)
                + "/" + RandomStringUtils.randomAlphabetic(15)
                + "/" + RandomStringUtils.randomAlphabetic(15);
    }

    public void addFileAttack(String fileName) {
        File file = new File(path + fileName);
        try {

            Link link = new Link(fileName, new FileResource(file));
            links.add(link);
            if (!url.contains(fileName)) {
                url.add(fileName);
                // Add a row the hard way
                table.setVisible(true);
                Object newItemId = table.addItem();
                Item row1 = table.getItem(newItemId);
                row1.getItemProperty("File").setValue(fileName);
                table.setPageLength(table.getItemIds().size());
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void addFileAttack(String subPath, String fileName) {
        File file = new File((pathDownload != null && !pathDownload.equals(Constants.EMPTY_CHARACTER) ? pathDownload : Constants.EMPTY_CHARACTER)
                + (subPath != null && !subPath.equals(Constants.EMPTY_CHARACTER) ? subPath + "/" : Constants.EMPTY_CHARACTER) + fileName);
        try {

            Link link = new Link(fileName, new FileResource(file));
            links.add(link);
            if (!url.contains(fileName)) {
                url.add(fileName);
                // Add a row the hard way
                table.setVisible(true);
                Object newItemId = table.addItem();
                Item row1 = table.getItem(newItemId);
                row1.getItemProperty("File").setValue(fileName);
                table.setPageLength(table.getItemIds().size());
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void setVisibleDeleteFile(Boolean isDeleteFile) {
        Object[] objArr = table.getVisibleColumns();
        List<String> objList = new ArrayList<>();
        for (Object obj : objArr) {
            if (!"button".equalsIgnoreCase(obj.toString())) {
                objList.add(obj.toString());
            }
        }

        if (!isDeleteFile) {
            objList.add("button");
            multiUpload.setEnabled(true);
        } else {
            multiUpload.setEnabled(false);
        }
        table.setVisibleColumns(objList.toArray());
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathDownload() {
        return pathDownload;
    }

    public void setPathDownload(String pathDownload) {
        this.pathDownload = pathDownload;
    }

    public String getExtensionInoge() {
        return extensionInoge;
    }

    public void setExtensionInoge(String extensionInoge) {
        this.extensionInoge = extensionInoge;
    }

    public String getExtensionAllow() {
        return extensionAllow;
    }

    public void setExtensionAllow(String extensionAllow) {
        this.extensionAllow = extensionAllow;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setRequired(boolean require) {
        if (require) {
            setCaptionAsHtml(true);
            setCaption(getCaption() + Constants.FILE_CONF.REQUIRE);
        }
    }

    public VerticalLayout getvLayoutAttach() {
        return vLayoutAttach;
    }

    public void setvLayoutAttach(VerticalLayout vLayoutAttach) {
        this.vLayoutAttach = vLayoutAttach;
    }

    public void setEnableUpload(boolean enable) {
        multiUpload.setEnabled(enable);
    }

    public boolean getEnableUpload() {
        return multiUpload.isEnabled();
    }

    public String getPrefixPath() {
        return prefixPath;
    }

    public void setPrefixPath(String prefixPath) {
        this.prefixPath = prefixPath;
    }

    // Implement both receiver that saves upload in a file and
// listener for successful upload
    class HandlerUpload implements Receiver, SucceededListener {

        public File fileTemp;

        @Override
        public OutputStream receiveUpload(String fileName, String mimeType) {
            // Create upload stream
            FileOutputStream fos = null; // Output stream to write to
            // Open the file for writing.
//                file = new File("/tmp/uploads/" + fileName);
//                fos = new FileOutputStream(file);

            try {
                boolean checkExtend = false;
                if (extensionAllow != null) {
                    String[] extendArr = extensionAllow.split(",");
                    for (String e : extendArr) {
                        if (fileName.toLowerCase().endsWith(e.toLowerCase())) {
                            checkExtend = true;
                            break;
                        }
                    }
                }
                if (checkExtend) {
                    //_ replace ky tu ";","&",... ky tu dac biet cua URL trong ten file
                    fileName = fileName
                            .replaceAll("[~!@#$%^&*()+`=;,\\-\\s+\\[\\]{}']", "_")
                            .replaceAll("(_)+", "_");
                    fileName = DateUtil.date2HHMMssNoSlash(new Date()) + "_" + fileName;
                    final String saltPath = buildPath();
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    fileTemp = new File(path + prefixName + fileName);
//                    try (FileOutputStream fos = new FileOutputStream(file)) {
//                        fos.write(IOUtils.toByteArray(inputStream));
//                        fos.close();
//                    }
                    fos = new FileOutputStream(fileTemp);
                    Notification.show(fileTemp.getName());
                } else {
                    Notification.show(BundleUtils.getString("fileTypeInvalid") + ": " + fileName);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                PrintLog.printLog(ex);
            }

            return fos; // Return the output stream to write to
        }

        @Override
        public void uploadSucceeded(Upload.SucceededEvent event) {
            Link link = new Link(fileTemp.getName(), new FileResource(fileTemp));
            link.setTargetName("_blank");
            link.setDescription(fileTemp.getName());
//                    layout.addComponent(link);
            links.add(link);
            if (!url.contains(prefixName + fileTemp.getName())) {
                url.add(prefixName + fileTemp.getName());
            } else {
                Notification.show(BundleUtils.getString("fileExist") + fileTemp.getName(), Notification.Type.TRAY_NOTIFICATION);
            }
            Notification.show("Upload success");
        }
    };
    
    public void setEnableToDelete(Boolean enabled){
        if(!enabled){
            table.removeGeneratedColumn("button");
        }
    }
}
