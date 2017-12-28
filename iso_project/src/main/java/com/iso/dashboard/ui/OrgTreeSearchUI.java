/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.service.OrganizationMngService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.AbstractSelect;
//import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
//import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
//import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
public class OrgTreeSearchUI extends HorizontalLayout {

    private TextField txtOrgName;
    private Button btnOpen;

    private Tree orgTree;
    private Button btnSelect;

    private Organization orgSelected;

    public OrgTreeSearchUI(String caption) {
        setCaption(caption);
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        setSizeFull();
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("toolbar");
        toolbar.setWidth("100%");

        txtOrgName = new TextField();
        txtOrgName.setWidth("100%");
        txtOrgName.addStyleName("none-border");
        txtOrgName.setDescription(BundleUtils.getString("orgMnt.treeSearh.title"));
        txtOrgName.setInputPrompt(BundleUtils.getString("orgMnt.treeSearh.title"));
//        txtOrgName.setPlaceholder(BundleUtils.getString("orgMnt.treeSearh.title"));
        toolbar.addComponent(txtOrgName);
        toolbar.setExpandRatio(txtOrgName, 9.5f);

        btnOpen = new Button();
        btnOpen.setIcon(FontAwesome.SEARCH);
        btnOpen.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnOpen.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                orgSelected = null;
                actionSearch();
            }
        });
        toolbar.addComponent(btnOpen);
        addComponent(toolbar);
    }

    public Component buildContentTree() {

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setCaption(BundleUtils.getString("orgMngt.list"));
        mainLayout.setIcon(FontAwesome.CALENDAR);
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        orgTree = new Tree();
        orgSelected = null;
        initTree(orgTree);
        handlerActionTree();
        CssLayout contenPanel = new CssLayout();
        contenPanel.addStyleName("dashboard-panels");
        Responsive.makeResponsive(contenPanel);
        contenPanel.addComponent(orgTree);

        mainLayout.addComponent(contenPanel);
        mainLayout.setExpandRatio(contenPanel, 1.0f);
        mainLayout.addComponent(buildButton());
        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
//        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        detailsWrapper.addComponent(mainLayout);
        return detailsWrapper;
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

    public static void initTree(Tree tree) {
        BeanItemContainer<Organization> container = new BeanItemContainer<>(
                Organization.class);
        List<Organization> lstDataTree = OrganizationMngService.getInstance().listOrganization(null, null);
        Map<Integer, Organization> mapIdWithOrgObj = new HashMap<>();
        if (lstDataTree != null && !lstDataTree.isEmpty()) {
            for (Organization org : lstDataTree) {
                mapIdWithOrgObj.put(org.getId(), org);
            }
        }
        container.addAll(lstDataTree);
        tree.setContainerDataSource(container);
        for (Object itemId : tree.getItemIds()) {
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
        }
        tree.setItemCaptionPropertyId("name");
        tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
        tree.addExpandListener(new Tree.ExpandListener() {

            @Override
            public void nodeExpand(Tree.ExpandEvent event) {
                Organization org = (Organization) event.getItemId();
                List<Organization> lstChild = OrganizationMngService.getInstance().listOrganization(null, String.valueOf(org.getId()));
                if (lstChild == null || lstChild.isEmpty()) {
                    tree.setChildrenAllowed(org, false);
                }
            }
        });

    }

    public void handlerActionTree() {
        orgTree.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                orgSelected = (Organization) event.getItemId();
            }
        });
    }

    public void actionSearch() {
        Component ui = buildContentTree();
        Window window = new Window("", ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 1 / 4;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(70.0f, Unit.PERCENTAGE);
//        window.setIcon(VaadinIcons.CALENDAR_USER);
        getBtnSelect().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (orgSelected == null) {
                    Notification.show(BundleUtils.getString("orgMngt.validate.nodeIsEmpty"));
                    return;
                }
                txtOrgName.setValue(orgSelected.getName());
                window.close();
            }

        });

        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
    }

    public TextField getTxtOrgName() {
        return txtOrgName;
    }

    public void setTxtOrgName(TextField txtOrgName) {
        this.txtOrgName = txtOrgName;
    }

    public Button getBtnOpen() {
        return btnOpen;
    }

    public void setBtnOpen(Button btnOpen) {
        this.btnOpen = btnOpen;
    }

    public Tree getOrgTree() {
        return orgTree;
    }

    public void setOrgTree(Tree orgTree) {
        this.orgTree = orgTree;
    }

    public Button getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(Button btnSelect) {
        this.btnSelect = btnSelect;
    }

    public Organization getOrgSelected() {
        return orgSelected;
    }

    public void setOrgSelected(Organization orgSelected) {
        this.orgSelected = orgSelected;
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
