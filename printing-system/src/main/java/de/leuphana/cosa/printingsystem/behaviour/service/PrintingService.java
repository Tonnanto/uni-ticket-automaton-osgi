package de.leuphana.cosa.printingsystem.behaviour.service;

import de.leuphana.cosa.printingsystem.structure.Printable;

public interface PrintingService {
    String PRINT_REPORT_CREATED_TOPIC = "printingservice/printreport/created";
    String PRINT_REPORT_KEY = "printReport";

    void printPrintable(Printable printable);
}
