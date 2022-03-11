package de.leuphana.cosa.messagingsystem.behaviour.service.event;

public interface MessagingEventService {
    void addMessagingEventListener(MessagingEventListener messagingEventListener);

    void removeMessagingEventListener(MessagingEventListener messagingEventListener);
}
