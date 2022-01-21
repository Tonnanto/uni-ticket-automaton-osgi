package de.leuphana.cosa.uisystembundle.behaviour;

import de.leuphana.cosa.uisystem.structure.SelectionView;
import de.leuphana.cosa.uisystem.structure.View;
import de.leuphana.cosa.uisystembundle.behaviour.service.UiServiceOsgi;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class UiServiceOsgiImpl implements UiServiceOsgi, BundleActivator {

    private ServiceReference<UiServiceOsgi> reference;
    private ServiceRegistration<UiServiceOsgi> registration;

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering UiService Bundle.");
        registration = bundleContext.registerService(
                UiServiceOsgi.class,
                new UiServiceOsgiImpl(),
                new Hashtable<String, String>());
        reference = registration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering UiService.");
        registration.unregister();
    }

    @Override
    public int selectStartLocation(final List<String> startLocations) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen sie Ihren Standort aus.";
            }

            @Override
            protected List<String> getOptions() {
                return startLocations;
            }
        };

        return view.displaySelection();
    }

    @Override
    public int selectEndLocation(final List<String> endLocations) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen sie Ihr Ziel aus.";
            }

            @Override
            protected List<String> getOptions() {
                return endLocations;
            }
        };

        return view.displaySelection();
    }

    @Override
    public int selectPriceRate(final List<String> priceRates) {
        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                return "Bitte wählen sie Ihre Preisgruppe aus.";
            }

            @Override
            protected List<String> getOptions() {
                return priceRates;
            }
        };

        return view.displaySelection();
    }

    @Override
    public void showRouteReview(final String startLocation, final String endLocation, final String distance, final String priceRate, final String price) {
        View view = new View() {
            @Override
            protected String getMessage() {
                return String.format("Route: %s -> %s (%s)\nPreis: %s (%s)", startLocation, endLocation, distance, price, priceRate);
            }
        };

        view.display();
    }

    @Override
    public boolean getPurchaseConfirmation() {
        final List<String> selectionOptions = new ArrayList<>();
        selectionOptions.add("Bestätigen");
        selectionOptions.add("Abbrechen");

        SelectionView view = new SelectionView() {
            @Override
            protected List<String> getOptions() {
                return selectionOptions;
            }

            @Override
            protected String getMessage() {
                return "Buchung bestätigen?";
            }
        };

        return view.displaySelection() == 0;
    }

    @Override
    public void showPurchaseConfirmation() {
        View view = new View() {
            @Override
            protected String getMessage() {
                return "Vielen Dank für Ihre Ticket Buchung.\n Wir wünschen Ihnen eine schöne Fahrt!\nAuf Wiedersehen!";
            }
        };

        view.display();
    }
}
