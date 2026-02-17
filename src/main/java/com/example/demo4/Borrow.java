package com.example.demo4;

public class Borrow {
    private int loanId;
    private String bookTitle;   // Changed from ID to Title
    private String studentName; // Changed from ID to Name
    private String date;        // Added Date

    public Borrow(int loanId, String bookTitle, String studentName, String date) {
        this.loanId = loanId;
        this.bookTitle = bookTitle;
        this.studentName = studentName;
        this.date = date;
    }

    public int getLoanId() { return loanId; }
    public String getBookTitle() { return bookTitle; }
    public String getStudentName() { return studentName; }
    public String getDate() { return date; }
}