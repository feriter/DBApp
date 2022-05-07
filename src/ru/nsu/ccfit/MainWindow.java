package ru.nsu.ccfit;

import ru.nsu.ccfit.queryForms.ParameterizedQueries;
import ru.nsu.ccfit.queryForms.QueryListForm;
import ru.nsu.ccfit.queryForms.QueryParametersForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

public class MainWindow extends JFrame {
    private int width = 800;
    private int height = 600;

    public MainWindow() {
        super("База данных автопредприятия");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 50, width, height);
        setLayout(null);

        var manageMenu = new ManageMenu();
        add(manageMenu);

        setVisible(true);
    }

}
