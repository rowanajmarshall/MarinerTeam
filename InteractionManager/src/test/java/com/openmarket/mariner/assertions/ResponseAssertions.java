package com.openmarket.mariner.assertions;

import java.net.URI;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.assertj.core.api.Assertions;

public final class ResponseAssertions {
    private final Response response;

    private ResponseAssertions(Response response) {
        this.response = response;
    }

    public ResponseAssertions hasHeader(String header, String expectedValue) {
        Assertions.assertThat(response.getHeaderString(header)).isEqualTo(expectedValue);
        return this;
    }
    
    public ResponseAssertions hasStatus(Response.Status status) {
        Assertions.assertThat(response.getStatus()).isEqualTo(status.getStatusCode());
        return this;
    }

    public <T> ResponseAssertions hasEntity(Class<T> entityType, T entity) {
        Assertions.assertThat(response.readEntity(entityType)).isEqualTo(entity);
        return this;
    }

    public <T> ResponseAssertions hasEntity(GenericType<T> entityType, T entity) {
        Assertions.assertThat(response.readEntity(entityType)).isEqualTo(entity);
        return this;
    }

    public UriAssertions hasUriHeader(String header) {
        return UriAssertions.assertThat(URI.create(response.getHeaderString(header)));
    }

    public static ResponseAssertions assertThat(Response response) {
        return new ResponseAssertions(response);
    }
}
