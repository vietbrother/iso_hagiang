/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import pl.pdfviewer.PdfViewer;
import pl.pdfviewer.listener.AngleChangeListener;
import pl.pdfviewer.listener.PageChangeListener;

/**
 *
 * @author VIET_BROTHER
 */
public class ApproveUI extends CustomComponent {

    private String url;
    private VerticalLayout mainLayout;
    private PdfViewer pdfView;
    private OptionGroup ogResult;
    private TextArea txaResult;

    private Button btnSave;
    private Button btnCancel;

    public ApproveUI(String caption, String url) {
        this.url = url;
        mainLayout = new VerticalLayout();
        mainLayout.setCaption(caption);
        mainLayout.addStyleName("dashboard-panels");
        mainLayout.addComponent(buildMainLayout());
        //mainLayout.addComponent(buildApprove());
        mainLayout.addComponent(buildButton());

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(mainLayout);
        setCompositionRoot(detailsWrapper);
    }

    public VerticalLayout buildMainLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");

//        PdfViewer c = new PdfViewer(new File("C:\\Users\\VIET_BROTHER\\Desktop\\Lập trình Java cơ bản - nâng cao - FPT Polytechnic\\Java\\Giáo trình FPT\\Java 1\\Lý thuyết\\MOB1013-Slide 7 - Ke thua.pdf"));
        pdfView = new PdfViewer(new File(url));
        pdfView.setHeight("1000px");
        pdfView.setWidth("1200px");
        pdfView.setBackAngleButtonCaption(FontAwesome.ROTATE_LEFT.getHtml());
        pdfView.setNextAngleButtonCaption(FontAwesome.ROTATE_RIGHT.getHtml());
        pdfView.setIncreaseButtonCaption(FontAwesome.SEARCH_PLUS.getHtml());
        pdfView.setDecreaseButtonCaption(FontAwesome.SEARCH_MINUS.getHtml());
        pdfView.setPreviousPageCaption(FontAwesome.ANGLE_LEFT.getHtml() + " Back");
        pdfView.setNextPageCaption("Next " + FontAwesome.ANGLE_RIGHT.getHtml());
        pdfView.addPageChangeListener(new PageChangeListener() {
            @Override
            public void pageChange(Integer page) {
                System.out.println(page + " PAGE CHANGE");
            }
        });
        pdfView.addAngleChangeListener(new AngleChangeListener() {
            @Override
            public void angleChange(Integer page) {
                System.out.println("Angle change");
            }
        });
        layout.addComponent(pdfView);

//        // A resource reference to some object
//        Resource res = new ThemeResource("C:\\Users\\VIET_BROTHER\\Desktop\\Lập trình Java cơ bản - nâng cao - FPT Polytechnic\\Java\\Giáo trình FPT\\Java 1\\Lý thuyết\\MOB1013-Slide 7 - Ke thua.pdf");
//
//        // Display the object
//        Embedded object = new Embedded("My PDF (requires browser plugin)", res);
//        object.setType(Embedded.TYPE_OBJECT);
//        object.setMimeType("application/pdf");
//        object.setWidth("400px");
//        object.setHeight("300px");
//        layout.addComponent(object);
//        // END-EXAMPLE: component.embedded.pdf
        return layout;
    }

    public Component buildApprove() {

        txaResult = new TextArea();
        txaResult.setImmediate(true);
        txaResult.setRequired(true);
        txaResult.setWidth("100.0%");
        txaResult.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        txaResult.setRequired(true);
        txaResult.setDescription(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setCaption(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setInputPrompt(BundleUtils.getString("approve.list.result.noReason"));
        txaResult.setVisible(false);

        ogResult = new OptionGroup(BundleUtils.getString("approve.list.result"));  // Pass a string used as caption (title) of the group of radio buttons.
        ogResult.addItems(Boolean.TRUE, Boolean.FALSE);  // Pass item ids to be used in constructing Item objects on our behalf.
        ogResult.setItemCaption(Boolean.TRUE, BundleUtils.getString("approve.list.result.yes")); // Specify a textual label rather than default generated value "true" & "false".
        ogResult.setItemCaption(Boolean.FALSE, BundleUtils.getString("approve.list.result.no"));
        ogResult.setValue(Boolean.TRUE);  // Specify which radio button is selected by default.
        // Add a listener to react to user selection.
        ogResult.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                 txaResult.setVisible(!((boolean) event.getProperty().getValue()));
            }
        });

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.setWidth("100%");
        details.addComponent(ogResult);
        details.addComponent(txaResult);
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
        temp.addComponents(//btnSave,
                btnCancel
        );
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);
        footer.addComponent(temp);
        footer.setComponentAlignment(temp, Alignment.BOTTOM_RIGHT);
//        footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);
//        footer.addComponent(btnCancel);
//        footer.setComponentAlignment(btnSave, Alignment.TOP_LEFT);
//        btnLayout.setSpacing(true);
//        btnLayout.setMargin(true);
//        btnLayout.addStyleName("fields");
//        btnLayout.addComponents(btnSave,
//                btnCancel);

        return footer;
    }

    public OptionGroup getOgResult() {
        return ogResult;
    }

    public void setOgResult(OptionGroup ogResult) {
        this.ogResult = ogResult;
    }

    public TextArea getTxaResult() {
        return txaResult;
    }

    public void setTxaResult(TextArea txaResult) {
        this.txaResult = txaResult;
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

    
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public PdfViewer getPdfView() {
        return pdfView;
    }

    public void setPdfView(PdfViewer pdfView) {
        this.pdfView = pdfView;
    }

}
