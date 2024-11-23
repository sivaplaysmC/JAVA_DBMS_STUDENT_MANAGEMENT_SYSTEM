package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementGUI extends JFrame implements ActionListener {
    private JLabel jtitle, studentName, studentID, studentGrade, dobLabel, genderLabel, contactLabel, emailLabel;
    private JTextField jstudentName, jstudentID, jstudentGrade, dobField, contactField, emailField, searchField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JButton addStudent, reset, deleteRecord, searchButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private DatabaseHandler dbHandler;

    public StudentManagementGUI(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
        setTitle("Student Management System by Group 5");
        setLayout(null);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title
        jtitle = new JLabel("STUDENT MANAGEMENT SYSTEM");
        jtitle.setBounds(250, 10, 700, 50);
        jtitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));

        // Form Labels
        studentName = new JLabel("Student Name");
        studentID = new JLabel("Student ID");
        studentGrade = new JLabel("Student Grade");
        dobLabel = new JLabel("Date of Birth");
        genderLabel = new JLabel("Gender");
        contactLabel = new JLabel("Contact Number");
        emailLabel = new JLabel("Email");

        studentName.setBounds(50, 80, 150, 30);
        studentID.setBounds(50, 120, 150, 30);
        studentGrade.setBounds(50, 160, 150, 30);
        dobLabel.setBounds(50, 200, 150, 30);
        genderLabel.setBounds(50, 240, 150, 30);
        contactLabel.setBounds(50, 280, 150, 30);
        emailLabel.setBounds(50, 320, 150, 30);

        // Form Fields
        jstudentName = new JTextField();
        jstudentID = new JTextField();
        jstudentGrade = new JTextField();
        dobField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();

        jstudentName.setBounds(200, 80, 200, 30);
        jstudentID.setBounds(200, 120, 200, 30);
        jstudentGrade.setBounds(200, 160, 200, 30);
        dobField.setBounds(200, 200, 200, 30);
        contactField.setBounds(200, 280, 200, 30);
        emailField.setBounds(200, 320, 200, 30);

        // Gender Radio Buttons
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        maleRadio.setBounds(200, 240, 80, 30);
        femaleRadio.setBounds(290, 240, 100, 30);
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        // Buttons
        addStudent = new JButton("Add Student");
        reset = new JButton("Reset");
        deleteRecord = new JButton("Delete Record");
        searchField = new JTextField();
        searchButton = new JButton("Search by ID");

        addStudent.setBounds(650, 150, 150, 30);
        reset.setBounds(650, 200, 150, 30);
        deleteRecord.setBounds(650, 250, 150, 30);
        searchField.setBounds(50, 360, 300, 30);
        searchButton.setBounds(360, 360, 150, 30);

        // Table
        String[] columnNames = { "Student Name", "Student ID", "Student Grade", "Date of Birth", "Gender",
                "Contact Number", "Email" };
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 400, 860, 150);

        // Add Components
        add(jtitle);
        add(studentName);
        add(studentID);
        add(studentGrade);
        add(dobLabel);
        add(genderLabel);
        add(contactLabel);
        add(emailLabel);
        add(jstudentName);
        add(jstudentID);
        add(jstudentGrade);
        add(dobField);
        add(maleRadio);
        add(femaleRadio);
        add(contactField);
        add(emailField);
        add(addStudent);
        add(reset);
        add(deleteRecord);
        add(searchField);
        add(searchButton);
        add(scrollPane);

        // Button Listeners
        addStudent.addActionListener(this);
        reset.addActionListener(this);
        deleteRecord.addActionListener(this);
        searchButton.addActionListener(this);

        // Load Initial Data
        dbHandler.loadStudentData(tableModel);

        // Inside the constructor of StudentManagementSystem
        JButton attendanceButton = new JButton("Manage Attendance");
        attendanceButton.setBounds(650, 300, 150, 30);
        add(attendanceButton);

        // Add ActionListener for the Attendance button
        attendanceButton.addActionListener(e -> {
            new AttendanceManagement(this.dbHandler); // Pass the existing connection to
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudent) {
            // Add Student
            String name = jstudentName.getText();
            String id = jstudentID.getText();
            String grade = jstudentGrade.getText();
            String dob = dobField.getText();
            String contact = contactField.getText();
            String email = emailField.getText();
            String gender = maleRadio.isSelected() ? "Male" : "Female";

            dbHandler.insertStudentData(name, id, grade, dob, gender, contact, email, tableModel);

        } else if (e.getSource() == reset) {
            // Reset Form
            jstudentName.setText("");
            jstudentID.setText("");
            jstudentGrade.setText("");
            dobField.setText("");
            genderGroup.clearSelection();
            contactField.setText("");
            emailField.setText("");

        } else if (e.getSource() == deleteRecord) {
            // Delete Student
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String studentID = tableModel.getValueAt(selectedRow, 1).toString();
                dbHandler.deleteStudentData(studentID, tableModel, selectedRow);
            }

        } else if (e.getSource() == searchButton) {
            // Search by ID
            String searchId = searchField.getText();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                if (tableModel.getValueAt(row, 1).equals(searchId)) {
                    studentTable.setRowSelectionInterval(row, row);
                    break;
                }
            }
        }
    }
}
