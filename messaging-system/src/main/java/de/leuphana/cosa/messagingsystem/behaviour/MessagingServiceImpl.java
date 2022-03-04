package de.leuphana.cosa.messagingsystem.behaviour;

import de.leuphana.cosa.messagingsystem.behaviour.service.MessagingService;
import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.messagingsystem.structure.message.Message;
import de.leuphana.cosa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.cosa.messagingsystem.structure.messagingprotocol.MessagingProtocol;
import de.leuphana.cosa.uisystem.structure.SelectionView;
import de.leuphana.cosa.uisystem.structure.StringInputView;
import de.leuphana.cosa.uisystem.structure.View;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MessagingServiceImpl implements MessagingService, BundleActivator {

    private ServiceReference<MessagingService> reference;
    private ServiceRegistration<MessagingService> registration;

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

        SelectionView selectionView = new SelectionView() {
            @Override
            protected List<String> getOptions() {
                List<String> option = new ArrayList<>();
                option.add("Keine Quittung");
                option.add("via E-Mail");
                option.add("via SMS");
                return option;
            }

            @Override
            protected String getMessage() {
                return "Möchten Sie eine Quittung zugestellt bekommen?";
            }
        };
        int selectedIndex = selectionView.displaySelection();
        if (selectedIndex != 0) {
            switch (selectedIndex) {
                case 1:
                    sendable.setMessageType(MessageType.EMAIL);
                    sendable.setSender("ticket@automat.de");
                    StringInputView stringView = new StringInputView() {
                        @Override
                        protected String getMessage() {
                            return "Geben Sie ihre E-Mail Adresse ein: ";
                        }

                        @Override
                        protected boolean isValidString(String s) {
                            return super.isValidString(s); // TODO: email validation?
                        }

                        @Override
                        protected String getValidationMessage() {
                            return "Bitte geben Sie eine gültige Email Adresse ein";
                        }
                    };
                    sendable.setReceiver(stringView.displayStringInput());
                    break;
                case 2:
                    sendable.setMessageType(MessageType.SMS);
                    sendable.setSender("0152242069");
                    stringView = new StringInputView() {
                        @Override
                        protected String getMessage() {
                            return "Geben Sie ihre Handynummer ein: ";
                        }

                        @Override
                        protected boolean isValidString(String s) {
                            return super.isValidString(s); // TODO: email validation?
                        }

                        @Override
                        protected String getValidationMessage() {
                            return "Bitte geben Sie eine gültige Handynummer ein";
                        }
                    };
                    sendable.setReceiver(stringView.displayStringInput());
                    break;
                default:
                    break;
            }
            AbstractMessagingFactory abstractMessagingFactory = AbstractMessagingFactory.getFactory(sendable.getMessageType());

            Message message = abstractMessagingFactory.createMessage(sendable.getSender(), sendable.getReceiver(), sendable.getContent());

            MessagingProtocol messageProtocol = abstractMessagingFactory.createMessagingProtocol();
            messageProtocol.open();
            messageProtocol.transfer(message);
            View view = new View() {
                @Override
                protected String getMessage() {
                    return "Sending ...";
                }
            };
            view.display();
            messageProtocol.close();


            logger.info("Message: " + sendable.getContent() + " transported via " + sendable.getMessageType());

            DeliveryReport deliveryReport = new DeliveryReport();
            deliveryReport.setSender(sendable.getSender());
            deliveryReport.setReceiver(sendable.getReceiver());
            deliveryReport.setContent(sendable.getContent());
            deliveryReport.setMessageType(sendable.getMessageType().toString());

//		for (MessagingEventListener listener: listeners) {
//			listener.onMessageSent(new MessagingEvent(deliveryReport));
//		}

            return deliveryReport;
        } else return null;
    }
}
