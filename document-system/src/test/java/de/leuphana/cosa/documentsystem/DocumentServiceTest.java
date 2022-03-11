package de.leuphana.cosa.documentsystem;

import de.leuphana.cosa.documentsystem.behaviour.DocumentServiceImpl;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.documentsystem.structure.TicketDocumentTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

class DocumentServiceTest {

    private DocumentServiceImpl documentSystem;

    @BeforeEach
    void setUp() {
        documentSystem = new DocumentServiceImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canTicketDocumentBeCreatedTest() {

        Locale locale = new Locale("de", "DE");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        Documentable documentable = new Documentable("Name", dateFormat.format(new Date()), "Hamburg -> Berlin (248km)\n" +
                "360,41€ (Normal-Tarif)", "Wir wünschen Ihnen eine\n" +
                "schöne Reise!");

        TicketDocumentTemplate document = documentSystem.createTicketDocument(documentable);
        System.out.println(document.getDocument());
        Assertions.assertNotNull(document);
    }
}