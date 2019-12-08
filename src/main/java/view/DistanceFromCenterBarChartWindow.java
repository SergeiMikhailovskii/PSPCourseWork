package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;

public class DistanceFromCenterBarChartWindow extends ApplicationFrame {

    public DistanceFromCenterBarChartWindow(String title, CategoryDataset dataset) {
        super(title);
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Расстояние в километрах",
                "Количество",
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
