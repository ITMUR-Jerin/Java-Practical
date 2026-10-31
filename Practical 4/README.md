# Practical 4: Client-Server Communication Using TCP

## Aim
To implement client-server communication using TCP in Java with the help of `ServerSocket` and `Socket`.

## Theory
TCP (Transmission Control Protocol) is a connection-oriented protocol that provides reliable communication between two devices.

In this program:
- The server creates a `ServerSocket` and waits for a client connection.
- The client connects to the server using a `Socket`.
- The client sends a message to the server.
- The server receives the message and sends a response back to the client.

## Concepts Used
- Java Networking
- TCP Protocol
- ServerSocket
- Socket
- InputStreamReader
- BufferedReader
- PrintWriter
- Exception Handling

## Source Code
- `TCPServer.java`
- `TCPClient.java`

## Output

### Server Output
![TCP Server Output](server-output.png)

### Client Output
![TCP Client Output](client-output.png)

## Conclusion
Successfully implemented TCP-based client-server communication in Java using ServerSocket and Socket.