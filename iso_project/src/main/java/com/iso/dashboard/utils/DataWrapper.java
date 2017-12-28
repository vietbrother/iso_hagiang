/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.ui.ProcedureSearchUI;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 *
 * Khai bao: Uploader uploader = new Uploader();
 *
 * Doc cac url: uploader.getUrl();
 *
 */
public class DataWrapper extends CustomComponent {

    Logger log = Logger.getLogger(DataWrapper.class);

    final VerticalLayout layout = new VerticalLayout();
    Button button = new Button();
    Table table = new Table();
    BeanItemContainer<String> containerLink;
    List<CProcedure> cProcedures = new ArrayList<>();
    VerticalLayout vLayoutAttach;

    public DataWrapper(String caption) {
        cProcedures.clear();
        initGUI();
    }

    public DataWrapper(Date date) {
        addUpload();
    }
    
    private void initGUI() {
        button.setCaption(BundleUtils.getString("flowProcedure.chooseProc"));
        button.setDescription(BundleUtils.getString("flowProcedure.chooseProc"));
        button.setImmediate(true);
        button.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        button.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        button.addClickListener((Button.ClickEvent event) -> {
            addUpload();
        });
        table.setImmediate(true);
        table.setWidth("100%");
        table.setHeight("100%");
        table.setVisible(false);
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        table.addContainerProperty("button", String.class, null);
        table.addContainerProperty("File", String.class, null);
        table.setColumnWidth("button", 50);
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
                        CProcedure cProcedure = (CProcedure) itemId;
                        table.removeItem(itemId);
                        cProcedures.remove(cProcedure);
                    }
                });
                return btnDelete;
            }
        });
        layout.addComponent(button);

//        vLayoutAttach = new VerticalLayout();
//        vLayoutAttach.setImmediate(true);
//        vLayoutAttach.setWidth("100%");
//        vLayoutAttach.setHeight("60px");
//        vLayoutAttach.setMargin(false);
//        vLayoutAttach.setSpacing(false);
//        vLayoutAttach.setStyleName("verticalLayOut-Scroll");
//        vLayoutAttach.addComponent(table);
        
        Panel panel = new Panel();
        panel.addStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
        panel.setHeight("100px");
        panel.setContent(table);

        layout.addComponent(panel);
        setCompositionRoot(layout);
    }

    private void addUpload() {
        ProcedureSearchUI ui = new ProcedureSearchUI("");
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 7f / 10;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(50.0f, Sizeable.Unit.PERCENTAGE);
        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnSelect().addClickListener((Button.ClickEvent event) -> {
            List<CProcedure> list = ui.getTreeTaskSelected();
            if (list != null) {
                list.stream().filter((emp) -> (!cProcedures.contains(emp))).forEach((emp) -> {
                    addProcedure(emp);
                });
            }
            window.close();
        });
        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public void addProcedure(CProcedure cProcedure) {
        try {

            if (!cProcedures.contains(cProcedure)) {
                cProcedures.add(cProcedure);
                // Add a row the hard way
                table.setVisible(true);
                Item row1 = table.addItem(cProcedure);
                row1.getItemProperty("File").setValue(cProcedure.getName());
                table.setPageLength(table.getItemIds().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setEnableToEdit(Boolean enabled) {
        button.setEnabled(enabled);
        if (!enabled) {
            table.setColumnCollapsingAllowed(true);
            table.setColumnCollapsed("button", true);
            table.addStyleName("hidecollapse");
        } else {
            table.setColumnCollapsingAllowed(false);
            table.removeStyleName("hidecollapse");
        }
    }

    public List<CProcedure> getcProcedures() {
        return cProcedures;
    }

    public void setcProcedures(List<CProcedure> cProcedures) {
        this.cProcedures = cProcedures;
    }

}
