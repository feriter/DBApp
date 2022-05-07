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

    private Vector<InputField> inputFields;

    public InsertIntoTableForm(Vector<String> columnNames, String tableName, Connection connection) {
        this.columnNames = columnNames;
        fieldsData = new Vector<>();
        inputFields = new Vector<>();

        setBounds(50, 50, 350, 300);

        for (int i = 0; i < columnNames.size(); ++i) {
            var field = new InputField(columnNames.get(i));
            if (columnNames.get(i).equals("id")) {
                field.setText("DEFAULT");
            }
            field.setBounds(40,10 + i * 25,400,20);
            inputFields.add(field);
            add(field);
        }
        var submitButton = new JButton("Submit");
        submitButton.addActionListener((ActionEvent e) -> {
            for (var it : inputFields) {
                fieldsData.add(it.getFieldText());
            }
            String insertSQL = "INSERT INTO " + tableName +
                    " VALUES (" + String.join(",", fieldsData) + ");";
            try {
                var st = connection.createStatement();
                var result = st.executeQuery(insertSQL);
                var md = result.getMetaData();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                fieldsData.clear();
            }
        });
        submitButton.setBounds(180,250,100,30);
        add(submitButton);

        setVisible(false);
    }
}
