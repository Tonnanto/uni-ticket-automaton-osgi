package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Route;
import org.osgi.service.event.Event;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BookingDetailToDocumentableAdapter {

    private final DocumentService documentService;
    private Price price;
    private Route route;

    public BookingDetailToDocumentableAdapter(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void onRouteCreated(Event event) {
        if (event.getProperty(RouteService.ROUTE_KEY) instanceof Route route) {
            this.route = route;

            // Call document service
            callDocumentService();
        }
    }

    public void onPriceDetermined(Event event) {
        if (event.getProperty(PricingService.PRICE_KEY) instanceof Price price) {
            this.price = price;

            // Call document service
            callDocumentService();
        }
    }

    private void callDocumentService() {
        if (route == null || price == null) return;

        Locale locale = new Locale("de", "DE");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        String name = "Ticket " + UUID.randomUUID();
        String header = dateFormat.format(new Date());
        String body = route.getStartLocation().getName() + " -> " + route.getEndLocation().getName() + " (" + (int) route.getDistance() + "km)\n" +
                String.format("%.2f€ (%s)", price.calculatePrice(), price.getPriceRate().toString());
        String footer = "Wir wünschen Ihnen eine\n" +
                "schöne Reise!";

        Documentable documentable = new Documentable(name, header, body, footer);

        documentService.createDocument(documentable);
    }
}
