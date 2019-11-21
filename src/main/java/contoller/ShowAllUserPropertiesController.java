package contoller;

import client.ClientSocket;
import view.ShowAllUserPropertiesWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ShowAllUserPropertiesController {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private ShowAllUserPropertiesWindow window;

    public ShowAllUserPropertiesController() {
        socket = ClientSocket.getSocket();
    }

    public void attachView(ShowAllUserPropertiesWindow window) {
        this.window = window;
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
