package view.panels;

import controller.Controller;
import tickets.ExpenseType;
import view.ViewFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Panel that facilitates adding tickets to the database.
 */
public class TicketPanel extends JPanel {
    private static JButton backButton;

    private static JButton addTicketButton;

    private static JList<String> splitTypeList;

    private static JList<Object> expenseTypeList;

    private static JScrollPane expenseTypeScrollPane;

    private static JList<String> payerList;

    private static JScrollPane payerScrollPane;

    private static JFormattedTextField totalField;

    private static ArrayList<JList<String>> ustPersonListArray;

    private static ArrayList<JScrollPane> ustScrollPaneArray;

    private static ArrayList<JFormattedTextField> ustAmountArray;

    private static JList<String> estPersonList;

    private static JScrollPane estPersonScrollPane;

    private static JLabel expenseTypeLabel;

    private static JLabel payerLabel;

    private static JLabel totalLabel;

    private static JLabel personsLabel;

    private static JLabel debtLabel;

    private final Controller controller;

    public TicketPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;

        backButton = new JButton("Back");
        backButton.addActionListener(viewFrame.new BackActionListener());

        addTicketButton = new JButton("Add Ticket");
        addTicketButton.addActionListener(new AddTicketActionListener());

        String[] splitType = {"Even", "Uneven"};
        splitTypeList = new JList<>(splitType);
        splitTypeList.addListSelectionListener(new SplitTypeSelectionListener());
        splitTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        expenseTypeLabel = new JLabel("Type of expense");
        DefaultListModel<Object> expenseListModel = new DefaultListModel<>();
        EnumSet.allOf(ExpenseType.class).forEach(expenseListModel::addElement);
        expenseTypeList = new JList<>(expenseListModel);
        expenseTypeScrollPane = new JScrollPane(expenseTypeList);
        expenseTypeList.addListSelectionListener(new ExpenseTypeSelectionListener());
        expenseTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        totalLabel = new JLabel("Total amount");

