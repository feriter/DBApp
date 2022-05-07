package ru.nsu.ccfit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class ManageTableForm extends Container {

    public ManageTableForm(String tableName, Connection connection) {
        var viewForm = new ViewTableForm(tableName, connection);
        add(viewForm);

        var insertForm = new InsertIntoTableForm(viewForm.getColumnNames(), tableName, connection);
        add(insertForm);

        setBounds(0, 0, 800, 550);
        setVisible(false);

        var backButton = new JButton("Table");
        backButton.addActionListener((ActionEvent e) -> {
            viewForm.setVisible(true);
            insertForm.setVisible(false);
        });
        backButton.setBounds(60,250,100,30);
        insertForm.add(backButton);

        var addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> {
            viewForm.setVisible(false);
            insertForm.setVisible(true);
        });
        addButton.setBounds(230,450,100,30);
        viewForm.add(addButton);

    }
}
