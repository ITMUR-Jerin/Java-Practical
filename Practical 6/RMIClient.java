
import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            // Look up the remote calculator object
            RemoteCalculator calculator = (RemoteCalculator) Naming.lookup(
                    "rmi://localhost:1099/CalculatorService"
            );

            System.out.println("Connected to RMI Server!");

            System.out.print("Enter first number: ");
            double a = scanner.nextDouble();

            System.out.print("Enter second number: ");
            double b = scanner.nextDouble();

            // Call remote methods
            System.out.println("\n--- Calculation Results ---");

            System.out.println("Addition: " + calculator.add(a, b));

            System.out.println("Subtraction: " +
                    calculator.subtract(a, b));

            System.out.println("Multiplication: " +
                    calculator.multiply(a, b));

        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

