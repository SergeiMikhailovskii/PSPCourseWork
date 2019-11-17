package view;

import javax.swing.*;

public class MenuWindow extends JFrame {
    private JButton evaluatePropertyBtn = new JButton("Evaluate property");
    private JButton button2 = new JButton("Button2");
    private JButton button3 = new JButton("Button3");
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

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(evaluatePropertyBtn);
        evaluatePropertyBtn.addActionListener(e -> {
            new EvaluatePropertyWindow(id).setVisible(true);
            this.setVisible(false);
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, evaluatePropertyBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, evaluatePropertyBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(button2);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button2, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, button2, 20, SpringLayout.SOUTH, evaluatePropertyBtn);

        getContentPane().add(button3);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button3, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, button3, 20, SpringLayout.SOUTH, button2);

        getContentPane().add(showAllUsersBtn);
        showAllUsersBtn.setVisible(role == 1);
        showAllUsersBtn.addActionListener(e -> {
            new ShowUsersWindow(id).setVisible(true);
            this.setVisible(false);
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showAllUsersBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showAllUsersBtn, 20, SpringLayout.SOUTH, button3);

        setSize(300, 300);

    }
}
