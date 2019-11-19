package contoller;

import client.ClientSocket;
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
        new DistanceFromCenterBarChartWindow("Distance from center").setVisible(true);
    }

    private void showYearPieChartWindow() {
        new YearPieChartWindow("Year chart", getBuildYearData()).setVisible(true);
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
