package base;

import client.ClientSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public abstract class BaseController {
    private Socket socket;

    protected BaseController() {
        socket = ClientSocket.getSocket();
    }

    protected void sendDataToServer(String res) {
        try {
            OutputStream os = socket.getOutputStream();
            os.write(res.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getDataFromServer() {
        byte[] bytes = new byte[100];
        String str = null;
        try {
            InputStream is = socket.getInputStream();
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
