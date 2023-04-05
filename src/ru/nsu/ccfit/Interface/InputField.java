package ru.nsu.ccfit.Interface;

import javax.swing.*;
import java.awt.*;

public class InputField extends Container {
    private String fieldName;
    private JTextField textField;

    public InputField(String fieldName) {
        this.fieldName = fieldName;

        var fieldNameArea = new JTextArea(fieldName);
        fieldNameArea.setBounds(0, 0, 150, 20);
        fieldNameArea.setEditable(false);
        add(fieldNameArea);

        textField = new JTextField("");
        textField.setBounds(160, 0, 200, 20);
        textField.setEditable(true);
        add(textField);

        setVisible(true);
    }

    public String getFieldText() {
        var res = textField.getText();
        return res.isEmpty() ? "null" : res;
    }

    public void setText(String text) {
        this.textField.setText(text);
    }
}
