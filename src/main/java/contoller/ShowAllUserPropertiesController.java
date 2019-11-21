package contoller;

import client.ClientSocket;
import constants.Actions;
import view.ShowAllUserPropertiesWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

public class ShowAllUserPropertiesController {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private ShowAllUserPropertiesWindow window;

    private int id;

    public ShowAllUserPropertiesController() {
        socket = ClientSocket.getSocket();
    }

    public void attachView(ShowAllUserPropertiesWindow window) {
        this.window = window;
    }

    public void setId(int id) {
        this.id = id;
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


    public void getAllUserProperties() {
        Vector<Vector> rowData = new Vector<>();
        sendDataToServer(Actions.GET_ALL_USER_PROPERTIES);
        sendDataToServer(String.valueOf(id));
        int count = Integer.parseInt(getDataFromServer());
        for (int i = 0; i < count; i++) {
            String res = getDataFromServer();
            System.out.println(res);
            String[] arr = res.split(" ");
            Vector<Object> object = new Vector<>(Arrays.asList(arr));
            rowData.add(object);
            System.out.println("added");
        }
        System.out.println(rowData.size()+" SIZE");
        window.setTable(rowData);
    }
}
