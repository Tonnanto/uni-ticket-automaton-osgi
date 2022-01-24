package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;

public class DocumentToPrintableAdapter {
    PrintingService printingService;

    public DocumentToPrintableAdapter(PrintingService printingService) {
        this.printingService = printingService;
    }

    public void onDocumentCreated(TicketDocumentTemplate document) {
        // convert to printable
        // hand to printingService
    }
}
