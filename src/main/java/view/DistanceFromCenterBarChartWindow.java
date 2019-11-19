package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class DistanceFromCenterBarChartWindow extends ApplicationFrame {

    public DistanceFromCenterBarChartWindow(String title) {
        super(title);
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Distance in km",
                "%",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        setContentPane(chartPanel);
        this.pack();
    }

    private CategoryDataset createDataset() {
        final String distance1 = "0-2";
        final String distance2 = "2-4";
        final String distance3 = "4-6";
        final String distance4 = "6-8";
        final String distance5 = "8-10";
        final String distance6 = "10-12";
        final String distanceFromCenter = "";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(10, distance1, distanceFromCenter);
        dataset.addValue(20, distance2, distanceFromCenter);
        dataset.addValue(30, distance3, distanceFromCenter);
        dataset.addValue(30, distance4, distanceFromCenter);
        dataset.addValue(5, distance5, distanceFromCenter);
        dataset.addValue(5, distance6, distanceFromCenter);

        return dataset;
    }
}
