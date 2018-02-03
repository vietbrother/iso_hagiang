package com.iso.dashboard.view.dashboard;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.DefaultScale;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.eventbus.Subscribe;
import com.iso.dashboard.DashboardUI;
import com.iso.dashboard.data.dummy.DummyDataGenerator;
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
import com.vaadin.ui.TabSheet;
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
import org.dussan.vaadin.dcharts.ChartImageFormat;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.canvasoverlays.DashedHorizontalLine;
import org.dussan.vaadin.dcharts.canvasoverlays.HorizontalLine;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.events.ChartData;
import org.dussan.vaadin.dcharts.events.chartImageChange.ChartImageChangeEvent;
import org.dussan.vaadin.dcharts.events.chartImageChange.ChartImageChangeHandler;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickEvent;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickHandler;
import org.dussan.vaadin.dcharts.events.mouseenter.ChartDataMouseEnterEvent;
import org.dussan.vaadin.dcharts.events.mouseenter.ChartDataMouseEnterHandler;
import org.dussan.vaadin.dcharts.events.mouseleave.ChartDataMouseLeaveEvent;
import org.dussan.vaadin.dcharts.events.mouseleave.ChartDataMouseLeaveHandler;
import org.dussan.vaadin.dcharts.events.rightclick.ChartDataRightClickEvent;
import org.dussan.vaadin.dcharts.events.rightclick.ChartDataRightClickHandler;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.Yaxes;
import org.dussan.vaadin.dcharts.metadata.directions.BarDirections;
import org.dussan.vaadin.dcharts.metadata.lines.LineCaps;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.TickRenderers;
import org.dussan.vaadin.dcharts.metadata.ticks.TickFormatters;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.CanvasOverlay;
import org.dussan.vaadin.dcharts.options.Grid;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.options.Title;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.series.BlockRenderer;
import org.dussan.vaadin.dcharts.renderers.series.BubbleRenderer;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.CanvasAxisTickRenderer;

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

        root.addComponent(buildOverView());
        root.addComponent(buildChart());
        //root.addComponent(testChart());

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

    private Component buildOverView() {
        CssLayout overviews = new CssLayout();
        overviews.addStyleName("sparks");
        overviews.setWidth("100%");
        Responsive.makeResponsive(overviews);

        DashboardOverviewChart s = new DashboardOverviewChart("Hô sơ", "00", "",
                FontAwesome.FILE_TEXT.getHtml(),
                "#197de1",
                DummyDataGenerator.chartColors[0], 22, 20, 80);
        overviews.addComponent(s);

        s = new DashboardOverviewChart("Công việc", "", "",
                FontAwesome.TASKS.getHtml(),
                "#1ABB9C",//"#60bc10",//"#80e614", //#60bc10",
                DummyDataGenerator.chartColors[2], 8, 89, 150);
        overviews.addComponent(s);

        s = new DashboardOverviewChart("Hồ sơ quá hạn", "", "",
                FontAwesome.CLOCK_O.getHtml(),
                "#ff3e96",//#9423d2",
                DummyDataGenerator.chartColors[3], 10, 30, 120);
        overviews.addComponent(s);

        s = new DashboardOverviewChart("Công việc quá hạn", "%", "",
                FontAwesome.EXCLAMATION_CIRCLE.getHtml(),
                "#ee5e61",//#e44221",
                DummyDataGenerator.chartColors[5], 50, 34, 100);
        overviews.addComponent(s);

        return overviews;
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
        //vertical.addComponent(chartDemo());
//        Panel panel = new Panel();
//        panel.setWidth("600px");
//        panel.setContent(chartsDemo1());
//        vertical.addComponent(panel);
//        vertical.addComponent(new Button("11111111111111111111"));
//        Panel panel1 = new Panel();
//        panel1.setHeight("600px");
//        panel1.setWidth("600px");
//        panel1.setContent(buildChartDemo());
//        vertical.addComponent(panel1);
        vertical.addComponent(buildChartstatistic());
        vertical.addComponent(buildChartDemo());
        HorizontalLayout temp1 = new HorizontalLayout();
        temp1.setWidth("100%");
        temp1.setSpacing(true);
        temp1.addComponents(buildPieChart(), buildPieChart(), buildPieChart());
        vertical.addComponent(temp1);

        //DCharts charts1 = chartsDemo12();
        //DCharts charts2 = chartsDemo2();
        DCharts charts3_1 = chartsDemo3_1();
        //DCharts charts3_2 = chartsDemo3_2();
        //DCharts charts4 = chartsDemo4();
        //DCharts charts5 = chartsDemo5();
        //DCharts charts6 = chartsDemo6();
        //DCharts charts7 = chartsDemo7();
        //DCharts charts8 = chartsDemo8();
        //vertical.addComponent(charts1);
        //vertical.addComponent(charts2);
        HorizontalLayout temp = new HorizontalLayout();
        temp.setWidth("100%");
        temp.setSpacing(true);
        temp.addComponents(chartsDemo3_1(), chartsDemo3_1(), chartsDemo3_1());
        //vertical.addComponent(temp);
        //vertical.addComponent(charts3_2);
//        vertical.addComponent(charts4);
//        vertical.addComponent(charts5);
//        vertical.addComponent(charts6);
//        vertical.addComponent(charts7);
//        vertical.addComponent(charts8);

        clMostUsedFunc.addComponent(vertical);
        return clMostUsedFunc;
    }

    private JFreeChartWrapper chartDemo() {
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
                "3D Bar Chart Demo", // chart title
                "Category", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
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
                        new BarDataset().backgroundColor("rgba(220,220,220,0.5)")
                        .label("Dataset 1").yAxisID("y-axis-1"))
                .addDataset(
                        new BarDataset().backgroundColor("rgba(151,187,205,0.5)")
                        .label("Dataset 2").yAxisID("y-axis-2")//.hidden(true)
                            )
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
        chart.setWidth("90%");
        chart.setHeight("90%");
        chart.addClickListener(new ChartJs.DataPointClickListener() {

            @Override
            public void onDataPointClick(int datasetIndex, int dataIndex) {
                Notification.show(barConfig.data().getDatasets().get(datasetIndex).toString());
            }
        });
        return chart;
    }

    private Component buildChartstatistic() {
        BarChartConfig config = new BarChartConfig();
        config.data()
                .labels("January", "February", "March", "April", "May", "June", "July")
                .addDataset(new BarDataset()
                        .label("Quá hạn")
                        .backgroundColor("#f6b2af")
                        .stack("Stack 0"))
                .addDataset(new BarDataset()
                        .label("Đang giải quyết")
                        .backgroundColor("#90e0de")
                        .stack("Stack 0"))
                .addDataset(new BarDataset()
                        .label("Đúng hạn")
                        .backgroundColor("#6fc227")
                        .stack("Stack 0"))
                .and()
                .options()
                .responsive(true)
                .title()
                .display(true)
                .text("Thống kê xử lý hồ sơ năm 2018")
                .and()
                .tooltips()
                .mode(InteractionMode.INDEX)
                .intersect(false)
                .and()
                .scales()
                .add(Axis.X, new DefaultScale()
                        .stacked(true))
                .add(Axis.Y, new DefaultScale()
                        .stacked(true))
                .and()
                .done();

        // add random data for demo
        List<String> labels = config.data().getLabels();
        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((double) (Math.random() > 0.5 ? 1.0 : 0.3) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(config);
        chart.setWidth("90%");
        chart.setHeight("90%");
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
        dashboardPanels.addComponent(buildPercentTaskChart());
        dashboardPanels.addComponent(buildPopularMovies());
        dashboardPanels.addComponent(buildNotes());

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

    private Component buildPercentTaskChart() {
        Component panel = createContentWrapper(buildPieChart());
        //panel.addStyleName("notes");
        return panel;
    }

    private Component buildPieChart() {
        PieChartConfig config = new PieChartConfig();
        config
                .data()
                .labels("Hoàn thành", "Đang thực hiện", "Chưa thực hiện")
                .addDataset(new PieDataset().label("Dataset 1"))
                .and();

        config.
                options()
                .responsive(true)
                .title()
                .display(true)
                .text("Tiến độ thực hiện công việc")
                .and()
                .animation()
                //.animateScale(true)
                .animateRotate(true)
                .and()
                .done();

        List<String> labels = config.data().getLabels();
        String[] lstColor = new String[]{"#6fc227", "#90e0de", "#f6b2af"};
        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            PieDataset lds = (PieDataset) ds;
            List<Double> data = new ArrayList<>();
            List<String> colors = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((double) (Math.round(Math.random() * 100)));
                colors.add(lstColor[i]);
            }
            lds.backgroundColor(colors.toArray(new String[colors.size()]));
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(config);
        chart.setJsLoggingEnabled(true);
//        chart.setSizeFull();
//        chart.setResponsive(true);
        return chart;
    }

    private Component buildPopularMovies() {
        return createContentWrapper(chartsDemo1());
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
        DashboardEventBus.post(new NotificationsCountUpdatedEvent(notifications.size()));

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

    private DCharts chartsDemo12() {
        DCharts chart = new DCharts();
        chart.autoSelectDecimalAndThousandsSeparator(new Locale("sl", "SI"));
        chart.setHeight("400px");
        chart.setCaption("test");

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

//        chart.addHandler(new ChartDataMouseEnterHandler() {
//            @Override
//            public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
//                showEventNotification("CHART DATA MOUSE ENTER",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataMouseLeaveHandler() {
//            @Override
//            public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
//                showEventNotification("CHART DATA MOUSE LEAVE",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataClickHandler() {
//            @Override
//            public void onChartDataClick(ChartDataClickEvent event) {
//                showEventNotification("CHART DATA CLICK", event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataRightClickHandler() {
//            @Override
//            public void onChartDataRightClick(ChartDataRightClickEvent event) {
//                showEventNotification("CHART DATA RIGHT CLICK",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartImageChangeHandler() {
//            @Override
//            public void onChartImageChange(ChartImageChangeEvent event) {
//                showEventNotification("CHART IMAGE CHANGE",
//                        event.getChartImage());
//            }
//        });
        return chart;
    }

    private DCharts chartsDemo2() {
        DCharts chart = new DCharts();
        chart.autoSelectDecimalAndThousandsSeparator(new Locale("sl", "SI"));
        chart.setCaption("test");

        DataSeries dataSeries = new DataSeries().newSeries()
                .add(11, 123, 1236, "Acura").add(45, 92, 1067, "Alfa Romeo")
                .add(24, 104, 1176, "AM General")
                .add(50, 23, 610, "Aston Martin Lagonda")
                .add(18, 17, 539, "Audi").add(7, 89, 864, "BMW")
                .add(2, 13, 1026, "Bugatti");

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.BUBBLE)
                .setRendererOptions(
                        new BubbleRenderer().setBubbleAlpha(0.6f)
                        .setHighlightAlpha(0.8f)).setShadow(true)
                .setShadowAlpha(0.05f);

        Options options = new Options().setCaptureRightClick(true)
                .setSeriesDefaults(seriesDefaults);
        chart.setDataSeries(dataSeries).setOptions(options).show();

        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);

//        chart.addHandler(new ChartDataMouseEnterHandler() {
//            @Override
//            public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
//                showEventNotification("CHART DATA MOUSE ENTER",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataMouseLeaveHandler() {
//            @Override
//            public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
//                showEventNotification("CHART DATA MOUSE LEAVE",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataClickHandler() {
//            @Override
//            public void onChartDataClick(ChartDataClickEvent event) {
//                showEventNotification("CHART DATA CLICK", event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataRightClickHandler() {
//            @Override
//            public void onChartDataRightClick(ChartDataRightClickEvent event) {
//                showEventNotification("CHART DATA RIGHT CLICK",
//                        event.getChartData());
//            }
//        });
        return chart;
    }

    private DCharts chartsDemo3_1() {
        DataSeries dataSeries = new DataSeries().newSeries().add("Hoàn thành", 45)
                .add("Đang thực hiện", 35).add("Quá hạn", 20);

        SeriesDefaults seriesDefaults = new SeriesDefaults().setRenderer(
                SeriesRenderers.PIE).setRendererOptions(
                        new PieRenderer().setShowDataLabels(true));

        Legend legend = new Legend().setShow(true).setLocation(
                LegendLocations.EAST);
        String[] lstColor = new String[]{"#6fc227", "#90e0de", "#f6b2af"};
        Options options = new Options().setCaptureRightClick(true)
                .setSeriesDefaults(seriesDefaults).setLegend(legend)
                .setSeriesColors(lstColor);

        final DCharts chart = new DCharts();
        chart.setWidth("400px");
        chart.setHeight("400px");
        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);

