package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.fakedao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

public class SubsectionDaoImplFake implements SubsectionDao {
    private List<Subsection> subsections = new ArrayList<>();

    public SubsectionDaoImplFake() {

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
    }

    public List<Subsection> findSubsections() {
        return subsections;
    }
}