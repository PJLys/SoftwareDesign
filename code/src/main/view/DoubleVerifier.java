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
        if (inputOK) {
            double value = getDouble(input);
            if (value >= 0) {
                return true;
            }
        }
        Toolkit.getDefaultToolkit().beep();
        inputField.setText("0");
        return false;
    }

    @Override
    public boolean verify(JComponent input) {
        try {
            getDouble(input);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    public static double getDouble(JComponent input) throws NumberFormatException {
        JTextField inputField = (JTextField) input;
        String text = inputField.getText();
        return Double.parseDouble(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField source = (JTextField) e.getSource();
        shouldYieldFocus(source);
        source.selectAll();
    }
}