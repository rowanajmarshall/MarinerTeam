package com.openmarket.mariner.tapons;

import com.openmarket.mariner.session.SessionManager;
import com.openmarket.mariner.session.event.DisruptionEvent;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Every("1s")
public class DisruptPollingJob extends Job {
    private Logger log = LoggerFactory.getLogger(DisruptPollingJob.class);

    private final SqsReceiver<Disrupt> receiver;
    private final SessionManager manager;

    @Inject
    public DisruptPollingJob(SqsReceiver<Disrupt> receiver, SessionManager manager) {
        this.receiver = receiver;
        this.manager = manager;
    }

    @Override
    public void doJob() {

        try {
            receiver.receive().ifPresent(disrupt -> manager.handleDisruptionEvent(new DisruptionEvent(disrupt.getPhoneNumber())));

        } catch (Exception e) {
            log.error("Error handling disruptions", e);
        }
    }
}
