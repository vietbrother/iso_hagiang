package com.iso.dashboard.view.dashboard;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.eventbus.Subscribe;
import com.iso.dashboard.DashboardUI;
//import com.vaadin.demo.dashboard.component.SparklineChart;
//import com.vaadin.demo.dashboard.component.TopGrossingMoviesChart;
//import com.vaadin.demo.dashboard.component.TopSixTheatersChart;
import com.iso.dashboard.dto.DashboardNotification;
import com.iso.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import com.iso.dashboard.event.DashboardEvent.NotificationsCountUpdatedEvent;
import com.iso.dashboard.event.DashboardEventBus;
import com.iso.dashboard.ui.MostUsedFunctionUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.view.dashboard.DashboardEdit.DashboardEditListener;
import com.vaadin.annotations.JavaScript;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JPanel;
import org.dussan.vaadin.dcharts.ChartImageFormat;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.events.mouseenter.ChartDataMouseEnterHandler;
import org.dussan.vaadin.dcharts.events.mouseleave.ChartDataMouseLeaveHandler;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.vaadin.addon.JFreeChartWrapper;
//import com.vaadin.v7.ui.Table;

@SuppressWarnings("serial")
@JavaScript({"vaadin://js/Chart.min.js", "vaadin://js/chartjs-connector.js"})
public final class DashboardView extends Panel implements View,
        DashboardEditListener {

    public static final String EDIT_ID = "dashboard-edit";
    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private NotificationsButton notificationsButton;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private Window notificationsWindow;

    public DashboardView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        DashboardEventBus.register(this);

        root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(false);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);

        root.addComponent(buildHeader());

        root.addComponent(buildChart());

        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                DashboardEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    private Component buildChart() {
        CssLayout clMostUsedFunc = new CssLayout();
        clMostUsedFunc.addStyleName("sparks");
        clMostUsedFunc.setWidth("100%");
        Responsive.makeResponsive(clMostUsedFunc);

        VerticalLayout vertical = new VerticalLayout();
        vertical.setMargin(true);
        vertical.setWidth("100%");

//        vertical.addComponent(buildChartDemo());
        vertical.addComponent(chartDemo());
        vertical.addComponent(chartsDemo1());

        clMostUsedFunc.addComponent(vertical);
        return clMostUsedFunc;
    }

    private JFreeChartWrapper chartDemo(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10.0, "Series 1", "Category 1");
        dataset.addValue(4.0, "Series 1", "Category 2");
        dataset.addValue(15.0, "Series 1", "Category 3");
        dataset.addValue(14.0, "Series 1", "Category 4");
        dataset.addValue(-5.0, "Series 2", "Category 1");
        dataset.addValue(-7.0, "Series 2", "Category 2");
        dataset.addValue(14.0, "Series 2", "Category 3");
        dataset.addValue(-3.0, "Series 2", "Category 4");
        dataset.addValue(6.0, "Series 3", "Category 1");
        dataset.addValue(17.0, "Series 3", "Category 2");
        dataset.addValue(-12.0, "Series 3", "Category 3");
        dataset.addValue(7.0, "Series 3", "Category 4");
        dataset.addValue(7.0, "Series 4", "Category 1");
        dataset.addValue(15.0, "Series 4", "Category 2");
        dataset.addValue(11.0, "Series 4", "Category 3");
        dataset.addValue(0.0, "Series 4", "Category 4");
        dataset.addValue(-8.0, "Series 5", "Category 1");
        dataset.addValue(-6.0, "Series 5", "Category 2");
        dataset.addValue(10.0, "Series 5", "Category 3");
        dataset.addValue(-9.0, "Series 5", "Category 4");
        dataset.addValue(9.0, "Series 6", "Category 1");
        dataset.addValue(8.0, "Series 6", "Category 2");
        dataset.addValue(0.0, "Series 6", "Category 3");
        dataset.addValue(6.0, "Series 6", "Category 4");
        dataset.addValue(-10.0, "Series 7", "Category 1");
        dataset.addValue(9.0, "Series 7", "Category 2");
        dataset.addValue(7.0, "Series 7", "Category 3");
        dataset.addValue(7.0, "Series 7", "Category 4");
        dataset.addValue(11.0, "Series 8", "Category 1");
        dataset.addValue(13.0, "Series 8", "Category 2");
        dataset.addValue(9.0, "Series 8", "Category 3");
        dataset.addValue(9.0, "Series 8", "Category 4");
        dataset.addValue(-3.0, "Series 9", "Category 1");
        dataset.addValue(7.0, "Series 9", "Category 2");
        dataset.addValue(11.0, "Series 9", "Category 3");
        dataset.addValue(-10.0, "Series 9", "Category 4");
        
        JFreeChart chart = ChartFactory.createBarChart3D(
            "3D Bar Chart Demo",      // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false);                   // urls

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setOutlineVisible(false);
        plot.setDomainGridlinesVisible(true);
        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 8.0));
        axis.setCategoryMargin(0.0);
        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
