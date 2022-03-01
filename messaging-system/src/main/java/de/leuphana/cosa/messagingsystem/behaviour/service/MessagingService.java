package de.leuphana.cosa.messagingsystem.behaviour.service;

import de.leuphana.cosa.messagingsystem.structure.Sendable;

public interface MessagingService {
	void logMessage(Sendable sendable);
}