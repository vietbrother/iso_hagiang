/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class GroupBoxUI extends VerticalLayout {

    OptionGroup ogMain;
    CheckBox cbAll;
    private boolean checkAll;

    /**
     * khoi tao doi tuong GroupBox
     *
     * @param notCheck: true ==> co nut check all, false ==>khong co nut check
     * all
     */
    public GroupBoxUI(String caption, boolean notCheck) {
        this.checkAll = notCheck;
        setCaption(caption);
        initComponent(checkAll);
    }

    public void initComponent(boolean notCheck) {
        ogMain = new OptionGroup();
        ogMain.setImmediate(true);
        ogMain.setWidth("100.0%");
        ogMain.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        ogMain.setMultiSelect(true);
        ogMain.setHeight("200px");

        if (notCheck) {
            VerticalLayout vlAll = new VerticalLayout();
            vlAll.setStyleName("verticalLayOut-Scroll");
            vlAll.setSpacing(true);
            cbAll = new CheckBox(BundleUtils.getString("common.selectAll"));
            cbAll.addStyleName("v-margin-left-3px");

            cbAll.addListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    for (Object item : ogMain.getItemIds()) {
                        if (cbAll.getValue()) {
                            ogMain.select(item);
                        } else {
                            ogMain.setValue(null);
                        }
                    }
                }
            });
            vlAll.addComponent(cbAll);
            this.addComponent(vlAll);
        }

        this.addComponent(ogMain);
    }

    public void setComponentHeight(String height) {
        ogMain.setHeight(height);
    }

    public OptionGroup getOgMain() {
        return ogMain;
    }

    public void setOgMain(OptionGroup ogMain) {
        this.ogMain = ogMain;
    }



    public CheckBox getCbAll() {
        return cbAll;
    }

    public void setCbAll(CheckBox cbAll) {
        this.cbAll = cbAll;
    }

    public void setRequired(boolean value) {
        cbAll.setRequired(value);
    }

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }
}
