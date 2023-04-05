package ru.nsu.ccfit.Interface;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private int width = 800;
    private int height = 600;

    public MainWindow() {
        super("База данных автопредприятия");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 50, width, height);
        setBackground(Color.WHITE);
        setLayout(null);

//        var manageMenu = new ManageMenu();
//        add(manageMenu);
        var mainMenu = new MainMenu();
        add(mainMenu);

        setVisible(true);
    }

}
