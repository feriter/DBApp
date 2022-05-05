package ru.nsu.ccfit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainMenu extends JFrame {
    private int width = 800;
    private int height = 600;
    private Connection connection;

    public MainMenu() {
        super("База данных автопредприятия");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 50, width, height);
        setLayout(null);

        ViewTableForm viewTableForm;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DBApp",
                    "admin", "rfhnjirf");

            viewTableForm = new ViewTableForm("Vehicles", connection);
            add(viewTableForm);

            var addButton = new JButton("Add");
            addButton.addActionListener((ActionEvent e) -> {
                viewTableForm.setVisible(false);
            });
            addButton.setBounds(600,500,120,60);
            //addButton.setVisible(true);
            //add(addButton);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        setVisible(true);
    }
}
