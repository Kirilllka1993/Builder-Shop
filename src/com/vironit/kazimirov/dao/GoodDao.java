package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.GoodDaoInterface;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;

import java.util.ArrayList;
import java.util.List;

public class GoodDao implements GoodDaoInterface {
    private List<Good> goods = new ArrayList<>();

    public void addGood(double cost, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name) {
        GoodBuilder GoodBuilder = new GoodBuilder();
        Good good = GoodBuilder.withCost(cost)
                .withSubsection(subsection)
                .withUnit(unit)
                .withQuantity(quantity)
                .withDiscount(discount)
                .withPurpose(purpose)
                .withName(name)
                .build();
        goods.add(good);
        for(Good good1:goods){
            System.out.println(good1.toString());
        }
    }

    @Override
    public Good findByNameGood(String name) {
        return null;
    }

    @Override
    public List<Good> showAllGoods() {
        return null;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        return null;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        return null;
    }

    @Override
    public void deleteGood(Good good) {

    }

    @Override
    public void updateGood() {

    }

    @Override
    public void addToCart(Good good) {

    }

    @Override
    public void removeFromCart() {

    }


    public void showSub() {

      /*  goods.add(new Good(1, "dsfdsf"));
        good.add(new Good(1,"Гидроизоляция"));
        for (Good subsection : goods) {
            System.out.println(good);
        }*/
    }
}
