package entities;

public class Property {
    private String address;
    private int square;
    private double price;
    private double distanceFromCenterCoefficient;
    private double buildYearCoefficient;
    private double repairDegreeCoefficient;
    private int userID;

    public Property(String address, int square, double price, double distanceFromCenterCoefficient,
                    double buildYearCoefficient, double repairDegreeCoefficient, int userID) {
        this.address = address;
        this.square = square;
        this.price = price;
        this.distanceFromCenterCoefficient = distanceFromCenterCoefficient;
        this.buildYearCoefficient = buildYearCoefficient;
        this.repairDegreeCoefficient = repairDegreeCoefficient;
        this.userID = userID;
    }

    public Property(String data) {
        String[] arr = data.split(" ");
        address = arr[0];
        square = Integer.parseInt(arr[1]);
        price = Double.parseDouble(arr[2]);
        distanceFromCenterCoefficient = Integer.parseInt(arr[3]);
        buildYearCoefficient = Integer.parseInt(arr[4]);
        repairDegreeCoefficient = Integer.parseInt(arr[5]);
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

    public double getDistanceFromCenterCoefficient() {
        return distanceFromCenterCoefficient;
    }

    public void setDistanceFromCenterCoefficient(int distanceFromCenterCoefficient) {
        this.distanceFromCenterCoefficient = distanceFromCenterCoefficient;
    }

    public double getBuildYearCoefficient() {
        return buildYearCoefficient;
    }

    public void setBuildYearCoefficient(int buildYearCoefficient) {
        this.buildYearCoefficient = buildYearCoefficient;
    }

    public double getRepairDegreeCoefficient() {
        return repairDegreeCoefficient;
    }

    public void setRepairDegreeCoefficient(int repairDegreeCoefficient) {
        this.repairDegreeCoefficient = repairDegreeCoefficient;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return address + " " + square + " " + price + " " + distanceFromCenterCoefficient + " " + buildYearCoefficient + " " + repairDegreeCoefficient + " " + userID;
    }
}
