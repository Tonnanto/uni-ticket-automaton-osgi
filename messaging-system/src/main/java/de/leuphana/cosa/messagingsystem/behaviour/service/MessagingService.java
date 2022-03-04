package de.leuphana.cosa.messagingsystem.behaviour.service;

import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;

public interface MessagingService {
	String MESSAGING_KEY = "message";

	DeliveryReport sendMessage(Sendable sendable);
}