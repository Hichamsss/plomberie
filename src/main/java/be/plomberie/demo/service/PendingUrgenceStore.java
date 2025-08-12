package be.plomberie.demo.service;

import be.plomberie.demo.web.dto.UrgenceForm;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PendingUrgenceStore {
    private final Map<String, UrgenceForm> bySession = new ConcurrentHashMap<>();

    public void put(String sessionId, UrgenceForm form) {
        bySession.put(sessionId, form);
    }

    public UrgenceForm pop(String sessionId) {
        return bySession.remove(sessionId);
    }
}
