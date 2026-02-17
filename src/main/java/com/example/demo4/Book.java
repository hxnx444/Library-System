package com.example.demo4;

// Example Book Class structure (in a separate file named Book.java)
public class Book {
    private int id;
    private String title;
    private String author;
    private String category; // <-- REQUIRED FIELD
    private int qty;

    public Book(int id, String title, String author, String category, int qty) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.qty = qty;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; } // <-- REQUIRED GETTER
    public int getQty() { return qty; }
    // ... other methods/setters
}