package de.leuphana.cosa.documentsystem.behaviour.service;

import de.leuphana.cosa.documentsystem.structure.BookingDetail;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;

public interface DocumentService {
    String DOCUMENT_CREATED_TOPIC = "documentservice/document/created";

    TicketDocumentTemplate createTicketDocument(BookingDetail bookingDetail);
}
