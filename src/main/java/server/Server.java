package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Server {

    private Connection db;
    private Statement statement;
    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
        System.out.println("Server initialized");
        server.listenConnections();
    }

    private void initializeServer() {
        try {
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/courseworkschema" +
                            "?verifyServerCertificate=false" +
                            "&useSSL=false" +
                            "&requireSSL=false" +
                            "&useLegacyDatetimeCode=false" +
                            "&amp" +
                            "&serverTimezone=UTC",
                    "Sergei",
                    "12345");
            statement = db.createStatement();
            socket = new ServerSocket(1024);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void listenConnections() {
        System.out.println("Listening connections ... ");
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket client = socket.accept();
                if (client != null){
                    System.out.println("accepted. port "+ client.getPort());
                }
                new Thread(() -> {
                    System.out.println("Client accepted");
                    try {
                        System.out.println("in try");
                        OutputStream outputStream = Objects.requireNonNull(client).getOutputStream();
                        InputStream inputStream = client.getInputStream();

                        String clientAction;
                        String queryContent;

                        boolean flag = true;

                        while (flag) {
                            byte[] msg = new byte[100];
                            int k = inputStream.read(msg);
                            clientAction = new String(msg, 0, k);
                            clientAction = clientAction.trim();
                            msg = new byte[100];
                            k = inputStream.read(msg);
                            queryContent = new String(msg, 0, k);
                            queryContent = queryContent.trim();
                            System.out.println(clientAction);
                            System.out.println(queryContent);

                            if (clientAction.equalsIgnoreCase("END")) {
                                flag = false;
                            }
                            else if (clientAction.equalsIgnoreCase("LOGIN")) {
                                System.out.println("Login action");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}