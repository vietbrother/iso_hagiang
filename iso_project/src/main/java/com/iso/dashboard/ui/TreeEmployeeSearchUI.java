/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
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
public class TreeEmployeeSearchUI extends HorizontalLayout {

    private Tree treeOrg;
    private Grid treeTask;

    private Button btnSelect;

    private List<Employee> treeTaskSelected;

    public TreeEmployeeSearchUI(String caption) {
        //setCaption(BundleUtils.getString("orgMngt.list"));
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setCaption(BundleUtils.getString("orgMngt.list"));
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        treeOrg = new Tree();
        treeTaskSelected = new ArrayList<>();
        initTreeOrg(treeOrg);

        treeTask = new Grid();
        treeTask.setHeightMode(HeightMode.ROW);
        initTreeTask(treeTask, new ArrayList<>());

        handlerActionTree();

        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);
        contenPanel.addComponent(treeOrg);
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

    public void getLstDataTree() {
        lstDataTree = new ArrayList<>();
        mapIdWithOrgObj = new HashMap<>();

        lstDataTree = OrganizationMngService.getInstance().listOrganization(null, null);
        if (lstDataTree != null && !lstDataTree.isEmpty()) {
            for (Organization org : lstDataTree) {
                mapIdWithOrgObj.put(org.getId(), org);
            }
        }
    }

    public static void initTreeOrg(Tree tree) {
        BeanItemContainer<Organization> container = new BeanItemContainer<>(
                Organization.class);
        List<Organization> lstDataTree = OrganizationMngService.getInstance().listOrganization(null, null);
        Map<Integer, Organization> mapIdWithOrgObj = new HashMap<>();
        if (lstDataTree != null && !lstDataTree.isEmpty()) {
            lstDataTree.stream().forEach((org) -> {
                mapIdWithOrgObj.put(org.getId(), org);
            });
        }
        container.addAll(lstDataTree);
        tree.setContainerDataSource(container);
        tree.getItemIds().stream().forEach((itemId) -> {
            //Here the icon can be set to anything. Just an example.
            Organization temp = (Organization) itemId;
            if (temp != null && temp.getParentId() == 0) {
                tree.setItemIcon(itemId, FontAwesome.BANK);
            } else {
                tree.setItemIcon(itemId, FontAwesome.FOLDER);
            }
            if (mapIdWithOrgObj.get(temp.getParentId()) != null) {
                tree.setParent(itemId, mapIdWithOrgObj.get(temp.getParentId()));
            }
        });
        tree.setItemCaptionPropertyId("name");
        tree.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        tree.addExpandListener((Tree.ExpandEvent event) -> {
            Organization org = (Organization) event.getItemId();
            List<Organization> lstChild = OrganizationMngService.getInstance().listOrganization(null, String.valueOf(org.getId()));
            if (lstChild == null || lstChild.isEmpty()) {
                tree.setChildrenAllowed(org, false);
            }
        });

    }

    public static void initTreeTask(Grid treeEmp, List<Employee> lstDataSource) {

        treeEmp.setWidth(800, Unit.PIXELS);
        treeEmp.removeAllColumns();
        treeEmp.setSelectionMode(Grid.SelectionMode.MULTI);
        treeEmp.setColumnReorderingAllowed(true);
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("index", Integer.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("title", String.class, null);
        container.addContainerProperty("department", String.class, null);
        int i = 1;
        for (Employee u : lstDataSource) {
            Item item = container.addItem(u);
            item.getItemProperty("index").setValue(i);
            item.getItemProperty("name").setValue(u.getFirstName());
            item.getItemProperty("title").setValue(u.getJob().getJobTitle());
            item.getItemProperty("department").setValue(u.getDepartment().getName());
            i++;
        }
        container.sort(new Object[]{"id"}, new boolean[]{true});
        treeEmp.setContainerDataSource(container);
        treeEmp.getColumn("index").setHeaderCaption(BundleUtils.getString("task.assign.list.index"));
        treeEmp.getColumn("name").setHeaderCaption(BundleUtils.getString("task.assign.list.name"));
        treeEmp.getColumn("title").setHeaderCaption(BundleUtils.getString("task.assign.list.title"));
        treeEmp.getColumn("department").setHeaderCaption(BundleUtils.getString("task.assign.list.department"));
        
        treeEmp.setHeightMode(HeightMode.ROW);
        treeEmp.setHeightByRows(container.size() == 0 ? 1 : container.size());
    }

    public void handlerActionTree() {
        treeOrg.addItemClickListener((ItemClickEvent event) -> {
            Organization orgSelected = (Organization) event.getItemId();
            initTreeTask(treeTask, EmployeeMngtService.getInstance().getEmployeeByOrganizationId(String.valueOf(orgSelected.getId())));
        });
        treeTask.addSelectionListener((SelectionEvent event) -> {
            Iterator it = event.getSelected().iterator();
            while (it.hasNext()) {
                treeTaskSelected.add((Employee) it.next());
            }
//            treeTaskSelected = event.getSelected();
        });
    }

    public Tree getOrgTree() {
        return treeOrg;
    }

    public void setOrgTree(Tree orgTree) {
        this.treeOrg = orgTree;
    }

    public Button getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(Button btnSelect) {
        this.btnSelect = btnSelect;
    }

    public Tree getTreeOrg() {
        return treeOrg;
    }

    public void setTreeOrg(Tree treeOrg) {
        this.treeOrg = treeOrg;
    }

    public Grid getTreeTask() {
        return treeTask;
    }

    public void setTreeTask(Grid treeTask) {
        this.treeTask = treeTask;
    }

    public List<Employee> getTreeTaskSelected() {
        return treeTaskSelected;
    }

    public void setTreeTaskSelected(List<Employee> treeTaskSelected) {
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
