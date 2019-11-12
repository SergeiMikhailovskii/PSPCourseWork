package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {

    private Connection db;
    private Statement statement;
    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
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


}
