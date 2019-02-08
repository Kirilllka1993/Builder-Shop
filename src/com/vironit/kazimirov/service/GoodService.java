package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface GoodService {
    void addGood (double price, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name, int amount);

    Good findByNameGood(String name);

    List<Good> showAllGoods();

    List<Good> findBySubsection(Subsection subsection);

    List<Good> findByPurpose(Purpose purpose);
    void deleteGood(int id);//Как удалять товар, по параметрам или нет

    void updateGood(int id, Good good);//могут ли все параметры быть равны нулю

}
