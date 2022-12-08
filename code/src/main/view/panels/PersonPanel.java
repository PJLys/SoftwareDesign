package view.panels;

import controller.Controller;
import view.ViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonPanel extends JPanel {
    private static JButton addPersonButton;

    private static JButton backButton;

    private static JTextField nameField;

    private final Controller controller;

    public PersonPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        addPersonButton = new JButton("Add person");
        addPersonButton.addActionListener(new AddPersonActionListener());
        backButton = new JButton("Back");
        backButton.addActionListener(viewFrame. new BackActionListener());
        nameField = new JTextField( 30);
        this.add(backButton);
        this.add(nameField);
        this.add(addPersonButton);
    }

    private class AddPersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PersonPanel.this.controller.addPerson(nameField.getText());
            nameField.setText("");
        }
    }
}
