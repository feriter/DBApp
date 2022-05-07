package ru.nsu.ccfit;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TableListForm extends Container {

    public TableListForm(Vector<String> tableNames) {
        setBounds(100, 0, 250, 580);

        var title = new JTextArea("Table list");
        title.setEditable(false);
        title.setVisible(true);
        title.setBounds(0, 5, 250, 20);
        add(title);

        setVisible(true);
    }
}
