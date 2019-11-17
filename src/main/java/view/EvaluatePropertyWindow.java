package view;

import contoller.EvaluatePropertyController;

import javax.swing.*;
import java.awt.*;

public class EvaluatePropertyWindow extends JFrame {
    private EvaluatePropertyController controller = new EvaluatePropertyController();

    private JLabel addressLB = new JLabel("Address: ");
    private JTextField addressTF = new JTextField();
    private JLabel squareLB = new JLabel("Square: ");
    private JTextField squareTF = new JTextField();
    private JLabel totalLB = new JLabel("Total: ");
    private JLabel distanceFromCenterLB = new JLabel("Distance from center: ");
    private JTextField distanceFromCenterTF = new JTextField();
    private JLabel buildYearLB = new JLabel("Year of build: ");
    private JTextField buildYearTF = new JTextField();
    private JLabel repairDegreeLB = new JLabel("Repair degree:");
    private JTextField repairDegreeTF = new JTextField();
    private JButton calculateBtn = new JButton("Calculate");

    private int id;

    EvaluatePropertyWindow(int id) {
        super("Evaluate Property");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.id = id;
        controller.attachView(this);
        controller.setId(id);
        initWindow();
    }

    public void onDataCalculated(double sum) {
        totalLB.setText(totalLB.getText() + (int) sum);
    }

    public void onDataSaved() {
        JOptionPane.showMessageDialog(this, "Info saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(addressLB);
        springLayout.putConstraint(SpringLayout.WEST, addressLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, addressLB, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(addressTF);
        addressTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, addressTF, 20, SpringLayout.EAST, addressLB);
        springLayout.putConstraint(SpringLayout.NORTH, addressTF, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(squareLB);
        springLayout.putConstraint(SpringLayout.WEST, squareLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, squareLB, 20, SpringLayout.SOUTH, addressTF);

        getContentPane().add(squareTF);
        squareTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, squareTF, 20, SpringLayout.EAST, squareLB);
        springLayout.putConstraint(SpringLayout.NORTH, squareTF, 20, SpringLayout.SOUTH, addressTF);

        getContentPane().add(distanceFromCenterLB);
        springLayout.putConstraint(SpringLayout.WEST, distanceFromCenterLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, distanceFromCenterLB, 20, SpringLayout.SOUTH, squareTF);

        getContentPane().add(distanceFromCenterTF);
        distanceFromCenterTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, distanceFromCenterTF, 20, SpringLayout.EAST, distanceFromCenterLB);
        springLayout.putConstraint(SpringLayout.NORTH, distanceFromCenterTF, 20, SpringLayout.SOUTH, squareTF);

        getContentPane().add(buildYearLB);
        springLayout.putConstraint(SpringLayout.WEST, buildYearLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, buildYearLB, 20, SpringLayout.SOUTH, distanceFromCenterTF);

        getContentPane().add(buildYearTF);
        buildYearTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, buildYearTF, 20, SpringLayout.EAST, buildYearLB);
        springLayout.putConstraint(SpringLayout.NORTH, buildYearTF, 20, SpringLayout.SOUTH, distanceFromCenterTF);

        getContentPane().add(repairDegreeLB);
        springLayout.putConstraint(SpringLayout.WEST, repairDegreeLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, repairDegreeLB, 20, SpringLayout.SOUTH, buildYearTF);

        getContentPane().add(repairDegreeTF);
        repairDegreeTF.setPreferredSize(new Dimension(200, 20));
        springLayout.putConstraint(SpringLayout.WEST, repairDegreeTF, 20, SpringLayout.EAST, repairDegreeLB);
        springLayout.putConstraint(SpringLayout.NORTH, repairDegreeTF, 20, SpringLayout.SOUTH, buildYearTF);

        getContentPane().add(calculateBtn);
        calculateBtn.addActionListener(e -> {
            try {
                String address = addressTF.getText();
                int square = Integer.parseInt(squareTF.getText());
                int distanceFromCenter = Integer.parseInt(distanceFromCenterTF.getText());
                int buildYear = Integer.parseInt(buildYearTF.getText());
                int repairDegree = Integer.parseInt(repairDegreeTF.getText());
                controller.calculatePrice(address, square, distanceFromCenter, buildYear, repairDegree);
            } catch (NullPointerException | NumberFormatException exc) {
                JOptionPane.showMessageDialog(this, "Fill all fields correctly", "Error!", JOptionPane.ERROR_MESSAGE);
                exc.printStackTrace();
            }
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, calculateBtn, 0, SpringLayout.HORIZONTAL_CENTER, repairDegreeTF);
        springLayout.putConstraint(SpringLayout.NORTH, calculateBtn, 20, SpringLayout.SOUTH, repairDegreeTF);

        getContentPane().add(totalLB);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, totalLB, 0, SpringLayout.HORIZONTAL_CENTER, repairDegreeTF);
        springLayout.putConstraint(SpringLayout.NORTH, totalLB, 20, SpringLayout.SOUTH, calculateBtn);

        setSize(450, 350);
    }
}
