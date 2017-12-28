/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.*;
import com.iso.dashboard.dto.CFlow;
import com.iso.dashboard.ui.FlowSearchUI;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
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
public class ChooseFlowUI extends CustomComponent {

    Logger log = Logger.getLogger(ChooseFlowUI.class);

    final VerticalLayout layout = new VerticalLayout();
    Button button = new Button();
    Table table = new Table();
    BeanItemContainer<String> containerLink;
    List<CFlow> cFlows = new ArrayList<>();
    VerticalLayout vLayoutAttach;

    public ChooseFlowUI(String caption) {
        cFlows.clear();
        initGUI();
    }

    public ChooseFlowUI(Date date) {
        addUpload();
    }
    
    private void initGUI() {
        button.setCaption(BundleUtils.getString("procedureFlow.chooseFlow"));
        button.setDescription(BundleUtils.getString("procedureFlow.chooseFlow"));
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
                        CFlow cFlow = (CFlow) itemId;
                        table.removeItem(itemId);
                        cFlows.remove(cFlow);
                    }
                });
                return btnDelete;
            }
        });
        layout.addComponent(button);

        vLayoutAttach = new VerticalLayout();
        vLayoutAttach.setImmediate(true);
        vLayoutAttach.setWidth("100%");
        vLayoutAttach.setHeight("60px");
        vLayoutAttach.setMargin(false);
        vLayoutAttach.setSpacing(false);
        vLayoutAttach.setStyleName("verticalLayOut-Scroll");
        vLayoutAttach.addComponent(table);

        layout.addComponent(vLayoutAttach);
        setCompositionRoot(layout);
    }

    private void addUpload() {
        FlowSearchUI ui = new FlowSearchUI(BundleUtils.getString("procedureFlow.chooseFlow"));
        Window window = new Window("", ui);
        float height = UI.getCurrent().getWidth() * 7f / 10;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(50.0f, Sizeable.Unit.PERCENTAGE);
        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ui.getBtnSelect().addClickListener((Button.ClickEvent event) -> {
            List<CFlow> list = ui.getTreeTaskSelected();
            if (list != null) {
                if(list.size() > 1){
                    ComponentUtils.showNotification(BundleUtils.getString("procedureFlow.chooseFlowOnlyOne"));
                    return;
                }
                list.stream().filter((emp) -> (!cFlows.contains(emp))).forEach((emp) -> {
                    addFlow(emp);
                });
            }
            window.close();
        });
        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public void addFlow(CFlow cFlow) {
        try {

            if (!cFlows.contains(cFlow)) {
                cFlows.add(cFlow);
                // Add a row the hard way
                table.setVisible(true);
                Item row1 = table.addItem(cFlow);
                row1.getItemProperty("File").setValue(cFlow.getName());
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

    public List<CFlow> getcFlows() {
        return cFlows;
    }

    public void setcFlows(List<CFlow> cFlows) {
        this.cFlows = cFlows;
    }

}
