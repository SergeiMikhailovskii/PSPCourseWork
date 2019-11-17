package view;

import contoller.ShowUsersController;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class ShowUsersWindow extends JFrame {
    private JTable table = new JTable();
    private JButton backBtn = new JButton("Back");
    private Vector<String> columnNames = new Vector<>();

    private ShowUsersController controller = new ShowUsersController();

    private int id;

    ShowUsersWindow(int id) {
        super("Users");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.id = id;
        controller.attachView(this);
        columnNames.addAll(List.of("Login", "Password", "Role"));
        getUsersFromDB();
        initWindow();
    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(backBtn);
        backBtn.addActionListener(e -> controller.navigateBack());
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, table);

        setSize(300, 300);
    }

    private void getUsersFromDB() {
        controller.getUsersFromDB();
    }

    public void onUsersLoaded(Vector<Vector> users) {
        table = new JTable(users, columnNames);
    }


}
