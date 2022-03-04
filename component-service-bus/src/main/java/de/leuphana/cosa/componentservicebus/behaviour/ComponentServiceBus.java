package de.leuphana.cosa.componentservicebus.behaviour;


import de.leuphana.cosa.componentservicebus.behaviour.adapter.BookingDetailToDocumentableAdapter;
import de.leuphana.cosa.componentservicebus.behaviour.adapter.DocumentToPrintableAdapter;
import de.leuphana.cosa.componentservicebus.behaviour.adapter.PrintReportToSendableAdapter;
import de.leuphana.cosa.componentservicebus.behaviour.adapter.RouteToPricableAdapter;
import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.util.tracker.ServiceTracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class ComponentServiceBus implements BundleActivator, EventHandler, LogListener {

    private BundleContext bundleContext;

    RouteToPricableAdapter routeToPricableAdapter;
    BookingDetailToDocumentableAdapter bookingDetailToDocumentableAdapter;
    DocumentToPrintableAdapter documentToPrintableAdapter;
    PrintReportToSendableAdapter printReportToSendableAdapter;

    ServiceTracker logReaderServiceTracker;
    private LogReaderService logReaderService;

    ServiceTracker routeServiceTracker;
    ServiceTracker pricingServiceTracker;
    ServiceTracker documentServiceTracker;
    ServiceTracker printingServiceTracker;
    ServiceTracker messagingServiceTracker;

    private RouteService routeService;
    private PricingService pricingService;
    private DocumentService documentService;
    private PrintingService printingService;
    private MessagingService messagingService;


    @Override
    public void start(BundleContext bundleContext) {
        this.bundleContext = bundleContext;

        try {
            setUpServiceTracker();
            setUpLoggingListener();
            startBundles();
            getServices();
            createAdapter();
            registerEventHandler();

            // initiate ticket order process
            routeService.selectRoute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getServices() {
        routeService = (RouteService) routeServiceTracker.getService();
        pricingService = (PricingService) pricingServiceTracker.getService();
        documentService = (DocumentService) documentServiceTracker.getService();
        printingService = (PrintingService) printingServiceTracker.getService();
        messagingService = (MessagingService) messagingServiceTracker.getService();
    }

    private void createAdapter() {
        routeToPricableAdapter = new RouteToPricableAdapter(pricingService);
        bookingDetailToDocumentableAdapter = new BookingDetailToDocumentableAdapter(documentService);
        documentToPrintableAdapter = new DocumentToPrintableAdapter(printingService);
        printReportToSendableAdapter = new PrintReportToSendableAdapter(messagingService);
    }

    private void startBundles() throws BundleException {
        bundleContext.getBundle("mvn:de.leuphana.cosa/ui-system/1.0-SNAPSHOT").start();
        if (routeServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT").start();
        }
        if (pricingServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/pricing-system/1.0-SNAPSHOT").start();
        }
        if (documentServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/document-system/1.0-SNAPSHOT").start();
        }
        if (printingServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/printing-system/1.0-SNAPSHOT").start();
        }
        if (messagingServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/messaging-system/1.0-SNAPSHOT").start();
        }
    }

    private void registerEventHandler() throws Exception {
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put(EventConstants.EVENT_TOPIC, new String[]{
                RouteService.ROUTE_CREATED_TOPIC,
                PricingService.PRICE_DETERMINED_TOPIC,
                DocumentService.DOCUMENT_CREATED_TOPIC,
                PrintingService.PRINT_REPORT_CREATED_TOPIC,
                MessagingService.MESSAGE_SEND
        });
        bundleContext.registerService(EventHandler.class.getName(), this, properties);
    }

    private void setUpLoggingListener() {
        logReaderService = (LogReaderService) logReaderServiceTracker.getService();
        logReaderService.addLogListener(this);
    }

    private void setUpServiceTracker() {
        routeServiceTracker = new ServiceTracker(bundleContext, RouteService.class.getName(), null);
        pricingServiceTracker = new ServiceTracker(bundleContext, PricingService.class.getName(), null);
        documentServiceTracker = new ServiceTracker(bundleContext, DocumentService.class.getName(), null);
        printingServiceTracker = new ServiceTracker(bundleContext, PrintingService.class.getName(), null);
        messagingServiceTracker = new ServiceTracker(bundleContext, MessagingService.class.getName(), null);

        logReaderServiceTracker = new ServiceTracker(bundleContext, LogReaderService.class.getName(), null);

        routeServiceTracker.open();
        pricingServiceTracker.open();
        documentServiceTracker.open();
        printingServiceTracker.open();
        messagingServiceTracker.open();

        logReaderServiceTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

        logReaderService = (LogReaderService) logReaderServiceTracker.getService();
        logReaderService.removeLogListener(this);
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("Event received: " + event.getTopic());
        System.out.println("Properties: " + Arrays.toString(event.getPropertyNames()));

        switch (event.getTopic()) {
            case RouteService.ROUTE_CREATED_TOPIC:
                routeToPricableAdapter.onRouteCreated(event);
                bookingDetailToDocumentableAdapter.onRouteCreated(event);
                break;

            case PricingService.PRICE_DETERMINED_TOPIC:
                bookingDetailToDocumentableAdapter.onPriceDetermined(event);
                break;

            case DocumentService.DOCUMENT_CREATED_TOPIC:
                documentToPrintableAdapter.onDocumentCreated(event);
                printReportToSendableAdapter.onDocumentCreated(event);
                break;

            case PrintingService.PRINT_REPORT_CREATED_TOPIC:
                printReportToSendableAdapter.onPrintReportCreated(event);
                break;

            case MessagingService.MESSAGE_SEND:
                break;

            default:
                break;
        }
    }

    @Override
    public void logged(LogEntry logEntry) {

        if (logEntry.getException() != null) {
            System.out.println(logEntry.getException().getMessage());
            return;
        }

        if (!logEntry.getLoggerName().equals("Orders")) return;

        // Write logs to local file system
        // TODO: File location?
        try {
            String filePath = System.getProperty("user.home") + "/Desktop/logs/orders.log";

            // Create file if not existing
            File yourFile = new File(filePath);
            yourFile.createNewFile();

            // write log message to file
            FileOutputStream outputStream = new FileOutputStream(filePath, true);
            outputStream.write(logEntry.getMessage().getBytes());

            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
