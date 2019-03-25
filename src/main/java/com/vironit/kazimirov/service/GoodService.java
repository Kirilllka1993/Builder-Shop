package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface GoodService {
    void addGood(Good good) throws GoodException, RepeatitionException;

    Good findByNameGood(String goodName);

    List<Good> findAllGoods();

    List<Good> findBySubsection(Subsection subsection);

    List<Good> findByPurpose(Purpose purpose);

    void deleteGood(int goodId);//Как удалять товар, по параметрам или нет

    void changePrice(int goodId, double price) throws GoodException;//могут ли все параметры быть равны нулю

    void changeSubsection(int goodId, Subsection subsection);

    void changePurpose(int goodId, Purpose purpose);

    void changeUnit(int goodId, String unit);

    void changeQuantity(int goodId, int quantity);

    void changeAmount(int goodId, int amount) throws GoodException;

    Good updateGood(int goodId, Good good);//надо ли делать
    
    List<Good> findGoodsByPrice(double minPrice, double maxPrice);

    Good findGoodById(int goodId);

    void reduceAmount(int goodId, int amount);

}
