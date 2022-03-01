package de.leuphana.cosa.messagingsystem.structure;

public interface Sendable {

	public String getSender();
	public String getReceiver();
	public String getContent();
	public MessageType getMessageType();
}