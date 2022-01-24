package de.leuphana.cosa.routesystem;

import de.leuphana.cosa.routesystem.behaviour.RouteServiceImpl;
import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteServiceTest {

    public static RouteServiceImpl routeService;

    @BeforeAll
    public static void setUp() {
        routeService = new RouteServiceImpl();
    }

    @Test
    public void canLocationsBeParsed() {
        Assertions.assertNotNull(routeService.getLocations());
        Assertions.assertFalse(routeService.getLocations().isEmpty());
    }

    @Test
    public void canRouteBeCreated() {
        List<Location> locations = new ArrayList<>(routeService.getLocations());
        Collections.shuffle(locations);

        Assertions.assertTrue(locations.size() >= 2);

        Location location1 = locations.get(0);
        Location location2 = locations.get(1);

        Route route = routeService.createRoute(location1, location2);

        Assertions.assertNotNull(route);
        Assertions.assertTrue(route.getDistance() > 0);

        System.out.println(route);
        System.out.printf("%.0f km%n",route.getDistance());
    }
}
