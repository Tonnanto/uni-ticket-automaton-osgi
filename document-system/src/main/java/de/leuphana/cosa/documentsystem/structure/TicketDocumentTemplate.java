package de.leuphana.cosa.documentsystem.structure;

public class TicketDocumentTemplate {
    String document;
    String name;

    public TicketDocumentTemplate(Documentable documentable) {
        this.document = documentable.getHeader() + "\n" +
                documentable.getBody() + "\n" +
                documentable.getFooter();
        this.name = documentable.getName();
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }
}