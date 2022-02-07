package de.leuphana.cosa.routesystem.behaviour;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.Route;
import de.leuphana.cosa.uisystem.structure.SelectionView;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class RouteServiceImpl implements RouteService, BundleActivator {

    private ServiceReference<RouteService> reference;
    private ServiceRegistration<RouteService> registration;
    private ServiceTracker eventAdminTracker;

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
                this,
                new Hashtable<String, String>());
        reference = registration.getReference();

        eventAdminTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        eventAdminTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering RouteService.");
        registration.unregister();
        eventAdminTracker.close();
    }

    /**
     * Use Case: Select Route
     * Creates a route object based on the locations selected by the user.
     * Triggers a event with the "ROUTE_CREATED_TOPIC" topic once the route is created.
     */
    public void selectRoute() {

        // Let user select start location
        Location startLocation = selectStartLocation(locations);

        // Let user select end location
        List<Location> endLocations = locations.stream().filter(location -> location != startLocation).collect(Collectors.toList());
        Location endLocation = selectEndLocation(endLocations);

        // Create route
        Route route = createRoute(startLocation, endLocation);

        // Trigger event
        EventAdmin eventAdmin = (EventAdmin) eventAdminTracker.getService();

        if (eventAdmin != null) {
            Dictionary<String, Object> content = new Hashtable<>();
            content.put(ROUTE_KEY, route);
            eventAdmin.sendEvent(new Event(ROUTE_CREATED_TOPIC, content));
        } else {
            System.out.println("EventAdmin not found: Event could not be triggered: " + ROUTE_CREATED_TOPIC);
        }
    }

    /**
     * Prompts the user to select a start location
     * @param startLocations the start locations the user can choose from
     * @return the selected start location
     */
    private Location selectStartLocation(List<Location> startLocations) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen Sie Ihren Standort aus.";
            }

            @Override
            protected List<String> getOptions() {
                return startLocations.stream().map((Location::getName)).collect(Collectors.toList());
            }
        };

        int selectedIndex = view.displaySelection();
        return startLocations.get(selectedIndex);
    }

    /**
     * Prompts the user to select a end location
     * @param endLocations the end locations the user can choose from
     * @return the selected end location
     */
    private Location selectEndLocation(List<Location> endLocations) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen Sie Ihr Ziel aus.";
            }

            @Override
            protected List<String> getOptions() {
                return endLocations.stream().map((Location::getName)).collect(Collectors.toList());
            }
        };

        int selectedIndex = view.displaySelection();
        return endLocations.get(selectedIndex);
    }

    /**
     * Creates a route object from the given locations
     */
    public Route createRoute(Location startLocation, Location endLocation) {
        double distance = calculateDistance(startLocation, endLocation);
        return new Route(startLocation, endLocation, distance);
    }

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
