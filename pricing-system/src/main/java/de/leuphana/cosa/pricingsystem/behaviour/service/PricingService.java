package de.leuphana.cosa.pricingsystem.behaviour.service;

import de.leuphana.cosa.pricingsystem.structure.PriceRate;

public interface PricingService {
    double calculatePrice(double distance, PriceRate rate);
}
