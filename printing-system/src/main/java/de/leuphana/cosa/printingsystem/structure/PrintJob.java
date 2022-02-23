package de.leuphana.cosa.printingsystem.structure;

import de.leuphana.cosa.printingsystem.structure.printjobstate.CreatedPrintJobState;
import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintJobState;


public class PrintJob {

//    private final Logger logger;
    private Printable printable;
    private PrintJobState printJobState;

    public PrintJob(Printable printable) {
//        logger = LogManager.getLogger(this.getClass());
        this.printable = printable;
        this.printJobState = new CreatedPrintJobState(this);
//        logger.info("Print job with printable name " + this.printable.getTitle() + " created!");
    }

    public Printable getPrintable() {
        return printable;
    }

    public PrintJobState getPrintJobState() {
        return printJobState;
    }

    public void setPrintJobState(PrintJobState printJobState) {
        this.printJobState = printJobState;
    }

    public void setPrintable(Printable printable) {
        this.printable = printable;
    }
}