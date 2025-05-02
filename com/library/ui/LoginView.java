// package com.library.ui;

// import com.library.model.Library;
// import com.library.model.User;
// import javafx.geometry.Insets;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.layout.*;
// import javafx.stage.Stage;

// public class LoginView {
//     private VBox view;

//     public LoginView(Stage primaryStage) {
//         view = new VBox(10);
//         view.setPadding(new Insets(20));

//         Label emailLabel = new Label("Email:");
//         TextField emailField = new TextField();

//         Label passwordLabel = new Label("Password:");
//         PasswordField passwordField = new PasswordField();

//         Button loginButton = new Button("Login");

//         Label messageLabel = new Label();

//         loginButton.setOnAction(e -> {
//             String email = emailField.getText();
//             String password = passwordField.getText();

//             User user = Library.getInstance().findUserByEmail(email);

//             if (user != null && user.getPassword().equals(password)) {
//                 if (user.getUserType().equals("ADMIN")) {
//                     AdminView adminView = new AdminView(primaryStage);
//                     primaryStage.setScene(new Scene(adminView.getView(), 800, 600));
//                 } else {
//                     ReaderView readerView = new ReaderView(primaryStage, user);
//                     primaryStage.setScene(new Scene(readerView.getView(), 800, 600));
//                 }
//             } else {
//                 messageLabel.setText("Invalid credentials.");
//             }
//         });

//         view.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton, messageLabel);
//     }

//     public VBox getView() {
//         return view;
//     }
// }

package com.library.ui;

import com.library.model.*;
import com.library.model.Library;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("Library Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        add(loginButton);

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        User user = Library.getInstance().findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            dispose();
            if (user instanceof Admin) {
                new AdminView((Admin) user);
            } else if (user instanceof Reader) {
                new ReaderView((Reader) user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
