package de.leuphana.cosa.messagingsystem.behaviour;

import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.messagingsystem.structure.message.Message;
import de.leuphana.cosa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.cosa.messagingsystem.structure.messagingprotocol.MessagingProtocol;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

public class MessagingServiceImpl implements MessagingService, BundleActivator {

	private ServiceReference<MessagingService> reference;
	private ServiceRegistration<MessagingService> registration;

//	private Logger logger;

	@Override
	public void start(BundleContext bundleContext) {
		System.out.println("Registering MessagingService.");
		registration = bundleContext.registerService(
				MessagingService.class,
				this,
				new Hashtable<String, String>());
		reference = registration
				.getReference();

	}

	@Override
	public void stop(BundleContext bundleContext) {
		System.out.println("Unregistering MessagingService.");
		registration.unregister();
	}

	@Override
	public DeliveryReport sendMessage(Sendable sendable) {
//		logger = LogManager.getLogger(this.getClass());
		
		AbstractMessagingFactory abstractMessagingFactory = AbstractMessagingFactory.getFactory(sendable.getMessageType());

		Message message = abstractMessagingFactory.createMessage(sendable.getSender(), sendable.getReceiver(), sendable.getContent());

		MessagingProtocol messageProtocol = abstractMessagingFactory.createMessagingProtocol();
		messageProtocol.open();
		messageProtocol.transfer(message);
		messageProtocol.close();

//		logger.info("Message: " + sendable.getContent() + " transported via " + sendable.getMessageType());

		DeliveryReport deliveryReport = new DeliveryReport();
		deliveryReport.setSender(sendable.getSender());
		deliveryReport.setReceiver(sendable.getReceiver());
		deliveryReport.setContent(sendable.getContent());
		deliveryReport.setMessageType(sendable.getMessageType().toString());

//		for (MessagingEventListener listener: listeners) {
//			listener.onMessageSent(new MessagingEvent(deliveryReport));
//		}

		return deliveryReport;
	}
}
