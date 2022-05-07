package ru.nsu.ccfit;

import ru.nsu.ccfit.queryForms.ManageQueryForm;
import ru.nsu.ccfit.queryForms.QueryListForm;
import ru.nsu.ccfit.queryForms.QueryParametersForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

public class ManageMenu extends Container {
    private Connection connection;
    private ManageTableForm currentTableForm;
    private ManageQueryForm currentQueryForm;
    private TableListForm tableListForm;
    private QueryListForm queryListForm;

    public ManageMenu() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DBApp",
                    "admin", "rfhnjirf");
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        setupTableListForm();
        setupQueryListForm();

        setBounds(0, 0, 800, 600);
        setVisible(true);
    }

    public void setupTableListForm() {
        try {
            var md = connection.getMetaData();
            var tableNames = new Vector<String>();
            var rs = md.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }

            tableListForm = new TableListForm(tableNames);
            add(tableListForm);

            var backButton = new JButton("Main menu");
            backButton.addActionListener((ActionEvent e) -> {
                currentTableForm.setVisible(false);
                tableListForm.setVisible(true);
                queryListForm.setVisible(true);
                currentTableForm.remove(backButton);
            });
            backButton.setBounds(110,450,100,30);

            for (int i = 0; i < tableNames.size(); ++i) {
                var button = new JButton(tableNames.get(i));
                button.setBounds(10, 30 + i * 25, 230, 22);

                button.addActionListener((ActionEvent e) -> {
                    var manageTableForm = new ManageTableForm(button.getText(), connection);
                    currentTableForm = manageTableForm;
                    add(manageTableForm);
                    manageTableForm.add(backButton);
                    queryListForm.setVisible(false);
                    tableListForm.setVisible(false);
                    manageTableForm.setVisible(true);
                });
                tableListForm.add(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupQueryListForm() {
        queryListForm = new QueryListForm();
        add(queryListForm);

        var backButton = new JButton("Main menu");
        backButton.addActionListener((ActionEvent e) -> {
            currentQueryForm.setVisible(false);
            tableListForm.setVisible(true);
            queryListForm.setVisible(true);
            currentQueryForm.remove(backButton);
        });
        backButton.setBounds(500,520,100,30);

        for (int i = 1; i <= 14; ++i) {
            var button = new JButton("Query " + i);
            button.setBounds(10, 5 + i * 25, 230, 22);

            int finalI = i;
            button.addActionListener((ActionEvent e) -> {
                var manageQueryForm = new ManageQueryForm(connection, finalI);
                currentQueryForm = manageQueryForm;
                add(manageQueryForm);
                manageQueryForm.add(backButton);
                queryListForm.setVisible(false);
                tableListForm.setVisible(false);
                manageQueryForm.setVisible(true);
            });
            queryListForm.add(button);
        }

    }
}
