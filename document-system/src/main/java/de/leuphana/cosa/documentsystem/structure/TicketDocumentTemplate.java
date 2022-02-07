package de.leuphana.cosa.documentsystem.structure;

public class TicketDocumentTemplate {
    String document;

    public TicketDocumentTemplate(Documentable documentable) {
        this.document = documentable.getHeader() + "\n" +
                documentable.getBody() + "\n" +
                documentable.getFooter();
    }

    public String getDocument() {
        return document;
    }
}