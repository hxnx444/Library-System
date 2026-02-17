package com.example.demo4;

public class Student {
    private int id;
    private String name;
    private String phone;
    private String email;

    public Student(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // --- CRITICAL: These Getters allow the Table to "see" the data ---
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}