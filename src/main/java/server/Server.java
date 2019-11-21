package server;

import constants.Actions;
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

                            if (clientAction.equalsIgnoreCase(Actions.END)) {
                                flag = false;
                            } else if (clientAction.equalsIgnoreCase(Actions.LOGIN)) {
                                logInUser(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.REGISTER)) {
                                String[] arr = queryContent.split(" ");
                                String login = arr[0];
                                String password = arr[1];
                                registerUser(login, password);
                                sendDataToClient(outputStream, "REGISTERED");
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_ALL_USERS)) {
                                getAllUsers(outputStream);
                            } else if (clientAction.equalsIgnoreCase(Actions.SAVE_PROPERTY)) {
                                insertProperty(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_DISTANCE_FROM_CENTER)) {
                                getDistanceCoefficient(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_BUILD_YEAR)) {
                                getBuildYearCoefficient(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_REPAIR_DEGREE)) {
                                getRepairDegreeCoefficient(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_BUILD_YEAR_CHART_DATA)) {
                                getBuildYearChartData(outputStream);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_DISTANCE_FROM_SERVER_BAR_CHART_DATA)) {
                                getDistanceFromCenterChartData(outputStream);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_ALL_USER_PROPERTIES)) {
                                getAllUserProperties(outputStream, queryContent);
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

    private void getAllUserProperties(OutputStream outputStream, String queryContent) {
        int id = Integer.parseInt(queryContent);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM property WHERE userId=" + id);
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            sendDataToClient(outputStream, String.valueOf(count));

            resultSet = statement.executeQuery("SELECT * FROM property WHERE userId=" + id);

            // todo move all columns names to constants

            int i = 0;
            while (resultSet.next()) {
                String res = resultSet.getString("address") + " "
                        + resultSet.getInt("square") + " "
                        + resultSet.getDouble("price") + " "
                        + resultSet.getInt("distanceFromCenterID") + " "
                        + resultSet.getInt("buildYearID") + " "
                        + resultSet.getInt("repairDegreeID") +" ";
                System.out.println(res);
                sendDataToClient(outputStream, res);
                i++;
            }
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDistanceFromCenterChartData(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM distancefromcenter");
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            sendDataToClient(outputStream, String.valueOf(count));
            for (int i = 1; i <= count; i++) {
                int val = 0;
                String borders = "";

                resultSet = statement.executeQuery("SELECT bottomBorder, topBorder FROM distancefromcenter WHERE id=" + i);
                while (resultSet.next()) {
                    borders = resultSet.getInt("bottomBorder") + "-" + resultSet.getInt("topBorder");
                }

                resultSet = statement.executeQuery("SELECT COUNT(*) FROM property WHERE distanceFromCenterID=" + i);
                while (resultSet.next()) {
                    val = resultSet.getInt(1);
                }

                sendDataToClient(outputStream, val + " " + borders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getBuildYearChartData(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM buildyear");
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            sendDataToClient(outputStream, String.valueOf(count));
            for (int i = 1; i <= count; i++) {
                int val = 0;
                String borders = "";

                resultSet = statement.executeQuery("SELECT bottomBorder, topBorder FROM buildyear WHERE id=" + i);
                while (resultSet.next()) {
                    borders = resultSet.getInt("bottomBorder") + "-" + resultSet.getInt("topBorder");
                }

                resultSet = statement.executeQuery("SELECT COUNT(*) FROM property WHERE buildYearID=" + i);
                while (resultSet.next()) {
                    val = resultSet.getInt(1);
                }

                sendDataToClient(outputStream, val + " " + borders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void logInUser(OutputStream outputStream, String queryContent) {
        String[] arr = queryContent.split(" ");
        String login = arr[0];
        String password = arr[1];
        int role;
        int id;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE login='" + login + "' AND password='" + password + "'");
            resultSet.last();
            if (resultSet.getRow() != 0) {
                role = resultSet.getInt("role");
                id = resultSet.getInt("id");
            } else {
                role = -1;
                id = -1;
            }
            sendDataToClient(outputStream, String.valueOf(role));
            sendDataToClient(outputStream, String.valueOf(id));

        } catch (SQLException e) {
            e.printStackTrace();
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