package view;

import javax.swing.*;
import java.util.Vector;

public class ShowAllUserPropertiesWindow extends JFrame {
    private JTable table = new JTable();
    private JButton backBtn = new JButton("Back");
    private Vector<String> columnNames = new Vector<>();

    private int id;

    public ShowAllUserPropertiesWindow(int id) {
        super("Show all user properties window");
        this.id = id;
        initWindow();
    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(backBtn);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, table);

        setSize(300, 300);

    }
}
