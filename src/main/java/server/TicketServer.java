package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TicketServer {
    private static final int PORT = 12345;
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new TicketServer().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastUpdate() {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage("UPDATE");
            }
        }
    }

    public void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }
}