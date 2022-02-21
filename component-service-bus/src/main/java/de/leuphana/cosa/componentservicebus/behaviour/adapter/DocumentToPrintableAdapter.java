package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.Printable;
import org.osgi.service.event.Event;

import java.util.List;

public class DocumentToPrintableAdapter {
    PrintingService printingService;
    TicketDocumentTemplate ticketDocument;

    public DocumentToPrintableAdapter(PrintingService printingService) {
        this.printingService = printingService;
    }

    public void onDocumentCreated(Event event) {
        if (event.getProperty(PricingService.PRICE_KEY) instanceof TicketDocumentTemplate ticketDocument) {
            this.ticketDocument = ticketDocument;
            Printable printable = new Printable(ticketDocument.getDocument(), ticketDocument.getName()); //Todo Sollte hier nicht auf ein Interface zugegriffen werden??
        }
        // hand to printingService
    }
}
