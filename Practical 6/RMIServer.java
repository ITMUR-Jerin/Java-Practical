
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class RMIServer {

    public static void main(String[] args) {

        try {
            // Create the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);

            // Create the remote calculator object
            RemoteCalculator calculator = new RemoteCalculatorImpl();

            // Register the object with the RMI registry
            Naming.rebind(
                    "rmi://localhost:1099/CalculatorService",
                    calculator
            );

            System.out.println("RMI Server is running...");
            System.out.println("Calculator service is ready.");

        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

