package com.openmarket.mariner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ApiModel( value = "ModelObject", description = "Object model" )
public final class ModelObject {

    @JsonProperty(value="field", required=true)
    @ApiModelProperty( value = "A string field", example = "some text", required = true )
    private final String field;

    @JsonCreator
    private ModelObject(@JsonProperty(value="field", required=true) String field) {
        Preconditions.checkNotNull(field);
        Preconditions.checkArgument(!field.isEmpty(), "Field cannot be empty");

        this.field = field;
    }

    public String field() {
        return field;
    }

    @JsonCreator
    public static ModelObject of(String field) {
        return new ModelObject(field);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        ModelObject other = (ModelObject) o;
        return new EqualsBuilder().append(this.field, other.field).build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(field).build();
    }

    @Override
    public String toString() {
        return "ModelObject [" + field + "]";
    }
}
