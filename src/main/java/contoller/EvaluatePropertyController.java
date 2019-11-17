package contoller;

import client.ClientSocket;
import entities.Property;
import view.EvaluatePropertyWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EvaluatePropertyController {
    private EvaluatePropertyWindow window;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public EvaluatePropertyController() {
        this.socket = ClientSocket.getSocket();
    }

    public void attachView(EvaluatePropertyWindow window) {
        this.window = window;
    }

    public void calculatePrice(String address, int square, int distanceFromCenter, int buildYear, int repairDegree) {
        int sum = 0;
        new Thread(() -> {
            // todo implement count price
            // todo implement setting current user
            // todo change distance, buildYear and repairDegree to real indexes from DB
            sendDataToServer("GET_DISTANCE_FROM_CENTER");
            sendDataToServer(String.valueOf(distanceFromCenter));
            double distanceCoefficient = Double.parseDouble(getDataFromServer());

            sendDataToServer("GET_BUILD_YEAR");
            sendDataToServer(String.valueOf(buildYear));
            double yearCoefficient = Double.parseDouble(getDataFromServer());

            Property property = new Property(address, square, 1000, distanceCoefficient, yearCoefficient, 1, 1);

            sendDataToServer("SAVE_PROPERTY");
            sendDataToServer(property.toString());
            String response = getDataFromServer();

            if (response.equalsIgnoreCase("Inserted")) {
                window.onDataSaved();
            }
        }).start();
        window.onDataCalculated(sum);
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
