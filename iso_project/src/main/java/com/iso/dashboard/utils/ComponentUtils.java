/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.google.common.collect.Lists;
import com.iso.dashboard.dto.CatItemDTO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
public class ComponentUtils {

    public static void fillDataCombo(ComboBox combo, String addAll, String valueDefault,
            List<CatItemDTO> appPatamDTO) {

        List<CatItemDTO> lstValue = Lists.newArrayList();

        //Neu lst truyen vao null
        if (DataUtil.isListNullOrEmpty(appPatamDTO)) {
            appPatamDTO = Lists.newArrayList();
        }

        lstValue.addAll(appPatamDTO);

        final BeanItemContainer<CatItemDTO> container = new BeanItemContainer<>(
                CatItemDTO.class);

        // Them gia tri 
        CatItemDTO allValue;
        String defaultStr;
        if (!DataUtil.isStringNullOrEmpty(addAll)) {
            defaultStr = addAll;
        } else {
            defaultStr = Constants.DEFAULT_VALUE;
        }
        allValue = new CatItemDTO();
        allValue.setItemName(BundleUtils.getString("combo.default.common"));
        allValue.setItemId(defaultStr);
        lstValue.add(0, allValue);

        CatItemDTO defaultValue = null;
        if (!DataUtil.isStringNullOrEmpty(valueDefault)) {
            Map<String, CatItemDTO> map = new HashMap<String, CatItemDTO>();
            for (CatItemDTO comboObject : appPatamDTO) {
                map.put(comboObject.getItemId(), comboObject);
            }
            defaultValue = map.get(valueDefault);
        }
        container.addAll(lstValue);
        combo.setFilteringMode(FilteringMode.CONTAINS);
        combo.setContainerDataSource(container);
        combo.setItemCaptionPropertyId(Constants.ITEM_NAME);
        combo.setNullSelectionAllowed(false);

        if (!DataUtil.isStringNullOrEmpty(addAll)) {
            combo.setValue(allValue);
        } else if (defaultValue != null) {
            combo.setValue(defaultValue);
        }
    }

    public static void fillDataComboNoDefault(ComboBox combo, String valueDefault,
            List<CatItemDTO> appPatamDTO) {

        List<CatItemDTO> lstValue = Lists.newArrayList();

        //Neu lst truyen vao null
        if (DataUtil.isListNullOrEmpty(appPatamDTO)) {
            appPatamDTO = Lists.newArrayList();
        }

        lstValue.addAll(appPatamDTO);

        final BeanItemContainer<CatItemDTO> container = new BeanItemContainer<>(
                CatItemDTO.class);

        CatItemDTO defaultValue;
        if (!DataUtil.isStringNullOrEmpty(valueDefault)) {
            Map<String, CatItemDTO> map = new HashMap<>();
            for (CatItemDTO comboObject : appPatamDTO) {
                map.put(comboObject.getItemId(), comboObject);
            }
            defaultValue = map.get(valueDefault);
        } else {
            defaultValue = appPatamDTO.get(0);
        }
        container.addAll(lstValue);
        combo.setFilteringMode(FilteringMode.CONTAINS);
        combo.setContainerDataSource(container);
        combo.setItemCaptionPropertyId(Constants.ITEM_NAME);
        combo.setNullSelectionAllowed(false);

        if (defaultValue != null) {
            combo.setValue(defaultValue);
        }
    }

    public static <T> void fillDataObjectCombo(ComboBox combo, String defaultValue, String selectedValue,
            List<T> appPatamDTO, Class<T> clazz, String valueFieldStr, String labelFieldStr) {
        try {
            List lstValue = Lists.newArrayList();
            if (DataUtil.isListNullOrEmpty(appPatamDTO)) {
                appPatamDTO = Lists.newArrayList();
            }
            lstValue.addAll(appPatamDTO);
            final BeanItemContainer container = new BeanItemContainer<>(clazz);
            Object defaultItem;
            if (DataUtil.isStringNullOrEmpty(defaultValue)) {
                defaultValue = Constants.DEFAULT_VALUE;
            }
            defaultItem = clazz.newInstance();
            Field valueField = defaultItem.getClass().getDeclaredField(valueFieldStr);
            valueField.setAccessible(true);
            if (valueField.getType().equals(java.lang.String.class)) {
                valueField.set(defaultItem, defaultValue);
            } else if (valueField.getType().equals(java.lang.Long.class)) {
                valueField.set(defaultItem, Long.valueOf(defaultValue));
            } else if (valueField.getType().equals(java.lang.Integer.class)) {
                valueField.set(defaultItem, Integer.valueOf(defaultValue));
            }
            Field labelField = defaultItem.getClass().getDeclaredField(labelFieldStr);
            labelField.setAccessible(true);
            labelField.set(defaultItem, BundleUtils.getString("combo.default.common"));
            lstValue.add(0, defaultItem);
            Object selectedItem = null;
            if (!DataUtil.isStringNullOrEmpty(selectedValue)) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (Object item : appPatamDTO) {
                    if (valueField.get(item) != null) {
                        map.put(valueField.get(item).toString(), item);
                    }
                }
                selectedItem = map.get(selectedValue);
            }
            container.addAll(lstValue);
            combo.setFilteringMode(FilteringMode.CONTAINS);
            combo.setContainerDataSource(container);
            combo.setItemCaptionPropertyId(labelFieldStr);
            combo.setNullSelectionAllowed(false);
            if (selectedItem != null) {
                combo.setValue(selectedItem);
            } else if (!DataUtil.isStringNullOrEmpty(defaultItem)) {
                combo.setValue(defaultItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrintLog.printLog(e);
        }
    }

    public static void showNotification(String content) {
        Notification notification = new Notification("<p style=\"font-family:verdana;\">" + content + "</p>");
//        Notification notification = new Notification("<p style=\"font-family: 'Open Sans', Arial, Helvetica, sans-serif;\">" + content + "</p>");
        notification.setHtmlContentAllowed(true);
//        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setDelayMsec(2500);
        notification.show(Page.getCurrent());
    }

    public static void fillPercentCombo(ComboBox combo, String selectedValue) {
        List<CatItemDTO> lstPercent = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            lstPercent.add(new CatItemDTO(String.valueOf(i), String.valueOf(i)));
        }
        if (!DataUtil.isNullOrEmpty(selectedValue)) {
            ComponentUtils.fillDataCombo(combo, Constants.EMPTY_CHARACTER, selectedValue, lstPercent);
        } else {
            ComponentUtils.fillDataComboNoDefault(combo, Constants.EMPTY_CHARACTER, lstPercent);
        }
    }

    public static void setReadOnly(Component[] component, boolean isReadOnly) {
        if (component != null && component.length > 0) {
            for (Component com : component) {
                com.setReadOnly(isReadOnly);
            }
        }
    }

    public static void setVisibleControl(Component[] component, boolean isVisible) {
        if (component != null && component.length > 0) {
            for (Component com : component) {
                com.setVisible(isVisible);
            }
        }
    }
}