//        chart.addHandler(new ChartDataMouseEnterHandler() {
//            @Override
//            public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
//                showEventNotification("CHART DATA MOUSE ENTER",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataMouseLeaveHandler() {
//            @Override
//            public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
//                showEventNotification("CHART DATA MOUSE LEAVE",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataClickHandler() {
//            @Override
//            public void onChartDataClick(ChartDataClickEvent event) {
//                showEventNotification("CHART DATA CLICK", event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataRightClickHandler() {
//            @Override
//            public void onChartDataRightClick(ChartDataRightClickEvent event) {
//                showEventNotification("CHART DATA RIGHT CLICK",
//                        event.getChartData());
//            }
//        });
        chart.setDataSeries(dataSeries).setOptions(options).show();

        return chart;
    }

    private DCharts chartsDemo3_2() {
        DataSeries dataSeries = new DataSeries().newSeries().add("none", 23)
                .add("error", 0).add("click", 5).add("impression", 25);

        SeriesDefaults seriesDefaults = new SeriesDefaults().setRenderer(
                SeriesRenderers.PIE).setRendererOptions(
                        new PieRenderer().setShowDataLabels(true));

        Options options = new Options().setCaptureRightClick(true)
                .setSeriesDefaults(seriesDefaults);

        DCharts chart = new DCharts();
        chart.setWidth("400px");
        chart.setHeight("400px");
        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);
