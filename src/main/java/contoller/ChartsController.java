package contoller;

import client.ClientSocket;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import view.DistanceFromCenterBarChartWindow;
import view.YearPieChartWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChartsController {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public ChartsController() {
        socket = ClientSocket.getSocket();
    }

    public void showCharts() {
        showYearPieChartWindow();
        showDistanceFromCenterBarChartWindow();
    }

    private void showDistanceFromCenterBarChartWindow() {
        new DistanceFromCenterBarChartWindow("Distance from center", getDistanceFromCenterBarChartData()).setVisible(true);
    }

    private void showYearPieChartWindow() {
        new YearPieChartWindow("Year chart", getBuildYearData()).setVisible(true);
    }

    private CategoryDataset getDistanceFromCenterBarChartData() {
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

    private DefaultPieDataset getBuildYearData() {
        sendDataToServer("GET_BUILD_YEAR_CHART_DATA");
        sendDataToServer(" ");
        int rows = Integer.parseInt(getDataFromServer());
        System.out.println(rows+" rows in db");

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("1960-1970", 0);
        dataset.setValue("1970-1980", 20);
        dataset.setValue("1980-1990", 20);
        dataset.setValue("1990-2000", 20);
        dataset.setValue("2000-2010", 10);
        dataset.setValue("2010-2020", 30);

        return dataset;
    }

    private void sendDataToServer(String res) {
        try {
            os = socket.getOutputStream();
            os.write(res.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDataFromServer() {
        byte[] bytes = new byte[100];
        String str = null;
        try {
            is = socket.getInputStream();
            //noinspection ResultOfMethodCallIgnored
            is.read(bytes);
            str = new String(bytes, StandardCharsets.UTF_8);
            str = str.trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
