package com.openmarket.mariner.tapons;

import com.openmarket.mariner.session.SessionManager;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import java.io.IOException;
import javax.inject.Inject;

@Every("1s")
public class TaponPollingJob extends Job {

    private final TaponReceiver receiver;
    private final SessionManager manager;

    @Inject
    public TaponPollingJob(TaponReceiver receiver, SessionManager manager) {
        this.receiver = receiver;
        this.manager = manager;
    }

    @Override
    public void doJob() {
        try {
            receiver.receive()
                    .ifPresent(tapon -> manager.initiate(tapon));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
