package de.leuphana.cosa.documentsystem;

import de.leuphana.cosa.documentsystem.behaviour.DocumentServiceImpl;
import de.leuphana.cosa.documentsystem.structure.BookingDetail;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import org.junit.jupiter.api.*;

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
	@Order(1)
	void canNormalTicketDocumentBeCreatedTest() {
		Documentable documentable = new Documentable("Name", "Header", "Body", "Footer");
		Assertions.assertNotNull(documentSystem.createTicketDocument(documentable));
	}
}