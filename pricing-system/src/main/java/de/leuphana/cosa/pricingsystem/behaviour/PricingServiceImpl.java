package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Pricable;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import de.leuphana.cosa.uisystem.structure.SelectionView;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class PricingServiceImpl implements PricingService, BundleActivator {

    private ServiceReference<PricingService> reference;
    private ServiceRegistration<PricingService> registration;
    private ServiceTracker eventAdminTracker;

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering PricingService.");
        registration = bundleContext.registerService(
                PricingService.class,
                this,
                new Hashtable<String, String>());
        reference = registration
                .getReference();

        eventAdminTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        eventAdminTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering PricingService.");
        registration.unregister();
    }

    /**
     * Use Case: Select Price Rate
     * Creates a price object based on the price rate selected by the user.
     * Triggers an event with the "PRICE_DETERMINED_TOPIC" topic once the price is determined.
     */
    public void selectPriceRate(Pricable pricable) {

        // Let user select price rate
        PriceRate priceRate = selectPriceRate(Arrays.stream(PriceRate.values()).toList(), pricable);

        // Calculate price
        Price price = new Price(pricable, priceRate);

        // trigger event (PRICE_DETERMINED_TOPIC)
        EventAdmin eventAdmin = (EventAdmin) eventAdminTracker.getService();

        if (eventAdmin != null) {
            Dictionary<String, Object> content = new Hashtable<>();
            content.put(PRICE_KEY, price);
            eventAdmin.sendEvent(new Event(PRICE_DETERMINED_TOPIC, content));
        } else {
            System.out.println("EventAdmin not found: Event could not be triggered: " + PRICE_DETERMINED_TOPIC);
        }
    }

    /**
     * Prompts the user to select a price rate
     * @param priceRates the price rates the user can choose from
     * @return the selected price rate
     */
    private PriceRate selectPriceRate(List<PriceRate> priceRates, Pricable pricable) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen Sie Ihren Tarif aus.";
            }

            @Override
            protected List<String> getOptions() {
                return priceRates.stream().map((priceRate) -> {
                    Price price = new Price(pricable, priceRate);
                    return String.format("%s: (%.2f€)", priceRate, price.calculatePrice());
                }).collect(Collectors.toList());
            }
        };

        int selectedIndex = view.displaySelection();
        return priceRates.get(selectedIndex);
    }
}
