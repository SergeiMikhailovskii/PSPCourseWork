package contoller;

import view.LoginWindow;

public class LoginController {
    private LoginWindow window;

    public void logInUser(String login, String password) {
        System.out.println(login + " " + password);
    }

    public void attachView(LoginWindow window) {
        this.window = window;
    }
}
