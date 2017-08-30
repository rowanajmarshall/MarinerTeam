package com.openmarket.mariner.tapons;

import com.openmarket.mariner.session.SessionManager;
import com.openmarket.mariner.session.event.TapOffEvent;
import com.openmarket.mariner.session.event.TapOnEvent;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import java.io.IOException;
import javax.inject.Inject;

@Every("1s")
public class TaponPollingJob extends Job {

    private final SqsReceiver<Tapon> receiver;
    private final SessionManager manager;

    @Inject
    public TaponPollingJob(SqsReceiver<Tapon> receiver, SessionManager manager) {
        this.receiver = receiver;
        this.manager = manager;
    }

    @Override
    public void doJob() {
        try {
            receiver.receive().ifPresent(tapon -> manager.handleTapOnEvent(new TapOnEvent(tapon.getPhoneNumber(), tapon.getStationCode())));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
