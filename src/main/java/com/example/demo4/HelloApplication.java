package com.example.demo4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class HelloApplication extends Application {

    private static final double SCENE_WIDTH = 1200;
    private static final double SCENE_HEIGHT = 850;
    private static final double TABLE_WIDTH = 1000;
    private static final double TABLE_HEIGHT = 650;

    private TableView<Book> tableBooks;
    private TableView<Student> tableStudents;
    private TableView<Borrow> tableBorrow;

    private TextField t4, t5, t6, tCategory, t7; // Book fields
    private TextField t8, t9, t10, tEmail; // Student fields

    @Override
    public void start(Stage s) {
        // --- DASHBOARD (g3) ---
        Label l8 = new Label("Dashboard");
        Button b4 = new Button("Manage Books");
        Button b5 = new Button("Manage Students");
        Button b6 = new Button("Borrow Book");
        Button b7 = new Button("Return Book");
        Button bViewBorrows = new Button("View Borrow List");
        Button b8 = new Button("Logout");
        b4.setPrefWidth(250); b5.setPrefWidth(250); b6.setPrefWidth(250);
        b7.setPrefWidth(250); bViewBorrows.setPrefWidth(250); b8.setPrefWidth(250);

        GridPane g3 = new GridPane();
        g3.setHgap(25); g3.setVgap(25); g3.setAlignment(Pos.CENTER);
        g3.add(l8, 0, 0); GridPane.setHalignment(l8, HPos.CENTER);
        g3.add(b4, 0, 1); g3.add(b5, 0, 2); g3.add(b6, 0, 3);
        g3.add(b7, 0, 4); g3.add(bViewBorrows, 0, 5); g3.add(b8, 0, 6);

        // --- MANAGE BOOKS SCREEN (g4) ---
        Label l9 = new Label("Book Inventory");
        t4 = new TextField(); t4.setPromptText("ID (Leave blank for auto)");
        t5 = new TextField(); t5.setPromptText("Title");
        t6 = new TextField(); t6.setPromptText("Author");
        tCategory = new TextField(); tCategory.setPromptText("Category");
        t7 = new TextField(); t7.setPromptText("Qty");

        Button bAddBook = new Button("Add");
        Button bUpdateBook = new Button("Update");
        Button bDeleteBook = new Button("Delete");
        Button bRefreshBooks = new Button("Refresh");
        Button b12 = new Button("Back");

        tableBooks = new TableView<>();
        tableBooks.setPrefHeight(TABLE_HEIGHT); tableBooks.setPrefWidth(TABLE_WIDTH);
        TableColumn<Book, Integer> cId = new TableColumn<>("ID"); cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Book, String> cTitle = new TableColumn<>("Title"); cTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> cAuthor = new TableColumn<>("Author"); cAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<Book, String> cCat = new TableColumn<>("Category"); cCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Book, Integer> cQty = new TableColumn<>("Qty"); cQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tableBooks.getColumns().addAll(cId, cTitle, cAuthor, cCat, cQty);

        // FIX: Binding selection to TextFields
        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                t4.setText(String.valueOf(newVal.getId()));
                t5.setText(newVal.getTitle());
                t6.setText(newVal.getAuthor());
                tCategory.setText(newVal.getCategory());
                t7.setText(String.valueOf(newVal.getQty()));
            }
        });

        GridPane g4 = new GridPane();
        g4.setHgap(20); g4.setVgap(15); g4.setAlignment(Pos.CENTER);
        g4.add(l9, 0, 0, 3, 1);
        g4.add(new Label("ID:"), 0, 1); g4.add(t4, 1, 1, 2, 1);
        g4.add(new Label("Title:"), 0, 2); g4.add(t5, 1, 2, 2, 1);
        g4.add(new Label("Author:"), 0, 3); g4.add(t6, 1, 3, 2, 1);
        g4.add(new Label("Category:"), 0, 4); g4.add(tCategory, 1, 4, 2, 1);
        g4.add(new Label("Qty:"), 0, 5); g4.add(t7, 1, 5, 2, 1);
        g4.add(bAddBook, 0, 6); g4.add(bUpdateBook, 1, 6); g4.add(bDeleteBook, 2, 6);
        g4.add(bRefreshBooks, 0, 7); g4.add(b12, 1, 7);
        g4.add(tableBooks, 0, 8, 3, 1);

        // --- MANAGE STUDENTS SCREEN (g5) ---
        Label l10 = new Label("Student Profiles");
        t8 = new TextField(); t8.setPromptText("Student ID");
        t9 = new TextField(); t9.setPromptText("Name");
        t10 = new TextField(); t10.setPromptText("Phone");
        tEmail = new TextField(); tEmail.setPromptText("Email");

        Button bAddStudent = new Button("Add");
        Button bUpdateStudent = new Button("Update");
        Button bDeleteStudent = new Button("Delete");
        Button bRefreshStudents = new Button("Refresh");
        Button b20 = new Button("Back");

        tableStudents = new TableView<>();
        tableStudents.setPrefHeight(TABLE_HEIGHT); tableStudents.setPrefWidth(TABLE_WIDTH);
        TableColumn<Student, Integer> cSId = new TableColumn<>("ID"); cSId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> cSName = new TableColumn<>("Name"); cSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> cSPhone = new TableColumn<>("Phone"); cSPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Student, String> cSEmail = new TableColumn<>("Email"); cSEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableStudents.getColumns().addAll(cSId, cSName, cSPhone, cSEmail);

        // FIX: Binding selection to TextFields
        tableStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                t8.setText(String.valueOf(newVal.getId()));
                t9.setText(newVal.getName());
                t10.setText(newVal.getPhone());
                tEmail.setText(newVal.getEmail());
            }
        });

        GridPane g5 = new GridPane();
        g5.setHgap(20); g5.setVgap(15); g5.setAlignment(Pos.CENTER);
        g5.add(l10, 0, 0, 3, 1);
        g5.add(new Label("ID:"), 0, 1); g5.add(t8, 1, 1, 2, 1);
        g5.add(new Label("Name:"), 0, 2); g5.add(t9, 1, 2, 2, 1);
        g5.add(new Label("Phone:"), 0, 3); g5.add(t10, 1, 3, 2, 1);
        g5.add(new Label("Email:"), 0, 4); g5.add(tEmail, 1, 4, 2, 1);
        g5.add(bAddStudent, 0, 5); g5.add(bUpdateStudent, 1, 5); g5.add(bDeleteStudent, 2, 5);
        g5.add(bRefreshStudents, 0, 6); g5.add(b20, 1, 6);
        g5.add(tableStudents, 0, 7, 3, 1);

        // --- BORROW/RETURN/VIEW (Summarized from your logic) ---
        ComboBox<String> cbStudentBorrow = new ComboBox<>();
        ComboBox<String> cbBookBorrow = new ComboBox<>();
        DatePicker dBorrow = new DatePicker(LocalDate.now());
        Button bConfirmBorrow = new Button("Confirm");
        Button bBackBorrow = new Button("Back");
        GridPane g6 = new GridPane(); g6.setHgap(25); g6.setVgap(25); g6.setAlignment(Pos.CENTER);
        g6.add(new Label("Borrow Book"), 0, 0, 2, 1);
        g6.add(new Label("Student:"), 0, 1); g6.add(cbStudentBorrow, 1, 1);
        g6.add(new Label("Book:"), 0, 2); g6.add(cbBookBorrow, 1, 2);
        g6.add(new Label("Date:"), 0, 3); g6.add(dBorrow, 1, 3);
        g6.add(bBackBorrow, 0, 5); g6.add(bConfirmBorrow, 1, 5);

        // --- SCENES & STYLING ---
        Scene sc3 = new Scene(g3, SCENE_WIDTH, SCENE_HEIGHT);
        Scene sc4 = new Scene(new StackPane(g4), SCENE_WIDTH, SCENE_HEIGHT);
        Scene sc5 = new Scene(new StackPane(g5), SCENE_WIDTH, SCENE_HEIGHT);
        Scene sc6 = new Scene(g6, SCENE_WIDTH, SCENE_HEIGHT);

        try {
            String css = getClass().getResource("style.css").toExternalForm();
            sc3.getStylesheets().add(css); sc4.getStylesheets().add(css);
            sc5.getStylesheets().add(css); sc6.getStylesheets().add(css);
            l8.getStyleClass().add("title-label"); l9.getStyleClass().add("title-label");
        } catch (Exception e) { System.out.println("CSS not found."); }

        // --- HANDLERS ---
        b4.setOnAction(e -> { s.setScene(sc4); refreshBookTable(); });
        b5.setOnAction(e -> { s.setScene(sc5); refreshStudentTable(); });
        b6.setOnAction(e -> {
            s.setScene(sc6);
            populateCombo(cbStudentBorrow, "SELECT STUDENT_ID, NAME FROM STUDENTS", "STUDENT_ID", "NAME");
            populateCombo(cbBookBorrow, "SELECT BOOK_ID, TITLE FROM BOOKS", "BOOK_ID", "TITLE");
        });
        b12.setOnAction(e -> s.setScene(sc3));
        b20.setOnAction(e -> s.setScene(sc3));
        bBackBorrow.setOnAction(e -> s.setScene(sc3));

        bAddBook.setOnAction(e -> {
            int id = t4.getText().isEmpty() ? new Random().nextInt(90000)+10000 : Integer.parseInt(t4.getText());
            executeSQL("INSERT INTO BOOKS VALUES ("+id+", '"+t5.getText()+"', '"+t6.getText()+"', '"+tCategory.getText()+"', "+t7.getText()+")");
            refreshBookTable();
        });

        bUpdateBook.setOnAction(e -> {
            executeSQL("UPDATE BOOKS SET TITLE='"+t5.getText()+"', AUTHOR='"+t6.getText()+"', CATEGORY='"+tCategory.getText()+"', QTY="+t7.getText()+" WHERE BOOK_ID="+t4.getText());
            refreshBookTable();
        });

        bDeleteBook.setOnAction(e -> {
            executeSQL("DELETE FROM BOOKS WHERE BOOK_ID="+t4.getText());
            refreshBookTable();
        });

        bConfirmBorrow.setOnAction(e -> {
            int stdId = Integer.parseInt(cbStudentBorrow.getValue().split(" - ")[0]);
            int bkId = Integer.parseInt(cbBookBorrow.getValue().split(" - ")[0]);
            String date = dBorrow.getValue().toString();
            executeSQL("INSERT INTO BORROW VALUES ("+(new Random().nextInt(9000))+", "+bkId+", "+stdId+", TO_DATE('"+date+"', 'YYYY-MM-DD'))");
            showAlert("Success", "Book Borrowed!");
        });

        // --- FINAL SETUP ---
        s.setTitle("Library System");
        s.setScene(sc3); // FIX: Set the initial scene
        s.show();
    }

    private void refreshBookTable() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        try (Connection conn = dbConn.getConnect(); ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM BOOKS")) {
            while (rs.next()) list.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            tableBooks.setItems(list);
        } catch (Exception ex) { showAlert("Error", ex.getMessage()); }
    }

    private void refreshStudentTable() {
        ObservableList<Student> list = FXCollections.observableArrayList();
        try (Connection conn = dbConn.getConnect(); ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM STUDENTS")) {
            while (rs.next()) list.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            tableStudents.setItems(list);
        } catch (Exception ex) { showAlert("Error", ex.getMessage()); }
    }

    private void populateCombo(ComboBox<String> box, String sql, String idCol, String nameCol) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection conn = dbConn.getConnect(); ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) list.add(rs.getInt(idCol) + " - " + rs.getString(nameCol));
            box.setItems(list);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void executeSQL(String sql) {
        try (Connection conn = dbConn.getConnect(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception ex) { showAlert("Error", ex.getMessage()); }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(title); a.setContentText(msg); a.show();
    }

    public static void main(String[] args) { launch(); }
}