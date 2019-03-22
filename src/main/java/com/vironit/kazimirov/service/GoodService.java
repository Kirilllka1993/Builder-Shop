package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;

import java.util.List;

public interface GoodService {
    void addGood(Good good) throws GoodException;

    Good findByNameGood(String goodName);

    List<Good> findAllGoods();

    List<Good> findBySubsection(Subsection subsection);

    List<Good> findByPurpose(Purpose purpose);

    void deleteGood(int goodId) throws GoodException;//Как удалять товар, по параметрам или нет

    void changePrice(int goodId, double price);//могут ли все параметры быть равны нулю

    void changeSubsection(int goodId, Subsection subsection);

    void changePurpose(int goodId, Purpose purpose);

    void changeUnit(int goodId, String unit);

    void changeQuantity(int goodId, int quantity);

    void changeAmount(int goodId, int amount);

    Good updateGood(int goodId, Good good);
    
    List<Good> findGoodsByPrice(double minPrice, double maxPrice);

    Good findGoodById(int goodId);

}
