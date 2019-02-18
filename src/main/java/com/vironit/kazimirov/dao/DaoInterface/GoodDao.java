package com.vironit.kazimirov.dao.DaoInterface;

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

    void deleteGood(int id) throws GoodException;//Как удалять товар, по параметрам или нет

    Good updateGood(int id, Good good) throws GoodNotFountException;//могут ли все параметры быть равны нулю

    List<Good> findGoodsByPrice(double minPrice, double maxPrice);

    Good findGoodById(int id);




}
