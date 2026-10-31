# Practical 6: Remote Method Invocation (RMI) in Java

## Aim
To implement Remote Method Invocation (RMI) in Java to perform addition, subtraction, and multiplication of two numbers.

## Theory
Remote Method Invocation (RMI) is a Java technology that allows an object running in one Java Virtual Machine to invoke methods on an object running in another Java Virtual Machine.

In this program:
- The remote interface declares the methods.
- The implementation class defines the arithmetic operations.
- The server registers the remote object with the RMI registry.
- The client looks up the remote object and invokes its methods.

## Operations Performed
1. Addition of two numbers.
2. Subtraction of two numbers.
3. Multiplication of two numbers.

## Concepts Used
- Java RMI
- Remote Interface
- RemoteException
- UnicastRemoteObject
- RMI Registry
- Naming.lookup()
- Naming.rebind()
- Client-Server Communication

## Source Code
- `RemoteCalculator.java`
- `RemoteCalculatorImpl.java`
- `RMIServer.java`
- `RMIClient.java`

## Output

### Server Output
![RMI Server Output](server-output.png)

### Client Output
![RMI Client Output](client-output.png)

## Conclusion
Successfully implemented Remote Method Invocation in Java to perform addition, subtraction, and multiplication through remote method calls.