package view;

import base.BaseView;
import contoller.ShowUsersController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class ShowUsersWindow extends JFrame implements BaseView {
    private JTable table = new JTable();
    private JButton backBtn = new JButton("Back");
    private JButton deleteBtn = new JButton("Delete");
    private Vector<Vector> users;

    private Vector<String> columnNames = new Vector<>();

    private ShowUsersController controller = new ShowUsersController();

    ShowUsersWindow(int id) {
        super("Users");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        controller.attachView(this);
        controller.setId(id);
        columnNames.addAll(List.of("Login", "Password", "Role"));
        getUsersFromDB();
        initWindow();
    }

    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(backBtn);
        backBtn.addActionListener(e -> controller.navigateBack());
        springLayout.putConstraint(SpringLayout.WEST, backBtn, 0, SpringLayout.WEST, table);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, table);
        springLayout.putConstraint(SpringLayout.EAST, backBtn, 0, SpringLayout.EAST, deleteBtn);

        getContentPane().add(deleteBtn);
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            controller.deleteUser(users.get(row).get(0).toString(), users.get(row).get(1).toString());
            ((DefaultTableModel) table.getModel()).removeRow(row);
        });
        springLayout.putConstraint(SpringLayout.WEST, deleteBtn, 0, SpringLayout.WEST, backBtn);
        springLayout.putConstraint(SpringLayout.NORTH, deleteBtn, 20, SpringLayout.SOUTH, backBtn);

        setSize(300, 300);
    }

    private void getUsersFromDB() {
        controller.getUsersFromDB();
    }

    public void onUsersLoaded(Vector<Vector> users) {
        this.users = users;
        table = new JTable(this.users, columnNames);
    }

    public void showMessageDialog(String text, int dialogType) {
        JOptionPane.showMessageDialog(this, text, "Login", dialogType);
    }


}
