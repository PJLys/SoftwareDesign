package view.panels;

import controller.Controller;
import view.ViewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel that facilitates adding and removing persons to the database.
 */
public class PersonPanel extends JPanel {
    private final JButton addPersonButton;

    private final JButton removePersonButton;

    private final JButton backButton;

    private final JList<String> nameList;

    private final JLabel nameLabel;

    private final JLabel listLabel;

    private final JTextField nameField;

    private final Controller controller;

    private final ViewFrame viewFrame;

    public PersonPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        this.viewFrame = viewFrame;
        this.addPersonButton = new JButton("Add person");
        this.addPersonButton.addActionListener(new AddPersonActionListener());
        this.removePersonButton = new JButton("Remove person");
        this.removePersonButton.addActionListener(new RemovePersonActionListener());
        this.backButton = new JButton("Back");
        this.backButton.addActionListener(viewFrame.new BackActionListener());
        this.nameField = new JTextField(30);
        this.nameLabel = new JLabel("Name:");
        this.nameList = new JList(ViewFrame.getPersonDLM());
        this.nameList.setEnabled(false);
        this.listLabel = new JLabel("Persons in database");
        GridLayout gridLayout = new GridLayout(0, 2);
        this.setLayout(gridLayout);
        this.add(this.backButton);
        this.add(new JLabel(""));
        this.add(nameLabel);
        this.add(this.nameField);
        this.add(this.addPersonButton);
        this.add(this.removePersonButton);
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(listLabel);
        this.add(this.nameList);
    }


    /**
     * ActionListener used for the add person button.
     */
    private class AddPersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PersonPanel.this.controller.addPerson(PersonPanel.this.nameField.getText());
            PersonPanel.this.nameField.setText("");
        }
    }

    /**
     * ActionListener used for the remove person button.
     */
    private class RemovePersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.isPersonUsed(PersonPanel.this.nameField.getText())) {
                JOptionPane.showMessageDialog(PersonPanel.this.viewFrame, "Cannot remove person because he/she is mentioned in a ticket.", "Error", JOptionPane.WARNING_MESSAGE);
                PersonPanel.this.nameField.setText("");
            }
            else {
                if (PersonPanel.this.controller.removePerson(PersonPanel.this.nameField.getText()) == -1) {
                    JOptionPane.showMessageDialog(PersonPanel.this.viewFrame, "Person does not exit", "Error", JOptionPane.ERROR_MESSAGE);};
                PersonPanel.this.nameField.setText("");
            }
        }
    }
}