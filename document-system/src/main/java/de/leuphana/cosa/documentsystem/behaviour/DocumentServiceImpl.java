package de.leuphana.cosa.documentsystem.behaviour;

import de.leuphana.cosa.documentsystem.behaviour.service.DocumentService;
import de.leuphana.cosa.documentsystem.structure.BookingDetail;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DocumentServiceImpl implements DocumentService, BundleActivator {

	private ServiceReference<DocumentService> reference;
	private ServiceRegistration<DocumentService> registration;

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

	}

	@Override
	public void stop(BundleContext bundleContext) {
		System.out.println("Unregistering DocumentService.");
		registration.unregister();
	}

	public void createDocument(BookingDetail bookingDetail) {

		// Show overview and get user confirmation
		// Create Ticket
		// Trigger event (DOCUMENT_CREATED_TOPIC)

	}

	public TicketDocumentTemplate createTicketDocument(BookingDetail bookingDetail) {
		TicketDocumentTemplate ticketDocument = new TicketDocumentTemplate(bookingDetail.getStart(), bookingDetail.getDestination(), bookingDetail.getDistance(), bookingDetail.getPrice(), bookingDetail.getPriceGroup());
		
		//logger.info("Document created: " + documentName);
		//todo benötigen wir noch die Map????
		documentMap.put("NormalTest", ticketDocument);

		
		return ticketDocument;
	}

	public TicketDocumentTemplate getDocument(String documentName) {
		return documentMap.get(documentName);
	}
}