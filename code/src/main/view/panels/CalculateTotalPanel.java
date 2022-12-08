package view.panels;

import controller.Controller;
import debts.Transaction;
import view.ViewFrame;

import javax.swing.*;

public class CalculateTotalPanel extends JPanel {
    private static JButton backButton;

    private final JList<Transaction> entryJList;

    private final DefaultListModel<Transaction> entryListModel;

    private final Controller controller;

    public CalculateTotalPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        backButton = new JButton("Back");
        backButton.addActionListener(viewFrame. new BackActionListener());

        JLabel label = new JLabel("Debts:");
        entryListModel = new DefaultListModel<>();
        entryJList = new JList<>(entryListModel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(backButton);
        this.add(label);
        this.add(entryJList);
    }

    public void recalculate() {
        entryListModel.removeAllElements();
        controller.calcDebt().forEach(entryListModel::addElement);
    }
}
