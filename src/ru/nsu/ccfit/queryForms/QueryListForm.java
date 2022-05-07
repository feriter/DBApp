package ru.nsu.ccfit.queryForms;

import javax.swing.*;
import java.awt.*;

public class QueryListForm extends Container {

    public QueryListForm() {
        setBounds(450, 0, 250, 580);

        var title = new JTextArea("Query list");
        title.setEditable(false);
        title.setVisible(true);
        title.setBounds(0, 5, 250, 20);
        add(title);

        setVisible(true);
    }
}
