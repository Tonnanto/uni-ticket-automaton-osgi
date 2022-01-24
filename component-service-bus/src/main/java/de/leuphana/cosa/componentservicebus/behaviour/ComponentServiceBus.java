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
import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.uisystem.behaviour.service.UiService;
import org.osgi.framework.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ComponentServiceBus implements BundleActivator, ServiceListener, EventHandler {

    static final String ROUTE_CREATED_TOPIC = "routeservice/route/created";
    static final String PRICE_DETERMINED_TOPIC = "priceservice/price/determined";
    static final String DOCUMENT_CREATED_TOPIC = "documentservice/document/created";
    static final String PRINT_REPORT_CREATED_TOPIC = "printingservice/printreport/created";

    private BundleContext bundleContext;

    RouteToPricableAdapter routeToPricableAdapter;
    BookingDetailToDocumentableAdapter bookingDetailToDocumentableAdapter;
    DocumentToPrintableAdapter documentToPrintableAdapter;
    PrintReportToSendableAdapter printReportToSendableAdapter;

    ServiceTracker uiServiceTracker;
    ServiceTracker routeServiceTracker;
    ServiceTracker pricingServiceTracker;
    ServiceTracker documentServiceTracker;
    ServiceTracker printingServiceTracker;
    ServiceTracker messagingServiceTracker;

    private UiService uiService;
    private RouteService routeService;
    private PricingService pricingService;
    private DocumentService documentService;
    private PrintingService printingService;
    private MessagingService messagingService;

    @Override
    public void start(BundleContext bundleContext) {

        this.bundleContext = bundleContext;
        try {
            registerEventHandler();
            setUpServiceTracker();
            startBundles();

            // TODO: initiate ticket order process

            // Test purpose
            routeService = (RouteService) routeServiceTracker.getService();
            uiService = (UiService) uiServiceTracker.getService();

            if (routeService != null) {
                List<Location> locations = routeService.getLocations();
                int selection = uiService.selectStartLocation(locations.stream().map((Location::getName)).toList());
                Location selectedLocation = locations.get(selection);
                System.out.println("Selected: " + selectedLocation.getName());
            }

//            bundleContext.addServiceListener(this, "(objectclass=" + UiService.class.getName() + ")");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void startBundles() throws BundleException {
        if (routeServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT").start();
        }
        if (uiServiceTracker.isEmpty()) {
            bundleContext.getBundle("mvn:de.leuphana.cosa/ui-system/1.0-SNAPSHOT").start();
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
                ROUTE_CREATED_TOPIC,
                PRICE_DETERMINED_TOPIC,
                DOCUMENT_CREATED_TOPIC,
                PRINT_REPORT_CREATED_TOPIC
        });
        bundleContext.registerService(EventHandler.class.getName(), this, properties);
    }

    private void setUpServiceTracker() {
        routeServiceTracker = new ServiceTracker(bundleContext, RouteService.class.getName(), null);
        uiServiceTracker = new ServiceTracker(bundleContext, UiService.class.getName(), null);
        pricingServiceTracker = new ServiceTracker(bundleContext, PricingService.class.getName(), null);
        documentServiceTracker = new ServiceTracker(bundleContext, DocumentService.class.getName(), null);
        printingServiceTracker = new ServiceTracker(bundleContext, PrintingService.class.getName(), null);
        messagingServiceTracker = new ServiceTracker(bundleContext, MessagingService.class.getName(), null);

        routeServiceTracker.open();
        uiServiceTracker.open();
        pricingServiceTracker.open();
        documentServiceTracker.open();
        printingServiceTracker.open();
        messagingServiceTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
//        if (uiSystemBundle != null) {
//            uiSystemBundle.stop();
//            uiSystemBundle.uninstall();
//        }
//        if (routeSystemBundle != null) {
//            routeSystemBundle.stop();
//            routeSystemBundle.uninstall();
//        }
//        if (pricingSystemBundle != null) {
//            pricingSystemBundle.stop();
//            pricingSystemBundle.uninstall();
//        }
//        if (documentSystemBundle != null) {
//            documentSystemBundle.stop();
//            documentSystemBundle.uninstall();
//        }
//        if (printingSystemBundle != null) {
//            printingSystemBundle.stop();
//            printingSystemBundle.uninstall();
//        }
//        if (messagingSystemBundle != null) {
//            messagingSystemBundle.stop();
//            messagingSystemBundle.uninstall();
//        }
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("Event received: " + event.getTopic());

        switch (event.getTopic()) {
            case ROUTE_CREATED_TOPIC:
                // TODO: delegate to adapter
                break;
            case PRICE_DETERMINED_TOPIC:
                // TODO: delegate to adapter
                break;
            case DOCUMENT_CREATED_TOPIC:
                // TODO: delegate to adapter
                break;
            case PRINT_REPORT_CREATED_TOPIC:
                // TODO: delegate to adapter
                break;
            default:
                break;
        }
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        System.out.println("Service changed");
        int type = serviceEvent.getType();
        switch (type){
            case(ServiceEvent.REGISTERED):
                System.out.println("Event: Service registered.");
                ServiceReference serviceReference = serviceEvent.getServiceReference();
                uiService = (UiService) (bundleContext.getService(serviceReference));
                uiService.showPurchaseConfirmation();
                break;
            case(ServiceEvent.UNREGISTERING):
                System.out.println("Event: Service unregistered.");
                bundleContext.ungetService(serviceEvent.getServiceReference());
                break;
            default:
                break;
        }
    }
}
