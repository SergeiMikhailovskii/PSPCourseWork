package contoller;

import client.ClientSocket;
import view.LoginWindow;
import view.MenuWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LoginController {
    private LoginWindow window;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public LoginController() {
        socket = ClientSocket.getSocket();
    }

    public void logInUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            sendDataToServer("LOGIN");
            sendDataToServer(login + " " + password);
            String result = getDataFromServer();
            int id = Integer.parseInt(getDataFromServer());
            if (result.equalsIgnoreCase("EMPTY")) {
                window.showRegisterDialog();
            } else if (result.equalsIgnoreCase("BASE_USER")) {
                window.showMessageDialog("You logged in as base user", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(0, id).setVisible(true);
                window.setVisible(false);
            } else {
                window.showMessageDialog("You logged in as admin", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(1, id).setVisible(true);
                window.setVisible(false);
            }
        } else {
            window.showMessageDialog("Fill the fields!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registerUser(String login, String password) {
        sendDataToServer("REGISTER");
        sendDataToServer(login + " " + password);
        String result = getDataFromServer();
        if (result.equalsIgnoreCase("REGISTERED")) {
            window.showMessageDialog("Registered", JOptionPane.INFORMATION_MESSAGE);
            logInUser(login, password);
        }
    }

    public void attachView(LoginWindow window) {
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
