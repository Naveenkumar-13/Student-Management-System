package com.gqt.core_java_mini_project2;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

// ========== MODEL CLASSES ==========
class Student{
    private String id;
    private String name;
    private String email;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { 
    	return id; 
    	}
    public String getName() { 
    	return name; 
    	}
    public String getEmail() { 
    	return email; 
    	}

    public void enrollCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            JOptionPane.showMessageDialog(null, "✅ Enrolled in " + course.getName());
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ Already enrolled in this course.");
        }
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public String toString() {
        return id + " - " + name + " (" + email + ")";
    }
}

class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public String toString() {
        return name;
    }
}

class Database {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();
}

class Admin {
    public void addStudent(String id, String name, String email) {
        Student student = new Student(id, name, email);
        Database.students.add(student);
        JOptionPane.showMessageDialog(null, "✅ Student added: " + student.getName());
    }

    public void addCourse(String name) {
        Course course = new Course(name);
        Database.courses.add(course);
        JOptionPane.showMessageDialog(null, "📘 Course added: " + course.getName());
    }
}

// ========== MAIN FRAME ==========
public class StudentManagementSystem extends JFrame {
    public StudentManagementSystem() {
        setTitle("Student Management System - Role Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("Select Your Role:");
        label.setBounds(130, 30, 200, 25);
        add(label);

        JButton adminBtn = new JButton("Admin");
        adminBtn.setBounds(50, 80, 100, 30);
        JButton professorBtn = new JButton("Professor");
        professorBtn.setBounds(150, 80, 100, 30);
        JButton studentBtn = new JButton("Student");
        studentBtn.setBounds(250, 80, 100, 30);

        add(adminBtn); add(professorBtn); add(studentBtn);

        adminBtn.addActionListener(e -> new LoginDialog(this, "Admin"));
        professorBtn.addActionListener(e -> new LoginDialog(this, "Professor"));
        studentBtn.addActionListener(e -> new LoginDialog(this, "Student"));

        setVisible(true);
    }

    public static void main(String[] args) {
        if (Database.courses.isEmpty()) {
            Database.courses.add(new Course("Java"));
            Database.courses.add(new Course("Python"));
            Database.courses.add(new Course("DBMS"));
            Database.courses.add(new Course("Web"));
        }
        new StudentManagementSystem();
    }
}

// ========== LOGIN DIALOG ==========
class LoginDialog extends JDialog {
    public LoginDialog(JFrame parent, String role) {
        super(parent, role + " Login", true);
        setSize(300, 200);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        JTextField userField = new JTextField();
        userField.setBounds(120, 30, 120, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 70, 120, 25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 110, 100, 30);

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (role.equals("Admin") && user.equals("Admin") && pass.equals("Naveen@123")) {
                dispose();
                new AdminPanel();
            } else if (role.equals("Professor") && user.equals("Professor") && pass.equals("Naveen@123")) {
                dispose();
                new ProfessorPanel();
            } else if (role.equals("Student") && user.equals("Student") && pass.equals("Naveen@123")) {
                dispose();
                new StudentPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

// ========== ADMIN PANEL ==========
class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(500, 400);
        setLayout(null);

        JButton addStudent = new JButton("Add Student");
        addStudent.setBounds(50, 50, 150, 30);
        JButton viewStudents = new JButton("View Students");
        viewStudents.setBounds(50, 100, 150, 30);
        JButton addCourse = new JButton("Add Course");
        addCourse.setBounds(50, 150, 150, 30);
        JButton logout = new JButton("Logout");
        logout.setBounds(50, 250, 150, 30);

        add(addStudent); add(viewStudents); add(addCourse); add(logout);

        Admin admin = new Admin();

        addStudent.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Student ID");
            String name = JOptionPane.showInputDialog("Enter Student Name");
            String email = JOptionPane.showInputDialog("Enter Student Email");
            admin.addStudent(id, name, email);
        });

        viewStudents.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Student s : Database.students) {
                sb.append(s.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "All Students", JOptionPane.INFORMATION_MESSAGE);
        });

        addCourse.addActionListener(e -> {
            String course = JOptionPane.showInputDialog("Enter Course Name");
            admin.addCourse(course);
        });

        logout.addActionListener(e -> {
            dispose();
            new StudentManagementSystem();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

// ========== PROFESSOR PANEL ==========
class ProfessorPanel extends JFrame {
    public ProfessorPanel() {
        setTitle("Professor Panel");
        setSize(500, 300);
        setLayout(null);

        JButton viewStudents = new JButton("View Students");
        viewStudents.setBounds(50, 50, 200, 30);
        JButton viewCourses = new JButton("View Courses");
        viewCourses.setBounds(50, 100, 200, 30);
        JButton logout = new JButton("Logout");
        logout.setBounds(50, 150, 200, 30);

        add(viewStudents); add(viewCourses); add(logout);

        viewStudents.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Student s : Database.students) {
                sb.append(s.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "All Students", JOptionPane.INFORMATION_MESSAGE);
        });

        viewCourses.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Course c : Database.courses) {
                sb.append(c.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        logout.addActionListener(e -> {
            dispose();
            new StudentManagementSystem();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

// ========== STUDENT PANEL ==========
class StudentPanel extends JFrame {
    public StudentPanel() {
        setTitle("Student Panel");
        setSize(500, 300);
        setLayout(null);

        JButton viewCourses = new JButton("View Available Courses");
        viewCourses.setBounds(50, 50, 200, 30);
        JButton enrollCourse = new JButton("Enroll in Course");
        enrollCourse.setBounds(50, 100, 200, 30);
        JButton viewEnrolled = new JButton("View Enrolled Courses");
        viewEnrolled.setBounds(50, 150, 200, 30);
        JButton logout = new JButton("Logout");
        logout.setBounds(50, 200, 200, 30);

        add(viewCourses); add(enrollCourse); add(viewEnrolled); add(logout);

        Student student = Database.students.isEmpty() ? null : Database.students.get(0);

        viewCourses.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Course c : Database.courses) {
                sb.append(c.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        enrollCourse.addActionListener(e -> {
            if (student == null) {
                JOptionPane.showMessageDialog(this, "No student found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String courseName = JOptionPane.showInputDialog("Enter Course Name to Enroll:");
            for (Course c : Database.courses) {
                if (c.getName().equalsIgnoreCase(courseName)) {
                    student.enrollCourse(c);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        viewEnrolled.addActionListener(e -> {
            if (student == null) {
                JOptionPane.showMessageDialog(this, "No student found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Course c : student.getEnrolledCourses()) {
                sb.append(c.toString()).append("\n");
            }
            if (sb.length() == 0) {
                sb.append("No enrolled courses.");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Enrolled Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        logout.addActionListener(e -> {
            dispose();
            new StudentManagementSystem();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
