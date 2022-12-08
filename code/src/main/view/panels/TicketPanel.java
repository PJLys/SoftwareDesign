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
import java.util.*;
import java.util.List;

public class TicketPanel extends JPanel {
    private static JButton backButton;

    private static JButton addTicketButton;

    private static JList<String> splitTypeList;

    private static JList<Object> expenseTypeList;

    private static JScrollPane expenseTypeScrollPane;

    private static JList<String> payerList;

    private static JScrollPane payerScrollPane;

    private static JTextField totalField;

    private static ArrayList<JList<String>> ustPersonListArray;

    private static ArrayList<JScrollPane> ustScrollPaneArray;

    private static ArrayList<JTextField> amountArray;

    private static JList<String> estPersonList;

    private static JScrollPane estPersonScrollPane;

    private static JLabel expenseTypeLabel;

    private static JLabel payerLabel;

    private static JLabel totalLabel;

    private static JLabel personsLabel;

    private static JLabel debtLabel;

    private static DefaultListModel<String> personDLM;

    private DefaultListModel<String> payerDLM;
    private final Controller controller;

    public TicketPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        payerDLM = new DefaultListModel<>();
        setPayerDLM();
        personDLM = new DefaultListModel<>();

        backButton = new JButton("Back");
        backButton.addActionListener(viewFrame. new BackActionListener());

        addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(new AddTicketActionListener());

        String[] splitType = {"Even", "Uneven"};
        splitTypeList = new JList<>(splitType);
        splitTypeList.addListSelectionListener(new SplitTypeSelectionListener());
        splitTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        expenseTypeLabel = new JLabel("Type of expense");
        DefaultListModel<Object> expenseListModel = new DefaultListModel<>();
        EnumSet.allOf(ExpenseType.class).forEach(expenseListModel::addElement);
        expenseTypeList = new JList<> (expenseListModel);
        expenseTypeScrollPane = new JScrollPane(expenseTypeList);
        expenseTypeList.addListSelectionListener(new ExpenseTypeSelectionListener());
        expenseTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        totalLabel = new JLabel("Total amount");
        totalField = new JTextField("0");
        totalField.addActionListener(new TotalActionListener());
        totalField.setInputVerifier(new DoubleVerifier());

        payerLabel = new JLabel("Payer:");
        payerList = new JList<>(payerDLM);
        payerList.addListSelectionListener(new PayerSelectionListener());
        payerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        payerScrollPane = new JScrollPane(payerList);

        personsLabel = new JLabel("Persons:");
        debtLabel = new JLabel("Amount:");

        GridLayout gridLayout = new GridLayout(0, 2);
        this.setLayout(gridLayout);
        this.add(backButton);

        this.add(addTicketButton);

        this.add(new JLabel("How is the ticket split"));
        this.add(splitTypeList);

