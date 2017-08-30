package com.openmarket.mariner.tapons;

import com.openmarket.mariner.session.SessionManager;
import com.openmarket.mariner.session.event.TapOffEvent;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import java.io.IOException;
import javax.inject.Inject;

@Every("1s")
public class TapoffPollingJob extends Job {

    private final SqsReceiver<Tapoff> receiver;
    private final SessionManager manager;

    @Inject
    public TapoffPollingJob(SqsReceiver<Tapoff> receiver, SessionManager manager) {
        this.receiver = receiver;
        this.manager = manager;
    }

    @Override
    public void doJob() {

        try {
            receiver.receive().ifPresent(tapoff -> manager.handleTapOffEvent(new TapOffEvent(tapoff.getPhoneNumber())));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
