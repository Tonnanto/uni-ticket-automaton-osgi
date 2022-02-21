package de.leuphana.cosa.printingsystem.structure;

import java.util.List;

public class Printable {
    private String Title;
    private String Content;

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public Printable(String title, String content) {
        Title = title;
        Content = content;
    }
}
