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
        for (Good good1 : goods) {
            System.out.println(good1.toString());
        }
    }

    @Override
    public Good findByNameGood(String name) {
        Good goodName = null;
        goods.add(new Good(2, 2.0, null, "м3", 5, null, null, "Утеплитель"));
        goods.add(new Good(3, 2.0, null, "м3", 5, null, null, "Сухие смеси"));
        goods.add(new Good(4, 2.0, null, "м3", 5, null, null, "Электрика"));
        goods.add(new Good(5, 2.0, null, "м3", 5, null, null, "Краска"));

        for (Good good : goods) {
            if (good.getName().equals(name)) {
                goodName = good;
            }
        }
        System.out.println(goodName.toString());
        return goodName;
    }

    @Override
    public List<Good> showAllGoods() {
        goods.add(new Good(2, 2.0, null, "м3", 5, null, null, "Утеплитель"));
        goods.add(new Good(3, 2.0, null, "м3", 5, null, null, "Сухие смеси"));
        goods.add(new Good(4, 2.0, null, "м3", 5, null, null, "Электрика"));
        goods.add(new Good(5, 2.0, null, "м3", 5, null, null, "Краска"));
        System.out.println(goods.toString() + "/n");
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        List<Good> goodSubsections=new ArrayList<>();
        Subsection subsection1=new Subsection(1,"Утеплитель");
        Subsection subsection2=new Subsection(2,"Сухие смеси");
        Subsection subsection3=new Subsection(3,"Лакокрасочные покрытия");
        Subsection subsection4=new Subsection(4,"Железобетонные изделия");
        goods.add(new Good(2, 2.0, subsection1, "м3", 5, null, null, "Пенополистирол"));
        goods.add(new Good(3, 2.0, subsection2, "м3", 5, null, null, "Сухие смеси"));
        goods.add(new Good(4, 2.0, subsection1, "м3", 5, null, null, "Электрика"));
        goods.add(new Good(5, 2.0, subsection4, "м3", 5, null, null, "Краска"));

        for (Good good : goods) {
            if (good.getSubsection().getTitle().equals(subsection.getTitle())) {
                goodSubsections.add(good);
            }
        }
        System.out.print(goodSubsections.toString());
        return goodSubsections;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        List<Good> goodPurposes=new ArrayList<>();
        Purpose purpose1=new Purpose(1,"Фундамент");
        Purpose purpose2=new Purpose(2,"Внутренние работы");
        Purpose purpose3=new Purpose(3,"Наружные работы");
        Purpose purpose4=new Purpose(4,"Кровля");
        goods.add(new Good(2, 2.0, null, "м3", 5, null, purpose1, "Пенополистирол"));
        goods.add(new Good(3, 2.0, null, "м3", 5, null, purpose3, "Сухие смеси"));
        goods.add(new Good(4, 2.0, null, "м3", 5, null, purpose2, "Сухие смеси"));
        goods.add(new Good(5, 2.0, null, "м3", 5, null, purpose2, "Краска"));

        for (Good good : goods) {
            if (good.getPurpose().getPurpose().equals(purpose.getPurpose())) {
                goodPurposes.add(good);
            }
        }
        System.out.print(goodPurposes.toString());
        return goodPurposes;
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
}
