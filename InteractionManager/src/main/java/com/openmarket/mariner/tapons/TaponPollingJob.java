package com.openmarket.mariner.tapons;

import com.openmarket.mariner.session.SessionManager;
import com.openmarket.mariner.session.event.TapOnEvent;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Every("1s")
public class TaponPollingJob extends Job {
    private Logger log = LoggerFactory.getLogger(TaponPollingJob.class);

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

        } catch (Exception e) {
            log.error("Tap on failed", e);
        }
    }
}
