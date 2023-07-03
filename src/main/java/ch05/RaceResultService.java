package ch05;

import java.util.Collection;
import java.util.HashSet;

public class RaceResultService {
    private Collection<Client> clients = new HashSet<>();

    public void addSubscriber(Client client) {
        clients.add(client);
    }

    public void send(Message message) {
        clients.forEach(client -> client.receive(message));
    }

    public void removeSubscriber(Client client) {
        clients.remove(client);
    }
}
