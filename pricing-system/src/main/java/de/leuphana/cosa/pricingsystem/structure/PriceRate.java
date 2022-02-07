package de.leuphana.cosa.pricingsystem.structure;

public enum PriceRate {
    NORMAL,
    CHEAP_TRAVEL,
    BARGAIN;

    @Override
    public String toString() {
        return switch(this) {
            case NORMAL -> "Normal-Tarif";
            case CHEAP_TRAVEL -> "GünstigerReisen-Tarif";
            case BARGAIN -> "Schnäppchen-Tarif";
        };
    }

    public double priceMultiplier() {
        return switch(this) {
            case NORMAL -> 1;
            case CHEAP_TRAVEL -> 0.75;
            case BARGAIN -> 0.5;
        };
    }
}
