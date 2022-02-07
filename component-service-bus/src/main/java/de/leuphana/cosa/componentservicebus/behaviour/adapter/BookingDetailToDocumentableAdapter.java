package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.BookingDetail;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Route;
import org.osgi.service.event.Event;

import javax.swing.text.Document;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BookingDetailToDocumentableAdapter {

    DocumentService documentService;
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
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);

        String name = "Ticket " + UUID.randomUUID();
        String header = dateFormat.format(new Date());
        String body = route.getStartLocation().getName() + " -> " + route.getEndLocation().getName() + " (" + (int) route.getDistance() + "km)";
        String footer = String.format("%.2f€ (%s)", price.calculatePrice(), price.getPriceRate().toString());

        Documentable documentable = new Documentable(name, header, body, footer);

        System.out.println(documentable);

        documentService.createDocument(documentable);
    }
}
