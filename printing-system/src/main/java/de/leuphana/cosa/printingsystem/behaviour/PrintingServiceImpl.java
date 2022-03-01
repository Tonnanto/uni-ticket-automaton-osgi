package de.leuphana.cosa.printingsystem.behaviour;

import de.leuphana.cosa.printingsystem.behaviour.service.PrintingService;
import de.leuphana.cosa.printingsystem.structure.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import java.time.LocalDate;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class PrintingServiceImpl implements PrintingService, BundleActivator {

    private ServiceReference<PrintingService> reference;
    private ServiceRegistration<PrintingService> registration;
    private ServiceTracker eventAdminTracker;

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
                this,
                new Hashtable<String, String>());
        reference = registration
                .getReference();

        eventAdminTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        eventAdminTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering PrintingService.");
        registration.unregister();
    }

    /**
     * Use Case: Print Printable
     * Prints the given Printable and returns a PrintReport
     * Triggers an event with the "PRINT_REPORT_CREATED_TOPIC" topic once the document is printed.
     */
    public void printPrintable(Printable printable) {

		// Trigger printing process
        PrintReport printReport = print(printable);

        // Create PrintReport (timestamp, ticketname, isPrinted)
        EventAdmin eventAdmin = (EventAdmin) eventAdminTracker.getService();

        if (eventAdmin != null) {
            Dictionary<String, Object> content = new Hashtable<>();
            content.put(PRINT_REPORT_KEY, printReport);
            eventAdmin.sendEvent(new Event(PRINT_REPORT_CREATED_TOPIC, content));
        } else {
            System.out.println("EventAdmin not found: Event could not be triggered: " + PRINT_REPORT_CREATED_TOPIC);
        }
    }

    /**
     * Creates a PrintJob and hands it to the selected printer
     * @param printable the Printable to print
     * @return the created PrintReport
     */
	public PrintReport print(Printable printable) {

		PrintJob printJob = new PrintJob(printable);
		// Suche des richtigen Druckers (simuliert)
		Printer selectedPrinter = null;
		for (Printer printer : printers) {
			// if( ) {
			selectedPrinter = printer;
			// }
		}

		assert selectedPrinter != null;
		selectedPrinter.addPrintJob(printJob);

		PrintReport printReport;

		if (selectedPrinter.print()) {
			printReport = new PrintReport(LocalDate.now().toString(), printable.getTitle(), true);
		} else {
            printReport = new PrintReport(LocalDate.now().toString(), printable.getTitle(), false);
        }

		return printReport;
	}
}