//        chart.getPlot().setBackgroundPaint(Color.WHITE);
//        chart.setBackgroundPaint(Color.WHITE);
        
//        JPanel panel = new ChartPanel(chart);
        return new JFreeChartWrapper(chart);
    }
    private ChartJs buildChartDemo() {
        BarChartConfig barConfig = new BarChartConfig();
        barConfig.
                data()
                .labels("January", "February", "March", "April", "May", "June", "July")
                .addDataset(
                        new BarDataset().backgroundColor("rgba(220,220,220,0.5)").label("Dataset 1").yAxisID("y-axis-1"))
                .addDataset(
                        new BarDataset().backgroundColor("rgba(151,187,205,0.5)").label("Dataset 2").yAxisID("y-axis-2").hidden(true))
                .addDataset(
                        new BarDataset().backgroundColor(
                                ColorUtils.randomColor(0.7), ColorUtils.randomColor(0.7), ColorUtils.randomColor(0.7),
                                ColorUtils.randomColor(0.7), ColorUtils.randomColor(0.7), ColorUtils.randomColor(0.7),
                                ColorUtils.randomColor(0.7)).label("Dataset 3").yAxisID("y-axis-1"))
                .and();
        barConfig.
                options()
                .responsive(true)
                .hover()
                .mode(InteractionMode.INDEX)
                .intersect(true)
                .animationDuration(400)
                .and()
                .title()
                .display(true)
                .text("Chart.js Bar Chart - Multi Axis")
                .and()
                .scales()
                .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
                .add(Axis.Y, new LinearScale().display(true).position(Position.RIGHT).id("y-axis-2").gridLines().drawOnChartArea(false).and())
                .and()
                .done();

        List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.addClickListener(new ChartJs.DataPointClickListener() {

            @Override
            public void onDataPointClick(int datasetIndex, int dataIndex) {
                Notification.show(barConfig.data().getDatasets().get(datasetIndex).toString());
            }
        });
        return chart;
    }

    private DCharts chartsDemo1() {
        DCharts chart = new DCharts();
//        chart.autoSelectDecimalAndThousandsSeparator(new Locale("sl", "SI"));
//        chart.setHeight("400px");
        chart.setWidth("100%");
        chart.setCaption("Demo");

        DataSeries dataSeries = new DataSeries();
        dataSeries.add(200, 600, 700, 1000);
        dataSeries.add(460, -210, 690, 820);
        dataSeries.add(-260, -440, 320, 200);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setFillToZero(true).setRenderer(SeriesRenderers.BAR);

        Series series = new Series()
                .addSeries(new XYseries().setLabel("Hotel"))
                .addSeries(new XYseries().setLabel("Event Regristration"))
                .addSeries(new XYseries().setLabel("Airfare"));

        Legend legend = new Legend()
                .setShow(true)
                .setRendererOptions(
                        new EnhancedLegendRenderer().setSeriesToggle(
                                SeriesToggles.SLOW).setSeriesToggleReplot(true))
                .setPlacement(LegendPlacements.OUTSIDE_GRID);

        Axes axes = new Axes().addAxis(
                new XYaxis().setRenderer(AxisRenderers.CATEGORY).setTicks(
                        new Ticks().add("May", "June", "July", "August")))
                .addAxis(
                        new XYaxis(XYaxes.Y).setPad(1.05f).setTickOptions(
                                new AxisTickRenderer().setFormatString("$%d")));

        Options options = new Options().setSeriesDefaults(seriesDefaults)
                .setSeries(series).setLegend(legend).setAxes(axes);

        chart.setDataSeries(dataSeries).setOptions(options)
                //				.setEnableDownload(true)
                .setChartImageFormat(ChartImageFormat.GIF).show();

        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);
        chart.setEnableChartImageChangeEvent(true);

        return chart;
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");

        titleLabel = new Label("Dashboard");
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        notificationsButton = buildNotificationsButton();
        Component edit = buildEditButton();
        HorizontalLayout tools = new HorizontalLayout(notificationsButton, edit);
        tools.addStyleName("toolbar");
        tools.setSpacing(true);
        header.addComponent(tools);

        return header;
    }

    private NotificationsButton buildNotificationsButton() {
        NotificationsButton result = new NotificationsButton();
        result.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                openNotificationsPopup(event);
            }
        });
        return result;
    }

    private Component buildEditButton() {
        Button result = new Button();
        result.setId(EDIT_ID);
        result.setIcon(FontAwesome.EDIT);
        result.addStyleName("icon-edit");
        result.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        result.setDescription("Edit Dashboard");
        result.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                getUI().addWindow(
                        new DashboardEdit(DashboardView.this, titleLabel
                                .getValue()));
            }
        });
        return result;
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        dashboardPanels.addComponent(buildMostUsedFunc());
        dashboardPanels.addComponent(buildNotes());
