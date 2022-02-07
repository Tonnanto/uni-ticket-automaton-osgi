package de.leuphana.cosa.routesystem.structure;

public class Route {
    private final Location startLocation;
    private final Location endLocation;
    private final double distance;

    public Route(Location startLocation, Location endLocation, double distance) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(startLocation.getName())
                .append(" -> ")
                .append(endLocation.getName());
        return stringBuilder.toString();
    }
}
