package de.leuphana.cosa.printingsystem.behaviour;

import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class PrintingServiceImpl implements PrintingService, BundleActivator {

	private ServiceReference<PrintingService> reference;
	private ServiceRegistration<PrintingService> registration;

	private final Set<Printer> printers;

	public PrintingServiceImpl() {
		printers = new HashSet<>();
		Printer printer = new Printer();
		printer.setColorType(ColorType.BLACK_WHITE);
		printers.add(printer);
	}

	@Override
	public void start(BundleContext bundleContext) {
		System.out.println("Registering PrintingService.");
		registration = bundleContext.registerService(
				PrintingService.class,
				new PrintingServiceImpl(),
				new Hashtable<String, String>());
		reference = registration
				.getReference();

	}

	@Override
	public void stop(BundleContext bundleContext) {
		System.out.println("Unregistering PrintingService.");
		registration.unregister();
	}

	public PrintReport print(Printable printable, PrintOptions printOptions, UserAccount userAccount) {

//		Check user account balance
		if (checkUserAccountBalance(userAccount) < printOptions.getTotalPrice()) return null;
		
		// TODO check if user account balance is positive
//		Check printer resources
//		Withdraw amount from user account
//		Create print job
//		Send print job to printer
//		Show print confirmation
		
		
		PrintJob printJob = new PrintJob(printable, printOptions);
		// Suche des richtigen Druckers (simuliert)
		Printer selectedPrinter = null;
		for (Printer printer : printers) {
			// if( ) {
			selectedPrinter = printer;
			// }
		}
		assert selectedPrinter != null;
		selectedPrinter.addPrintJob(printJob);

		PrintReport printReport = null;

		if (selectedPrinter.print()) {
			String name = "PrintReport for " + printJob.getPrintable().getTitle();
			printReport = new PrintReport(name, printOptions);
		}

//		for (PrintingEventListener listener: listeners) {
//			listener.onPrintReportCreated(new PrintingEvent(printReport));
//		}

		return printReport;
	}

	private Double checkUserAccountBalance(UserAccount userAccount) {
		return userAccount.getAccountBalance();
	}
}