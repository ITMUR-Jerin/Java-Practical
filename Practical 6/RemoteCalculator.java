
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCalculator extends Remote {

    double add(double a, double b) throws RemoteException;

    double subtract(double a, double b) throws RemoteException;

    double multiply(double a, double b) throws RemoteException;
}

