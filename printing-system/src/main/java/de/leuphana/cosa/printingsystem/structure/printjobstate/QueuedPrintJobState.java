package de.leuphana.cosa.printingsystem.structure.printjobstate;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public class QueuedPrintJobState extends PrintJobState {

	public QueuedPrintJobState(PrintJob printJob) {
		super(printJob);
	}

	@Override
	public PrintJobState changePrintJobState(PrintAction printAction) {
		switch (printAction) {
			case PRINT -> new PrintedPrintJobState(printJob);
			case PAUSE -> new PausedPrintJobState(printJob);
			case CANCEL -> new CanceledPrintJobState(printJob);
			default -> throw new IllegalArgumentException("Unexpected value: " + printAction);
		}
		return null;
	}

}