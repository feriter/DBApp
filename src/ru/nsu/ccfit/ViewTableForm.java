package ru.nsu.ccfit;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ViewTableForm extends Container {
    private String tableName;
    private Vector<String> columnNames;
    private Vector<Vector<Object>> data;
    private Connection connection;

    private JButton addButton;
    private JTable table;

    public ViewTableForm(String tableName, Connection connection) {
        this.tableName = tableName;
        columnNames = new Vector<>();
        data = new Vector<>();
        this.connection = connection;

        setBounds(0,0,800,500);

        String sql = "SELECT * FROM " + tableName + ";";
        try {
            var st = connection.createStatement();
            var result = st.executeQuery(sql);
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
            result.close();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table = new JTable(data, columnNames) {
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);
                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
        var scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 40, 750, 400);
        scrollPane.setVisible(true);
        add(scrollPane);

        var tableNameField = new JTextPane();
        tableNameField.setText(tableName);
        var doc = tableNameField.getStyledDocument();
        var attr = new SimpleAttributeSet();
        StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(attr, 25);
        doc.setParagraphAttributes(0, doc.getLength(), attr, false);
        tableNameField.setBounds(300, 5, 200, 35);
        tableNameField.setEditable(false);
        add(tableNameField);
    }

    public Vector<String> getColumnNames() {
        return columnNames;
    }
}
