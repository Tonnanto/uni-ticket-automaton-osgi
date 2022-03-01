package de.leuphana.cosa.pricingsystem;

import de.leuphana.cosa.pricingsystem.behaviour.PricingServiceImpl;
import de.leuphana.cosa.pricingsystem.structure.Pricable;
import de.leuphana.cosa.pricingsystem.structure.Price;
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
        Pricable pricable = new Pricable() {
            @Override
            public double getAmount() {  return 100;  }

            @Override
            public String getName() { return "Test Strecke"; }
        };

        double normalPrice = new Price(pricable, PriceRate.NORMAL).calculatePrice();
        double cheapTravelPrice = new Price(pricable, PriceRate.CHEAP_TRAVEL).calculatePrice();
        double bargainPrice = new Price(pricable, PriceRate.BARGAIN).calculatePrice();

        Assertions.assertEquals(pricable.getAmount() * 0.03 * 1.45, normalPrice);
        Assertions.assertEquals(pricable.getAmount() * 0.03 * 1.45 * 0.75, cheapTravelPrice);
        Assertions.assertEquals(pricable.getAmount() * 0.03 * 1.45 * 0.5, bargainPrice);
    }

}
