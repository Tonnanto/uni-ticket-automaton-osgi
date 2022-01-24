package de.leuphana.cosa.printingsystem.behaviour.service.event;

import de.leuphana.cosa.printingsystem.structure.PrintReport;

import java.util.EventObject;

public class PrintingEvent extends EventObject {
    PrintReport printReport;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PrintingEvent(PrintReport source) {
        super(source);
        printReport = source;
    }

    public PrintReport getPrintReport() {
        return printReport;
    }
}
