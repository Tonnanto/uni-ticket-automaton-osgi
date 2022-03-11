package de.leuphana.cosa.documentsystem.structure;

public class TicketDocumentTemplate {
    final String document;
    String name;

    public TicketDocumentTemplate(Documentable documentable) {
        this.document = buildTicket(documentable.getHeader(), documentable.getBody(), documentable.getFooter());
        this.name = documentable.getName();
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    private String buildTicket(String header, String body, String footer) {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append(String.format("┌───────────────────────────────────┐%n"));
        sb.append(
                String.format(
                        "│ %-33s │%n",
                        header
                )
        );
        sb.append(String.format("├───────────────────────────────────┤%n"));

        // Body
        for (String line : body.lines().toList()) {
            sb.append(
                    String.format(
                            "│ %-33s │%n",
                            line
                    )
            );
        }
        sb.append(String.format("├───────────────────────────────────┤%n"));

        // Footer
        for (String line : footer.lines().toList()) {
            sb.append(
                    String.format(
                            "│ %-33s │%n",
                            line
                    )
            );
        }

        sb.append(String.format("└───────────────────────────────────┘%n"));

        return sb.toString();
    }
}