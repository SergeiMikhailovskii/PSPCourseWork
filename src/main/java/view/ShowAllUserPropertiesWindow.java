package view;

import base.BaseView;
import contoller.ShowAllUserPropertiesController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ShowAllUserPropertiesWindow extends JFrame implements BaseView {
    private JTable table = new JTable();
    private JButton findPropertyBtn = new JButton("Find property");
    private JButton searchDlgBtn = new JButton("Search");
    private JDialog dialog = new JDialog();
    private JTextField dialogTF = new JTextField();

    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector> data;

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
        this.data = data;
        table = new JTable(data, columnNames);
    }

    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 0, SpringLayout.NORTH, getContentPane());

        getContentPane().add(findPropertyBtn);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, findPropertyBtn, 0, SpringLayout.HORIZONTAL_CENTER, scrollPane);
        springLayout.putConstraint(SpringLayout.NORTH, findPropertyBtn, 20, SpringLayout.SOUTH, scrollPane);
        findPropertyBtn.addActionListener(e -> dialog.setVisible(true));

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(new JLabel("Enter the property address"));
        dialogTF.setPreferredSize(new Dimension(100, 20));
        pane.add(dialogTF);
        pane.add(searchDlgBtn);
        dialog.add(pane);
        dialog.setPreferredSize(new Dimension(300, 300));

        searchDlgBtn.addActionListener(e -> {
            String address = dialogTF.getText();
            if (address.equalsIgnoreCase("")) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int size = model.getRowCount();
                for (int i = 0; i < size; i++) {
                    model.removeRow(0);
                }

                for (Vector newDatum : this.data) {
                    model.addRow(newDatum);
                }
                table.setModel(model);
            } else {
                Vector<Vector> newData = new Vector<>();
                for (Vector vector : data) {
                    if (vector.get(0).equals(address)) {
                        newData.add(vector);
                    }
                }
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int size = model.getRowCount();
                for (int i = 0; i < size; i++) {
                    model.removeRow(0);
                }

                for (Vector newDatum : newData) {
                    model.addRow(newDatum);
                }
                table.setModel(model);

            }
            dialog.setVisible(false);

        });

        setSize(500, 500);
    }

}
