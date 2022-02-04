package de.leuphana.cosa.utilities.view;

/**
 * Views handle user interaction with the console.
 * This basic View class can be used to display a message to the console.
 * See SelectionView and StringInputView in order to handle user inputs.
 */
public abstract class View {

    protected final static String separator = "\n----------------------------------------------------";
    protected final static String inputPrefix = ">>> ";

    /**
     * @return the message that will be displayed to the user
     */
    protected abstract String getMessage();

    /**
     * displays the view to the console.
     */
    public void display() {
        System.out.println(separator);
        System.out.println(getMessage());
    }
}
