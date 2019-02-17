package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;


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

    public void addSubsection(Subsection subsection) throws RepeatitionException {
        if (subsections.stream().anyMatch(s -> s.getTitle().equals(subsection.getTitle())) == true) {
            throw new RepeatitionException();
        }
        int lastIndex = subsections.size();
        subsection.setId(lastIndex + 1);
        subsections.add(subsection);
        System.out.println(subsection.getId() + " " + subsection.getTitle());

        for (Subsection subsection1 : subsections) {
            System.out.println(subsection1.toString());
        }
    }


    public List<Subsection> findSubsections() {
        subsections.stream().forEach(System.out::println);
        return subsections;
    }
}