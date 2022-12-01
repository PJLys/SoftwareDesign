package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoubleVerifier extends InputVerifier implements ActionListener {
    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean inputOK = verify(input);
        JTextField inputField = (JTextField) input;
        if (!inputOK) {
            Toolkit.getDefaultToolkit().beep();
            inputField.setText("");
        }
        return inputOK;
    }

    @Override
    public boolean verify(JComponent input) {
        return checkField(input);
    }

    private boolean checkField(JComponent input) {
        try {
            JTextField inputField = (JTextField) input;
            double x = Double.parseDouble(inputField.getText());
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField source = (JTextField) e.getSource();
        shouldYieldFocus(source);
        source.selectAll();
    }
}