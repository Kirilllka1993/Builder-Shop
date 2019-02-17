package com.vironit.kazimirov.entity.builder.Subsection;

import com.vironit.kazimirov.entity.Subsection;

public class SubsectionBuilder {
    private Subsection subsection;

    public SubsectionBuilder() {
        subsection = new Subsection();
    }

    public SubsectionBuilder withTitle(String title) {
        subsection.setTitle(title);
        return this;
    }

    public Subsection build() {
        return subsection;
    }


}
