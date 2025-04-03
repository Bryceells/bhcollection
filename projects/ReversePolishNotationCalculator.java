// This program reads a Reverse Polish Notation mathematical Expression
// and evaluates it.  The program will show each step if the user chooses that
// otherwise the program will only print out the end result

// Bryce Hagen
// BIT 143
// 2025 Winter Quarter
// A4.0  never turned in a first :)

import java.util.*;

public class ReversePolishNotationCalculator {

    // Helper method to print the remaining expression and the current state of the stack
    private static Scanner printRemainingExpression(Stack<Double> numbers, Scanner scExpression) {
        String remainderOfExpr = scExpression.nextLine(); // Get the remainder of the input expression
        System.out.println("Remaining expression: " + remainderOfExpr + " Stack: " + numbers);
        scExpression.close(); // Close the current scanner
        return new Scanner(remainderOfExpr + "\n"); // Create a new scanner for the remaining expression
    }

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in); // Scanner to read input from the user
        char evalAgain = 'y'; // Variable to control whether to evaluate another expression

        // Outer loop to allow repeated evaluations
        ShouldWeTryAgain: while (evalAgain == 'y') {
            Stack<Double> numberStack = new Stack<>(); // Stack to store operands (numbers)
            double nextNumber; // Variable to store the next number from the expression

            // Display the initial calculator prompt
            System.out.println("\nRPN calculator");
            System.out.println("\tSupported operators: + - * /");
            System.out.print("Type your RPN expression in so that it can be evaluated: ");
            String rpnExpr = keyboard.nextLine(); // Read the RPN expression from the user

            // Check if the user wants an explanation of the evaluation
            boolean explain = false;
            System.out.print(
                    "Would you like me to explain how the expression is evaluated? (y or Y for yes, anything else means no) ");
            String szExplain = keyboard.nextLine().trim().toLowerCase();
            if (szExplain.length() == 1 && szExplain.charAt(0) == 'y') {
                System.out.println("We WILL explain the evaluation, step by step");
                explain = true;
            }

            Scanner scExpr = new Scanner(rpnExpr + "\n"); // Scanner to parse the RPN expression

            // Loop to process each token in the RPN expression
            while (scExpr.hasNext()) {  
                if (scExpr.hasNextDouble()) {
                    nextNumber = scExpr.nextDouble(); // Read the next number
                    numberStack.push(nextNumber); // Push the number onto the stack
                    if (explain) {
                        System.out.println("\tPushing " + nextNumber + " onto the stack of operands (numbers)");
                    }
                    if (scExpr.hasNext()) {
                        scExpr = printRemainingExpression(numberStack, scExpr); // Print remaining expression if in explain mode
                    }
                } else {
                    String operator = scExpr.next(); // Read the next operator
                    // Check if the operator is valid
                    if (operator.length() != 1 || "+-*/".indexOf(operator) == -1) {
                        System.err.println("ERROR! Operator not recognized or invalid: " + operator);
                        System.out.println(
                                "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain; // Restart the evaluation loop
                    }
                    
                    // Check if there are enough operands for the operation
                    if (numberStack.size() < 2) {
                        System.err.println("ERROR! Expected to find 2 operands (numbers) but we don't have enough numbers on the stack!");
                        System.out.println(
                                "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain; // Restart the evaluation loop
                    }
                    
                    // Pop the top two operands from the stack
                    double operand2 = numberStack.pop();
                    double operand1 = numberStack.pop();
                    double result = 0;

                    // Perform the operation based on the operator
                    switch (operator.charAt(0)) {
                        case '+':
                            result = operand1 + operand2; // Addition
                            break;
                        case '-':
                            result = operand1 - operand2; // Subtraction
                            break;
                        case '*':
                            result = operand1 * operand2; // Multiplication
                            break;
                        case '/':
                            if (operand2 == 0) { // Check for division by zero
                                System.err.println("ERROR! Division by zero");
                                continue ShouldWeTryAgain; // Restart the evaluation loop
                            }
                            result = operand1 / operand2; // Division
                            break;
                    }
                    numberStack.push(result); // Push the result back onto the stack

                    // Print the operation details if in explain mode
                    if (explain) {
                        System.out.println("\tPopping " + operand1 + " and " + operand2 + " then applying " + operator + " to them");
                        System.out.println("\tThen pushing the result of " + operand1 + " " + operator + " " + operand2 + " back onto the stack");
                    }

                    if (scExpr.hasNext()) {
                        scExpr = printRemainingExpression(numberStack, scExpr); // Print remaining expression if in explain mode
                    }
                }
            }

            // Check the final state of the stack after processing the expression
            if (numberStack.size() == 1) {
                System.out.println("END RESULT: " + numberStack.pop()); // Print the final result
            } else if (numberStack.isEmpty()) {
                System.err.println("ERROR! Ran out of operands (numbers)"); // Error if no operands are on the stack
            } else {
                System.err.println("ERROR! Ran out of operators before we used up all the operands (numbers):");
                // Print any remaining operands on the stack
                while (!numberStack.isEmpty()) {
                    System.err.println("\t" + numberStack.pop());
                }
            }

            // Ask the user if they want to evaluate another expression
            System.out.print("\nWould you like to evaluate another expression? (y or Y for yes, anything else to exit) ");
            String repeat = keyboard.nextLine().trim().toLowerCase();
            evalAgain = (repeat.length() == 1 && repeat.charAt(0) == 'y') ? 'y' : 'n'; // Update the loop control variable
        }
        System.out.println("Thank you for using RPN Calculator!"); // Exit message
    }
}