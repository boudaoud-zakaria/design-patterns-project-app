package com.library.model;

import com.library.dao.FileManager;
import com.library.pattern.Observable;
import com.library.pattern.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Library implements Observable {
    private static Library instance;
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;
    private List<Observer> observers;
    private FileManager fileManager;

    private Library() {
        fileManager = new FileManager();
        books = fileManager.loadBooks();
        users = fileManager.loadUsers();
        loans = fileManager.loadLoans();
        observers = new ArrayList<>();
    }

    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    // Book operations
    public void addBook(Book book) {
        books.add(book);
        fileManager.saveBooks(books);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        fileManager.saveBooks(books);
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // User operations
    public void addUser(User user) {
        users.add(user);
        fileManager.saveUsers(users);
    }

    public void removeUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
        fileManager.saveUsers(users);
    }

    public User findUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User findUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Loan operations
    public Loan createLoan(User user, Book book) {
        if (user instanceof Reader && !((Reader) user).canBorrow()) {
            return null;
        }
        
        if (!book.isAvailable()) {
            return null;
        }
        
        String loanId = UUID.randomUUID().toString();
        Loan loan = new Loan(loanId, user.getId(), book.getIsbn());
        loans.add(loan);
        
        // Update user and book status
        if (user instanceof Reader) {
            ((Reader) user).incrementLoans();
        }
        book.setAvailable(false);
        
        // Save changes
        fileManager.saveLoans(loans);
        fileManager.saveUsers(users);
        fileManager.saveBooks(books);
        
        // Notify observers
        notifyObservers("New loan created: " + user.getName() + " borrowed " + book.getTitle());
        
        return loan;
    }

    public boolean returnBook(String loanId) {
        Loan loan = findLoanById(loanId);
        if (loan == null || loan.isReturned()) {
            return false;
        }
        
        loan.returnBook();
        
        // Update book availability
        Book book = findBookByIsbn(loan.getBookIsbn());
        if (book != null) {
            book.setAvailable(true);
        }
        
        // Update user loan count
        User user = findUserById(loan.getUserId());
        if (user instanceof Reader) {
            ((Reader) user).decrementLoans();
        }
        
        // Save changes
        fileManager.saveLoans(loans);
        fileManager.saveUsers(users);
        fileManager.saveBooks(books);
        
        // Notify observers
        if (book != null && user != null) {
            notifyObservers("Book returned: " + user.getName() + " returned " + book.getTitle());
        }
        
        return true;
    }

    public Loan findLoanById(String id) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Loan> getLoansByUser(String userId) {
        return loans.stream()
                .filter(loan -> loan.getUserId().equals(userId) && !loan.isReturned())
                .collect(Collectors.toList());
    }

    public List<Loan> getAllActiveLoans() {
        return loans.stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }

    // Observer pattern methods
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}