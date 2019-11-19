package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;

public class YearPieChartWindow extends ApplicationFrame {

    public YearPieChartWindow(String title) {
        super(title);
        this.setSize(500, 500);
        setContentPane(createDemoPanel());
    }

    private JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    private JFreeChart createChart(PieDataset dataset) {
        return ChartFactory.createPieChart("Flats", dataset, true, true, false);
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("1960-1970", 0);
        dataset.setValue("1970-1980", 20);
        dataset.setValue("1980-1990", 20);
        dataset.setValue("1990-2000", 20);
        dataset.setValue("2000-2010", 10);
        dataset.setValue("2010-2020", 30);
        return dataset;
    }
}
