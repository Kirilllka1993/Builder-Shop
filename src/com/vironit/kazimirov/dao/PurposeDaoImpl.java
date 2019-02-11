package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Purpose.PurposeBuilder;

import java.util.ArrayList;
import java.util.List;

public class PurposeDaoImpl implements PurposeDao {
    private List<Purpose> purposes = new ArrayList<>();

    public PurposeDaoImpl() {
        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        purposes.add(purpose1);
        purposes.add(purpose2);
        purposes.add(purpose3);
        purposes.add(purpose4);
    }

    @Override
    public void addPurpose(String title) {
        PurposeBuilder purposeBuilder = new PurposeBuilder();
        Purpose purpose = purposeBuilder.withTitle(title)
                .build();
        purposes.add(purpose);
        for (Purpose purpose1 : purposes) {
            System.out.println(purpose1 + "\n");
        }
    }


    @Override
    public List<Purpose> showPurposes() {
        purposes.stream().forEach(System.out::println);

        /*for (Purpose purpose : purposes) {
            System.out.println(purpose.toString());
        }*/
        return purposes;
    }
}
