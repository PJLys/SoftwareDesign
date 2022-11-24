package view.panels;

import view.ViewFrame;

import javax.swing.*;

public class MenuPanel extends JPanel {
    private JButton personButton;
    private JButton estButton;
    private JButton ustButton;
    private JButton calculateTotalButton;

    public MenuPanel(ViewFrame viewFrame) {
        personButton = new JButton("Add a new person");
        personButton.addActionListener(viewFrame. new PersonActionListener());
        estButton = new JButton("Create a new even split ticket");
        estButton.addActionListener(viewFrame. new ESTActionListener());
        ustButton = new JButton("Create a new uneven split ticket");
        ustButton.addActionListener(viewFrame. new USTActionListener());
        calculateTotalButton = new JButton("Calculate the total of the trip");
        calculateTotalButton.addActionListener(viewFrame. new CalculateActionListener());
    }
}
