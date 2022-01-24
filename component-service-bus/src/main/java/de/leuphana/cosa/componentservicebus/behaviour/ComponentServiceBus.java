package de.leuphana.cosa.componentservicebus.behaviour;

import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingService;
import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.routesystem.behaviour.service.RouteService;
import de.leuphana.cosa.uisystembundle.behaviour.service.UiServiceOsgi;
import de.leuphana.cosa.documentsystembundle.behaviour.service.DocumentServiceOsgi;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class ComponentServiceBus implements BundleActivator, EventHandler {

    private BundleContext bundleContext;

    private Bundle uiSystemBundle;

    private Bundle routeSystemBundle;
    private Bundle pricingSystemBundle;
    private Bundle documentSystemBundle;
    private Bundle printingSystemBundle;
    private Bundle messagingSystemBundle;

    private ServiceReference<UiServiceOsgi> uiServiceReference;
    private ServiceReference<RouteService> routeServiceReference;
    private ServiceReference<PricingService> pricingServiceReference;
    private ServiceReference<DocumentServiceOsgi> documentServiceReference;
    private ServiceReference<PrintingService> printingServiceReference;
    private ServiceReference<MessagingService> messagingServiceReference;

    private UiServiceOsgi uiService;
    private RouteService routeService;
    private PricingService pricingService;
    private DocumentServiceOsgi documentService;
    private PrintingService printingService;
    private MessagingService messagingService;

    String[] eventTopics = new String[]{};

    @Override
    public void start(BundleContext bundleContext) {

        this.bundleContext = bundleContext;
        try {
            registerEventHandler();
            startBundles();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void startBundles() throws Exception {
        uiSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/ui-system-bundle/1.0-SNAPSHOT");
//        routeSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT");
//        pricingSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/pricing-system/1.0-SNAPSHOT");
        documentSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/document-system-bundle/1.0-SNAPSHOT");
//        printingSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/printing-system/1.0-SNAPSHOT");
//        messagingSystemBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/messaging-system/1.0-SNAPSHOT");

        uiSystemBundle.start();
//        routeSystemBundle.start();
//        pricingSystemBundle.start();
        documentSystemBundle.start();
//        printingSystemBundle.start();
//        messagingSystemBundle.start();

        uiServiceReference = uiSystemBundle.getBundleContext().getServiceReference(UiServiceOsgi.class);
//        routeServiceReference = routeSystemBundle.getBundleContext().getServiceReference(RouteService.class);
//        pricingServiceReference = pricingSystemBundle.getBundleContext().getServiceReference(PricingService.class);
        documentServiceReference = documentSystemBundle.getBundleContext().getServiceReference(DocumentServiceOsgi.class);
        Class<?> cls = documentSystemBundle.loadClass("de.leuphana.cosa.documentsystembundle.behaviour.service.DocumentServiceOsgi");
        System.out.println(documentSystemBundle.getBundleContext().getService(documentServiceReference) instanceof DocumentServiceOsgi);
//        printingServiceReference = printingSystemBundle.getBundleContext().getServiceReference(PrintingService.class);
//        messagingServiceReference = messagingSystemBundle.getBundleContext().getServiceReference(MessagingService.class);


        // Test
        uiService = uiSystemBundle.getBundleContext().getService(uiServiceReference);
//        routeService = routeSystemBundle.getBundleContext().getService(routeServiceReference);
//        pricingService = pricingSystemBundle.getBundleContext().getService(pricingServiceReference);
        documentService = documentSystemBundle.getBundleContext().getService(documentServiceReference);
//        printingService = printingSystemBundle.getBundleContext().getService(printingServiceReference);
//        messagingService = messagingSystemBundle.getBundleContext().getService(messagingServiceReference);

        uiService.showPurchaseConfirmation();

//        printingService.print(new Printable() {
//            @Override
//            public String getTitle() {
//                return "null";
//            }
//
//            @Override
//            public List<String> getContent() {
//                return List.of("CONTENT", "ZEILE1", "ZEILE2");
//            }
//        }, new PrintOptions(), new UserAccount());
    }

    private void registerEventHandler() throws Exception {
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put(EventConstants.EVENT_TOPIC, eventTopics);
        bundleContext.registerService(EventHandler.class.getName(), this, properties);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (uiSystemBundle != null) {
            uiSystemBundle.stop();
            uiSystemBundle.uninstall();
        }
        if (routeSystemBundle != null) {
            routeSystemBundle.stop();
            routeSystemBundle.uninstall();
        }
        if (pricingSystemBundle != null) {
            pricingSystemBundle.stop();
            pricingSystemBundle.uninstall();
        }
        if (documentSystemBundle != null) {
            documentSystemBundle.stop();
            documentSystemBundle.uninstall();
        }
        if (printingSystemBundle != null) {
            printingSystemBundle.stop();
            printingSystemBundle.uninstall();
        }
        if (messagingSystemBundle != null) {
            messagingSystemBundle.stop();
            messagingSystemBundle.uninstall();
        }
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("Event received: " + event.getTopic());
    }
}
