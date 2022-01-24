package de.leuphana.cosa.componentservicebus.behaviour.adapter;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class BookingDetailToDocumentableAdapter implements EventHandler {

    String[] eventTopics = new String[]{};

    @Override
    public void handleEvent(Event event) {

    }
}
