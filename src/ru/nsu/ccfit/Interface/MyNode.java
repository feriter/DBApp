package ru.nsu.ccfit.Interface;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyNode extends DefaultMutableTreeNode {
    private String name;

    public MyNode(String name1, String name2) {
        super(name1);
        name = name2;
    }

    public String getName() {
        return name;
    }
}
