package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {
    private String id;
    private String userId;
    private String bookIsbn;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(String id, String userId, String bookIsbn) {
        this.id = id;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusDays(14); // 2 weeks by default
        this.returned = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void returnBook() {
        this.returned = true;
    }

    @Override
    public String toString() {
        return id + "," + userId + "," + bookIsbn + "," + loanDate + "," + returnDate + "," + returned;
    }
}