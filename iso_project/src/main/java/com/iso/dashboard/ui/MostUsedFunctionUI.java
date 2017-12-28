/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class MostUsedFunctionUI extends CustomComponent {

    private VerticalLayout mainLayout;

    private List<String> lstMostUsedFunc = new ArrayList<>();

    public MostUsedFunctionUI() {
        lstMostUsedFunc.add("userMngt");
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();
        mainLayout.setSpacing(true);
        Responsive.makeResponsive(mainLayout);

        Label mostUsedFuncTitle = new Label(" " + BundleUtils.getStringCas("menu.mostUsedFunc"));
        mostUsedFuncTitle.addStyleName(ValoTheme.LABEL_H4);
        mostUsedFuncTitle.addStyleName(ValoTheme.LABEL_COLORED);
        mostUsedFuncTitle.addStyleName(ValoTheme.LABEL_BOLD);
        mostUsedFuncTitle.addStyleName(ValoTheme.LABEL_HUGE);
//        mainLayout.addComponent(mostUsedFuncTitle);

        for (String viewName : lstMostUsedFunc) {
            Button btn = new Button(BundleUtils.getStringCas("menu." + viewName));
            btn.setIcon(FontAwesome.ARROW_CIRCLE_O_RIGHT);
            btn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            btn.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo(viewName);
                }
            });
            mainLayout.addComponent(btn);
        }
    }

    public List<String> getLstMostUsedFunc() {
        return lstMostUsedFunc;
    }

    public void setLstMostUsedFunc(List<String> lstMostUsedFunc) {
        this.lstMostUsedFunc = lstMostUsedFunc;
    }

}
