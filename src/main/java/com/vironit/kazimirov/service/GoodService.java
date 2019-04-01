package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface GoodService {
    int addGood(Good good) throws GoodException, RepeatitionException;//выполнено

    Good findByNameGood(String goodName) throws GoodNotFoundException;//выполнено

    List<Good> findAllGoods();//выполнено

    List<Good> findBySubsection(Subsection subsection);//выполнено

    List<Good> findByPurpose(Purpose purpose);//выполнено

    void deleteGood(int goodId);//Как удалять товар, по параметрам или нет выполнено

    void changePrice(int goodId, double price) throws GoodException;//могут ли все параметры быть равны нулю выполнео

    void changeSubsection(int goodId, Subsection subsection);//выполнено

    void changePurpose(int goodId, Purpose purpose); //выполнено

    void changeUnit(int goodId, String unit);//выполнено

    void changeQuantity(int goodId, int quantity);//выполнено

    void changeAmount(int goodId, int amount) throws GoodException;//выполнено

    void updateGood(int goodId, Good good);//надо ли делать выполнено
    
    List<Good> findGoodsByPrice(double minPrice, double maxPrice);//выполнено

    Good findGoodById(int goodId) throws GoodNotFoundException;//выполнено

    void reduceAmount(int goodId, int amount);

}
