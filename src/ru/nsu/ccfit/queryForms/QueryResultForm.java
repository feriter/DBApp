package ru.nsu.ccfit.queryForms;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class QueryResultForm extends Container {

    public QueryResultForm() {
        setBounds(0, 0, 800, 500);
        setVisible(false);
    }

    public void fill(Vector<Vector<Object>> data, Vector<String> columnNames) {
        var table = new JTable(data, columnNames) {
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
        add(scrollPane);
        scrollPane.setBounds(20, 20, 760, 460);
        setVisible(true);
    }
}
