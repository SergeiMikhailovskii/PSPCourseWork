package contoller;

import base.BaseController;
import constants.Actions;
import view.LoginWindow;
import view.MenuWindow;

import javax.swing.*;

public class LoginController extends BaseController {
    private LoginWindow window;

    public void logInUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            sendDataToServer(Actions.LOGIN);
            sendDataToServer(login + " " + password);
            int role = Integer.parseInt(getDataFromServer());
            int id = Integer.parseInt(getDataFromServer());
            if (role == -1) {
                window.showRegisterDialog();
            } else if (role == 0) {
                window.showMessageDialog("You logged in as base user", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(0, id).setVisible(true);
                window.setVisible(false);
            } else if (role == 1) {
                window.showMessageDialog("You logged in as admin", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(1, id).setVisible(true);
                window.setVisible(false);
            }
        } else {
            window.showMessageDialog("Fill the fields!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registerUser(String login, String password) {
        sendDataToServer(Actions.REGISTER);
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
}
