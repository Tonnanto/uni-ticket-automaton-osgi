package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import de.leuphana.cosa.routesystem.structure.Route;

public class RouteToPricableAdapter {
    PricingService pricingService;

    public RouteToPricableAdapter(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void onRouteCreated(Route route) {

    }

    public void onPriceRateSelected(PriceRate priceRate) {

    }
}
