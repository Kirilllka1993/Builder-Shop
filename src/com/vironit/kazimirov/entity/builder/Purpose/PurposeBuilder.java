package com.vironit.kazimirov.entity.builder.Purpose;

import com.vironit.kazimirov.entity.Purpose;

public class PurposeBuilder {
    private Purpose purpose;

    public PurposeBuilder() {
        purpose = new Purpose();
    }


    public PurposeBuilder withTitle(String purpose1) {
        purpose.setPurpose(purpose1);
        return this;
    }

    public Purpose build() {
        return purpose;
    }
}
