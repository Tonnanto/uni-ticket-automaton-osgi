package de.leuphana.cosa.routesystem.behaviour.service;

public interface RouteService {
    String ROUTE_CREATED_TOPIC = "routeservice/route/created";
    String ROUTE_KEY = "route";

    void selectRoute();
}
