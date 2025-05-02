package com.library.dao;

import com.library.model.*;
// import com.library.pattern.UserFactory;

import java.io.*;
// import java.nio.file.Files;
// import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String BOOKS_FILE = "books.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String LOANS_FILE = "loans.txt";

    public FileManager() {
        // Create files if they don't exist
        createFileIfNotExists(BOOKS_FILE);
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(LOANS_FILE);
    }

    private void createFileIfNotExists(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + fileName);
                e.printStackTrace();
            }
        }
    }

    // Books
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String isbn = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    boolean available = Boolean.parseBoolean(parts[4]);
                    
                    Book book = new Book(isbn, title, author, year);
                    book.setAvailable(available);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books");
            e.printStackTrace();
        }
        
        return books;
    }

    public void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books");
            e.printStackTrace();
        }
    }

    // Users
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String userType = parts[4];
                    
                    User user;
                    if (userType.equals("ADMIN")) {
                        user = new Admin(id, name, email, password);
                    } else {
                        user = new com.library.model.Reader(id, name, email, password);
                        if (parts.length >= 7) {
                            // int maxBooks = Integer.parseInt(parts[5]);
                            int currentLoans = Integer.parseInt(parts[6]);
                            
                            for (int i = 0; i < currentLoans; i++) {
                                ((com.library.model.Reader) user).incrementLoans();
                            }
                        }
                    }
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users");
            e.printStackTrace();
        }
        
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users");
            e.printStackTrace();
        }
    }

    // Loans
    public List<Loan> loadLoans() {
        List<Loan> loans = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(LOANS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String userId = parts[1];
                    String bookIsbn = parts[2];
                    LocalDate loanDate = LocalDate.parse(parts[3]);
                    LocalDate returnDate = LocalDate.parse(parts[4]);
                    boolean returned = Boolean.parseBoolean(parts[5]);
                    
                    Loan loan = new Loan(id, userId, bookIsbn);
                    // We need to set these manually since the constructor sets default values
                    if (returned) {
                        loan.returnBook();
                    }
                    loans.add(loan);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loans");
            e.printStackTrace();
        }
        
        return loans;
    }

    public void saveLoans(List<Loan> loans) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOANS_FILE))) {
            for (Loan loan : loans) {
                writer.write(loan.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving loans");
            e.printStackTrace();
        }
    }
}