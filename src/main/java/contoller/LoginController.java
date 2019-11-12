package contoller;

import view.LoginWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LoginController {
    private LoginWindow window;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public LoginController() {
        connectToServer();
    }

    public void logInUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            sendDataToServer("LOGIN");
            sendDataToServer("");

            //todo fix query

            //if (data from server == not found)
            window.showRegisterDialog();
        } else {
            window.showMessageDialog("Fill the fields!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void attachView(LoginWindow window) {
        this.window = window;
    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 1024);
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
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

}
