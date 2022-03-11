package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.Printable;
import org.osgi.service.event.Event;

import java.util.List;

public class DocumentToPrintableAdapter {
    private final PrintingService printingService;

    public DocumentToPrintableAdapter(PrintingService printingService) {
        this.printingService = printingService;
    }

    public void onDocumentCreated(Event event) {
        if (event.getProperty(DocumentService.DOCUMENT_KEY) instanceof TicketDocumentTemplate ticketDocument) {
            // convert Document to Printable
            Printable printable = new Printable() {
                @Override
                public String getTitle() {
                    return ticketDocument.getName();
                }

                @Override
                public List<String> getContent() {
                    return ticketDocument.getDocument().lines().toList();
                }
            };

            // hand to printingService
            printingService.printPrintable(printable);
        }
    }
}
