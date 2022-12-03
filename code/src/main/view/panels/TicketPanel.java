package view.panels;

import controller.Controller;
import tickets.ExpenseType;
import view.DoubleVerifier;
import view.ViewFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;

public class TicketPanel extends JPanel {
    private static JButton backButton;

    private static JButton addTicketButton;

    private static JList<String> splitTypeList;

    private static JList expenseTypeList;

    private static JList<String> payerList;

    private static ArrayList<JList> personListArray;

    private static ArrayList<JTextField> amountArray;

    private static JLabel expenseTypeLabel;

    private static JLabel payerLabel;

    private static JLabel personsLabel;

    private static JLabel debtLabel;

    private String[] personStringList;
    private final Controller controller;

    public TicketPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        setPersonStringList();

        backButton = new JButton("Back");
        backButton.addActionListener(viewFrame. new BackActionListener());

        addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(new AddTicketActionListener());

        String[] splitType = {"Even", "Uneven"};
        splitTypeList = new JList<>(splitType);
        splitTypeList.addListSelectionListener(new SplitTypeSelectionListener());

        expenseTypeLabel = new JLabel("Type of expense");
        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
        EnumSet.allOf(ExpenseType.class).forEach(defaultListModel::addElement);
        expenseTypeList = new JList<>(defaultListModel);
        expenseTypeList.addListSelectionListener(new ExpenseTypeSelectionListener());

        payerLabel = new JLabel("Payer:");
        payerList = new JList<>(personStringList);
        payerList.addListSelectionListener(new PayerSelectionListener());

        personsLabel = new JLabel("Persons:");
        debtLabel = new JLabel("Amount:");

        GridLayout gridLayout = new GridLayout(0, 2);
        this.setLayout(gridLayout);
        this.add(backButton);

        this.add(addTicketButton);

        this.add(new JLabel("How is the ticket split"));
        this.add(splitTypeList);

        this.add(expenseTypeLabel);
        this.add(expenseTypeList);
        this.add(payerLabel);
        this.add(payerList);
    }

    public void reinitialize() {
        setPersonStringList();
        expenseTypeList.clearSelection();
        payerList.clearSelection();
        splitTypeList.clearSelection();

        addTicketButton.setVisible(false);
        expenseTypeLabel.setVisible(false);
        expenseTypeList.setVisible(false);
        payerLabel.setVisible(false);
        payerList.setVisible(false);
        personsLabel.setVisible(false);
        debtLabel.setVisible(false);

        amountArray = new ArrayList<>();
        personListArray = new ArrayList<>();
    }

    private void setPersonStringList() {
        personStringList = new String[]{"Placeholder 1", "Placeholder 2"};
    }

    private void initializeUST() {
        this.add(personsLabel);
        this.add(debtLabel);
        personsLabel.setVisible(true);
        debtLabel.setVisible(true);
        addUSTPersonDebtFields();
    }

    private void addUSTPersonDebtFields() {
        JTextField amountField = new JTextField();
        amountField.setInputVerifier(new DoubleVerifier());
        amountArray.add(amountField);
        JList personList = new JList(personStringList);
        personList.addListSelectionListener(new USTPersonSelectionListener());
        personListArray.add(personList);
        this.add(personList);
        this.add(amountField);
    }

    private static class AddTicketActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Use controller to add ticket
            System.out.println("Placeholder for creating UST");
            System.out.println(splitTypeList.getSelectedValue());
            System.out.println(expenseTypeList.getSelectedValue());
        }
    }

    private static class SplitTypeSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            expenseTypeList.setVisible(true);
            expenseTypeLabel.setVisible(true);
        }
    }

    private static class ExpenseTypeSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            payerList.setVisible(true);
            payerLabel.setVisible(true);
        }
    }

    private class PayerSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) {
                TicketPanel.this.initializeUST();
            }
        }
    }

    private class USTPersonSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                addTicketButton.setVisible(true);
                JList source = (JList) e.getSource();
                if (personListArray.indexOf(source) == personListArray.size() - 1) {
                    TicketPanel.this.addUSTPersonDebtFields();
                    TicketPanel.this.revalidate();
                }
            }
        }
    }
}
