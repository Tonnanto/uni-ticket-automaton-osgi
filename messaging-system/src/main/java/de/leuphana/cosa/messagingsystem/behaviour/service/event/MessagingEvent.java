package de.leuphana.cosa.messagingsystem.behaviour.service.event;

import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;

import java.util.EventObject;

public class MessagingEvent extends EventObject {

    private DeliveryReport deliveryReport;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MessagingEvent(DeliveryReport source) {
        super(source);
        deliveryReport = source;
    }

    public DeliveryReport getDeliveryReport() {
        return deliveryReport;
    }
}
