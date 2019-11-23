package view;

import base.BaseView;
import contoller.ShowAllUserPropertiesController;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class ShowAllUserPropertiesWindow extends JFrame implements BaseView {
    private JTable table = new JTable();
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

    public void setTable(Vector<Vector> data) {
        table = new JTable(data, columnNames);
    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 0, SpringLayout.NORTH, getContentPane());

        setSize(500, 500);
    }

}
