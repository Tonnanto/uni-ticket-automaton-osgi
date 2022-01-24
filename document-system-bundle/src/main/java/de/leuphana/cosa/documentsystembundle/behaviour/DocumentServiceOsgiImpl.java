package de.leuphana.cosa.documentsystembundle.behaviour;

import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import de.leuphana.cosa.documentsystembundle.behaviour.service.DocumentServiceOsgi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DocumentServiceOsgiImpl implements DocumentServiceOsgi, BundleActivator {

    private ServiceReference<DocumentServiceOsgi> reference;
    private ServiceRegistration<DocumentServiceOsgi> registration;

    private final Logger logger;
    private Map<String, TicketDocumentTemplate> documentMap;


    public DocumentServiceOsgiImpl() {
        logger = LogManager.getLogger(this.getClass());
        documentMap = new HashMap<>();
    }

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Registering DocumentService.");
        registration = bundleContext.registerService(
                DocumentServiceOsgi.class,
                new DocumentServiceOsgiImpl(),
                new Hashtable<String, String>());
        reference = registration
                .getReference();

    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering DocumentService.");
        registration.unregister();
    }

    // TODO: temporary: return type String
    public TicketDocumentTemplate createTicketDocument(String start, String destination, int distance, double price, String priceGroup) {
        TicketDocumentTemplate ticketDocument = new TicketDocumentTemplate(start, destination, distance, price, priceGroup);

        //logger.info("Document created: " + documentName);
        //todo benötigen wir noch die Map????
        documentMap.put("NormalTest", ticketDocument);


        return ticketDocument;
    }

    public TicketDocumentTemplate getDocument(String documentName) {
        return documentMap.get(documentName);
    }
}
