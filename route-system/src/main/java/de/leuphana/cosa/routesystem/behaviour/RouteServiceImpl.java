package de.leuphana.cosa.routesystem.behaviour;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.Route;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class RouteServiceImpl implements RouteService, BundleActivator {

    private ServiceReference<RouteService> reference;
    private ServiceRegistration<RouteService> registration;

    private List<Location> locations;

    public RouteServiceImpl() {
        try {
            this.locations = parseLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering RouteService.");
        registration = bundleContext.registerService(
                RouteService.class,
                new RouteServiceImpl(),
                new Hashtable<String, String>());
        reference = registration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering RouteService.");
        registration.unregister();
    }

    public void selectRoute() {

        // Let user select start location
        // Let user select end location
        // Create route
        // Trigger event (ROUTE_CREATED_TOPIC)

    }

    @Override
    public Route createRoute(Location startLocation, Location endLocation) {
        double distance = calculateDistance(startLocation, endLocation);
        return new Route(startLocation, endLocation, distance);
    }

    @Override
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Calculates the distance between two Locations (Coordinates) in km.
     * @param startLocation 1. location
     * @param endLocation 2. location
     * @return distance in km
     */
    private double calculateDistance(Location startLocation, Location endLocation) {

        double startLat = startLocation.getLatitude();
        double startLong = startLocation.getLongitude();
        
        double endLat = endLocation.getLatitude();
        double endLong = endLocation.getLongitude();

        double radEarth = 6.3781 * (Math.pow(10.0, 6.0));
        double startPhi = startLat * (Math.PI / 180);
        double endPhi = endLat * (Math.PI / 180);

        double latDelta = (startLat - endLat) * (Math.PI / 180);
        double longDelta = (startLong - endLong) * (Math.PI / 180);

        double cal1 = Math.sin(latDelta / 2) * Math.sin(latDelta / 2) + (Math.cos(startPhi) * Math.cos(endPhi) * Math.sin(longDelta / 2) * Math.sin(longDelta / 2));
        double cal2 = 2 * Math.atan2((Math.sqrt(cal1)), (Math.sqrt(1 - cal1)));

        return radEarth * cal2 / 1000;
    }

    /**
     * Reads and parses location data from locations.json file
     * @return list of parsed locations
     * @throws IOException Thrown if the file was not found
     */
    private List<Location> parseLocations() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Location[].class.getResourceAsStream("/locations.json");
        Location[] locations = mapper.readValue(is, Location[].class);

        return Arrays.stream(locations).collect(Collectors.toList());
    }
}
