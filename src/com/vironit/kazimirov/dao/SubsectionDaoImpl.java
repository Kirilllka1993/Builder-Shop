package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.entity.Subsection;

import com.vironit.kazimirov.entity.builder.Subsection.SubsectionBuilder;


import java.util.ArrayList;
import java.util.List;

public class SubsectionDaoImpl implements SubsectionDao {
    private List<Subsection> subsections = new ArrayList<>();
    public SubsectionDaoImpl() {




        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");
        subsections.add(subsection1);
        subsections.add(subsection2);
        subsections.add(subsection3);
        subsections.add(subsection4);


    }

    public void addSubsection(int id, String title) {
        SubsectionBuilder subsectionBuilder = new SubsectionBuilder();
        Subsection subsection = subsectionBuilder.withId(id)
                .withTitle(title)
                .build();
        subsections.add(subsection);
        System.out.println(subsection.getId() + " " + subsection.getTitle());
        for (Subsection subsection1 : subsections) {
            System.out.println(subsection1.toString());
        }
    }


    public List<Subsection> showSubsections() {
        subsections.stream().forEach(System.out::println);
        /*for (Subsection subsection : subsections) {
            System.out.println(subsection.toString());
        }*/
        return subsections;
    }

}