package de.leuphana.cosa.printingsystem.behaviour.service;

import de.leuphana.cosa.printingsystem.structure.PrintOptions;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.UserAccount;

public interface PrintingService {
    PrintReport print(Printable printable, PrintOptions printOptions, UserAccount userAccount);
}
