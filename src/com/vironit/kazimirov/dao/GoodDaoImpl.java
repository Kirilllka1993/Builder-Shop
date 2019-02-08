package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GoodDaoImpl implements GoodDao {
    private List<Good> goods = new ArrayList<>();

    public GoodDaoImpl() {
        Discount discount1 = new Discount(50);
        Discount discount2 = new Discount(60);
        Discount discount3 = new Discount(10);
        Discount discount4 = new Discount(0);

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        goods.add(new Good(2, 2.0, subsection1, "м3", 5, discount4, purpose1, "Пеноплекс", 54));
        goods.add(new Good(3, 2.0, subsection2, "м3", 5, discount1, purpose2, "Шпатлевка", 13));
        goods.add(new Good(4, 2.0, subsection3, "м3", 5, discount2, purpose3, "Краска для дерева", 15));
        goods.add(new Good(5, 2.0, subsection4, "м3", 5, discount4, purpose4, "Техноэласт", 18));
    }

    public void addGood(double price, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name, int amount) {
        GoodBuilder GoodBuilder = new GoodBuilder();
        Good good = GoodBuilder.withCost(price)
                .withSubsection(subsection)
                .withUnit(unit)
                .withQuantity(quantity)
                .withDiscount(discount)
                .withPurpose(purpose)
                .withName(name)
                .withAmount(amount)
                .build();
        goods.add(good);
        for (Good good1 : goods) {
            System.out.println(good1.toString());
        }
    }

    @Override
    public Good findByNameGood(String name) {
        Good goodName = null;


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
        for (Good good : goods) {
            System.out.println(good + "\n");
        }
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        List<Good> goodSubsections = new ArrayList<>();

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
        List<Good> goodPurposes = new ArrayList<>();
        for (Good good : goods) {
            if (good.getPurpose().getPurpose().equals(purpose.getPurpose())) {
                goodPurposes.add(good);
            }
        }
        System.out.print(goodPurposes.toString());
        return goodPurposes;
    }

    @Override
    public void deleteGood(int id) {

        for (int i = 0; i <= goods.size() - 1; i++) {
            Good good = goods.get(i);
            if (good.getId() == id)
                goods.remove(goods.get(i));
        }
        for (Good good : goods) {
            System.out.println(good + "\n");
        }


    }

    @Override
    public void updateGood(int id, Good good) {
        for (int i = 0; i <= goods.size() - 1; i++) {
            Good good1 = goods.get(i);
            if (good1.getId() == id) {
                goods.get(i).setAmount(good.getAmount());
                goods.get(i).setName(good.getName());
                goods.get(i).setPrice(good.getPrice());
                goods.get(i).setDiscount(good.getDiscount());
                goods.get(i).setSubsection(good.getSubsection());
                goods.get(i).setPurpose(good.getPurpose());
                goods.get(i).setQuantity(good.getQuantity());
                goods.get(i).setUnit(good.getUnit());
            }

        }
        for (Good good2 : goods) {
            System.out.println(good2 + "\n");

        }


    }

}
