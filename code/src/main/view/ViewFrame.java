package view;

import controller.Controller;
import controller.TicketController;
import view.panels.CalculateTotalPanel;
import view.panels.TicketPanel;
import view.panels.MenuPanel;
import view.panels.PersonPanel;

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

    public void initialize(TicketController ticketController) {
        this.controller = ticketController;
        createPanels();
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(menuPanel);
        this.setVisible(true);
    }

    private void createPanels() {
        personPanel = new PersonPanel(controller, this);
        ticketPanel = new TicketPanel(controller, this);
        calculateTotalPanel = new CalculateTotalPanel(controller, this);
        menuPanel = new MenuPanel(this);
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
            getContentPane().removeAll();
            ticketPanel.reinitialize();
            getContentPane().add(ticketPanel);
            repaint();
            setVisible(true);
        }
    }

    public class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculateTotalPanel.recalculate();
            getContentPane().removeAll();
            getContentPane().add(calculateTotalPanel);
            repaint();
            setVisible(true);
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

    }
}
