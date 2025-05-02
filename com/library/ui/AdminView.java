package com.library.ui;

import com.library.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminView extends JFrame {
    private Admin admin;
    private JTextArea output;

    public AdminView(Admin admin) {
        this.admin = admin;

        setTitle("Admin Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        output = new JTextArea();
        add(new JScrollPane(output), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton viewBooks = new JButton("View Books");
        JButton viewUsers = new JButton("View Users");

        viewBooks.addActionListener(e -> showBooks());
        viewUsers.addActionListener(e -> showUsers());

        controlPanel.add(viewBooks);
        controlPanel.add(viewUsers);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showBooks() {
        List<Book> books = Library.getInstance().getAllBooks();
        output.setText("Books:\n");
        books.forEach(b -> output.append(b.toString() + "\n"));
    }

    private void showUsers() {
        List<User> users = Library.getInstance().getAllUsers();
        output.setText("Users:\n");
        users.forEach(u -> output.append(u.toString() + "\n"));
    }
}