        totalField = new JFormattedTextField(new CustomNumberFormatter());
        totalField.addPropertyChangeListener(new TotalPropertyChangeListener());
        payerLabel = new JLabel("Payer:");
        payerList = new JList<>(ViewFrame.getPersonDLM());
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
    }

    /**
     * Updates the names in the DefaultListModels of the person selection JScrollPanes.
     */
    private static void updateUSTPersonDLM() {
        for (int i = 0; i < ustPersonListArray.size(); i++) {
            DefaultListModel dlm = (DefaultListModel) ustPersonListArray.get(i).getModel();
            // Add all missing names
            for (int j = 0; j < ViewFrame.getPersonDLM().getSize(); j++) {
                if (!dlm.contains(ViewFrame.getPersonDLM().get(j))) {
                    dlm.addElement(ViewFrame.getPersonDLM().get(j));
                }
            }
            // Remove payer's name
            dlm.removeElement(payerList.getSelectedValue());
            // Remove elements selected in higher JLists
            for (int k = 0; k < i; k++) {
                dlm.removeElement(ustPersonListArray.get(k).getSelectedValue());
            }
        }
    }

    /**
     * Reinitializes the panel, after it has been left to go to the menu.
     */
    public void reinitialize() {
        expenseTypeList.clearSelection();
        payerList.clearSelection();
        splitTypeList.clearSelection();

        addTicketButton.setVisible(false);
        expenseTypeLabel.setVisible(false);
        expenseTypeScrollPane.setVisible(false);
        payerLabel.setVisible(false);
        payerScrollPane.setVisible(false);
        personsLabel.setVisible(false);
        debtLabel.setVisible(false);


        if (ustAmountArray != null) {
            ustAmountArray.forEach(this::remove);
        }
        if (ustPersonListArray != null) {
            ustScrollPaneArray.forEach(this::remove);
        }
        Component[] components = this.getComponents();

        totalField.setText("0");
        if (Arrays.asList(components).contains(totalField)) {
            this.remove(totalField);
        }
        if (Arrays.asList(components).contains(totalLabel)) {
            this.remove(totalLabel);
        }

        if (Arrays.asList(components).contains(personsLabel)) {
            this.remove(personsLabel);
        }

        if (estPersonScrollPane != null) {
            if (Arrays.asList(components).contains(estPersonScrollPane)) {
                this.remove(estPersonScrollPane);
            }
        }
        if (Arrays.asList(components).contains(debtLabel)) {
            this.remove(debtLabel);
        }
        ustAmountArray = new ArrayList<>();
        ustPersonListArray = new ArrayList<>();
        ustScrollPaneArray = new ArrayList<>();
    }

    /**
     * Initializes the fields for an uneven split ticket.
     */
    private void initializeUST() {
        this.add(personsLabel);
        this.add(debtLabel);
        personsLabel.setVisible(true);
        debtLabel.setVisible(true);
        addUSTPersonDebtFields();
        updateUSTPersonDLM();
    }

    /**
     * Initializes the fields for an even split ticket.
     */
    private void initializeEST() {
        DefaultListModel<String> estPersonDLM = new DefaultListModel<>();
        for (int i = 0; i < ViewFrame.getPersonDLM().size(); i++) {
            if (!Objects.equals(ViewFrame.getPersonDLM().get(i), payerList.getSelectedValue())) {
                estPersonDLM.addElement(ViewFrame.getPersonDLM().get(i));
            }
        }
        estPersonList = new JList<>(estPersonDLM);
        estPersonList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        estPersonList.addListSelectionListener(new ESTPersonSelectionListener());
        estPersonScrollPane = new JScrollPane(estPersonList);
        this.add(personsLabel);
        this.add(estPersonScrollPane);
        personsLabel.setVisible(true);
        estPersonScrollPane.setVisible(true);
    }

    /**
     * Adds an extra JScrollPane for a person and a JTextField for the corresponding amount. Used for an uneven split ticket.
     */
    private void addUSTPersonDebtFields() {
        JFormattedTextField amountField = new JFormattedTextField(new CustomNumberFormatter());
        amountField.addPropertyChangeListener(new USTAmountPropertyChangeListener());
        amountField.setText("0");
        ustAmountArray.add(amountField);
        // Copy the DefaultListModel
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = 0; i < ViewFrame.getPersonDLM().getSize(); i++) {
            dlm.addElement(ViewFrame.getPersonDLM().getElementAt(i));
        }
        JList<String> personList = new JList<>(dlm);
        personList.addListSelectionListener(new USTPersonSelectionListener());
        personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(personList);
        ustPersonListArray.add(personList);
        ustScrollPaneArray.add(scrollPane);
        this.add(scrollPane);
        this.add(amountField);
    }

    /**
     * Used when the type of ticket gets changed, after further field have been shown. It removes all irrelevant components from the panel.
     */
    public void changeSplitType() {
        Component[] components = this.getComponents();
        if (Arrays.asList(components).contains(personsLabel)) {
            this.remove(personsLabel);
        }

        if (Arrays.asList(components).contains(totalLabel)) {
            this.remove(totalLabel);
            this.remove(totalField);
        }

        if (Arrays.asList(components).contains(estPersonScrollPane)) {
            this.remove(estPersonScrollPane);
            this.initializeUST();
        } else if (!ustScrollPaneArray.isEmpty()) {
            ustScrollPaneArray.forEach(this::remove);
            ustScrollPaneArray.clear();
            ustAmountArray.forEach(this::remove);
            ustAmountArray.clear();
            ustPersonListArray.clear();
            this.remove(debtLabel);
            this.add(totalLabel);
            this.add(totalField);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Checks whether all fields are correctly filled in, to decide if the addTicketButton should get shown.
     *
     * @return A boolean indicating whether addTicketButton should be shown.
     */
    public boolean checkTicketButtonVisibility() {
        Boolean estCheck = (estPersonList != null);
        if (estCheck) {
            estCheck = (splitTypeList.getSelectedIndex() == 0) & !expenseTypeList.isSelectionEmpty() & !payerList.isSelectionEmpty() & ((double) totalField.getValue() > 0) & !estPersonList.isSelectionEmpty();
        }

        Boolean ustCheck = (splitTypeList.getSelectedIndex() == 1) & !expenseTypeList.isSelectionEmpty() & !payerList.isSelectionEmpty() & (ustPersonListArray != null);
        if (ustCheck) {
            ustCheck = ustCheck & (ustPersonListArray.size() >= 1);
        }
        if (ustCheck & (ustAmountArray != null)) {
            ustCheck = ustCheck & (ustAmountArray.size() > 0);
            if (ustCheck) {
                // At least one person should be selected and have a valid amount
                ustCheck = ustCheck & !ustPersonListArray.get(0).isSelectionEmpty() & (double) ustAmountArray.get(0).getValue() > 0;
                // The others should have an amount if a person is selected
                for (int i = 1; i < ustPersonListArray.size(); i++) {
                    if (!ustPersonListArray.get(i).isSelectionEmpty()) {
                        ustCheck = ustCheck & ((double) ustAmountArray.get(i).getValue() > 0);
                    }
                }
            }
        }
        return estCheck | ustCheck;
    }

    /**
     * ActionListener class used for the addTicketButton.
     */
    private class AddTicketActionListener implements ActionListener {
        /**
         * Adds a ticket to the database.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (splitTypeList.getSelectedIndex() == 0) {
                TicketPanel.this.controller.makeEvenSplitTicket((ExpenseType) expenseTypeList.getSelectedValue(), payerList.getSelectedValue(), (double) totalField.getValue(), estPersonList.getSelectedValuesList());
            } else {
                HashMap<String, Double> hashMap = new HashMap<>();
                String name;
                double amount;
                for (int i = 0; i < ustPersonListArray.size(); i++) {
                    if (!ustPersonListArray.get(i).isSelectionEmpty()) {
                        name = ustPersonListArray.get(i).getSelectedValue();
                        amount = (double) ustAmountArray.get(i).getValue();
                        if (hashMap.containsKey(name)) {
                            hashMap.replace(name, hashMap.get(name) + amount);
                        } else {
                            hashMap.put(name, amount);
                        }
                    }
                }
                TicketPanel.this.controller.makeUnevenSplitTicket((ExpenseType) expenseTypeList.getSelectedValue(), payerList.getSelectedValue(), hashMap);
            }
            TicketPanel.this.reinitialize();
            TicketPanel.this.repaint();
            TicketPanel.this.revalidate();
        }
    }

    /**
     * ListSelectionListener used for the splitTypeList.
     */
    private class SplitTypeSelectionListener implements ListSelectionListener {
        /**
         * Displays expenseType fields.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
                if (expenseTypeList.isSelectionEmpty()) {
                    expenseTypeScrollPane.setVisible(true);
                    expenseTypeLabel.setVisible(true);
                } else {
                    changeSplitType();
                }
            }
        }
    }

    /**
     * ListSelectionListener used for the ExpenseTypeList.
     */
    private class ExpenseTypeSelectionListener implements ListSelectionListener {
        /**
         * Displays payer fields.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
                payerScrollPane.setVisible(true);
                payerLabel.setVisible(true);
            }
        }
    }

    /**
     * ListSelectionListener used for the payerList.
     */
    private class PayerSelectionListener implements ListSelectionListener {
        /**
         * Checks whether UST or EST is selected in splitTypeList and adds total fields or initializes UST respectively.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (!Arrays.asList(TicketPanel.this.getComponents()).contains(personsLabel)) {
                    addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
                    if (splitTypeList.getSelectedIndex() == 0) {
                        TicketPanel.this.add(totalLabel);
                        TicketPanel.this.add(totalField);
                        TicketPanel.this.revalidate();
                    } else if (splitTypeList.getSelectedIndex() == 1) {
                        TicketPanel.this.initializeUST();
                    }
                } else if (splitTypeList.getSelectedIndex() == 1) {
                    updateUSTPersonDLM();
                }
            }
        }
    }

    /**
     * PropertyChangeListener used for totalField.
     */
    private class TotalPropertyChangeListener implements PropertyChangeListener {
        /**
         * Initializes the EST selection field, if the value is bigger than 0.
         *
         * @param evt the event to be processed
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField source = (JFormattedTextField) evt.getSource();
            double value = (double) source.getValue();
            Component[] components = TicketPanel.this.getComponents();
            if ((value > 0) & (!Arrays.asList(components).contains(estPersonScrollPane))) {
                TicketPanel.this.initializeEST();
                TicketPanel.this.revalidate();
            }
            addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
        }
    }

    /**
     * PropertyChangeListener used for ust amount fields.
     */
    private class USTAmountPropertyChangeListener implements PropertyChangeListener {
        /**
         * Adds the next person selection field & amount field if needed.
         *
         * @param evt the event to be processed
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JFormattedTextField source = (JFormattedTextField) evt.getSource();
            try {
                double value = (double) source.getValue();
                if ((value > 0) & (ustAmountArray.contains(source))) {
                    if ((ustAmountArray.indexOf(source) == ustAmountArray.size() - 1) & (ustPersonListArray.size() < payerList.getModel().getSize() - 1) & (!ustPersonListArray.get(ustAmountArray.indexOf(source)).isSelectionEmpty())) {
                        TicketPanel.this.addUSTPersonDebtFields();
                        updateUSTPersonDLM();
                        TicketPanel.this.revalidate();
                    }
                    addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
                }
            } catch (NullPointerException ignored) {
            }
            addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
        }
    }

    /**
     * ListSelectionListener used for ustPersonListArray entries.
     */
    private class USTPersonSelectionListener implements ListSelectionListener {
        /**
         * Checks whether to add another person selection field and updates names in them.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<String> source = (JList<String>) e.getSource();
            if (!e.getValueIsAdjusting() & !source.isSelectionEmpty()) {
                if ((ustPersonListArray.indexOf(source) == ustPersonListArray.size() - 1) & (ustPersonListArray.size() < payerList.getModel().getSize() - 1) & ((double) ustAmountArray.get(ustPersonListArray.indexOf(source)).getValue()) > 0) {
                    TicketPanel.this.addUSTPersonDebtFields();
                    TicketPanel.this.revalidate();
                }
            }
            updateUSTPersonDLM();
            addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
        }
    }

    /**
     * ListSelectionListener used for estPersonList.
     */
    private class ESTPersonSelectionListener implements ListSelectionListener {
        /**
         * Displays the addTicketButton, if an item is selected.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                addTicketButton.setVisible(TicketPanel.this.checkTicketButtonVisibility());
            }
        }
    }

    /**
     * Custom implementatin of NumberFormatter, used in the amount field in the GUI.
     */
    public class CustomNumberFormatter extends NumberFormatter {
        public CustomNumberFormatter() {
            super();
            DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRANCE);
            format.applyPattern("###,##0.0#");
            setFormat(format);
            setValueClass(Double.class);
            setAllowsInvalid(false);
            setCommitsOnValidEdit(true);
            setMinimum(0.0);
        }
    }
}
