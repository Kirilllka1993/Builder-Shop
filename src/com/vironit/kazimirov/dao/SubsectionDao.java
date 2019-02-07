package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Subsection;

import com.vironit.kazimirov.entity.builder.Subsection.SubsectionBuilder;


import java.util.ArrayList;
import java.util.List;

public class SubsectionDao {
    private List<Subsection> subsections = new ArrayList<>();

    public void addSubsection(int id, String title) {
        SubsectionBuilder subsectionBuilder = new SubsectionBuilder();
        Subsection subsection = subsectionBuilder.withId(id)
                .withTitle(title)
                .build();
        subsections.add(subsection);
        System.out.println(subsection.getId() + " " + subsection.getTitle());
    }


    public void showSubsections() {

        List<Subsection> subsections = new ArrayList<>();
        subsections.add(new Subsection(1, "Утеплитель"));//Выполняется только с работающей базой данной
        subsections.add(new Subsection(2, "Гидроизоляция"));
        subsections.add(new Subsection(3, "Сухие смеси"));
        subsections.add(new Subsection(4, "Минеральная вата"));
        for (Subsection subsection : subsections) {
            System.out.println(subsection.toString());
        }
    }

}