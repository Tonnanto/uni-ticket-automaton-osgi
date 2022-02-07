package de.leuphana.cosa.documentsystem.structure;

public class BookingDetail {
    private String start;
    private String destination;
    private int distance;
    private double price;
    private String priceGroup;

    public BookingDetail(String start, String destination, int distance, double price, String priceGroup) {
        this.start = start;
        this.destination = destination;
        this.distance = distance;
        this.price = price;
        this.priceGroup = priceGroup;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    @Override
    public String toString() {
        return "BookingDetail{" +
                "start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                ", price=" + price +
                ", priceGroup='" + priceGroup + '\'' +
                '}';
    }
}
