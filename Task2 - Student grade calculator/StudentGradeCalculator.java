import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Welcome to the Student Grade Calculator!");
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scn.nextInt();
        int totalMarks = 0;
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks for subject " + i + ": ");
            int marks = scn.nextInt();
            totalMarks += marks;
        }
        double averagePercentage = (double) totalMarks / numSubjects;
        char grade;

        if(averagePercentage >= 90) {
            grade = 'A';
        } else if(averagePercentage >= 80) {
            grade = 'B';
        } else if(averagePercentage >= 70) {
            grade = 'C';
        } else if(averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        System.out.println("Total marks: " + totalMarks);
        System.out.println("The average percentage is: " + averagePercentage + "%");
        System.out.println("The grade is: " + grade);
        scn.close();
    }
}
