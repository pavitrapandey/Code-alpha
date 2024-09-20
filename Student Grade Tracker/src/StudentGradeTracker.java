import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

class Student {
    String name;
    double maths;
    double physics;
    double chemistry;
    double english;
    double hindi;
    int rollNo;

    public Student(String name, double maths, double physics, double chemistry, double english, double hindi, int rollNo) {
        this.name = name;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
        this.english = english;
        this.hindi = hindi;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public double getMaths() {
        return maths;
    }

    public double getPhysics() {
        return physics;
    }

    public double getChemistry() {
        return chemistry;
    }

    public double getEnglish() {
        return english;
    }

    public double getHindi() {
        return hindi;
    }

    public int getRollNo() {
        return rollNo;
    }

    public double calculatePercentage() {
        double total = maths + physics + chemistry + english + hindi;
        return total / 5;
    }

    public String calculate() {
        double per = calculatePercentage();
        StringBuilder gr = new StringBuilder();

        if (per >= 91) gr.append("A+");
        else if (per >= 81) gr.append("A");
        else if (per >= 71) gr.append("B+");
        else if (per >= 61) gr.append("B");
        else if (per >= 51) gr.append("C+");
        else if (per >= 41) gr.append("C");
        else if (per >= 33) gr.append("D");
        else gr.append("Failed");

        if (maths < 33) gr.append(" (failed in Maths)");
        else if (physics < 33) gr.append(" (failed in Physics)");
        else if (chemistry < 33) gr.append(" (failed in Chemistry)");
        else if (english < 33) gr.append(" (failed in English)");
        else if (hindi < 33) gr.append(" (failed in Hindi)");
        return gr.toString();
    }

    public Object[] toTableRow() {
        return new Object[]{rollNo, name, maths, physics, chemistry, english, hindi,
                String.format("%.2f", calculatePercentage()) + "%", calculate()};
    }
}

public class StudentGradeTracker {
    static ArrayList<Student> st = new ArrayList<>();
    static DefaultTableModel tModel;
    static JTextField nameField, mathsField, physicsField, chemistryField, englishField, hindiField;
    static int rollNoCounter = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Grade Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 650);
            frame.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel nameLabel = new JLabel("Student Name:");
            nameField = new JTextField(15);
            JLabel mathsLabel = new JLabel("Maths Grade:");
            mathsField = new JTextField(5);
            JLabel physicsLabel = new JLabel("Physics Grade:");
            physicsField = new JTextField(5);
            JLabel chemistryLabel = new JLabel("Chemistry Grade:");
            chemistryField = new JTextField(5);
            JLabel englishLabel = new JLabel("English Grade:");
            englishField = new JTextField(5);
            JLabel hindiLabel = new JLabel("Hindi Grade:");
            hindiField = new JTextField(5);
            JButton addButton = new JButton("Add Student");
            JButton calculateButton = new JButton("Calculate Grades");
            JButton sortByGradesButton = new JButton("Sort by Grades");
            JButton searchButton = new JButton("Search Student");
            JButton deleteButton = new JButton("Delete Student");
            JButton changeColorButton = new JButton("Change Color");

            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            inputPanel.add(mathsLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(mathsField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            inputPanel.add(physicsLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(physicsField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            inputPanel.add(chemistryLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(chemistryField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            inputPanel.add(englishLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(englishField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            inputPanel.add(hindiLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(hindiField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            inputPanel.add(addButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(calculateButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 7;
            inputPanel.add(sortByGradesButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(searchButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 8;
            inputPanel.add(deleteButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(changeColorButton, gbc);

            String[] columnNames = {"Roll No", "Name", "Maths", "Physics", "Chemistry", "English", "Hindi", "Average", "Grade"};
            tModel = new DefaultTableModel(columnNames, 0);
            JTable studentTable = new JTable(tModel);
            JScrollPane tableScrollPane = new JScrollPane(studentTable);
            tableScrollPane.setPreferredSize(new Dimension(780, 400));

            frame.add(inputPanel, BorderLayout.NORTH);
            frame.add(tableScrollPane, BorderLayout.CENTER);

            nameField.addActionListener(e -> mathsField.requestFocus());
            mathsField.addActionListener(e -> physicsField.requestFocus());
            physicsField.addActionListener(e -> chemistryField.requestFocus());
            chemistryField.addActionListener(e -> englishField.requestFocus());
            englishField.addActionListener(e -> hindiField.requestFocus());
            hindiField.addActionListener(e -> addStudent());

            addButton.addActionListener(e -> addStudent());

            calculateButton.addActionListener(e -> {
                if (st.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No student data available.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double sum = 0;
                double highest = st.get(0).calculatePercentage();
                double lowest = st.get(0).calculatePercentage();

                for (Student student : st) {
                    double percentage = student.calculatePercentage();
                    sum += percentage;

                    if (percentage > highest) {
                        highest = percentage;
                    }

                    if (percentage < lowest) {
                        lowest = percentage;
                    }
                }

                double average = sum / st.size();

                JOptionPane.showMessageDialog(frame,
                        "Grade Summary:\n" +
                                "Average Percentage: " + String.format("%.2f", average) + "%\n" +
                                "Highest Percentage: " + String.format("%.2f", highest) + "%\n" +
                                "Lowest Percentage: " + String.format("%.2f", lowest) + "%",
                        "Summary", JOptionPane.INFORMATION_MESSAGE);
            });

            sortByGradesButton.addActionListener(e -> {
                if (st.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No student data to sort.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                st.sort(Comparator.comparingDouble(Student::calculatePercentage).reversed());
                tModel.setRowCount(0);
                for (Student student : st) {
                    tModel.addRow(student.toTableRow());
                }
            });

            searchButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(frame, "Enter Student Name:", "Search Student", JOptionPane.QUESTION_MESSAGE);
                if (name == null || name.isEmpty()) {
                    return;
                }

                boolean found = false;
                for (Student student : st) {
                    if (student.getName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(frame,
                                "Student Found:\n" +
                                        "Roll No: " + student.getRollNo() + "\n" +
                                        "Name: " + student.getName() + "\n" +
                                        "Maths: " + student.getMaths() + "\n" +
                                        "Physics: " + student.getPhysics() + "\n" +
                                        "Chemistry: " + student.getChemistry() + "\n" +
                                        "English: " + student.getEnglish() + "\n" +
                                        "Hindi: " + student.getHindi() + "\n" +
                                        "Average: " + student.calculatePercentage() + "%\n" +
                                        "Grade: " + student.calculate(),
                                "Student Details", JOptionPane.INFORMATION_MESSAGE);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            deleteButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(frame, "Enter Student Name to Delete:", "Delete Student", JOptionPane.QUESTION_MESSAGE);
                if (name == null || name.isEmpty()) {
                    return;
                }

                boolean deleted = st.removeIf(student -> student.getName().equalsIgnoreCase(name));
                if (deleted) {
                    JOptionPane.showMessageDialog(frame, "Student deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    refreshStudentTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            changeColorButton.addActionListener(e -> {
                Color newColor = JColorChooser.showDialog(frame, "Choose Table Background Color", studentTable.getBackground());
                if (newColor != null) {
                    studentTable.setBackground(newColor);
                }
            });

            frame.setVisible(true);
        });
    }

    public static void addStudent() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a student name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double maths = Double.parseDouble(mathsField.getText().trim());
            double physics = Double.parseDouble(physicsField.getText().trim());
            double chemistry = Double.parseDouble(chemistryField.getText().trim());
            double english = Double.parseDouble(englishField.getText().trim());
            double hindi = Double.parseDouble(hindiField.getText().trim());

            if (isDuplicateStudent(name)) {
                JOptionPane.showMessageDialog(null, "Student with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            st.add(new Student(name, maths, physics, chemistry, english, hindi, rollNoCounter++));

            // Sort students by name
            st.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
            // Update roll numbers based on sorted order
            for (int i = 0; i < st.size(); i++) {
                st.get(i).rollNo = i + 1;
            }

            refreshStudentTable();
            clearInputFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid grades.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void clearInputFields() {
        nameField.setText("");
        mathsField.setText("");
        physicsField.setText("");
        chemistryField.setText("");
        englishField.setText("");
        hindiField.setText("");
    }
    public static boolean isDuplicateStudent(String name) {
        for (Student student : st) {
            if (student.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static void refreshStudentTable() {
        tModel.setRowCount(0);
        for (Student student : st) {
            tModel.addRow(student.toTableRow());
        }
    }
}
