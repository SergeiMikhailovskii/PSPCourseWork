package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;

public class DistanceFromCenterBarChartWindow extends ApplicationFrame {
    private CategoryDataset dataset;

    public DistanceFromCenterBarChartWindow(String title, CategoryDataset dataset) {
        super(title);
        this.dataset = dataset;
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Distance in km",
                "%",
                dataset,
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

}
