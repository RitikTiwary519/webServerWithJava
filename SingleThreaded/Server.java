import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(0); // optional: remove timeout

        System.out.println("Server is listening on port: " + port);

        while (true) {
            try {
                Socket acceptedConnection = socket.accept();
                System.out.println("Connected to " + acceptedConnection.getRemoteSocketAddress());

                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

                String input = fromClient.readLine(); // read message from client
                System.out.println("Client said: " + input);

                toClient.println("Hello World from the server");

                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // socket.close(); // ❌ Do not close the socket here inside the loop
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
