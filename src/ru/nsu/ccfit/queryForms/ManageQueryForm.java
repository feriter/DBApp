package ru.nsu.ccfit.queryForms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Vector;

public class ManageQueryForm extends Container {
    private final QueryParametersForm queryParametersForm;

    public ManageQueryForm(Connection connection, int queryNumber) {
        queryParametersForm = new QueryParametersForm(queryNumber);
        add(queryParametersForm);

        var executeButton = new JButton("Execute");
        executeButton.addActionListener((ActionEvent e) -> {
            processQuery(connection);
            queryParametersForm.setVisible(false);
        });
        executeButton.setBounds(200, 520, 100, 30);
        add(executeButton);

        setBounds(0, 0, 800, 600);
        setVisible(false);
    }

    public void processQuery(Connection connection) {
        var sql = queryParametersForm.makeCalledQuery();

        try {
            var st = connection.createStatement();
            var result = st.executeQuery(sql);

            var columnNames = new Vector<String>();
            var data = new Vector<Vector<Object>>();

            var resultMD = result.getMetaData();
            var columns = resultMD.getColumnCount();

            for (int i = 0; i < columns; ++i) {
                columnNames.add(resultMD.getColumnName(i + 1));
            }

            while (result.next()) {
                var row = new Vector<>();
                for (int i = 0; i < columns; ++i) {
                    row.add(result.getObject(i + 1));
                }
                data.add(row);
            }

            var queryResultForm = new QueryResultForm();
            queryResultForm.fill(data, columnNames);
            add(queryResultForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
