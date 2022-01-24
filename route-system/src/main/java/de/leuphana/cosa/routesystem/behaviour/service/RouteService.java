package de.leuphana.cosa.routesystem.behaviour.service;

import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.Route;

import java.util.List;

public interface RouteService {
    Route createRoute(Location startLocation, Location endLocation);
    List<Location> getLocations();
}
