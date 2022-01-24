package de.leuphana.cosa.pricingsystem;

import de.leuphana.cosa.pricingsystem.behaviour.PricingServiceImpl;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PricingServiceTest {

    public static PricingServiceImpl priceService;

    @BeforeAll
    static void setUp() {
        priceService = new PricingServiceImpl();
    }

    @Test
    void canPriceBeCalculated() {
        double distance = 100;

        double normalPrice = priceService.calculatePrice(distance, PriceRate.NORMAL);
        double cheapTravelPrice = priceService.calculatePrice(distance, PriceRate.CHEAP_TRAVEL);
        double bargainPrice = priceService.calculatePrice(distance, PriceRate.BARGAIN);

        Assertions.assertEquals(distance * 1.45, normalPrice);
        Assertions.assertEquals(distance * 1.45 * 0.75, cheapTravelPrice);
        Assertions.assertEquals(distance * 1.45 * 0.5, bargainPrice);
    }

}
