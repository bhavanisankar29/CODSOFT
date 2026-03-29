import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class NumberGame {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Random random = new Random();
        int lowerBound = 1, upperBound = 100;
        int numberToGuess = random.nextInt(upperBound-lowerBound+1)+lowerBound;
        int attemptsUsed = 0;
        boolean hasGuessed = false;
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have selected a number between " + lowerBound + " and " + upperBound + ". Can you guess it?");
        while (!hasGuessed) {
            try {
                System.out.print("Enter your guess: ");
                int userGuess = scn.nextInt();
                attemptsUsed++;
                if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high! Try again.");
                } else {
                    hasGuessed = true;
                    System.out.println("Congratulations! You've guessed the number " + numberToGuess + " in " + attemptsUsed + " attempts!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scn.nextLine(); 
            }
        }
        scn.close();
    }
}