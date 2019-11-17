package contoller;

import client.ClientSocket;
import view.MenuWindow;
import view.ShowUsersWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class ShowUsersController {
    private ShowUsersWindow window;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private int id;

    public ShowUsersController() {
        socket = ClientSocket.getSocket();
    }

    public void attachView(ShowUsersWindow window) {
        this.window = window;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getUsersFromDB() {
        Vector<Vector> rowData = new Vector<>();
        sendDataToServer("GET_ALL_USERS");
        sendDataToServer(" ");
        int rows = Integer.parseInt(getDataFromServer());
        for (int i = 0; i < rows; i++) {
            String row = getDataFromServer();
            String[] arr = row.split(" ");
            Vector<Object> object = new Vector<>();
            object.add(arr[0]);
            object.add(arr[1]);
            object.add(arr[2]);
            rowData.add(object);
        }
        window.onUsersLoaded(rowData);
    }

    public void navigateBack() {
        new MenuWindow(1, id).setVisible(true);
        window.setVisible(false);
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