        this.add(expenseTypeLabel);
        this.add(expenseTypeScrollPane);
        this.add(payerLabel);
        this.add(payerScrollPane);
        this.add(totalLabel);
        this.add(totalField);
        this.add(personsLabel);
    }

    public void reinitialize() {
        setPayerDLM();
        expenseTypeList.clearSelection();
        payerList.clearSelection();
        splitTypeList.clearSelection();

        addTicketButton.setVisible(false);
        expenseTypeLabel.setVisible(false);
        expenseTypeScrollPane.setVisible(false);
        payerLabel.setVisible(false);
        payerScrollPane.setVisible(false);
        totalLabel.setVisible(false);
        totalField.setVisible(false);
        personsLabel.setVisible(false);
        debtLabel.setVisible(false);

        if (amountArray != null) {
            amountArray.forEach(this::remove);
        }
        if (ustPersonListArray != null) {
            ustPersonListArray.forEach(this::remove);

        }
        Component[] components = this.getComponents();
        if (estPersonList != null) {
            if (Arrays.asList(components).contains(estPersonList)) {
                this.remove(estPersonList);
            }
        }
        if (Arrays.asList(components).contains(debtLabel)) {
            this.remove(debtLabel);
        }
        amountArray = new ArrayList<>();
        ustPersonListArray = new ArrayList<>();
        ustScrollPaneArray = new ArrayList<>();
    }

    private void setPayerDLM() {
        ArrayList<String> personStringList;
        personStringList = controller.getPersonStringList();
        personStringList.forEach(name -> payerDLM.addElement(name));
    }

    private void initializeUST() {
        this.add(debtLabel);
        personsLabel.setVisible(true);
        debtLabel.setVisible(true);
        addUSTPersonDebtFields();
    }

    private void initializeEST() {
        estPersonList = new JList<>(personDLM);
        estPersonList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        estPersonList.addListSelectionListener(new ESTPersonSelectionListener());
        estPersonScrollPane = new JScrollPane(estPersonList);
        this.add(estPersonScrollPane);
        personsLabel.setVisible(true);
    }

    private void updatePersonDLM() {
        personDLM.removeAllElements();
        for (int i = 0; i < payerDLM.size(); i++) {
            if (!Objects.equals(payerDLM.get(i), payerList.getSelectedValue())) {
                personDLM.addElement(payerDLM.get(i));
            }
        }
    }

    private void addUSTPersonDebtFields() {
        JTextField amountField = new JTextField("0");
        amountField.setInputVerifier(new DoubleVerifier());
        amountArray.add(amountField);
        JList<String> personList = new JList<>(personDLM);
        personList.addListSelectionListener(new USTPersonSelectionListener());
        personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(personList);
        ustPersonListArray.add(personList);
        ustScrollPaneArray.add(scrollPane);
        this.add(scrollPane);
        this.add(amountField);
    }

    public void changeSplitType() {
        personsLabel.setVisible(false);
        debtLabel.setVisible(false);
        addTicketButton.setVisible(false);

        Component[] components = this.getComponents();
        System.out.println(!ustScrollPaneArray.isEmpty());
        if (Arrays.asList(components).contains(estPersonScrollPane)) {
            this.remove(estPersonScrollPane);
            this.initializeUST();
        }
        else if (!ustScrollPaneArray.isEmpty()){
            ustScrollPaneArray.forEach(this::remove);
            amountArray.forEach(this::remove);
            this.remove(debtLabel);
            this.initializeEST();
        }
        this.revalidate();
        this.repaint();
    }

    private class AddTicketActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (splitTypeList.getSelectedIndex() == 0) {
                TicketPanel.this.controller.makeEvenSplitTicket((ExpenseType) expenseTypeList.getSelectedValue(), payerList.getSelectedValue(), Double.parseDouble(totalField.getText()), estPersonList.getSelectedValuesList());
            }
            else {
                HashMap<String, Double> hashMap = new HashMap<>();
                String name;
                Double amount;
                for (int i = 0; i < ustPersonListArray.size(); i++) {
                    if (!ustPersonListArray.get(i).isSelectionEmpty()) {
                        name = ustPersonListArray.get(i).getSelectedValue();
                        amount = Double.valueOf(amountArray.get(i).getText());
                        if (hashMap.containsKey(name)) {
                            amount += hashMap.get(name);
                        }
                        hashMap.put(name, amount);
                    }
                }
                TicketPanel.this.controller.makeUnevenSplitTicket((ExpenseType) expenseTypeList.getSelectedValue(), payerList.getSelectedValue(), hashMap);
            }
        }
    }

    private class SplitTypeSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (expenseTypeList.isSelectionEmpty()) {
                    expenseTypeScrollPane.setVisible(true);
                    expenseTypeLabel.setVisible(true);
                }
                else {
                    changeSplitType();
                }
            }
        }
    }

    private class ExpenseTypeSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
                payerScrollPane.setVisible(true);
                payerLabel.setVisible(true);
                TicketPanel.this.updatePersonDLM();
        }
    }

    private class PayerSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            TicketPanel.this.updatePersonDLM();
            totalField.setVisible(true);
            totalLabel.setVisible(true);
        }
    }

    private class TotalActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField) e.getSource();
            try {
                double value = DoubleVerifier.getDouble(source);
                if (value > 0) {
                    if (splitTypeList.getSelectedIndex() == 0) {
                        TicketPanel.this.initializeEST();
                    }
                    else {
                        TicketPanel.this.initializeUST();
                    }
                }
            }
            catch (NumberFormatException ignored) {
            }
        }
    }

    private class USTPersonSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<String> source = (JList<String>) e.getSource();
            if (!e.getValueIsAdjusting() & !source.isSelectionEmpty()) {
                addTicketButton.setVisible(true);
                if (ustPersonListArray.indexOf(source) == ustPersonListArray.size() - 1) {
                    TicketPanel.this.addUSTPersonDebtFields();
                    TicketPanel.this.revalidate();
                }
            }
        }
    }

    private static class ESTPersonSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<String> source = (JList<String>) e.getSource();
            if (!e.getValueIsAdjusting() & !source.isSelectionEmpty()) {
                addTicketButton.setVisible(true);
            }
        }
    }
}
