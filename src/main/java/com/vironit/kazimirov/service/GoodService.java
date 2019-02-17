package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;

import java.util.List;

public interface GoodService {
    void addGood(Good good) throws GoodException;

    Good findByNameGood(String name) throws GoodException;

    List<Good> findAllGoods();

    List<Good> findBySubsection(Subsection subsection) throws GoodException;

    List<Good> findByPurpose(Purpose purpose) throws GoodException;

    void deleteGood(int id) throws GoodException;//Как удалять товар, по параметрам или нет

    Good updateGood(int id, Good good);//могут ли все параметры быть равны нулю

    List<Good> findGoodsByPrice(double minPrice, double maxPrice);

}
