package ru.nsu.ccfit.Interface;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.Vector;

public class TableModel extends AbstractTableModel {
    private boolean editingEmptyRow = false;
    private Integer emptyRowIndex;
    private final Vector<Vector<Object>> data;
    private final Vector<String> columnNames;
    private final TableConnectionManager tableConnectionManager;

    public TableModel(Vector<Vector<Object>> data, Vector<String> columnNames, TableConnectionManager tcm) {
        this.data = data;
        this.columnNames = columnNames;
        this.tableConnectionManager = tcm;

        data.sort(Comparator.comparingInt(r -> (int) r.get(0)));
        emptyRowIndex = data.size();
        var emptyRow = new Vector<>();
        for (int i = 0; i < columnNames.size(); ++i) {
            emptyRow.add(null);
        }
        data.add(emptyRow);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        for (int row = 0; row < getRowCount(); row++) {
            Object o = getValueAt(row, columnIndex);
            if (o != null) {
                return o.getClass();
            }
        }
        return Object.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (emptyRowIndex.equals(rowIndex) && !editingEmptyRow) {
            editingEmptyRow = true;
        }
        if (!emptyRowIndex.equals(rowIndex)) {
            if (editingEmptyRow) {
                try {
                    tableConnectionManager.addRow(data.get(emptyRowIndex));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            editingEmptyRow = false;
            data.get(rowIndex).set(columnIndex, aValue);
            tableConnectionManager.editCell(rowIndex, columnIndex, aValue);
        } else {
            data.get(rowIndex).set(columnIndex, aValue);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
