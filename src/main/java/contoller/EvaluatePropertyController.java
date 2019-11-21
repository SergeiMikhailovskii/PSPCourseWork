package contoller;

import client.ClientSocket;
import constants.Actions;
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
    private int id;

    public EvaluatePropertyController() {
        this.socket = ClientSocket.getSocket();
    }

    public void attachView(EvaluatePropertyWindow window) {
        this.window = window;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void calculatePrice(String address, int square, int distanceFromCenter, int buildYear, int repairDegree) {
        double sum;
        // todo implement count price
        // todo implement setting current user
        // todo change distance, buildYear and repairDegree to real indexes from DB
        sendDataToServer(Actions.GET_DISTANCE_FROM_CENTER);
        sendDataToServer(String.valueOf(distanceFromCenter));
        double distanceCoefficient = Double.parseDouble(getDataFromServer());
        int distanceID = (int) Double.parseDouble(getDataFromServer());

        sendDataToServer(Actions.GET_BUILD_YEAR);
        sendDataToServer(String.valueOf(buildYear));
        double yearCoefficient = Double.parseDouble(getDataFromServer());
        int yearID = (int) Double.parseDouble(getDataFromServer());

        sendDataToServer(Actions.GET_REPAIR_DEGREE);
        sendDataToServer(String.valueOf(repairDegree));
        double repairDegreeCoefficient = Double.parseDouble(getDataFromServer());
        int repairID = (int) Double.parseDouble(getDataFromServer());

        sum = 1500 * square * distanceCoefficient * yearCoefficient * repairDegreeCoefficient;

        Property property = new Property(address, square, sum, distanceID, yearID, repairID, id);

        sendDataToServer(Actions.SAVE_PROPERTY);
        sendDataToServer(property.toString());
        String response = getDataFromServer();

        if (response.equalsIgnoreCase("Inserted")) {
            window.onDataCalculated(sum);
            window.onDataSaved();
        }
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
