CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255),
    student_id VARCHAR(255) UNIQUE,
    student_grade VARCHAR(255),
    dob DATE,
    gender VARCHAR(10),
    contact VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(255),
    attendance_date DATE,
    status ENUM('Present', 'Absent', 'Late', 'Excused'),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
