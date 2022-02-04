package de.leuphana.cosa.pricingsystem.behaviour.service;

import de.leuphana.cosa.pricingsystem.structure.PriceRate;

public interface PricingService {
    String PRICE_DETERMINED_TOPIC = "priceservice/price/determined";

    double calculatePrice(double distance, PriceRate rate);
}
