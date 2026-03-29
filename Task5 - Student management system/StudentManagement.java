import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StudentManagement {

    static final Path FILE_PATH = resolveDataFilePath();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Remove Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");

            int choice = readInt("Choose option: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> editStudent();
                case 3 -> removeStudent();
                case 4 -> searchStudent();
                case 5 -> displayAll();
                case 6 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        int roll = readInt("Enter Roll No: ");

        if (findByRoll(roll, readAll()) != null) {
            System.out.println("Roll number already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        try {
            ensureParentDirectory();
        } catch (IOException e) {
            System.out.println("Error creating data directory!");
            return;
        }

        try (FileWriter fw = new FileWriter(FILE_PATH.toString(), true)) {
            fw.write(roll + "," + name + "," + grade + "\n");
            System.out.println("Student Added successfully!");
        } catch (IOException e) {
            System.out.println("Error Saving Student!");
        }
    }

    static void editStudent() {
        int roll = readInt("Enter Roll No to Edit: ");

        List<String> students = readAll();
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            String[] data = parseStudent(students.get(i));
            if (data != null && Integer.parseInt(data[0]) == roll) {
                System.out.print("New Name: ");
                String name = sc.nextLine();
                System.out.print("New Grade: ");
                String grade = sc.nextLine();
                students.set(i, roll + "," + name + "," + grade);
                found = true;
                break;
            }
        }

        writeAll(students);
        System.out.println(found ? "Student updated!" : "Student not found!");
    }

    static void removeStudent() {
        int roll = readInt("Enter Roll No to Remove: ");

        List<String> students = readAll();
        boolean removed = students.removeIf(s -> {
            String[] data = parseStudent(s);
            return data != null && Integer.parseInt(data[0]) == roll;
        });

        writeAll(students);
        System.out.println(removed ? "Student removed!" : "Student not found!");
    }

    static void searchStudent() {
        int roll = readInt("Enter Roll No to Search: ");

        String[] data = findByRoll(roll, readAll());
        if (data != null) {
            System.out.println("Roll: " + data[0]);
            System.out.println("Name: " + data[1]);
            System.out.println("Grade: " + data[2]);
            return;
        }

        System.out.println("Student not found!");
    }

    static void displayAll() {
        List<String> students = readAll();
        if (students.isEmpty()) {
            System.out.println("No records found!");
            return;
        }

        System.out.println("\nRoll | Name | Grade");
        for (String s : students) {
            String[] data = parseStudent(s);
            if (data != null) {
                System.out.println(data[0] + " | " + data[1] + " | " + data[2]);
            }
        }
    }

    static List<String> readAll() {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH.toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            // Missing file on first run is normal.
            if (!(e instanceof FileNotFoundException)) {
                System.out.println("Error reading file!");
            }
        }
        return list;
    }

    static void writeAll(List<String> list) {
        try {
            ensureParentDirectory();
        } catch (IOException e) {
            System.out.println("Error creating data directory!");
            return;
        }

        try (FileWriter fw = new FileWriter(FILE_PATH.toString())) {
            for (String s : list) fw.write(s + "\n");
        } catch (IOException e) {
            System.out.println("Error writing file!");
        }
    }

    static Path resolveDataFilePath() {
        Path projectFolder = Paths.get("Task5 - Student management system");
        if (Files.isDirectory(projectFolder)) {
            return projectFolder.resolve("students.txt");
        }
        return Paths.get("students.txt");
    }

    static void ensureParentDirectory() throws IOException {
        Path parent = FILE_PATH.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
        }
    }

    static String[] parseStudent(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] data = line.split(",", -1);
        if (data.length != 3) {
            return null;
        }

        try {
            Integer.parseInt(data[0].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        data[0] = data[0].trim();
        data[1] = data[1].trim();
        data[2] = data[2].trim();
        return data;
    }

    static String[] findByRoll(int roll, List<String> students) {
        for (String student : students) {
            String[] data = parseStudent(student);
            if (data != null && Integer.parseInt(data[0]) == roll) {
                return data;
            }
        }
        return null;
    }
}