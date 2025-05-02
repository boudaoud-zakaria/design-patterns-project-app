package com.library.ui;

import com.library.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReaderView extends JFrame {
    private Reader reader;
    private JTextArea output;

    public ReaderView(Reader reader) {
        this.reader = reader;

        setTitle("Reader Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        output = new JTextArea();
        add(new JScrollPane(output), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton myLoans = new JButton("My Loans");
        JButton availableBooks = new JButton("Available Books");

        myLoans.addActionListener(e -> showLoans());
        availableBooks.addActionListener(e -> showAvailableBooks());

        controlPanel.add(myLoans);
        controlPanel.add(availableBooks);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showLoans() {
        List<Loan> loans = Library.getInstance().getLoansByUser(reader.getId());
        output.setText("My Loans:\n");
        loans.forEach(l -> output.append(l.toString() + "\n"));
    }

    private void showAvailableBooks() {
        List<Book> books = Library.getInstance().getAllBooks();
        output.setText("Available Books:\n");
        books.stream()
                .filter(Book::isAvailable)
                .forEach(b -> output.append(b.toString() + "\n"));
    }
}
