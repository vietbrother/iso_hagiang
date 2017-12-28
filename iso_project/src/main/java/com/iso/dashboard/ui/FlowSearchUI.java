/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.component.GridFilter;
import com.iso.dashboard.dto.CFlow;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.service.FlowMngtService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.SelectionEvent;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.AbstractSelect;
//import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
//import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowSearchUI extends HorizontalLayout {

    private GridFilter treeTask;

    private Button btnSelect;

    private List<CFlow> treeTaskSelected;

    public FlowSearchUI(String caption) {
        //setCaption(BundleUtils.getString("orgMngt.list"));
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setCaption(caption);
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        treeTaskSelected = new ArrayList<>();

        treeTask = new GridFilter();
        treeTask.setHeightMode(HeightMode.ROW);
        initTreeTask(treeTask, FlowMngtService.getInstance().listCFlows(null));

        handlerActionTree();

        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);
        contenPanel.addComponent(treeTask);

        mainLayout.addComponent(contenPanel);
        mainLayout.setExpandRatio(contenPanel, 1.0f);
        mainLayout.addComponent(buildButton());
        
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addComponent(mainLayout);
        addComponent(detailsWrapper);
    }

    public Component buildButton() {
        /**
         * Create button action
         */
        // btnSelect
        btnSelect = new Button();
        btnSelect.setCaption(BundleUtils.getString("choose"));
        btnSelect.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSelect.setWidth(Constants.STYLE_CONF.AUTO_VALUE);
        btnSelect.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        btnSelect.setIcon(ISOIcons.SAVE);

        HorizontalLayout temp = new HorizontalLayout();
        temp.setSpacing(true);
        temp.addStyleName("fields");
        temp.addComponents(btnSelect);
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);
        footer.addComponent(temp);
        footer.setComponentAlignment(temp, Alignment.BOTTOM_RIGHT);

        return footer;
    }

    public List<Organization> lstDataTree = new ArrayList<>();
    public List<CTask> lstDataTreeTask = new ArrayList<>();
    public Map<Integer, Organization> mapIdWithOrgObj = new HashMap<>();

    public static void initTreeTask(Grid treeEmp, List<CFlow> lstDataSource) {

        treeEmp.setWidth(800, Unit.PIXELS);
        treeEmp.removeAllColumns();
        treeEmp.setSelectionMode(Grid.SelectionMode.MULTI);
        treeEmp.setColumnReorderingAllowed(true);
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("code", String.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("description", String.class, null);
        int i = 1;
        for (CFlow u : lstDataSource) {
            Item item = container.addItem(u);
            item.getItemProperty("index").setValue(i);
            item.getItemProperty("code").setValue(u.getCode());
            item.getItemProperty("name").setValue(u.getName());
            item.getItemProperty("description").setValue(u.getDescription());
            i++;
        }
        container.sort(new Object[]{"id"}, new boolean[]{true});
        treeEmp.setContainerDataSource(container);
    }

    public void handlerActionTree() {
        treeTask.addSelectionListener((SelectionEvent event) -> {
            Iterator it = event.getSelected().iterator();
            while (it.hasNext()) {
                treeTaskSelected.add((CFlow) it.next());
            }
        });
    }


    public Button getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(Button btnSelect) {
        this.btnSelect = btnSelect;
    }

    public Grid getTreeTask() {
        return treeTask;
    }

    public void setTreeTask(GridFilter treeTask) {
        this.treeTask = treeTask;
    }

    public List<CFlow> getTreeTaskSelected() {
        return treeTaskSelected;
    }

    public void setTreeTaskSelected(List<CFlow> treeTaskSelected) {
        this.treeTaskSelected = treeTaskSelected;
    }

    public List<CTask> getLstDataTreeTask() {
        return lstDataTreeTask;
    }

    public void setLstDataTreeTask(List<CTask> lstDataTreeTask) {
        this.lstDataTreeTask = lstDataTreeTask;
    }

    public void setLstDataTree(List<Organization> lstDataTree) {
        this.lstDataTree = lstDataTree;
    }

    public Map<Integer, Organization> getMapIdWithOrgObj() {
        return mapIdWithOrgObj;
    }

    public void setMapIdWithOrgObj(Map<Integer, Organization> mapIdWithOrgObj) {
        this.mapIdWithOrgObj = mapIdWithOrgObj;
    }

}
