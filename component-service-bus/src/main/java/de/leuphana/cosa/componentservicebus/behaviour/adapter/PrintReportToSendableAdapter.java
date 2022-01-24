package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import org.osgi.framework.BundleContext;

public class PrintReportToSendableAdapter {
    MessagingService messagingService;

    public PrintReportToSendableAdapter(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void onPrintReportCreated(PrintReport printReport) {
        // convert to sendable
        // hand sendable to messagingService for logging
    }
}
