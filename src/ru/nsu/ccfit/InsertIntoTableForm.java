package ru.nsu.ccfit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class InsertIntoTableForm extends Container {
    private Vector<String> columnNames;
    private Vector<String> fieldsData;

    private Vector<JTextField> inputFields;
    private JButton submitButton;

    public InsertIntoTableForm(Vector<String> columnNames, String tableName, Connection connection) {
        this.columnNames = columnNames;
        fieldsData = new Vector<>();
        inputFields = new Vector<>();

        var pane = new JPanel();
        pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.setVisible(true);
        add(pane);

        for (int i = 0; i < columnNames.size(); ++i) {
            var field = new JTextField(columnNames.get(i));
            field.setBounds(80,50 + i * 70,200,50);
            inputFields.add(field);
            pane.add(field);
        }
        submitButton = new JButton("Submit");
        submitButton.addActionListener((ActionEvent e) -> {
            for (var it : inputFields) {
                fieldsData.add(it.getText());
            }
            String insertSQL = "INSERT INTO " + tableName +
                    " VALUES (" + String.join(",", fieldsData) + ");";
            try {
                var st = connection.createStatement();
                var result = st.executeQuery(insertSQL);
                var md = result.getMetaData();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        submitButton.setBounds(0,0,500,200);
        pane.add(submitButton);
    }
}
