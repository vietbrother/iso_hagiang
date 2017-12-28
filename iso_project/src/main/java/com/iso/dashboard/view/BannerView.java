/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import java.util.Locale;

/**
 *
 * @author VIET_BROTHER
 */
public class BannerView extends HorizontalLayout {

    public BannerView() {
        Label labelBanner = new Label(BundleUtils.getStringCas("logo.text"));
//            labelBanner.setCaption(BundleUtils.getStringCas("logo.text"));
        labelBanner.setStyleName("text-logo");
//        Image image = new Image(null, new ThemeResource("img/logo_so_y_te_ha_giang.png"));
        Image image = new Image(null, new ThemeResource("img/profile-pic-300px_main.jpg"));
        image.setSizeFull();
        image.addStyleName("image-header");
        
        addStyleName("header-banner-style");
        setSizeFull();
        addComponents(image);
        setExpandRatio(image, 1.5f);
        addComponents(labelBanner);
        setExpandRatio(labelBanner, 8.5f);
    }

}
