package de.leuphana.cosa.messagingsystem;

import de.leuphana.cosa.messagingsystem.behaviour.MessagingServiceImpl;
import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MessagingServiceTest {

    private static Sendable sendable;
    private static MessagingServiceImpl messagingService;

    @BeforeAll
    static void setUpBeforeClass() {
        messagingService = new MessagingServiceImpl();

        sendable = new Sendable();
        sendable.setContent("This is a message");
    }

    @AfterAll
    static void tearDownAfterClass() {
        messagingService = null;
    }

    @Test
    void canMessageBeSentViaEmail() {
        messagingService.setMessageType(MessageType.EMAIL);
        messagingService.setSender("test@test.de");
        messagingService.setReceiver("rec@test.de");
        Assertions.assertNotNull(messagingService.sendingMessage(sendable));
    }

    @Test
    void canMessageBeSentViaSMS() {
        messagingService.setMessageType(MessageType.SMS);
        messagingService.setSender("012345678899");
        messagingService.setReceiver("987654321");
        Assertions.assertNotNull(messagingService.sendingMessage(sendable));
    }

}
// TODO: 11.03.2022 keine Methode ohne Userinputs. Komponente umschreiben?