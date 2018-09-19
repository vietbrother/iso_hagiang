/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.view;

//import com.iso.dashboard.component.CommonTableFilterPanel;
import com.iso.dashboard.frontEnd.controller.PublicProcedureController;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.view.BannerView;
import com.iso.dashboard.view.FooterView;
import com.iso.dashboard.view.PersonalFileSearchView;
//import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author VIET_BROTHER
 */
public class PublicProcedureView extends VerticalLayout implements View {

    private HomePage homePage;
    private ProcedurePage procedurePage;
    private PersonalFileSearchView searchFilesPage;

    public PublicProcedureView() {
//        setSizeFull();
        BannerView banner = new BannerView();
        banner.setHeight("72px");
        addComponent(banner);

        MenuBar barmenu = new MenuBar();
        barmenu.addStyleName("home");
        barmenu.setWidth("100%");
        barmenu.setHeight("100%");

// Put some items in the menu hierarchically
        barmenu.addItem(BundleUtils.getStringCas("menu.dashboard"), null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                homePage.setVisible(true);
                procedurePage.setVisible(false);
                searchFilesPage.setVisible(false);
            }
        });
        barmenu.addItem(BundleUtils.getStringCas("menu.cataProcedureMngt"), null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                homePage.setVisible(false);
                procedurePage.setVisible(true);
                searchFilesPage.setVisible(false);
            }
        });
        barmenu.addItem(BundleUtils.getStringCas("menu.searchFileMngt"), null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                homePage.setVisible(false);
                procedurePage.setVisible(false);
                searchFilesPage.setVisible(true);
            }
        });

        HorizontalLayout login = new HorizontalLayout();
        login.addStyleName("viewMenu");
        login.setWidth("100%");


        
        login.addComponent(barmenu);


        addComponent(login);
        homePage = new HomePage();
        addComponent(homePage);
        procedurePage = new ProcedurePage();
        addComponent(procedurePage);
        searchFilesPage = new PersonalFileSearchView();
        addComponent(searchFilesPage);
        homePage.setVisible(true);
        procedurePage.setVisible(false);
        searchFilesPage.setVisible(false);

        addComponent(new FooterView());
//        Label title = new Label(BundleUtils.getStringCas("menu.cataProcedureMngt"), ContentMode.HTML);
//        title.addStyleName(ValoTheme.LABEL_H2);
//        title.addStyleName(ValoTheme.LABEL_BOLD);
//        title.addStyleName(ValoTheme.LABEL_COLORED);
//
//        Panel panel = new Panel();
////        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
//        panel.setSizeFull();
//        addComponent(panel);
//        setExpandRatio(panel, 8.0f);
//
//        VerticalLayout root = new VerticalLayout();
//        root.setSizeFull();
//        root.setSpacing(false);
//        root.addStyleName("dashboard-view");
//        Responsive.makeResponsive(root);
//
//        Component content = buildContent();
//        root.addComponent(title);
//        root.addComponent(content);
//
//        panel.setContent(root);
        new PublicProcedureController(this);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //action when load view
        procedurePage.setVisible(false);
        homePage.setVisible(true);
    }

    public ProcedurePage getProcedurePage() {
        return procedurePage;
    }

    public void setProcedurePage(ProcedurePage procedurePage) {
        this.procedurePage = procedurePage;
    }

}
