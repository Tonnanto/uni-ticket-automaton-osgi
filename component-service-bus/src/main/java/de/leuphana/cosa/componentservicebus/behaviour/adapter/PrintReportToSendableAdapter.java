package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

public class PrintReportToSendableAdapter {
    MessagingService messagingService;

    public PrintReportToSendableAdapter(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void onPrintReportCreated(Event event) {
        // convert to sendable
        Sendable sendable = new Sendable();
        if(event.getProperty(PrintingService.PRINT_KEY) instanceof PrintReport printReport) {
            sendable.setContent(printReport.getTicketName());
        }
        // hand sendable to messagingService for logging
        messagingService.sendMessage(sendable);
    }
}
