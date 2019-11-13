package view;

import contoller.ShowUsersController;

import javax.swing.*;

public class ShowUsersWindow extends JFrame {
    private JTable table;
    private String[] columnNames = {"Login", "Password", "Role"};

    private ShowUsersController controller = new ShowUsersController();

    public ShowUsersWindow() {
        controller.attachView(this);
        initWindow();
    }

    private void initWindow() {

    }

}
