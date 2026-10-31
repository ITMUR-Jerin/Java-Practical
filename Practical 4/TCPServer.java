
import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) {

        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is waiting for a client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(
                         socket.getOutputStream(), true)) {

                System.out.println("Client connected!");

                String message = input.readLine();

                System.out.println("Message from client: " + message);

                output.println("Hello Client! Message received successfully.");
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

