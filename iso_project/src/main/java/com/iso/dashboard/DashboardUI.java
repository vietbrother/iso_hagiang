package com.iso.dashboard;

import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.iso.dashboard.data.DataProvider;
import com.iso.dashboard.data.dummy.DummyDataProvider;
//import com.iso.dashboard.data.dummy.DummyDataProvider;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.event.DashboardEvent.BrowserResizeEvent;
import com.iso.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import com.iso.dashboard.event.DashboardEvent.UserLoggedOutEvent;
import com.iso.dashboard.event.DashboardEvent.UserLoginRequestedEvent;
import com.iso.dashboard.event.DashboardEventBus;
import com.iso.dashboard.frontEnd.view.PublicProcedureView;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.view.BannerView;
import com.iso.dashboard.view.LoginView;
import com.iso.dashboard.view.MainView;
import com.vaadin.annotations.JavaScript;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("dashboard")
@Widgetset("com.iso.dashboard.DashboardWidgetSet")
@Title("ISO Dashboard")
@JavaScript({"vaadin://js/Chart.min.js", "vaadin://js/chartjs-connector.js"})
@SuppressWarnings("serial")
public final class DashboardUI extends UI {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);

        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                                DashboardEventBus.post(new BrowserResizeEvent());
                            }
                });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        Employee user = (Employee) VaadinSession.getCurrent()
                .getAttribute(Employee.class.getName());

        System.out.println(getUI().getPage().getLocation().getPath());
        if (getUI().getPage().getLocation().getPath().contains(BundleUtils.getStringCas("url_public"))) {
            setContent(new PublicProcedureView());
        } else {
//            if (user != null && "admin".equals(user.getRole())) {
            if (user != null) {
                // Authenticated user
//            setContent(new MainView());
                VerticalLayout ver = new VerticalLayout();
                ver.setSizeFull();
                BannerView banner = new BannerView();
                banner.getLoginRegisterLayout().setVisible(false);
                MainView main = new MainView();
                ver.addComponents(banner);
                ver.setExpandRatio(banner, 1f);
                ver.addComponents(main);
                ver.setExpandRatio(main, 9f);
//            setContent(new MainView());
                setContent(ver);
                removeStyleName("loginview");
                getNavigator().navigateTo(getNavigator().getState());
            } else {
                setContent(new LoginView());
                addStyleName("loginview");
            }
        }

    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        String username = event.getUserName();
        String password = event.getPassword();
        if (DataUtil.isNullOrEmpty(username)) {
            ComponentUtils.showNotification(BundleUtils.getString("login.username.empty"));
        } else if (DataUtil.isNullOrEmpty(password)) {
            ComponentUtils.showNotification(BundleUtils.getString("login.password.empty"));
        } else {
            Employee user = EmployeeMngtService.getInstance().getLoginInfo(username, password);
            if (user == null) {
                ComponentUtils.showNotification(BundleUtils.getString("login.invalid"));
            } else {
                VaadinSession.getCurrent().setAttribute(Employee.class.getName(), user);
                updateContent();
            }
        }

    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((DashboardUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}
