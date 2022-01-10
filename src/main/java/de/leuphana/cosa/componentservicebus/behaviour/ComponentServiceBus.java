package de.leuphana.cosa.componentservicebus.behaviour;

import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import org.osgi.framework.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;

public class ComponentServiceBus implements BundleActivator, ServiceListener, EventHandler {

    private BundleContext bundleContext;
    private ServiceReference printingServiceReference;

    private Bundle printingSystemBundle;
    private Bundle routeSystemBundle;

    String[] eventTopics = new String[]{};

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        this.bundleContext = bundleContext;
        try {
            registerEventHandler();
            startBundles();


//            bundleContext.addServiceListener(this, "(objectclass=" + RouteService.class.getName() + ")");
//            bundleContext.addServiceListener(this, "(objectclass=" + PrintingService.class.getName() + ")");
        } catch (BundleException exception) {
            exception.printStackTrace();
        }
    }

    private void startBundles() throws BundleException {
        printingSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/printing-system/1.0-SNAPSHOT");
        routeSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT");

        printingSystemBundle.start();
        routeSystemBundle.start();
    }

    private void registerEventHandler() {
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put(EventConstants.EVENT_TOPIC, eventTopics);
        bundleContext.registerService(EventHandler.class.getName(), this, properties);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (printingServiceReference != null) {
            bundleContext.ungetService(printingServiceReference);
        }
        if (printingSystemBundle != null) {
            printingSystemBundle.stop();
        }
        if (routeSystemBundle != null) {
            routeSystemBundle.stop();
        }
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
            case(ServiceEvent.REGISTERED):
                System.out.println("Notification of service registered.");
                System.out.println(serviceEvent.getServiceReference().getBundle().getSymbolicName());
                printingServiceReference = serviceEvent.getServiceReference();
                PrintingService service = (PrintingService) (bundleContext.getService(printingServiceReference));
                System.out.println( service.print(null, null, null));
                break;

            case(ServiceEvent.UNREGISTERING):
                System.out.println("Notification of service unregistered.");
                bundleContext.ungetService(serviceEvent.getServiceReference());
                break;

            default:
                break;
        }
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("Event received: " + event.getTopic());
    }
}
