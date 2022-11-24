package view;

import controller.Controller;
import controller.TicketController;
import view.panels.PersonPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private Controller controller;
    private JPanel personPanel, estPanel, ustPanel, calculateTotalPanel, menuPanel;
    private JButton personButton, estButton, ustButton, calculateTotalButton, addPersonButton, addESTButton, addUSTButton;

    public int initialize(TicketController ticketController) {
        this.controller = ticketController;
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPanels();
        return 0;
    }

    private int createPanels() {
        personPanel = new PersonPanel(controller, this);
        estPanel = new JPanel();
        ustPanel = new JPanel();
        calculateTotalPanel = new JPanel();
        menuPanel = new JPanel();
        return 0;
    }

    public class PersonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(personPanel);
            repaint();
        }
    }

    public class ESTActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(estPanel);
            repaint();
        }
    }

    public class USTActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(ustPanel);
            repaint();
        }
    }

    public class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(calculateTotalPanel);
            repaint();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
