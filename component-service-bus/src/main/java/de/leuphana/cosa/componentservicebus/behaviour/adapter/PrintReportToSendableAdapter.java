package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

public class PrintReportToSendableAdapter {
    private final MessagingService messagingService;
    private TicketDocumentTemplate ticketDocument;

    public PrintReportToSendableAdapter(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public  void onDocumentCreated(Event event) {
        if(event.getProperty(DocumentService.DOCUMENT_KEY) instanceof TicketDocumentTemplate ticketDocument) {
            this.ticketDocument = ticketDocument;
        }
    }

    public void onPrintReportCreated(Event event) {
        // convert to sendable
        Sendable sendable = new Sendable();
        if(event.getProperty(PrintingService.PRINT_KEY) instanceof PrintReport printReport) {
            sendable.setContent(ticketDocument.getDocument());
        }
        // hand sendable to messagingService for logging
        messagingService.sendMessage(sendable);
    }
}
