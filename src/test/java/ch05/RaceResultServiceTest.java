package ch05;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RaceResultServiceTest {
    private final RaceResultService raceResultService = new RaceResultService();
    private final Client clientA = mock(Client.class, "clientA");
    private final Client clientB = mock(Client.class, "clientB");
    private final Message message = mock(Message.class);

    // Zero subscribers
    @Test
    void notSubscribedClientShouldNotReceiveMessage() {
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    // One subscriber
    @Test
    void subscribedClientShouldReceiveMessage() {
        raceResultService.addSubscriber(clientA);
        raceResultService.send(message);

        verify(clientA).receive(message);
    }

    // two subscribers
    @Test
    void allSubscribedClientsShouldRecieveMessages() {
        raceResultService.addSubscriber(clientA);
        raceResultService.addSubscriber(clientB);
        raceResultService.send(message);

        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    @Test
    void shouldSendOnlyOneMessageToMultiSubscriber() {
        raceResultService.addSubscriber(clientA);
        raceResultService.addSubscriber(clientA);
        raceResultService.send(message);

        verify(clientA).receive(message);
    }

    @Test
    void unsubscribedClientShouldNotReceiveMessages() {
        raceResultService.addSubscriber(clientA);
        raceResultService.removeSubscriber(clientA);
        raceResultService.send(message);

        verify(clientA, never()).receive(message);
    }
}
