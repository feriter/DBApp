package ru.nsu.ccfit.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class TableConnectionManager {
    private final String tableName;
    private final Vector<String> columnNames;
    private final Vector<Vector<Object>> data;
    private final Connection connection;

    private final TableModel tableModel;

    public TableConnectionManager(String tableName, Connection connection) {
        this.tableName = tableName;
        columnNames = new Vector<>();
        data = new Vector<>();
        this.connection = connection;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableModel = new TableModel(data, columnNames, this);
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public Vector<String> getColumnNames() {
        return columnNames;
    }

    public void editCell(int row, int column, Object value) {
        String sql = "UPDATE " + tableName +
                " SET " + getTableModel().getColumnName(column) + " = " + value.toString() +
                " WHERE " + getColumnNames().get(0) +
                " = " + getTableModel().getValueAt(row, 0) + ";";
        try {
            var st = connection.createStatement();
            var result = st.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRow(Vector<Object> rowData) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for (int i = 0; i < rowData.size() - 1; ++i) {
            if (rowData.get(i) == null) {
                sql.append("null,");
            } else {
                sql.append(rowData.get(i).toString()).append(",");
            }
        }
        if (rowData.get(rowData.size() - 1) == null) {
            sql.append("null);");
        } else {
            sql.append(rowData.get(rowData.size() - 1)).append(");");
        }
        var st = connection.createStatement();
        var result = st.executeQuery(sql.toString());
    }
}
