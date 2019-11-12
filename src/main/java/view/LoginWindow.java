package view;

import contoller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField loginTF = new JTextField();
    private JTextField passwordTF = new JTextField();
    private JLabel loginLB = new JLabel("Login:");
    private JLabel passwordLB = new JLabel("Password:");
    private JButton loginBtn = new JButton("Login");

    private LoginController loginController = new LoginController();

    private LoginWindow() {
        super("Login window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginController.attachView(this);
        initWindow();
    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(loginLB);
        springLayout.putConstraint(SpringLayout.WEST, loginLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, loginLB, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(loginTF);
        loginTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, loginTF, 20, SpringLayout.EAST, loginLB);
        springLayout.putConstraint(SpringLayout.NORTH, loginTF, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(passwordLB);
        springLayout.putConstraint(SpringLayout.WEST, passwordLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, passwordLB, 20, SpringLayout.SOUTH, loginTF);

        getContentPane().add(passwordTF);
        passwordTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, passwordTF, 20, SpringLayout.EAST, passwordLB);
        springLayout.putConstraint(SpringLayout.NORTH, passwordTF, 20, SpringLayout.SOUTH, loginTF);

        getContentPane().add(loginBtn);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, loginBtn, 20, SpringLayout.SOUTH, passwordTF);
        loginBtn.addActionListener(e -> {
            String login = loginTF.getText();
            String password = passwordTF.getText();
            loginController.logInUser(login, password);
        });

        setSize(450, 200);
    }

    public void showDialog(String text) {
        JOptionPane.showMessageDialog(this, text, "Login", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }
}
