package view;

import base.BaseView;
import contoller.ChartsController;

import javax.swing.*;

public class MenuWindow extends JFrame implements BaseView {
    private JButton evaluatePropertyBtn = new JButton("Evaluate property");
    private JButton showAllUserPropertiesBtn = new JButton("Show all user properties");
    private JButton showCharts = new JButton("Show charts");
    private JButton showAllUsersBtn = new JButton("Show all users");

    private int role;
    private int id;

    public MenuWindow(int role, int id) {
        super("Menu window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.id = id;
        this.role = role;
        initWindow();
    }

    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(evaluatePropertyBtn);
        evaluatePropertyBtn.addActionListener(e -> {
            new EvaluatePropertyWindow(id).setVisible(true);
            // this.setVisible(false);
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, evaluatePropertyBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, evaluatePropertyBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(showAllUserPropertiesBtn);
        showAllUserPropertiesBtn.addActionListener(e -> new ShowAllUserPropertiesWindow(id).setVisible(true));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showAllUserPropertiesBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showAllUserPropertiesBtn, 20, SpringLayout.SOUTH, evaluatePropertyBtn);

        getContentPane().add(showCharts);
        showCharts.addActionListener(e -> new ChartsController().showCharts());
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showCharts, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showCharts, 20, SpringLayout.SOUTH, showAllUserPropertiesBtn);

        getContentPane().add(showAllUsersBtn);
        showAllUsersBtn.setVisible(role == 1);
        showAllUsersBtn.addActionListener(e -> new ShowUsersWindow(id).setVisible(true));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showAllUsersBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showAllUsersBtn, 20, SpringLayout.SOUTH, showCharts);

        setSize(300, 300);

    }
}
