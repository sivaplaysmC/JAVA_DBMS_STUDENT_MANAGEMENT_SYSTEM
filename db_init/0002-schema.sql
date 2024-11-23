INSERT INTO students (student_name, student_id, student_grade, dob, gender, contact, email) VALUES
('Ravi Kumar', 'S001', 'Grade 10', '2005-03-15', 'Male', '9876543210', 'ravikumar@example.com'),
('Priya Sharma', 'S002', 'Grade 10', '2005-06-22', 'Female', '9123456789', 'priyasharma@example.com'),
('Aishwarya Reddy', 'S003', 'Grade 11', '2004-09-10', 'Female', '9988776655', 'aishwarya.reddy@example.com'),
('Arjun Patel', 'S004', 'Grade 12', '2003-12-25', 'Male', '9456781234', 'arjun.patel@example.com'),
('Neha Gupta', 'S005', 'Grade 10', '2005-01-30', 'Female', '9122334455', 'neha.gupta@example.com');

INSERT INTO attendance (student_id, attendance_date, status) VALUES
('S001', '2024-11-01', 'Present'),
('S001', '2024-11-02', 'Absent'),
('S002', '2024-11-01', 'Late'),
('S002', '2024-11-02', 'Present'),
('S003', '2024-11-01', 'Excused'),
('S003', '2024-11-02', 'Present'),
('S004', '2024-11-01', 'Present'),
('S004', '2024-11-02', 'Present'),
('S005', '2024-11-01', 'Absent'),
('S005', '2024-11-02', 'Late');
