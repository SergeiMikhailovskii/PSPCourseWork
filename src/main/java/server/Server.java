package server;

import entities.Property;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

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
                new Thread(() -> {
                    System.out.println("Client accepted");
                    try {
                        OutputStream outputStream = client.getOutputStream();
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

                            if (clientAction.equalsIgnoreCase("END")) {
                                flag = false;
                            } else if (clientAction.equalsIgnoreCase("LOGIN")) {
                                String[] arr = queryContent.split(" ");
                                String login = arr[0];
                                String password = arr[1];
                                int result = isUserExists(login, password);
                                if (result == 1) {
                                    sendDataToClient(outputStream, "ADMIN");
                                } else if (result == 0) {
                                    sendDataToClient(outputStream, "BASE_USER");
                                } else {
                                    sendDataToClient(outputStream, "EMPTY");
                                }
                            } else if (clientAction.equalsIgnoreCase("REGISTER")) {
                                String[] arr = queryContent.split(" ");
                                String login = arr[0];
                                String password = arr[1];
                                registerUser(login, password);
                                sendDataToClient(outputStream, "REGISTERED");
                            } else if (clientAction.equalsIgnoreCase("GET_ALL_USERS")) {
                                getAllUsers(outputStream);
                            } else if (clientAction.equalsIgnoreCase("SAVE_PROPERTY")) {
                                insertProperty(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase("GET_DISTANCE_FROM_CENTER")) {
                                getDistanceCoefficient(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase("GET_BUILD_YEAR")) {
                                getBuildYearCoefficient(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase("GET_REPAIR_DEGREE")) {
                                getRepairDegreeCoefficient(outputStream, queryContent);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getRepairDegreeCoefficient(OutputStream outputStream, String queryContent) {
        int repairDegree = Integer.parseInt(queryContent);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM repairdegree WHERE bottomBorder<=" + repairDegree + " AND topBorder >=" + repairDegree);
            if (resultSet.next()) {
                double res = resultSet.getDouble("repairCoefficient");
                sendDataToClient(outputStream, String.valueOf(res));
                res = resultSet.getInt("id");
                sendDataToClient(outputStream, String.valueOf(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getBuildYearCoefficient(OutputStream outputStream, String queryContent) {
        int year = Integer.parseInt(queryContent);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM buildyear WHERE bottomBorder<=" + year + " AND topBorder >=" + year);
            if (resultSet.next()) {
                double res = resultSet.getDouble("yearCoefficient");
                sendDataToClient(outputStream, String.valueOf(res));
                res = resultSet.getInt("id");
                sendDataToClient(outputStream, String.valueOf(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDistanceCoefficient(OutputStream outputStream, String queryContent) {
        int distance = Integer.parseInt(queryContent);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM distancefromcenter WHERE bottomBorder<=" + distance + " AND topBorder >=" + distance);
            if (resultSet.next()) {
                double res = resultSet.getDouble("distanceCoefficient");
                sendDataToClient(outputStream, String.valueOf(res));
                res = resultSet.getInt("id");
                sendDataToClient(outputStream, String.valueOf(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllUsers(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            resultSet.last();
            int rows = resultSet.getRow();
            sendDataToClient(outputStream, String.valueOf(rows));
            resultSet.first();

            //todo prettify this part of code
            String res = resultSet.getString("login") + " " + resultSet.getString("password") + " " + resultSet.getInt("role");
            sendDataToClient(outputStream, res);
            while (resultSet.next()) {
                res = resultSet.getString("login") + " " + resultSet.getString("password") + " " + resultSet.getInt("role");
                sendDataToClient(outputStream, res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendDataToClient(OutputStream outputStream, String data) {
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int isUserExists(String login, String password) {
        int role = -1;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE login='" + login + "' AND password='" + password + "'");
            resultSet.last();
            if (resultSet.getRow() != 0) {
                role = resultSet.getInt("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    private void registerUser(String login, String password) {
        try {
            statement.executeUpdate("INSERT INTO user (login, password, role) VALUE ('" + login + "', '" + password + "', 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertProperty(OutputStream outputStream, String queryContent) {
        try {
            System.out.println(queryContent);
            Property property = new Property(queryContent);
            statement.executeUpdate("INSERT INTO property " +
                    "(address, square, price, distanceFromCenterID, buildYearID, repairDegreeID, userId)" +
                    " VALUE ('" + property.getAddress() + "', " + property.getSquare() + ", "
                    + property.getPrice() + ", " + property.getDistanceFromCenterCoefficient() + ", "
                    + property.getBuildYearCoefficient() + ", " + property.getRepairDegreeCoefficient() + ", " + property.getUserID() + ")");
            outputStream.write("Inserted".getBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}