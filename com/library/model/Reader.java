package com.library.model;

public class Reader extends User {
    private int maxBooks;
    private int currentLoans;

    public Reader(String id, String name, String email, String password) {
        super(id, name, email, password, "READER");
        this.maxBooks = 3;
        this.currentLoans = 0;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public int getCurrentLoans() {
        return currentLoans;
    }

    public void incrementLoans() {
        this.currentLoans++;
    }

    public void decrementLoans() {
        if (this.currentLoans > 0) {
            this.currentLoans--;
        }
    }

    public boolean canBorrow() {
        return currentLoans < maxBooks;
    }

    @Override
    public String toString() {
        return super.toString() + "," + maxBooks + "," + currentLoans;
    }
}