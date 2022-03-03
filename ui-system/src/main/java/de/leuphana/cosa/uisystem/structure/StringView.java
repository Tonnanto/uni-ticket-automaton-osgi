package de.leuphana.cosa.uisystem.structure;

import java.util.Scanner;

public abstract class StringView extends View{
    public String readIntInput() {

        System.out.print(inputPrefix);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            String enteredString = scanner.next();

            // input number is valid
            return enteredString;
        }

        // this return statement should never be reached. -1 indicates an error
        return null;
    }
}
