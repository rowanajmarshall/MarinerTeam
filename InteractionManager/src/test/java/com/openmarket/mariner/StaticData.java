package com.openmarket.mariner;


import com.openmarket.mariner.model.ModelObject;

public final class StaticData {
    public static final ModelObject A = ModelObject.of("A");
    public static final ModelObject B = ModelObject.of("B");

    private StaticData() {
        // no instantiation.
    }
}
