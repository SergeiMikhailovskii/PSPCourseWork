package view;

import contoller.ShowAllUserPropertiesController;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class ShowAllUserPropertiesWindow extends JFrame {
    private JTable table = new JTable();
    private JButton backBtn = new JButton("Back");
    private Vector<String> columnNames = new Vector<>();

    private ShowAllUserPropertiesController controller = new ShowAllUserPropertiesController();

    private int id;

    ShowAllUserPropertiesWindow(int id) {
        super("Show all user properties window");
        controller.attachView(this);
        columnNames.addAll(List.of("Address", "Square", "Price", "DistanceFromCenterID", "BuildYearID", "RepairDegreeID"));
        this.id = id;
        controller.setId(this.id);
        controller.getAllUserProperties();
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
