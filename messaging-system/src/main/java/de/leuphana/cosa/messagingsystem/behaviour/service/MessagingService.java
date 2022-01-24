package de.leuphana.cosa.messagingsystem.behaviour.service;

import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;

public interface MessagingService {
	DeliveryReport sendMessage(Sendable sendable);
}