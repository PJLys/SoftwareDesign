package view.panels;

import view.ViewFrame;

import javax.swing.*;

public class MenuPanel extends JPanel {
    private JButton personButton;
    private JButton ticketButton;

    private JButton calculateTotalButton;

    public MenuPanel(ViewFrame viewFrame) {
        personButton = new JButton("Add or remove a person");
        personButton.addActionListener(viewFrame. new PersonActionListener());
        ticketButton = new JButton("Create a new ticket");
        ticketButton.addActionListener(viewFrame. new TicketActionListener());
        calculateTotalButton = new JButton("Calculate the total of the trip");
        calculateTotalButton.addActionListener(viewFrame. new CalculateActionListener());
        this.add(personButton);
        this.add(ticketButton);
        this.add(calculateTotalButton);
    }
}
