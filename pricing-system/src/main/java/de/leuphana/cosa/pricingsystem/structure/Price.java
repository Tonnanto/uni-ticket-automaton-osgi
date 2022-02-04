package de.leuphana.cosa.pricingsystem.structure;

public class Price {
    private Pricable pricable;
    private PriceRate priceRate;

    public Price(Pricable pricable, PriceRate priceRate) {
        this.pricable = pricable;
        this.priceRate = priceRate;
    }

    public Pricable getPricable() {
        return pricable;
    }

    public PriceRate getPriceRate() {
        return priceRate;
    }

    public double calculatePrice() {
        return pricable.getAmount() * 1.45 * priceRate.priceMultiplier();
    }
}
