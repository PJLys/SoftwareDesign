package view;

import controller.Controller;
import controller.TicketController;
import databases.TicketDB;
import person.Person;
import view.panels.CalculateTotalPanel;
import view.panels.MenuPanel;
import view.panels.PersonPanel;
import view.panels.TicketPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private Controller controller;
    private JPanel personPanel, menuPanel;

    private CalculateTotalPanel calculateTotalPanel;

    private TicketPanel ticketPanel;

    private static DefaultListModel<String> personDLM;

    private int personCounter;

    private int ticketCounter;

    public static DefaultListModel<String> getPersonDLM() {
        return personDLM;
    }

    public void initialize(TicketController ticketController) {
        this.controller = ticketController;
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        personDLM = new DefaultListModel<>();
        personCounter = 0;
        ticketCounter = 0;
        createPanels();
    }

    private void createPanels() {
        personPanel = new PersonPanel(controller, this);
        ticketPanel = new TicketPanel(controller, this);
        calculateTotalPanel = new CalculateTotalPanel(controller, this);
        menuPanel = new MenuPanel(this);
        this.getContentPane().add(menuPanel);
        this.setVisible(true);
    }

    public class PersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(personPanel);
            repaint();
            setVisible(true);
        }
    }

    public class TicketActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (personCounter >= 2) {
                getContentPane().removeAll();
                ticketPanel.reinitialize();
                getContentPane().add(ticketPanel);
                repaint();
                setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(ViewFrame.this, "Add at least 2 persons to the databse before creating a ticket", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ticketCounter >= 1) {
                calculateTotalPanel.recalculate();
                getContentPane().removeAll();
                getContentPane().add(calculateTotalPanel);
                repaint();
                setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(ViewFrame.this, "Add tickets before calculating the total debts", "Error", JOptionPane.WARNING_MESSAGE);

            }
        }
    }

    public class BackActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(menuPanel);
            repaint();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "PersonAdded":
//                ticketPanel.addPersonToDLM((Person) evt.getNewValue());
                Person person = (Person) evt.getNewValue();
                personDLM.addElement(person.getName());
                personCounter++;
                break;
            case "TicketAdded":
                JOptionPane.showMessageDialog(this, "Ticket added", "Action completed", JOptionPane.INFORMATION_MESSAGE);
                ticketCounter++;
                break;
            case "PersonAlreadyExists": JOptionPane.showMessageDialog(this, "This person already exists in the database", "Error", JOptionPane.WARNING_MESSAGE);
            break;
        }
    }
}
