package com.example.demo4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    // --- FXML Variables (Must match fx:id in SceneBuilder) ---
    @FXML
    private TableView<Borrow> tableBorrow;

    @FXML
    private TableColumn<Borrow, Integer> colLoanId;

    @FXML
    private TableColumn<Borrow, String> colStudentName; // Changed to String for Name

    @FXML
    private TableColumn<Borrow, String> colBookTitle;   // Changed to String for Title

    @FXML
    private TableColumn<Borrow, String> colDate;        // Added Date Column

    // --- Initialization ---
    @FXML
    public void initialize() {
        // 1. Link the columns to the new Borrow Class attributes
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // 2. Load data from Database
        loadData();
    }

    // --- Helper Method to get Data ---
    private void loadData() {
        ObservableList<Borrow> list = FXCollections.observableArrayList();

        Connection conn = dbConn.getConnect();

        if (conn != null) {
            try {
                // JOIN query to get names instead of just IDs
                String sql = "SELECT b.LOAN_ID, s.NAME, k.TITLE, b.BORROW_DATE " +
                        "FROM BORROW b " +
                        "JOIN STUDENTS s ON b.STUDENT_ID = s.STUDENT_ID " +
                        "JOIN BOOKS k ON b.BOOK_ID = k.BOOK_ID";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    // Match the new Borrow constructor
                    list.add(new Borrow(
                            rs.getInt("LOAN_ID"),
                            rs.getString("TITLE"),
                            rs.getString("NAME"),
                            rs.getString("BORROW_DATE")
                    ));
                }

                // 3. Put the list into the TableView
                tableBorrow.setItems(list);

                conn.close();
                System.out.println("Data loaded successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Could not connect to database.");
        }
    }
}