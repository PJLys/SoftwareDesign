package view.panels;

import controller.Controller;
import tickets.ExpenseType;
import view.ViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

public class ESTPanel extends JPanel {
    private static JButton addTicketButton;

    private static JList typeList;

    private static JList payerList;

    private final Controller controller;

    public ESTPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(new AddTicketActionListener());
        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
        EnumSet.allOf(ExpenseType.class).forEach(defaultListModel::addElement);
        typeList = new JList<>(defaultListModel);
        this.add(addTicketButton);
        this.add(typeList);

    }

    public static class AddTicketActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Use controller to add ticket
            System.out.println("Placeholder for creating EST");
        }
    }

}
