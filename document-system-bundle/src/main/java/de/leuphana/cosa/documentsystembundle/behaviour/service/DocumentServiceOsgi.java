package de.leuphana.cosa.documentsystembundle.behaviour.service;

import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;

public interface DocumentServiceOsgi {
    TicketDocumentTemplate createTicketDocument(String start, String destination, int distance, double price, String priceGroup);
}