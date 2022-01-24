package de.leuphana.cosa.documentsystem.behaviour.service;

import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;

public interface DocumentService {
    TicketDocumentTemplate createTicketDocument(String start, String destination, int distance, double price, String priceGroup);
}