//
//        chart.addHandler(new ChartDataMouseEnterHandler() {
//            @Override
//            public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
//                showEventNotification("CHART DATA MOUSE ENTER",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataMouseLeaveHandler() {
//            @Override
//            public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
//                showEventNotification("CHART DATA MOUSE LEAVE",
//                        event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataClickHandler() {
//            @Override
//            public void onChartDataClick(ChartDataClickEvent event) {
//                showEventNotification("CHART DATA CLICK", event.getChartData());
//            }
//        });
//
//        chart.addHandler(new ChartDataRightClickHandler() {
//            @Override
//            public void onChartDataRightClick(ChartDataRightClickEvent event) {
//                showEventNotification("CHART DATA RIGHT CLICK",
//                        event.getChartData());
//            }
//        });

        chart.setDataSeries(dataSeries).setOptions(options).show();

        return chart;
    }

    private DCharts chartsDemo4() {
        DataSeries dataSeries = new DataSeries() //
                .add(0.9176, 0.9296, 0.927, 0.9251, 0.9241, 0.9225, 0.9197,
                        0.9164, 0.9131, 0.9098, 0.9064, 0.9028, 0.8991, 0.8957,
                        0.8925, 0.8896, 0.8869, 0.8844, 0.882, 0.8797, 0.8776,
                        0.8755, 0.8735, 0.8715, 0.8696, 0.8677, 0.8658, 0.8637,
                        0.8616, 0.8594, 0.8572, 0.8548, 0.8524, 0.8499, 0.8473,
                        0.8446, 0.8418, 0.8389, 0.8359, 0.8328, 0.8295, 0.8262,
                        0.8227, 0.8191, 0.8155, 0.8119, 0.8083, 0.8048, 0.8013,
                        0.7979, 0.7945, 0.7912, 0.7879, 0.7846, 0.7813, 0.778,
                        0.7747, 0.7714, 0.768, 0.7647, 0.7612, 0.7577, 0.7538,
                        0.7496, 0.7449, 0.7398, 0.7342, 0.7279, 0.721, 0.7137,
                        0.7059, 0.6977, 0.6889, 0.6797, 0.6698, 0.6593, 0.6482,
                        0.6367, 0.6247, 0.6121, 0.5989, 0.5852, 0.571, 0.5561,
                        0.5402, 0.5232, 0.505, 0.4855, 0.4643, 0.4414, 0.4166,
                        0.3893, 0.3577, 0.3204, 0.2764, 0.2272, 0.1774, 0.1231,
                        0.0855, 0.0849) //
                .add(0.0824, 0.0704, 0.073, 0.0749, 0.0759, 0.0775, 0.0803,
                        0.0836, 0.0869, 0.0902, 0.0936, 0.0972, 0.1009, 0.1043,
                        0.1075, 0.1104, 0.1131, 0.1156, 0.118, 0.1203, 0.1224,
                        0.1245, 0.1265, 0.1285, 0.1304, 0.1323, 0.1342, 0.1363,
                        0.1384, 0.1406, 0.1428, 0.1452, 0.1476, 0.1501, 0.1527,
                        0.1554, 0.1582, 0.1611, 0.1641, 0.1672, 0.1705, 0.1738,
                        0.1773, 0.1809, 0.1845, 0.1881, 0.1917, 0.1952, 0.1987,
                        0.2021, 0.2055, 0.2088, 0.2121, 0.2154, 0.2187, 0.222,
                        0.2253, 0.2286, 0.232, 0.2353, 0.2388, 0.2423, 0.2462,
                        0.2504, 0.2551, 0.2602, 0.2658, 0.2721, 0.279, 0.2863,
                        0.2941, 0.3023, 0.3111, 0.3203, 0.3302, 0.3407, 0.3518,
                        0.3633, 0.3753, 0.3879, 0.4011, 0.4148, 0.429, 0.4439,
                        0.4598, 0.4768, 0.495, 0.5145, 0.5357, 0.5586, 0.5834,
                        0.6107, 0.6423, 0.6796, 0.7236, 0.7728, 0.8226, 0.8769,
                        0.9145, 0.9151);

        Title title = new Title(
                "Contribution of Urban and Rural Population to National Percentiles (edited data)");

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setShowMarker(false).setFill(true).setFillAndStroke(true);

        Legend legend = new Legend()
                .setShow(true)
                .setRenderer(LegendRenderers.ENHANCED)
                .setRendererOptions(
                        new EnhancedLegendRenderer().setNumberRows(1))
                .setPlacement(LegendPlacements.OUTSIDE_GRID)
                .setLabels("Rural", "Urban").setLocation(LegendLocations.SOUTH);

        Axes axes = new Axes().addAxis(
                new XYaxis(XYaxes.X)
                .setPad(0)
                .setMin(1)
                .setMax(100)
                .setLabel("Population Percentile")
                .setLabelRenderer(LabelRenderers.CANVAS)
                .setTickInterval(3)
                .setTickOptions(
                        new CanvasAxisTickRenderer()
                        .setShowGridline(false))).addAxis(
                        new XYaxis(XYaxes.Y)
                        .setMin(0)
                        .setMax(1)
                        .setLabel("Percentage of Population")
                        .setLabelRenderer(LabelRenderers.CANVAS)
                        .setTickOptions(
                                new CanvasAxisTickRenderer()
                                .setFormatter(TickFormatters.PERCENT)
                                .setShowGridline(false)
                                .setFormatString("%d%%")));

        Grid grid = new Grid().setDrawBorder(false).setShadow(false)
                .setBackground("white");

        Options options = new Options().setTitle(title).setStackSeries(true)
                .setSeriesColors("#77933C", "#B9CDE5")
                .setSeriesDefaults(seriesDefaults).setLegend(legend)
                .setAxes(axes).setGrid(grid);

        DCharts chart = new DCharts();
        chart.setWidth(750, Unit.PIXELS);
        chart.setHeight(300, Unit.PIXELS);
        chart.setMarginRight(10);
        chart.setEnableDownload(true);
        chart.setChartImageFormat(ChartImageFormat.GIF);
        chart.setDataSeries(dataSeries).setOptions(options).show();

        return chart;
    }

    private DCharts chartsDemo5() {
        DataSeries dataSeries = new DataSeries()
                .newSeries()
                .add(0.9, 120, "Vernors")
                .add(1.8, 140, "Fanta")
                .add(3.2, 90, "Barqs", "{background:'#ddbb33'}")
                .add(4.1, 140, "Arizon Iced Tea")
                .add(4.5, 91, "Red Bull")
                .add(6.8, 17, "Go Girl")
                //
                .newSeries().add(1.3, 44, "Pepsi").add(2.1, 170, "Sierra Mist")
                .add(2.6, 66, "Moutain Dew")
                .add(4, 52, "Sobe")
                .add(5.4, 16, "Amp")
                .add(6, 48, "Aquafina")
                //
                .newSeries()
                .add(1, 59, "Coca-Cola", "{background:'rgb(250, 160, 160)'}")
                .add(2, 50, "Ambasa").add(3, 90, "Mello Yello")
                .add(4, 90, "Sprite").add(5, 71, "Squirt").add(5, 155, "Youki");

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.BLOCK);

        Legend legend = new Legend()
                .setShow(true)
                .setRenderer(LegendRenderers.ENHANCED)
                .setRendererOptions(
                        new EnhancedLegendRenderer()
                        .setSeriesToggleReplot(true));

        Axes axes = new Axes()
                .addAxis(new XYaxis(XYaxes.X).setMin(0).setMax(8)).addAxis(
                        new XYaxis(XYaxes.Y).setMin(0).setMax(200));

        Series series = new Series()
                .addSeries(new XYseries())
                .addSeries(
                        new XYseries()
                        .setRenderer(SeriesRenderers.BLOCK)
                        .setRendererOptions(
                                new BlockRenderer()
                                .setCss("background:'#A1EED6'")))
                .addSeries(
                        new XYseries()
                        .setRenderer(SeriesRenderers.BLOCK)
                        .setRendererOptions(
                                new BlockRenderer()
                                .setCss("background:'#D3E4A0'")));

        Options options = new Options().setSeriesDefaults(seriesDefaults)
                .setLegend(legend).setAxes(axes).setSeries(series);

        DCharts chart = new DCharts();
        chart.setWidth("500px");
        chart.setHeight("300px");
        chart.setDataSeries(dataSeries).setOptions(options).show();
        return chart;
    }

    private DCharts chartsDemo6() {
        DataSeries dataSeries = new DataSeries().add(2, 6, 7, 12);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.BAR);

        Axes axes = new Axes().addAxis(new XYaxis().setRenderer(
                AxisRenderers.CATEGORY).setTicks(
                        new Ticks().add("a", "b", "c", "d")));

        Highlighter highlighter = new Highlighter().setShow(false);

        CanvasOverlay canvasOverlay = new CanvasOverlay()
                .setShow(true)
                .setObject(
                        new HorizontalLine().setY(4).setLineWidth(6)
                        .setColor("rgb(100, 55, 124)").setShadow(false))
                .setObject(
                        new HorizontalLine().setY(6).setLineWidth(12)
                        .setXminOffset("8px").setXmaxOffset("29px")
                        .setColor("rgb(50, 55, 30)").setShadow(false))
                .setObject(
                        new DashedHorizontalLine().setY(8).setLineWidth(6)
                        .setXoffset(1).setColor("rgb(133, 120, 24)")
                        .setShadow(false))
                .setObject(
                        new HorizontalLine().setY(10).setLineWidth(3)
                        .setXoffset(0).setColor("rgb(89, 198, 154)")
                        .setShadow(false))
                .setObject(
                        new DashedHorizontalLine().setY(13).setLineWidth(3)
                        .setDashPattern(16, 12).setXoffset(0)
                        .setLineCap(LineCaps.ROUND)
                        .setColor("rgb(66, 98, 144)").setShadow(false));

        Options options = new Options().setSeriesDefaults(seriesDefaults)
                .setAxes(axes).setHighlighter(highlighter)
                .setCanvasOverlay(canvasOverlay);

        DCharts chart = new DCharts();
        chart.setWidth(450, Unit.PIXELS);
        chart.setHeight(300, Unit.PIXELS);
        chart.setDataSeries(dataSeries).setOptions(options).show();
        return chart;
    }

    private DCharts chartsDemo7() {
        DataSeries dataSeries = new DataSeries();
        dataSeries.newSeries().add(2, 1).add(4, 2).add(6, 3).add(3, 4);
        dataSeries.newSeries().add(5, 1).add(1, 2).add(3, 3).add(4, 4);
        dataSeries.newSeries().add(4, 1).add(12, 2).add(1, 3).add(2, 4);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.BAR)
                .setShadowAngle(135);

        Axes axes = new Axes().addAxis(new XYaxis(XYaxes.Y)
                .setRenderer(AxisRenderers.CATEGORY));

        Legend legend = new Legend()
                .setShow(true)
                .setPlacement(LegendPlacements.OUTSIDE_GRID)
                .setRenderer(LegendRenderers.ENHANCED)
                .setRendererOptions(
                        new EnhancedLegendRenderer().setSeriesToggle(
                                SeriesToggles.SLOW).setSeriesToggleReplot(true));

        Options options = new Options().setSeriesDefaults(seriesDefaults)
                .setAxes(axes).setLegend(legend);

        DCharts chart = new DCharts();
        chart.setWidth(450, Unit.PIXELS);
        chart.setHeight(300, Unit.PIXELS);
        chart.setDataSeries(dataSeries).setOptions(options).show();
        return chart;
    }

    private DCharts chartsDemo8() {
        DataSeries dataSeries = new DataSeries()
                .add(14, 3, 4, -3, 5, 2, -3, -7);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.BAR)
                //                .setRendererOptions(
                //                        new BarRenderer().setWaterfall(true).setVaryBarColor(
                //                                true))
                .setPointLabels(new PointLabels().setHideZeros(true))
                .setYaxis(Yaxes.Y2);

        Ticks ticks = new Ticks().add("2008", "Apricots", "Tomatoes",
                "Potatoes", "Rhubarb", "Squash", "Grapes", "Peanuts");
        ticks.add("2009");

        Axes axes = new Axes().addAxis(
                new XYaxis()
                .setRenderer(AxisRenderers.CATEGORY)
                .setTicks(ticks)
                .setTickRenderer(TickRenderers.CANVAS)
                .setTickOptions(
                        new CanvasAxisTickRenderer().setAngle(-90)
                        .setFontSize("10pt").setShowMark(false)
                        .setShowGridline(false))).addAxis(
                        new XYaxis(XYaxes.Y2).setMin(0).setTickInterval(5));

        Title title = new Title("Crop Yield Charnge, 2008 to 2009");

        Options options = new Options().setSeriesDefaults(seriesDefaults)
                .setAxes(axes).setTitle(title);

        DCharts chart = new DCharts();
        chart.setWidth(450, Unit.PIXELS);
        chart.setHeight(300, Unit.PIXELS);
        chart.setDataSeries(dataSeries).setOptions(options).show();
        return chart;
    }

    public VerticalLayout testChart() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        DCharts charts1 = chartsDemo12();
        DCharts charts2 = chartsDemo2();
        DCharts charts3_1 = chartsDemo3_1();
        DCharts charts3_2 = chartsDemo3_2();
        DCharts charts4 = chartsDemo4();
        DCharts charts5 = chartsDemo5();
        DCharts charts6 = chartsDemo6();
        DCharts charts7 = chartsDemo7();
        DCharts charts8 = chartsDemo8();

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponent(charts3_1);
        layout3.addComponent(charts3_2);

        TabSheet demoTabSheet = new TabSheet();
        layout.addComponent(demoTabSheet);
        demoTabSheet.addTab(charts1, "Demo 1");
        demoTabSheet.addTab(charts2, "Demo 2");
        demoTabSheet.addTab(layout3, "Demo 3");
        demoTabSheet.addTab(charts4, "Demo 4");
        demoTabSheet.addTab(charts5, "Demo 5");
        demoTabSheet.addTab(charts6, "Demo 6");
        demoTabSheet.addTab(charts7, "Demo 7");
        demoTabSheet.addTab(charts8, "Demo 8");

        return layout;
    }
}
