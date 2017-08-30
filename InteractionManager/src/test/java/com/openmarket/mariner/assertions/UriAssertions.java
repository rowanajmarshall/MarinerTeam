package com.openmarket.mariner.assertions;

import java.net.URI;
import org.assertj.core.api.Assertions;

public final class UriAssertions {
    private final URI uri;

    private UriAssertions(final URI uri) {
        this.uri = uri;
    }

    public UriAssertions withPath(final String path) {
        Assertions.assertThat(uri.getPath()).isEqualTo(path);
        return this;
    }

    public static UriAssertions assertThat(final URI uri) {
        return new UriAssertions(uri);
    }
}
