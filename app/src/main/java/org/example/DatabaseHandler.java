package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import java.util.Date;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "user";
    private Connection connection;

    // New constructor for initializing with a mock connection (used in unit tests)
    public DatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStudentData(DefaultTableModel tableModel) {
        try {
            String query = "SELECT student_name, student_id, student_grade, DATE_FORMAT(dob, '%d-%m-%Y'), gender, contact, email FROM students";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[] {
                        rs.getString("student_name"),
                        rs.getString("student_id"),
                        rs.getString("student_grade"),
                        rs.getString("DATE_FORMAT(dob, '%d-%m-%Y')"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("email")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStudentData(String name, String id, String grade, String dob, String gender, String contact,
            String email, DefaultTableModel tableModel) {
        try {
            String query = "INSERT INTO students (student_name, student_id, student_grade, dob, gender, contact, email) VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.setString(3, grade);
            stmt.setString(4, dob);
            stmt.setString(5, gender);
            stmt.setString(6, contact);
            stmt.setString(7, email);
            stmt.executeUpdate();

            tableModel.addRow(new Object[] { name, id, grade, dob, gender, contact, email });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentData(String id, DefaultTableModel tableModel, int rowIndex) {
        try {
            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();

            tableModel.removeRow(rowIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAttendanceByStudentID(String studentID) throws SQLException {
        String query = "SELECT attendance_date, status FROM attendance WHERE student_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, studentID);
        return stmt.executeQuery();
    }

    public void markAttendance(String studentID, Date date, String status) {
        try {
            String query = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentID);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setString(3, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllStudents() throws SQLException {
        String query = "SELECT student_name, student_id FROM students";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt.executeQuery();
    }

}
