package view;

import javax.swing.*;
import java.awt.*;

public class EvaluatePropertyWindow extends JFrame {
    private String[] buildingTypes = {"Stock", "Flat"};
    private JComboBox<String> buildingTypeJCB = new JComboBox<>(buildingTypes);
    private JTextField yearProfitTF = new JTextField();
    private JTextField buildCostsTF = new JTextField();
    private JTextField operatingCostsTF = new JTextField();
    private JTextField capitalizationPercentTF = new JTextField();

    EvaluatePropertyWindow() {
        super("Evaluate Property");
        initWindow();

    }

    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(buildingTypeJCB);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buildingTypeJCB, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, buildingTypeJCB, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(yearProfitTF);
        yearProfitTF.setPreferredSize(new Dimension(200,20));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, yearProfitTF, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, yearProfitTF, 20, SpringLayout.SOUTH, buildingTypeJCB);

        getContentPane().add(buildCostsTF);
        buildCostsTF.setPreferredSize(new Dimension(200,20));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buildCostsTF, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, buildCostsTF, 20, SpringLayout.SOUTH, yearProfitTF);

        getContentPane().add(operatingCostsTF);
        operatingCostsTF.setPreferredSize(new Dimension(200,20));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, operatingCostsTF, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, operatingCostsTF, 20, SpringLayout.SOUTH, buildCostsTF);

        getContentPane().add(capitalizationPercentTF);
        capitalizationPercentTF.setPreferredSize(new Dimension(200,20));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, capitalizationPercentTF, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, capitalizationPercentTF, 20, SpringLayout.SOUTH, operatingCostsTF);

        setSize(300, 300);
    }

}
