package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

import java.util.List;

public class PrintReportToSendableAdapter {
    MessagingService messagingService;

    public PrintReportToSendableAdapter(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void onPrintReportCreated(Event event) {
        if (event.getProperty(PrintingService.PRINT_REPORT_KEY) instanceof PrintReport printReport) {
            Sendable sendable = new Sendable() {
                @Override
                public String getSender() {
                    return "Sender";
                }

                @Override
                public String getReceiver() {
                    return "MessagingService";
                }

                @Override
                public String getContent() {
                    if (!printReport.isPrinted())
                        return "Failed to print: " + printReport.getTicketName();
                    return "Printed successfully: " + printReport.getTicketName();

                }

                @Override
                public MessageType getMessageType() {
                    return MessageType.INSTANT;
                }
            };

            // TODO
            // - Soll außer dem Logging noch eine Message versendet (simuliert) werden?
            // - Was genau soll geloggt werden? Druckbestätigung?
            // - Welche informationen sollen geloggt werden / Welche Attribute kriegt der PrintReport?

            messagingService.logMessage(sendable);
        }
    }
}
