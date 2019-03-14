package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class PurposeDaoImplFake implements PurposeDao {
    private List<Purpose> purposes = new ArrayList<>();

    public PurposeDaoImplFake() {
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
    public void addPurpose(Purpose purpose) throws RepeatitionException {
        if (purposes.stream().anyMatch(s -> s.getPurpose().equals(purpose.getPurpose())) == true) {
            throw new RepeatitionException("Such purpose is exist");
        }
        int lastIndex = purposes.size();
        purpose.setId(lastIndex + 1);
        purposes.add(purpose);
    }

    @Override
    public List<Purpose> findPurposes() {
        return purposes;
    }
}
