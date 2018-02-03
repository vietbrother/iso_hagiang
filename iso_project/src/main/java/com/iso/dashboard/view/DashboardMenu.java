package com.iso.dashboard.view;

import com.google.common.eventbus.Subscribe;
import com.iso.dashboard.DashboardUI;
import com.iso.dashboard.component.ProfilePreferencesWindow;
import com.iso.dashboard.dto.CMenu;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.MUserMenu;
import com.iso.dashboard.event.DashboardEvent.NotificationsCountUpdatedEvent;
import com.iso.dashboard.event.DashboardEvent.PostViewChangeEvent;
import com.iso.dashboard.event.DashboardEvent.ProfileUpdatedEvent;
import com.iso.dashboard.event.DashboardEvent.ReportsCountUpdatedEvent;
import com.iso.dashboard.event.DashboardEvent.UserLoggedOutEvent;
import com.iso.dashboard.event.DashboardEventBus;
import com.iso.dashboard.service.TaskOrgMngService;
import com.iso.dashboard.service.UserMenuService;
import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.vaadin.v7.ui.AbstractSelect.AcceptItem;
//import com.vaadin.v7.ui.Table;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({"serial", "unchecked"})
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    public static final String NOTIFICATIONS_TASK_ID = "dashboard-menu-notifications-task";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private Label notificationsBadge;
    private Label reportsBadge;
    private MenuItem settingsItem;

    private Label notificationsTask;
    public static Employee user = (Employee) VaadinSession.getCurrent().getAttribute(Employee.class.getName());

    public Map<String, String> mapParentMenu;
    public Map<String, String> mapChildMenu;

    public Map<String, CMenu> mapRole;

    public DashboardMenu() {
        mapRole = new HashMap<>();
        Employee user = (Employee) VaadinSession.getCurrent()
                .getAttribute(Employee.class.getName());
        List<MUserMenu> lstMenu = UserMenuService.getInstance().listUserMenu(null, String.valueOf(user.getId()));
        if (lstMenu != null) {
            for (MUserMenu mUserMenu : lstMenu) {
                mapRole.put(mUserMenu.getMenu().getCode(), mUserMenu.getMenu());
            }
        }

        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        // There's only one DashboardMenu per UI so this doesn't need to be
        // unregistered from the UI-scoped DashboardEventBus.
        DashboardEventBus.register(this);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("ISO <strong>Dashboard</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout();
//        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private Employee getCurrentUser() {
        return (Employee) VaadinSession.getCurrent()
                .getAttribute(Employee.class.getName());
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final Employee user = getCurrentUser();
        settingsItem = settings.addItem("",
                new ThemeResource("img/profile-pic-300px.jpg"), null);
        updateUserName(null);
        settingsItem.addItem(BundleUtils.getString("login.editProfile"), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, false);
            }
        });
        settingsItem.addItem(BundleUtils.getString("login.preferences"), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, true);
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem(BundleUtils.getString("login.signOut"), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                DashboardEventBus.post(new UserLoggedOutEvent());
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName()
                        .contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);

