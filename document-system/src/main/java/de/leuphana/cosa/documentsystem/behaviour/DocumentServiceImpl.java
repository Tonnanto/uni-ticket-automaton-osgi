package de.leuphana.cosa.documentsystem.behaviour;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.uisystem.structure.SelectionView;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;
import org.osgi.util.tracker.ServiceTracker;

import java.text.SimpleDateFormat;
import java.util.*;

public class DocumentServiceImpl implements DocumentService, BundleActivator {

    private ServiceRegistration<DocumentService> registration;
    private ServiceTracker eventAdminTracker;
    private ServiceTracker loggerFactoryTracker;

    public DocumentServiceImpl() {
    }

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering DocumentService.");
        registration = bundleContext.registerService(
                DocumentService.class,
                this,
                new Hashtable<String, String>());

        eventAdminTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        eventAdminTracker.open();

        loggerFactoryTracker = new ServiceTracker(bundleContext, LoggerFactory.class.getName(), null);
        loggerFactoryTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering DocumentService.");
        registration.unregister();
    }

    /**
     * Use Case: Create Document
     * Prompts user confirmation
     * Sends a log with details about the document
     * Creates a TicketDocumentTemplate from the given documentable object
     * Triggers an event with the "DOCUMENT_CREATED_TOPIC" topic once the document is printed.
     */
    @Override
    public void createDocument(Documentable documentable) {

        // Show overview and get user confirmation
        if (confirmTicket(documentable)) {
            // Log new Order
            logDocumentableCreation(documentable);

            // Create Ticket
            TicketDocumentTemplate ticketDocumentTemplate = createTicketDocument(documentable);

            // Trigger event (DOCUMENT_CREATED_TOPIC)
            EventAdmin eventAdmin = (EventAdmin) eventAdminTracker.getService();

            if (eventAdmin != null) {
                Dictionary<String, Object> content = new Hashtable<>();
                content.put(DOCUMENT_KEY, ticketDocumentTemplate);
                eventAdmin.sendEvent(new Event(DOCUMENT_CREATED_TOPIC, content));
            } else {
                System.out.println("EventAdmin not found: Event could not be triggered: " + DOCUMENT_CREATED_TOPIC);
            }
        }
    }

    public TicketDocumentTemplate createTicketDocument(Documentable documentable) {
        return new TicketDocumentTemplate(documentable);
    }

    /**
     * Sends log to the CSB with documentable detail: (timestamp, document name, route, price)
      */
    private void logDocumentableCreation(Documentable documentable) {
        LoggerFactory loggerFactory = (LoggerFactory) loggerFactoryTracker.getService();

        if (loggerFactory != null) {
            Logger logger = loggerFactory.getLogger("Orders");

            String logMessage = "\n" + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) +
                    " - " + documentable.getName() + "; " +
                    documentable.getBody().replace("\n", "; ");
            logger.audit(logMessage);
        } else {
            System.out.println("LoggerFactory not found: logger could not be triggered: " + this.getClass());
        }
    }

    private boolean confirmTicket(Documentable documentable) {
        SelectionView selectionView = new SelectionView() {
            @Override
            protected List<String> getOptions() {
                List<String> option = new ArrayList<>();
                option.add("Nein");
                option.add("Ja");
                return option;
            }

            @Override
            protected String getMessage() {
                return "Sind alle Eingaben korrekt? \n \n" +
                        documentable.getHeader() + "\n" +
                        documentable.getBody() + "\n" +
                        documentable.getFooter();
            }
        };
        int selectedIndex = selectionView.displaySelection();
        return selectedIndex != 0;
    }
}