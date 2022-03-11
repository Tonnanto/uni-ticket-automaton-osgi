package de.leuphana.cosa.printingsystem.structure;

import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintAction;
import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintJobState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Printer {
    private final Queue<PrintJob> printJobQueue;
    private ColorType colorType;

    public Printer() {
        printJobQueue = new LinkedList<>();
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
    }

    public void addPrintJob(PrintJob printJob) {
        printJobQueue.add(printJob);
        PrintJobState printJobState = printJob.getPrintJobState();
        printJob.setPrintJobState(printJobState.changePrintJobState(PrintAction.QUEUE));
    }

    public boolean print() {
        PrintJob printJob = printJobQueue.remove();
        PrintJobState printJobState = printJob.getPrintJobState();
        printJobState.changePrintJobState(PrintAction.PRINT);

        System.out.println("Printing ...\n");
        List<String> contentLines = printJob.getPrintable().getContent();
        for (String line : contentLines) {
            System.out.println(line);

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println("\nPrint Job finished!\n");

        return true;
    }

}
