package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Pricable;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

public class PricingServiceImpl implements PricingService, BundleActivator {

    private ServiceReference<PricingService> reference;
    private ServiceRegistration<PricingService> registration;

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering PricingService.");
        registration = bundleContext.registerService(
                PricingService.class,
                new PricingServiceImpl(),
                new Hashtable<String, String>());
        reference = registration
                .getReference();

    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering PricingService.");
        registration.unregister();
    }

    public void selectPriceRate(Pricable pricable) {

        // Let user select price rate
        // Calculate price
        // trigger event (PRICE_DETERMINED_TOPIC)

    }

    @Override
    public double calculatePrice(double distance, PriceRate rate) {
        return distance * 1.45 * rate.priceMultiplier();
    }
}
