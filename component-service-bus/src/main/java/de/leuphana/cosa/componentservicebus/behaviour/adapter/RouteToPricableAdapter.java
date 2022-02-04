package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Pricable;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Route;
import org.osgi.service.event.Event;

public class RouteToPricableAdapter {
    PricingService pricingService;

    public RouteToPricableAdapter(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void onRouteCreated(Event event) {
        if (event.getProperty(RouteService.ROUTE_KEY) instanceof Route route) {

            // Map Route to Pricable
            Pricable pricable = new Pricable() {
                @Override
                public double getAmount() {
                    return route.getDistance();
                }

                @Override
                public String getName() {
                    return route.toString();
                }
            };

            // Call pricing service
            pricingService.selectPriceRate(pricable);
        }
    }
}
