package de.leuphana.cosa.messagingsystem;

import de.leuphana.cosa.messagingsystem.behaviour.MessagingServiceImpl;
import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.simple.SimpleLoggerContext;
import org.apache.logging.log4j.spi.LoggerContext;
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

        sendable = new Sendable() {
            @Override
            public String getSender() {
                return "rainer.zufall@web.de";
            }

            @Override
            public String getReceiver() {
                return "slotos@leuphana.de";
            }

            @Override
            public String getContent() {
                return "This is a message";
            }

            @Override
            public MessageType getMessageType() {
                return MessageType.INSTANT;
            }
        };
    }

    @AfterAll
    static void tearDownAfterClass() {
        messagingService = null;
    }

    @Test
    void canMessageBeSent() {
        Assertions.assertNotNull(messagingService.sendMessage(sendable));
    }

	@Test
	void canOrderBeLogged() {

        LoggerContext context = LogManager.getContext();
        Assertions.assertFalse(context instanceof SimpleLoggerContext);

    	messagingService.logMessage(sendable);
	}
}
