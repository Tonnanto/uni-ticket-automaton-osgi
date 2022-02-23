package de.leuphana.cosa.printingsystem.structure;


public class PrintReport {
    private final String timestamp;
    private final String ticketName;
    private final boolean isPrinted;

    public PrintReport(String timestamp, String ticketName, boolean isPrinted) {
        this.timestamp = timestamp;
        this.ticketName = ticketName;
        this.isPrinted = isPrinted;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTicketName() {
        return ticketName;
    }

    public boolean isPrinted() {
        return isPrinted;
    }
}
