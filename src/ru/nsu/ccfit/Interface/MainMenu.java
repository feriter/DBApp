package ru.nsu.ccfit.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;

public class MainMenu extends Container {
    private Connection connection;
    private Vector<String> tableNames;
    private JComboBox comboBox;
    private TableConnectionManager tableConnectionManager;
    private JScrollPane dataField;
    private ParametersPane paramPane;

    public MainMenu() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DBApp",
                    "admin", "rfhnjirf");

            var md = connection.getMetaData();
            tableNames = new Vector<>();
            var rs = md.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        var queryNames = ParameterizedQueries.getQueryNames();
        var allNames = new Vector<String>();
        allNames.addAll(tableNames);
        allNames.addAll(queryNames);
        comboBox = new JComboBox(allNames);
        comboBox.setVisible(true);
        comboBox.setBounds(30, 10, 270, 20);
        comboBox.addActionListener((ActionEvent e) -> {
            if (paramPane != null) {
                remove(paramPane);
            }
            if (dataField != null) {
                remove(dataField);
            }
            JComboBox box = (JComboBox) e.getSource();
            var selected = box.getSelectedIndex();
            if (selected < tableNames.size()) {
                tableConnectionManager = new TableConnectionManager(allNames.get(selected), connection);
                var table = new JTable(tableConnectionManager.getTableModel());
                dataField = new JScrollPane(table);
                dataField.setBounds(30, 40, 710, 510);
                dataField.setVisible(true);
                add(dataField);
            } else {
                paramPane = new ParametersPane();
                add(paramPane);

                var query = selected - tableNames.size() + 1;

                var inputs = new ArrayList<InputField>();
                var paramNames = ParameterizedQueries.getParameterNames(query);
                for (int i = 0; i < paramNames.size(); ++i) {
                    var input = new InputField(paramNames.get(i));
                    input.setBounds(10, 10 + 25 * i, 280, 20);
                    paramPane.add(input);
                    inputs.add(input);
                }

                var submitButton = new JButton("SUBMIT");
                submitButton.setBounds(105, 170, 90, 20);
                paramPane.add(submitButton);
                submitButton.addActionListener((ActionEvent a) -> {
                    var q = ParameterizedQueries.getQueryParts(query);
                    assert(q.size() == inputs.size() + 1):"Incorrect number of parameters";

                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < inputs.size(); ++i) {
                        result.append(q.get(i));
                        result.append(inputs.get(i).getFieldText());
                    }
                    result.append(q.get(inputs.size()));
                    var sql = result.toString();
                    var columnNames = new Vector<String>();
                    var data = new Vector<Vector<Object>>();

                    try {
                        var st = connection.createStatement();
                        var res = st.executeQuery(sql);

                        var columns = res.getMetaData().getColumnCount();

                        for (int i = 0; i < columns; ++i) {
                            columnNames.add(res.getMetaData().getColumnName(i + 1));
                        }

                        while (res.next()) {
                            var row = new Vector<>();
                            for (int i = 0; i < columns; ++i) {
                                row.add(res.getObject(i + 1));
                            }
                            data.add(row);
                        }
                        if (query != 7) {
                            var model = new TableModel(data, columnNames, null);
                            var queryTable = new JTable(model);
                            dataField = new JScrollPane(queryTable);
                            dataField.setBounds(30, 40, 710, 510);
                            dataField.setVisible(true);
                            add(dataField);
                        } else {
                            var root = new MyNode("", "");
                            var chiefs = new Vector<MyNode>();
                            var masters = new Vector<MyNode>();
                            var heads = new Vector<MyNode>();
                            var workers = new Vector<MyNode>();

                            for (var row : data) {
                                var repeat = -1;
                                for (int j = 0; j < chiefs.size(); ++j) {
                                    if (chiefs.get(j).getName().equals(row.get(3))) {
                                        repeat = j;
                                        break;
                                    }
                                }
                                if (repeat == -1) {
                                    var newChief = new MyNode((String)row.get(3), (String)row.get(3));
                                    chiefs.add(newChief);
                                    root.add(newChief);
                                }
                                for (int j = 0; j < masters.size(); ++j) {
                                    if (masters.get(j).getName().equals(row.get(2))) {
                                        repeat = j;
                                        break;
                                    }
                                }
                                if (repeat == -1) {
                                    var newMaster = new MyNode((String)row.get(2), (String)row.get(2));
                                    masters.add(newMaster);
                                    MyNode parent = null;
                                    for (int k = 0; k < chiefs.size(); ++k) {
                                        if (chiefs.get(k).getName().equals(row.get(3))) {
                                            parent = chiefs.get(k);
                                            break;
                                        }
                                    }
                                    if (parent != null) {
                                        parent.add(newMaster);
                                    }
                                }
                                for (int j = 0; j < heads.size(); ++j) {
                                    if (heads.get(j).getName().equals(row.get(1))) {
                                        repeat = j;
                                        break;
                                    }
                                }
                                if (repeat == -1) {
                                    var newHead = new MyNode((String)row.get(1), (String)row.get(1));
                                    heads.add(newHead);
                                    MyNode parent = null;
                                    for (int k = 0; k < masters.size(); ++k) {
                                        if (masters.get(k).toString().equals(row.get(2))) {
                                            parent = masters.get(k);
                                            break;
                                        }
                                    }
                                    if (parent != null) {
                                        parent.add(newHead);
                                    }
                                }
                                for (int j = 0; j < workers.size(); ++j) {
                                    if (workers.get(j).toString().equals(row.get(0))) {
                                        repeat = j;
                                        break;
                                    }
                                }
                                if (repeat == -1) {
                                    var newWorker = new MyNode((String)row.get(0), (String)row.get(0));
                                    workers.add(newWorker);
                                    MyNode parent = null;
                                    for (int k = 0; k < heads.size(); ++k) {
                                        if (heads.get(k).toString().equals(row.get(1))) {
                                            parent = heads.get(k);
                                            break;
                                        }
                                    }
                                    if (parent != null) {
                                        parent.add(newWorker);
                                    }
                                }
                            }

                            var tree = new JTree(root);
                            tree.setBounds(30,40,710,510);
                            tree.setVisible(true);
                            add(tree);
                            repaint();
                        }
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                    remove(paramPane);
                });
                repaint();
            }
        });
        add(comboBox);

        setBounds(0, 0, 800, 600);
        setVisible(true);
    }
}
