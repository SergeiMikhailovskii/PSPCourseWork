package entities;

public class Property {
    private String address;
    private int square;
    private double price;
    private int distanceFromCenter;
    private int buildYear;
    private int repairDegree;
    private int userID;

    public Property(String address, int square, double price, int distanceFromCenter, int buildYear, int repairDegree, int userID) {
        this.address = address;
        this.square = square;
        this.price = price;
        this.distanceFromCenter = distanceFromCenter;
        this.buildYear = buildYear;
        this.repairDegree = repairDegree;
        this.userID = userID;
    }

    public Property(String data) {
        String[] arr = data.split(" ");
        address = arr[0];
        square = Integer.parseInt(arr[1]);
        price = Double.parseDouble(arr[2]);
        distanceFromCenter = Integer.parseInt(arr[3]);
        buildYear = Integer.parseInt(arr[4]);
        repairDegree = Integer.parseInt(arr[5]);
        userID = Integer.parseInt(arr[6]);
    }

    public Property() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setDistanceFromCenter(int distanceFromCenter) {
        this.distanceFromCenter = distanceFromCenter;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public int getRepairDegree() {
        return repairDegree;
    }

    public void setRepairDegree(int repairDegree) {
        this.repairDegree = repairDegree;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return address + " " + square + " " + price + " " + distanceFromCenter + " " + buildYear + " " + repairDegree + " " + userID;
    }
}
