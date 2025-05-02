package com.library.ui;

import javax.swing.SwingUtilities;

public class MainFrame {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView();
        });
    }
}
