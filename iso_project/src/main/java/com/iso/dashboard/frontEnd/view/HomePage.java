/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.view;

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
import com.iso.dashboard.data.dummy.DummyDataGenerator;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.view.dashboard.DashboardOverviewChart;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class HomePage extends VerticalLayout {

    public HomePage() {
        setSizeFull();
        addStyleName("dashboard-view");
        Responsive.makeResponsive(this);
        Label title = new Label(BundleUtils.getStringCas("menu.dashboard"), ContentMode.HTML);
        title.addStyleName(ValoTheme.LABEL_H2);
        title.addStyleName(ValoTheme.LABEL_BOLD);
        title.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(title);
        addComponent(buildOverView());
        addComponent(buildChart());
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

        vertical.addComponent(buildChartstatistic());
        vertical.addComponent(buildChartDemo());
        HorizontalLayout temp1 = new HorizontalLayout();
        temp1.setWidth("100%");
        temp1.setSpacing(true);
        temp1.addComponents(buildPieChart(), buildPieChart(), buildPieChart());
        vertical.addComponent(temp1);

        clMostUsedFunc.addComponent(vertical);
        return clMostUsedFunc;
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

}
