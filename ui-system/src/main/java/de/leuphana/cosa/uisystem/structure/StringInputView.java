package de.leuphana.cosa.uisystem.structure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This View allows the user to enter a String.
 * The entered String can be validated and will be handed to a command.
 */
public abstract class StringInputView extends View {


    /**
     * This method displays the view, reads the user input, and returns the string.
     */
    public String displayStringInput() {
        // display view
        System.out.println(separator);
        System.out.println(getMessage());

        // read user input
        return readStringInput();
    }

    /**
     * Reads a String from the console.
     * Only accepts String values that conform to isValidString.
     *
     * @return the selected integer value
     */
    private String readStringInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            String enteredString = null;
            try {
                enteredString = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!isValidString(enteredString)) {
                // input String is not valid
                System.out.println(getValidationMessage());
//                System.out.print(inputPrefix);
                continue;
            }

            // input String is valid
            return enteredString;
        }
    }

    /**
     * Override this method to validate the input String.
     * Always returns true by default.
     *
     * @param s the input String that should be validated
     * @return whether the String is valid
     */
    protected boolean isValidString(String s) {
        return !s.isEmpty();
    }

    /**
     * Override this method to use a custom message when an invalid String has been entered.
     *
     * @return the validation message
     */
    protected String getValidationMessage() {
        return "Input invalid";
    }
}