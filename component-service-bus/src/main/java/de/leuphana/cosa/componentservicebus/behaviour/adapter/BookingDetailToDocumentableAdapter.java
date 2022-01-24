package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.pricingsystem.structure.PriceRate;
import de.leuphana.cosa.routesystem.structure.Route;

public class BookingDetailToDocumentableAdapter {

    DocumentService documentService;

    public BookingDetailToDocumentableAdapter(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void onRouteCreated(Route route) {

    }

    public void onPriceDetermined(PriceRate priceRate, double price) {

    }
}
