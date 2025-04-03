// Bryce Hagen
import java.io.*;
import java.util.*;

public class Sum {
    // Class constant for the number of digits
    private static final int NUM_DIGITS = 25;

    public static void main(String[] args) {
        processInputFile("sum.txt");
    }

    private static void processInputFile(String fileName) {
        int totalLines = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
                totalLines++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        }

        System.out.println("Total lines = " + totalLines);
    }
    
    private static void processLine(String line) {
        String[] numbers = line.split("\\s+");
        int[] result = new int[NUM_DIGITS];

        for (String number : numbers) {
            int[] numArray = convertToDigitsArray(number);
            addArrays(result, numArray);
        }

        printResult(line, result);
    }

    private static int[] convertToDigitsArray(String number) {
        int[] digitsArray = new int[NUM_DIGITS];

        for (int i = 0; i < number.length(); i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            digitsArray[NUM_DIGITS - number.length() + i] = digit;
        }

        return digitsArray;
    }

    private static void addArrays(int[] result, int[] numArray) {
        int carry = 0;

        for (int i = NUM_DIGITS - 1; i >= 0; i--) {
            int sum = result[i] + numArray[i] + carry;
            result[i] = sum % 10;
            carry = sum / 10;
        }
    }

    private static void printResult(String line, int[] result) {
      // Split the line into individual numbers
      String[] numbers = line.split("\\s+");

      // Print the original numbers with addition signs
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);
            if (i < numbers.length - 1) {
               System.out.print(" + ");
            }
       }

       System.out.print(" = ");

       // Find the first non-zero digit index
       int firstNonZero = 0;
       while (firstNonZero < NUM_DIGITS && result[firstNonZero] == 0) {
           firstNonZero++;
       }

       // Print the result without leading zeros
       if (firstNonZero == NUM_DIGITS) {
           System.out.println("0");
       } else {
           for (int i = firstNonZero; i < NUM_DIGITS; i++) {
               System.out.print(result[i]);
           }
           System.out.println();
        }
    }
}