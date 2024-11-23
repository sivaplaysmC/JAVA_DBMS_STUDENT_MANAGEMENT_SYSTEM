package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

public class AttendanceManagement extends JFrame implements ActionListener {
    private DatabaseHandler dbHandler;
    private JTable studentTable, attendanceTable;
    private DefaultTableModel studentTableModel, attendanceTableModel;
    private JButton markAttendanceButton, refreshButton, closeButton;

    public AttendanceManagement(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;

        setTitle("Attendance Management");
        setLayout(null);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Attendance Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(350, 10, 300, 30);
        add(title);

        // Student Table
        String[] studentColumns = { "Student Name", "Student ID" };
        studentTableModel = new DefaultTableModel(studentColumns, 0);
        studentTable = new JTable(studentTableModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(50, 60, 400, 400);
        add(studentScrollPane);

        // Attendance Table
        String[] attendanceColumns = { "Date", "Status" };
        attendanceTableModel = new DefaultTableModel(attendanceColumns, 0);
        attendanceTable = new JTable(attendanceTableModel);
        JScrollPane attendanceScrollPane = new JScrollPane(attendanceTable);
        attendanceScrollPane.setBounds(500, 60, 400, 400);
        add(attendanceScrollPane);

        // Buttons
        markAttendanceButton = new JButton("Mark Attendance");
        markAttendanceButton.setBounds(150, 500, 150, 30);
        add(markAttendanceButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(400, 500, 150, 30);
        add(refreshButton);

        closeButton = new JButton("Close");
        closeButton.setBounds(650, 500, 150, 30); // Position it appropriately
        add(closeButton);

        // Action Listeners
        markAttendanceButton.addActionListener(this);
        refreshButton.addActionListener(this);
        closeButton.addActionListener(this);

        loadStudentData();

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadAttendanceData(getSelectedStudentID());
            }
        });

        setVisible(true);
    }

    private void loadStudentData() {
        try {
            studentTableModel.setRowCount(0);
            ResultSet rs = dbHandler.getAllStudents();
            while (rs.next()) {
                studentTableModel.addRow(new Object[] { rs.getString("student_name"), rs.getString("student_id") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAttendanceData(String studentID) {
        try {
            attendanceTableModel.setRowCount(0);
            ResultSet rs = dbHandler.getAttendanceByStudentID(studentID);
            while (rs.next()) {
                attendanceTableModel.addRow(new Object[] { rs.getDate("attendance_date"), rs.getString("status") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedStudentID() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            return studentTableModel.getValueAt(selectedRow, 1).toString();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == markAttendanceButton) {
            String studentID = getSelectedStudentID();
            if (studentID == null) {
                JOptionPane.showMessageDialog(this, "Select a student first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] statuses = { "Present", "Absent", "Late", "Excused" };
            String status = (String) JOptionPane.showInputDialog(this, "Select Attendance Status:", "Mark Attendance",
                    JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

            if (status != null) {
                dbHandler.markAttendance(studentID, new Date(), status);
                loadAttendanceData(studentID);
            }
        }

        if (e.getSource() == refreshButton) {
            loadStudentData();
        }

        if (e.getSource() == closeButton) {
            dispose(); // Close the attendance management window
        }
    }
}
