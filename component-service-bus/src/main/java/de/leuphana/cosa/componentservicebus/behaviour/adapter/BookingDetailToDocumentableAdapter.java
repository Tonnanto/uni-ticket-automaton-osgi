package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.BookingDetail;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.routesystem.structure.Route;
import org.osgi.service.event.Event;

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

        BookingDetail bookingDetail = new BookingDetail(
                route.getStartLocation().getName(),
                route.getEndLocation().getName(),
                (int) route.getDistance(),
                price.calculatePrice(),
                price.getPriceRate().toString()
        );

        System.out.println(bookingDetail);

        // TODO
        // Note: Ich glaube wir sollten ein Documentable mit Header, Body, Footer übergeben
        // BookingDetails fühlen sich falsch an, dadurch würde der DocumentService ja von der Fachlichkeit der anderen Services erfahren.
//         documentService.createDocument(bookingDetail);
    }
}
