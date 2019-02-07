package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface GoodDaoInterface {

    void addGood(double cost, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name);

    Good findByNameGood(String name);

    List<Good> showAllGoods();

    List<Good> findBySubsection(Subsection subsection);

    List<Good> findByPurpose(Purpose purpose);

    void deleteGood(Good good);//Как удалять товар, по параметрам или нет

    void updateGood();//могут ли все параметры быть равны нулю

    void addToCart(Good good);

    void removeFromCart();


}