//        dashboardPanels.addComponent(buildPopularMovies());

        return dashboardPanels;
    }

    private Component buildMostUsedFunc() {
        MostUsedFunctionUI ui = new MostUsedFunctionUI();
        ui.setCaption(BundleUtils.getStringCas("menu.mostUsedFunc"));
        ui.setSizeFull();
        return createContentWrapper(ui);
    }

    private Component buildNotes() {
        TextArea notes = new TextArea("Notes");
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");
        return panel;
    }

    private Component buildPopularMovies() {
        return createContentWrapper(new Button("buildPopularMovies"));
    }

    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");
        toolbar.setSpacing(false);

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");
        MenuItem root = tools.addItem("", FontAwesome.COG, null);
        root.addItem("Configure", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });
        root.addSeparator();
        root.addItem("Close", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }

    private void openNotificationsPopup(final ClickEvent event) {
        VerticalLayout notificationsLayout = new VerticalLayout();

        Label title = new Label("Notifications");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        notificationsLayout.addComponent(title);

        Collection<DashboardNotification> notifications = DashboardUI
                .getDataProvider().getNotifications();
        DashboardEventBus.post(new NotificationsCountUpdatedEvent());

        for (DashboardNotification notification : notifications) {
            VerticalLayout notificationLayout = new VerticalLayout();
            notificationLayout.setMargin(false);
            notificationLayout.setSpacing(false);
            notificationLayout.addStyleName("notification-item");

            Label titleLabel = new Label(notification.getFirstName() + " "
                    //+ notification.getLastName() + " "
                    + notification.getAction());
            titleLabel.addStyleName("notification-title");

            Label timeLabel = new Label(notification.getPrettyTime());
            timeLabel.addStyleName("notification-time");

            Label contentLabel = new Label(notification.getContent());
            contentLabel.addStyleName("notification-content");

            notificationLayout.addComponents(titleLabel, timeLabel,
                    contentLabel);
            notificationsLayout.addComponent(notificationLayout);
        }

        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth("100%");
        footer.setSpacing(false);
        Button showAll = new Button("View All Notifications",
                new ClickListener() {
                    @Override
                    public void buttonClick(final ClickEvent event) {
                        Notification.show("Not implemented in this demo");
                    }
                });
        showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        showAll.addStyleName(ValoTheme.BUTTON_SMALL);
        footer.addComponent(showAll);
        footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
        notificationsLayout.addComponent(footer);

        if (notificationsWindow == null) {
            notificationsWindow = new Window();
            notificationsWindow.setWidth(300.0f, Unit.PIXELS);
            notificationsWindow.addStyleName("notifications");
            notificationsWindow.setClosable(false);
            notificationsWindow.setResizable(false);
            notificationsWindow.setDraggable(false);
            notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
            notificationsWindow.setContent(notificationsLayout);
        }

        if (!notificationsWindow.isAttached()) {
            notificationsWindow.setPositionY(event.getClientY()
                    - event.getRelativeY() + 40);
            getUI().addWindow(notificationsWindow);
            notificationsWindow.focus();
        } else {
            notificationsWindow.close();
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        notificationsButton.updateNotificationsCount(null);
    }

    @Override
    public void dashboardNameEdited(final String name) {
        titleLabel.setValue(name);
    }

    private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }

    public static final class NotificationsButton extends Button {

        private static final String STYLE_UNREAD = "unread";
        public static final String ID = "dashboard-notifications";

        public NotificationsButton() {
            setIcon(FontAwesome.BELL);
            setId(ID);
            addStyleName("notifications");
            addStyleName(ValoTheme.BUTTON_ICON_ONLY);
            DashboardEventBus.register(this);
        }

        @Subscribe
        public void updateNotificationsCount(
                final NotificationsCountUpdatedEvent event) {
            setUnreadCount(DashboardUI.getDataProvider()
                    .getUnreadNotificationsCount());
        }

        public void setUnreadCount(final int count) {
            setCaption(String.valueOf(count));

            String description = "Notifications";
            if (count > 0) {
                addStyleName(STYLE_UNREAD);
                description += " (" + count + " unread)";
            } else {
                removeStyleName(STYLE_UNREAD);
            }
            setDescription(description);
        }
    }

}
