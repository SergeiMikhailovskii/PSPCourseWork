package contoller;

import view.LoginWindow;

import javax.swing.*;

public class LoginController {
    private LoginWindow window;

    public void logInUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            // todo implement server query to db
        } else {
            window.showMessageDialog("Fill the fields!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void attachView(LoginWindow window) {
        this.window = window;
    }
}
