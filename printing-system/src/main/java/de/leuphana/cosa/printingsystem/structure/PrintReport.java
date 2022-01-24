package de.leuphana.cosa.printingsystem.structure;


public class PrintReport {
    PrintOptions printOptions;

    private String name;

    public PrintReport(String name, PrintOptions printOptions) {
        setName(name);
        this.printOptions = printOptions;
    }

    public double getPricePerPage() {
        return printOptions.getPricePerPage();
    }

    public int getNumberOfPages() {
        return printOptions.getNumberOfPages();
    }

    public double getTotalPrice() {
        return printOptions.getTotalPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
