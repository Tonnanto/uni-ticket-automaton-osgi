package de.leuphana.cosa.componentservicebus.behaviour;

import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import org.osgi.framework.*;

public class ComponentServiceBus implements BundleActivator, ServiceListener {

    private BundleContext bundleContext;
    private ServiceReference serviceReference;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        this.bundleContext = bundleContext;
        try {
            bundleContext.addServiceListener(this, "(objectclass=" + PrintingService.class.getName() + ")");
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if(serviceReference != null) {
            bundleContext.ungetService(serviceReference);
        }
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
            case(ServiceEvent.REGISTERED):
                System.out.println("Notification of service registered.");
                serviceReference = serviceEvent.getServiceReference();
                PrintingService service = (PrintingService) (bundleContext.getService(serviceReference));
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
}
