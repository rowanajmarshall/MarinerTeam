package com.openmarket.mariner.it;

import java.net.URI;

public class LocalhostUris {
    private final String applicationBase;

    public LocalhostUris(final int port) {
        applicationBase = String.format("http://localhost:%d/", port);
    }

    public URI sms() {
        return URI.create(applicationBase + "sms");
    }
}
