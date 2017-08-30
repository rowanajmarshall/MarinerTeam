package com.openmarket.mariner.model;

import static com.openmarket.mariner.StaticData.*;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestModelObject {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Rule
    public final ExpectedException except = ExpectedException.none();

    @Test
    public void serializesToJSON() throws Exception {
        Assertions.assertThat(MAPPER.writeValueAsString(A))
                  .isEqualTo(fixture("fixtures/a.json"));
        Assertions.assertThat(MAPPER.writeValueAsString(B))
                  .isEqualTo(fixture("fixtures/b.json"));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        Assertions.assertThat(MAPPER.readValue(fixture("fixtures/a.json"), ModelObject.class))
                  .isEqualTo(A);
        Assertions.assertThat(MAPPER.readValue(fixture("fixtures/b.json"), ModelObject.class))
                  .isEqualTo(B);
    }

    @Test
    public void creatorCannotBeNull() {
        except.expect(NullPointerException.class);
        ModelObject.of(null);
    }

    @Test
    public void creatorCannotBeEmpty() {
        except.expect(IllegalArgumentException.class);
        ModelObject.of("");
    }

    @Test
    public void testEquality() {
        Assertions.assertThat(A).isNotEqualTo(null);
        Assertions.assertThat(A).isNotEqualTo(B);
        Assertions.assertThat(A).isNotEqualTo(new Object());
        Assertions.assertThat(A).isEqualTo(A);
        Assertions.assertThat(A).isEqualTo(ModelObject.of("A"));
        Assertions.assertThat(B).isEqualTo(ModelObject.of("B"));

        Assertions.assertThat(A.hashCode()).isEqualTo(ModelObject.of("A").hashCode());
        Assertions.assertThat(B.hashCode()).isEqualTo(ModelObject.of("B").hashCode());
    }

    @Test
    public void testToString() {
        Assertions.assertThat(A.toString()).contains("A");
    }
}
