package de.leuphana.cosa.pricingsystem.behaviour.service;

import de.leuphana.cosa.pricingsystem.structure.Pricable;

public interface PricingService {
    String PRICE_DETERMINED_TOPIC = "priceservice/price/determined";
    String PRICE_KEY = "price";

    void selectPriceRate(Pricable pricable);
}
