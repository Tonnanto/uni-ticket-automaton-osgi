package de.leuphana.cosa.componentservicebus.behaviour;


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

    private BundleContext bundleContext;

    ServiceTracker uiServiceTracker;
    ServiceTracker routeServiceTracker;

    private UiService uiService;
    private RouteService routeService;
//    private PricingService pricingService;
//    private DocumentService documentService;
//    private PrintingService printingService;
//    private MessagingService messagingService;

    String[] eventTopics = new String[]{};

    @Override
    public void start(BundleContext bundleContext) {

        this.bundleContext = bundleContext;
        try {
            registerEventHandler();
            setUpServiceTracker();

            if (routeServiceTracker.isEmpty()) {
                bundleContext.getBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT").start();
            }
            if (uiServiceTracker.isEmpty()) {
                bundleContext.getBundle("mvn:de.leuphana.cosa/ui-system/1.0-SNAPSHOT").start();
            }

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

    private void registerEventHandler() throws Exception {
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put(EventConstants.EVENT_TOPIC, eventTopics);
        bundleContext.registerService(EventHandler.class.getName(), this, properties);
    }

    private void setUpServiceTracker() {
        routeServiceTracker = new ServiceTracker(bundleContext, RouteService.class.getName(), null );
        uiServiceTracker = new ServiceTracker(bundleContext, UiService.class.getName(), null );

        routeServiceTracker.open();
        uiServiceTracker.open();
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
