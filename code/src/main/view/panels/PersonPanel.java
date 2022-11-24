package view.panels;

import controller.Controller;
import view.ViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonPanel extends JPanel {
    private static JButton addPersonButton;

    private static JTextArea nameField;

    private final Controller controller;

    public PersonPanel(Controller controller, ViewFrame viewFrame) {
        this.controller = controller;
        addPersonButton = new JButton("Add person");
        addPersonButton.addActionListener(new AddPersonActionListener());
        nameField = new JTextArea(1, 30);
    }

    public static class AddPersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Use controller to create new person
            System.out.println(nameField.getText());
        }
    }
}
