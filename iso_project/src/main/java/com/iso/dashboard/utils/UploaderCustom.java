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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
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
import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

/**
 *
 *
 * Khai bao: Uploader uploader = new Uploader();
 *
 * Doc cac url: uploader.getUrl();
 *
 */
public class UploaderCustom extends CustomComponent {

    Logger log = Logger.getLogger(UploaderCustom.class);

    public static String PATH_UPLOAD = BundleUtils.getStringCas("path_upload");

    MultiFileUpload multiUpload;
    String folder = "VAADIN/UPLOAD";
    String prefixName = Constants.EMPTY_CHARACTER;
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

    private final String maxFileSize = "15mb";
    private VerticalLayout mainLayout;
    private Plupload uploader;
    private Label info;
    private ProgressBar bar;
    private UploadCustomAction action;
    private Button btnDownload;

    public static interface TYPE_UPLOAD {

        public static final String TASK = "TASK";
        public static final String AVATAR = "AVATAR";
        public static final String TASK_REPORT = "TASK_REPORT";
    }

    public UploaderCustom(String caption, String pathUpload, UploadCustomAction action) {
        if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.AVATAR)) {
            this.path = BundleUtils.getStringCas("path_upload_avatar") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK)) {
            this.path = BundleUtils.getStringCas("path_upload_task") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK_REPORT)) {
            this.path = BundleUtils.getStringCas("path_upload_task_report") + DateUtil.date2String(new Date()) + "/";
        } else {
            this.path = PATH_UPLOAD + "/" + DateUtil.date2String(new Date()) + "/";
        }
        url.clear();
        links.clear();

        this.action = action;
        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setCaption(caption);
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }

    public UploaderCustom(String caption, String pathUpload) {
        if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.AVATAR)) {
            this.path = BundleUtils.getStringCas("path_upload_avatar") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK)) {
            this.path = BundleUtils.getStringCas("path_upload_task") + DateUtil.date2String(new Date()) + "/";
        } else if (pathUpload != null && pathUpload.equals(TYPE_UPLOAD.TASK_REPORT)) {
            this.path = BundleUtils.getStringCas("path_upload_task_report") + DateUtil.date2String(new Date()) + "/";
        } else {
            this.path = PATH_UPLOAD + "/" + DateUtil.date2String(new Date()) + "/";
        }
        url.clear();
        links.clear();

        this.action = action;
        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setCaption(caption);
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }

    public void buildMainLayout() {
        uploader = new Plupload(BundleUtils.getString("chooseAttachFile"), FontAwesome.UPLOAD);
        info = new Label();
        info.setWidth("100%");
        bar = new ProgressBar(0.0f);
        bar.setWidth("100%");
        btnDownload = new Button();
        btnDownload.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnDownload.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        btnDownload.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
        btnDownload.setIcon(ISOIcons.DOWNLOAD);
        btnDownload.setDescription(BundleUtils.getString("common.button.download"));
        btnDownload.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    String fileName = 
                            //VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                            //+ File.separator
                            uploader.getUploadPath()
                            + File.separator
                            + uploader.getUploadedFiles()[0].getName()
                            ;
                    File file = (File) uploader.getUploadedFiles()[0].getUploadedFile();
                    //File file = new File(fileName);
                    Resource resource = new FileResource(file);
                    Page.getCurrent().open(resource, null, false);
                } catch (Exception e) {
                    e.printStackTrace();
                    ComponentUtils.showNotification("Download Error");
                }
            }
        });
        btnDownload.setVisible(false);

        uploader.setMaxFileSize(maxFileSize);
        uploader.setMultiSelection(false);
        uploader.setUploadPath(this.path);

        //show notification after file is uploaded
        uploader.addFileUploadedListener(new Plupload.FileUploadedListener() {
            @Override
            public void onFileUploaded(PluploadFile file) {
                Notification.show("Uploaded file: " + file.getName());
                info.setValue(file.getName());
            }
        });

        //update upload progress
        uploader.addUploadProgressListener(new Plupload.UploadProgressListener() {
            @Override
            public void onUploadProgress(PluploadFile file) {
//                info.setValue("Uploading " + file.getName()
//                        + " " + file.getPercent() + "%");
                bar.setValue((float) file.getPercent() / 100);
            }
        });

        //autostart the uploader after addind files
        uploader.addFilesAddedListener(new Plupload.FilesAddedListener() {
            @Override
            public void onFilesAdded(PluploadFile[] files) {
                String fileName = files[0].getName();
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
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    uploader.start();
                } else {
                    Notification.show(BundleUtils.getString("fileTypeInvalid") + ": " + fileName);
                }
            }
        });

        //notify, when the upload process is completed
        uploader.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
            @Override
            public void onUploadComplete() {
//                info.setValue("Upload is completed!");
                bar.setValue(1.0f);
                btnDownload.setVisible(true);
                //action.actionUploadSuccess();
            }
        });

        //handle errors
        uploader.addErrorListener(new Plupload.ErrorListener() {
            @Override
            public void onError(PluploadError error) {
                Notification.show("ERROR: "
                        + error.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });

        HorizontalLayout hori = new HorizontalLayout();
        hori.setWidth("100%");
        hori.addComponents(uploader, btnDownload);
        hori.setComponentAlignment(btnDownload, Alignment.MIDDLE_RIGHT);
        Responsive.makeResponsive(hori);

        mainLayout.addComponents(hori, bar, info);
    }

    public Label getInfo() {
        return info;
    }

    public void setInfo(Label info) {
        this.info = info;
    }

    public ProgressBar getBar() {
        return bar;
    }

    public void setBar(ProgressBar bar) {
        this.bar = bar;
    }

    public UploadCustomAction getAction() {
        return action;
    }

    public void setAction(UploadCustomAction action) {
        this.action = action;
    }

    public Plupload getUploader() {
        return uploader;
    }

    public void setUploader(Plupload uploader) {
        this.uploader = uploader;
    }

    public interface UploadCustomAction {

        public void actionUploadSuccess();
    }
}
