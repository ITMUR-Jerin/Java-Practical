
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteCalculatorImpl extends UnicastRemoteObject
        implements RemoteCalculator {

    public RemoteCalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }
}

