package com.openmarket.mariner.session;

import com.openmarket.mariner.tapons.Tapon;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionManager {
    private SessionFactory sessionFactory;
    public ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    public SessionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void initiate(Tapon tapon) {
        final Session initialSession = sessionFactory.create(tapon.getPhoneNumber(), tapon.getStationCode());

        Optional.ofNullable(sessions.put(tapon.getPhoneNumber(), initialSession))
            .ifPresent(oldSession -> oldSession.tapoff());

        initialSession.tapon();
    }

    public Session sessionFor(String phoneNumber) {
        return sessions.get(phoneNumber);
    }
}
