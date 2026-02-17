package com.example.demo4;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConn {
    public static Connection getConnect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "STUDENT",
                    "123"
            );
        } catch (Exception ex) {
            System.out.println("Connection failed: " + ex.getMessage());
            return null;
        }
    }
}