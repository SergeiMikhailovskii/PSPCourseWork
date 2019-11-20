package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class YearPieChartWindow extends ApplicationFrame {

    private DefaultPieDataset dataset;

    public YearPieChartWindow(String title, DefaultPieDataset dataset) {
        super(title);
        this.setSize(500, 500);
        this.dataset = dataset;
        setContentPane(createDemoPanel());
    }

    private JPanel createDemoPanel() {
        JFreeChart chart = createChart();
        return new ChartPanel(chart);
    }

    private JFreeChart createChart() {
        return ChartFactory.createPieChart("Flats", dataset, true, true, false);
    }
}
