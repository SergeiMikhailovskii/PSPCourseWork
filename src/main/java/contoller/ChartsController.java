package contoller;

import client.ClientSocket;
import constants.Actions;
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
import java.util.HashMap;
import java.util.Map;

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
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final String distanceFromCenter = "";

        sendDataToServer(Actions.GET_DISTANCE_FROM_SERVER_BAR_CHART_DATA);
        sendDataToServer(" ");
        int rows = Integer.parseInt(getDataFromServer());

        for (int i = 0; i < rows; i++) {
            String res = getDataFromServer();
            String[] arr = res.split(" ");
            dataset.addValue(Integer.parseInt(arr[0]), arr[1], distanceFromCenter);
        }

        return dataset;
    }

    private DefaultPieDataset getBuildYearData() {
        Map<String, Integer> data = new HashMap<>();
        DefaultPieDataset dataset = new DefaultPieDataset();
        int sum = 0;

        sendDataToServer(Actions.GET_BUILD_YEAR_CHART_DATA);
        sendDataToServer(" ");
        int rows = Integer.parseInt(getDataFromServer());

        for (int i = 0; i < rows; i++) {
            String res = getDataFromServer();
            String[] arr = res.split(" ");
            data.put(arr[1], Integer.parseInt(arr[0]));
            sum += Integer.parseInt(arr[0]);
        }

        for (Map.Entry<String, Integer> item : data.entrySet()) {
            if (item.getValue() != 0) {
                dataset.setValue(item.getKey(), (double) item.getValue() * 100 / sum);
            }
        }

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
