package de.leuphana.cosa.printingsystem.structure.printjobstate;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public class PrintedPrintJobState extends PrintJobState {

//	private final Logger logger;

	public PrintedPrintJobState(PrintJob printJob) {
		super(printJob);
//		logger = LogManager.getLogger(this.getClass());
//		logger.info("Print job with document name " + printJob.getPrintable().getTitle() + " printed!");
	}

	@Override
	public PrintJobState changePrintJobState(PrintAction printAction) {
		// TODO ...
		return null;
	}
}