//            if (view == DashboardViewType.REPORTS) {
//                // Add drop target to reports button
//                DragAndDropWrapper reports = new DragAndDropWrapper(
//                        menuItemComponent);
//                reports.setSizeUndefined();
//                reports.setDragStartMode(DragStartMode.NONE);
//                reports.setDropHandler(new DropHandler() {
//
//                    @Override
//                    public void drop(final DragAndDropEvent event) {
//                        UI.getCurrent().getNavigator().navigateTo(
//                                DashboardViewType.REPORTS.getViewName());
//                        Table table = (Table) event.getTransferable()
//                                .getSourceComponent();
//                        DashboardEventBus.post(new TransactionReportEvent(
//                                (Collection<Transaction>) table.getValue()));
//                    }
//
//                    @Override
//                    public AcceptCriterion getAcceptCriterion() {
//                        return AcceptItem.ALL;
//                    }
//
//                });
//                menuItemComponent = reports;
//            }
//            if (view == DashboardViewType.DASHBOARD) {
//                notificationsBadge = new Label();
//                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
//                menuItemComponent = buildBadgeWrapper(menuItemComponent,
//                        notificationsBadge);
//            }
//            if (view == DashboardViewType.ORGANIZATION_MNGT) {
//                notificationsBadge = new Label();
//                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
//                menuItemComponent = buildBadgeWrapper(menuItemComponent,
//                        notificationsBadge);
//            }
//            if (view == DashboardViewType.REPORTS) {
//                reportsBadge = new Label();
//                reportsBadge.setId(REPORTS_BADGE_ID);
//                menuItemComponent = buildBadgeWrapper(menuItemComponent,
//                        reportsBadge);
//            }
            if (mapRole.get(view.getViewName()) != null) {
                if (view.isParentView()) {
                    if (view.isContainChild()) {
                        Button parent = new ValoMenuItemButton(view);
                        List<DashboardViewType> lstSubMenu = DashboardViewType.mapSubMenu.get(view.getViewName());
                        VerticalLayout subMenuContent = new VerticalLayout();
                        subMenuContent.setPrimaryStyleName("menuSubMenu");
                        subMenuContent.setStyleName("menuSubMenuContent");
                        subMenuContent.addStyleName("close");
                        subMenuContent.setWidth("100%");
                        subMenuContent.setMargin(false);
                        subMenuContent.setSpacing(false);
                        for (DashboardViewType subMenu : lstSubMenu) {
                            if (mapRole.get(subMenu.getViewName()) != null) {
                                Component child = new ValoMenuItemButton(subMenu);
                                if ("taskPersonalMngt".equals(subMenu.getViewName())) {
                                    notificationsBadge = new Label();
                                    notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                                    menuItemComponent = buildBadgeWrapper(child,
                                            notificationsBadge);
                                    subMenuContent.addComponent(menuItemComponent);
                                } else {

                                    subMenuContent.addComponent(child);
                                }
                            }
                        }
                        parent.addClickListener(new ClickListener() {

                            @Override
                            public void buttonClick(ClickEvent event) {
                                if (subMenuContent.getStyleName().contains("close")) {
                                    subMenuContent.setVisible(true);
                                    subMenuContent.removeStyleName("close");
                                } else {
                                    subMenuContent.setVisible(false);
                                    subMenuContent.addStyleName("close");
                                }
                            }
                        });
                        menuItemsLayout.addComponent(parent);
                        menuItemsLayout.addComponent(subMenuContent);
                        subMenuContent.setVisible(false);

                    } else {
                        menuItemsLayout.addComponent(menuItemComponent);
                    }
                }
            }

        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
            final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
        int taskCount = 0;
        try {
            List<CTask> tasks = TaskOrgMngService.getInstance().getListTaskPersonal(String.valueOf(user.getId()), String.valueOf(user.getDepartment().getId()));
            taskCount = tasks.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateNotificationsCount(new NotificationsCountUpdatedEvent(taskCount));
    }

    @Subscribe
    public void postViewChange(final PostViewChangeEvent event) {
        // After a successful view change the menu can be hidden in mobile view.
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }

    @Subscribe
    public void updateNotificationsCount(
            final NotificationsCountUpdatedEvent event) {
//        int unreadNotificationsCount = DashboardUI.getDataProvider()
//                .getUnreadNotificationsCount();
        int unreadNotificationsCount = (event == null ? 0 : event.getCount());
        notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
        notificationsBadge.setVisible(unreadNotificationsCount > 0);
    }

//    @Subscribe
//    public void updateNotificationsTaskCount(
//            final NotificationsCountUpdatedEvent event) {
//        int unreadNotificationsCount = DashboardUI.getDataProvider()
//                .getUnreadNotificationsCount();
//        notificationsTask.setValue(String.valueOf(unreadNotificationsCount));
//        notificationsTask.setVisible(unreadNotificationsCount > 0);
//    }
    @Subscribe
    public void updateReportsCount(final ReportsCountUpdatedEvent event) {
        reportsBadge.setValue(String.valueOf(event.getCount()));
        reportsBadge.setVisible(event.getCount() > 0);
    }

    @Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
        Employee user = getCurrentUser();
        settingsItem.setText(user.getFirstName());
    }

    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(BundleUtils.getStringCas("menu." + view.getViewName()));
            DashboardEventBus.register(this);
            if (!view.isContainChild()) {
                addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(final ClickEvent event) {
                        UI.getCurrent().getNavigator()
                                .navigateTo(view.getViewName());
                    }
                });
            }

        }

        @Subscribe
        public void postViewChange(final PostViewChangeEvent event) {
            removeStyleName(STYLE_SELECTED);
            if (event.getView() == view) {
                addStyleName(STYLE_SELECTED);
            }
        }
    }

}
