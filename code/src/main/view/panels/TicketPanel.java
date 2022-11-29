package view.panels;

import controller.Controller;
import tickets.ExpenseType;
import view.ViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

public class TicketPanel extends JPanel {
    private static JButton addTicketButton;

    private static JList splitTypeList;

    private static JList expenseTypeList;

    private static JList payerList;

    private final Controller controller;

    public TicketPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;

        addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(new AddTicketActionListener());

        String splitType[] = {"Even", "Uneven"};
        splitTypeList = new JList(splitType);

        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
        EnumSet.allOf(ExpenseType.class).forEach(defaultListModel::addElement);
        expenseTypeList = new JList<>(defaultListModel);

        this.add(splitTypeList);
        this.add(expenseTypeList);
        this.add(addTicketButton);
    }

    public static class AddTicketActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Use controller to add ticket
            System.out.println("Placeholder for creating EST");
        }
    }

}
