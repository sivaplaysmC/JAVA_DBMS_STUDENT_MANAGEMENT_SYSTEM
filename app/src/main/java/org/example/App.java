package org.example;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseHandler dbHandler = new DatabaseHandler();
            new StudentManagementGUI(dbHandler);
        });
    }
}
