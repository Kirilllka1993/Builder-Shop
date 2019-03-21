package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;

import java.util.List;

public interface GoodDao {

    void addGood(Good good) throws GoodException;

    Good findByNameGood(String name) ;

    List<Good> findAllGoods();

    List<Good> findBySubsection(Subsection subsection);

    List<Good> findByPurpose(Purpose purpose);

    void deleteGood(int idGood) throws GoodException;//Как удалять товар, по параметрам или нет

    void changePrice(int idGood, double price);//могут ли все параметры быть равны нулю

    void changeSubsection(int idGood, Subsection subsection);

    void changePurpose(int idGood, Purpose purpose);

    void changeUnit(int idGood, String unit);

    void changeQuantity(int idGood, int quantity);

    void changeAmount(int idGood, int amount);

    List<Good> findGoodsByPrice(double minPrice, double maxPrice);

    Good findGoodById(int id);


    Good updateGood(int goodId, Good good);
}
