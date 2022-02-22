package de.leuphana.cosa.documentsystem.behaviour;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.uisystem.structure.SelectionView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import java.util.*;

public class DocumentServiceImpl implements DocumentService, BundleActivator {

    private ServiceReference<DocumentService> reference;
    private ServiceRegistration<DocumentService> registration;
    private ServiceTracker eventAdminTracker;

    private final Logger logger;
    private Map<String, TicketDocumentTemplate> documentMap;


    public DocumentServiceImpl() {
        logger = LogManager.getLogger(this.getClass());
        documentMap = new HashMap<>();
    }

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering DocumentService.");
        registration = bundleContext.registerService(
                DocumentService.class,
                this,
                new Hashtable<String, String>());
        reference = registration
                .getReference();

        eventAdminTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        eventAdminTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering DocumentService.");
        registration.unregister();
    }

    @Override
    public void createDocument(Documentable documentable) {

        // Show overview and get user confirmation
        if (confirmTicket(documentable)) {
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

    public TicketDocumentTemplate getDocument(String documentName) {
        return documentMap.get(documentName);
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