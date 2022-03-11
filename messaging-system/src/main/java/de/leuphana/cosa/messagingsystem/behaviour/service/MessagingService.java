package de.leuphana.cosa.messagingsystem.behaviour.service;

import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;

public interface MessagingService {
    String MESSAGING_KEY = "message";
    String MESSAGE_SENT_TOPIC = "messagingservice/message/sent";

    DeliveryReport sendMessage(Sendable sendable);
}