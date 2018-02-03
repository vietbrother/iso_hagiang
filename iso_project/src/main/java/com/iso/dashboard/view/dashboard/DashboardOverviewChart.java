package com.iso.dashboard.view.dashboard;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.CategoryScale;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.iso.dashboard.data.dummy.DummyDataGenerator;
import com.vaadin.annotations.JavaScript;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.awt.Color;
import java.util.ArrayList;

@SuppressWarnings("serial")
@JavaScript({"vaadin://js/Chart.min.js", "vaadin://js/chartjs-connector.js"})
public class DashboardOverviewChart extends VerticalLayout {

    public DashboardOverviewChart(final String name, final String unit,
            final String prefix,
            final String icon,
            final String colorName, final Color color, final int howManyPoints,
            final int min, final int max) {
        setSizeUndefined();
        addStyleName("spark");
        setMargin(false);
        setSpacing(false);
        setDefaultComponentAlignment(Alignment.TOP_CENTER);

        int[] values = DummyDataGenerator.randomSparklineValues(howManyPoints,
                min, max);

        Label title = new Label();
//        title.setSizeUndefined();
        title.setContentMode(ContentMode.HTML);
        title.setValue(icon + " " + name);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        addComponent(title);

        Label current = new Label("<b style=\"color:" + colorName + ";     line-height: 47px;\n"
                + "    font-size: 40px;"
                + "    font-weight: 600;"
                + "\">" + prefix + values[values.length - 1] + unit
                + "</b>",
                ContentMode.HTML);
//        current.setSizeUndefined();
//        current.addStyleName(ValoTheme.LABEL_H1);
        current.addStyleName(ValoTheme.LABEL_LARGE);
        current.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        addComponent(current);

//        addComponent(buildSparkline(values, color));
//
//        List<Integer> vals = Arrays.asList(ArrayUtils.toObject(values));
//        Label highLow = new Label(
//                "High <b>" + java.util.Collections.max(vals)
//                + "</b> &nbsp;&nbsp;&nbsp; Low <b>"
//                + java.util.Collections.min(vals) + "</b>",
//                ContentMode.HTML);
//        highLow.addStyleName(ValoTheme.LABEL_TINY);
//        highLow.addStyleName(ValoTheme.LABEL_LIGHT);
//        highLow.setSizeUndefined();
//        addComponent(highLow);
    }

    private Component buildSparkline(final int[] values, final Color color) {
        LineChartConfig lineConfig = new LineChartConfig();
        lineConfig.data()
                .labels("", "", "", "", "", "", "")
                .addDataset(new LineDataset().label(""))
                .and()
                .options()
                .responsive(true)
                .title()
                .display(true)
                .text("")
                .and()
                .scales()
                .and()
                .done();

        // add random data for demo
        List<String> labels = lineConfig.data().getLabels();
        for (Dataset<?, ?> ds : lineConfig.data().getDatasets()) {
            LineDataset lds = (LineDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((double) Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
            lds.borderColor("#197de1");
            lds.backgroundColor("#ffffff");
        }

        ChartJs chart = new ChartJs(lineConfig);
        chart.setJsLoggingEnabled(true);
        chart.setWidth("120px");
        chart.setHeight("40px");
        return chart;
    }
}
