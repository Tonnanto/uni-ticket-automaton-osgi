package de.leuphana.cosa.documentsystem;

import de.leuphana.cosa.documentsystem.behaviour.DocumentServiceImpl;
import de.leuphana.cosa.documentsystem.structure.BookingDetail;
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
		BookingDetail bookingDetail = new BookingDetail("Berlin", "Hamburg", 120, 69.99, "Normal");
		Assertions.assertNotNull(documentSystem.createTicketDocument(bookingDetail));
	}
}