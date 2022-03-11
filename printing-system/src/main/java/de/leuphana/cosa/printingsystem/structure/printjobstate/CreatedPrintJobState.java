package de.leuphana.cosa.printingsystem.structure.printjobstate;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public class CreatedPrintJobState extends PrintJobState {
	
	public CreatedPrintJobState(PrintJob printJob) {
		super(printJob);
	}

	@Override
	public PrintJobState changePrintJobState(PrintAction printAction) {

		if (printAction == PrintAction.QUEUE) {
			return new QueuedPrintJobState(printJob);
		}
		throw new IllegalArgumentException("Unexpected value: " + printAction);
	}

}