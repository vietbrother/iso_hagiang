/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.view;

import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Locale;

/**
 *
 * @author VIET_BROTHER
 */
public class BannerView extends HorizontalLayout {

    private HorizontalLayout loginRegisterLayout;
    public BannerView() {
        Label labelBanner = new Label(BundleUtils.getStringCas("logo.text"));
//            labelBanner.setCaption(BundleUtils.getStringCas("logo.text"));
        labelBanner.setStyleName("text-logo-homepage");
//        Image image = new Image(null, new ThemeResource("img/logo_so_y_te_ha_giang.png"));
        Image image = new Image(null, new ThemeResource("img/profile-pic-300px_main.jpg"));
        image.setSizeFull();
        image.addStyleName("image-header-logo");
        Image imageLogo = new Image(null, new ThemeResource("img/logo_banner.png"));
        imageLogo.setSizeFull();
        imageLogo.addStyleName("image-logo");

        Label hotline = new Label("", ContentMode.HTML);
        hotline.setValue("<div class=\"hotline\"> Đường dây nóng<br><span>1900 0311</span> </div>");

        addStyleName("header-banner-style");
        setSizeFull();
        addComponents(image);
        addComponents(imageLogo);
        setExpandRatio(image, 0.5f);
        setExpandRatio(imageLogo, 1.5f);
        addComponents(labelBanner);
        setExpandRatio(labelBanner, 4.0f);
        addComponents(hotline);
        setExpandRatio(hotline, 1.5f);

        loginRegisterLayout = new HorizontalLayout();
        loginRegisterLayout.setWidth("100%");
        loginRegisterLayout.setHeight("100%");
        loginRegisterLayout.addStyleName("login-register-layout");
        Button loginBtn = new Button(BundleUtils.getString("login.signIn"));
        loginBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginBtn.setIcon(FontAwesome.SIGN_IN);
        Button registerBtn = new Button("Register");
        registerBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        registerBtn.setIcon(FontAwesome.USER_PLUS);
        addComponent(loginRegisterLayout);
        setExpandRatio(loginRegisterLayout, 1.9f);
        loginRegisterLayout.addComponent(registerBtn);
        loginRegisterLayout.setComponentAlignment(registerBtn, Alignment.TOP_LEFT);
        loginRegisterLayout.addComponent(loginBtn);
        loginRegisterLayout.setComponentAlignment(loginBtn, Alignment.TOP_LEFT);
        loginBtn.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getPage().open(getUI().getPage().getLocation().getHost(), "_blank");
            }
        });
        
        
        
    }

    public HorizontalLayout getLoginRegisterLayout() {
        return loginRegisterLayout;
    }

    public void setLoginRegisterLayout(HorizontalLayout loginRegisterLayout) {
        this.loginRegisterLayout = loginRegisterLayout;
    }

}
