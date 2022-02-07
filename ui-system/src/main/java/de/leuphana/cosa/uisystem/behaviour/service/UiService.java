package de.leuphana.cosa.uisystem.behaviour.service;

import java.util.List;

public interface UiService {
    int selectStartLocation(final List<String> startLocations);
    int selectEndLocation(final List<String> endLocations);
    int selectPriceRate(final List<String> priceRates);
    void showRouteReview(final String startLocation, final String endLocation, final String distance, final String priceRate, final String price);
    boolean getPurchaseConfirmation();
    void showPurchaseConfirmation();
}
