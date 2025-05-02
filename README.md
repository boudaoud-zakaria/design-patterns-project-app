# 📚 Java Library Management System using (design patterns)

This is a simple **Library Management System** built with **Java Swing**. The app allows two types of users: **Admins** and **Readers**. Admins can manage books and users, while Readers can browse available books.

## 🖥️ Features

### ✅ Admin
- Login using hardcoded credentials (`admin@gmail.com`, `admin123`)
- Add and remove books
- View registered readers

### 📖 Reader
- Login with email and password
- View available books
- Borrow and return books

## 🗂️ Project Structure

- src/
- └── com/
- └── library/
- ├── data/ # Serialized data storage files
- ├── model/ # Core models: Book, User, Admin, Reader
- ├── ui/ # Swing GUI views and main frame
- └── util/ # Utility classes (Constants.java)

## 🚀 How to Run

### 1. Compile:
```bash
javac -d . com/library/ui/MainFrame.java
````
### 2. run:
```bash
java com.library.ui.MainFrame
````

## 💾 Data Storage

- The app uses Java serialization to save books and users in files located in com.library.data.

- On exit, all changes (e.g., added books or registered users) are saved automatically.



