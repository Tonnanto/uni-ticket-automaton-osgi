package de.leuphana.cosa.messagingsystem.structure.communicationpartner;

public class EmailReceiver implements Receiver {
	// sp�ter Role-Object-Pattern
	private String name;
	// TODO statt String sp�ter event. Address
	private String address;
	
	public EmailReceiver(String receiverAddress) {
		this.address = receiverAddress;
	}
}
