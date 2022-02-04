package de.leuphana.cosa.routesystem.behaviour.service;

import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.Route;

import java.util.List;

public interface RouteService {
    String ROUTE_CREATED_TOPIC = "routeservice/route/created";

    Route createRoute(Location startLocation, Location endLocation);
    List<Location> getLocations();
}
