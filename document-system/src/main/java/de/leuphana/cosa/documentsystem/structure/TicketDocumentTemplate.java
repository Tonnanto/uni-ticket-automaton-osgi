package de.leuphana.cosa.documentsystem.structure;

import java.time.LocalDate;

public class TicketDocumentTemplate {

	private final String start;
	private final String destination;
	private final int distance;
	private final String priceGroup;
	private final double price;
	private final LocalDate date;

	public TicketDocumentTemplate(String start, String destination, int distance, double price, String priceGroup) {
		this.start = start;
		this.destination = destination;
		this.distance = distance;
		this.price = price;
		this.priceGroup = priceGroup;
		this.date = LocalDate.now();
	}

	public String getStart() {
		return start;
	}

	public String getDestination() {
		return destination;
	}

	public double getPrice() {
		return price;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public int getDistance() {
		return distance;
	}

	public LocalDate getDate() {
		return date;
	}
}