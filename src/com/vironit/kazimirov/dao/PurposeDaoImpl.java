package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.builder.Purpose.PurposeBuilder;
import com.vironit.kazimirov.exception.RepeatitionException;

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
    public void addPurpose(String purpose) {
        PurposeBuilder purposeBuilder = new PurposeBuilder();
        Purpose purpose1 = purposeBuilder.withTitle(purpose)
                .build();
        try {
            if (purposes.stream().anyMatch(s -> s.getPurpose().equals(purpose)) == true) {
                throw new RepeatitionException();
            }
            purposes.add(purpose1);
        } catch (RepeatitionException e) {
            System.err.println("Such purpose is exist");
        } finally {
            for (Purpose purpose2 : purposes) {
                System.out.println(purpose2 + "\n");
            }
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
