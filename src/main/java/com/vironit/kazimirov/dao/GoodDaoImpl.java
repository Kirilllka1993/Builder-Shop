package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoodDaoImpl implements GoodDao {
    private List<Good> goods = new ArrayList<>();

    public GoodDaoImpl() {

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
    }

    public void addGood(Good good) throws GoodException {

        if (good.getPrice() < good.getDiscount()) {
            throw new GoodException("The discount can't be more then price");
        }
        int lastIndex = goods.size();
        good.setId(lastIndex + 1);
        goods.add(good);
        //goods.stream().forEach(System.out::println);

    }

    @Override
    public Good findByNameGood(String name) throws GoodException {
        Good goodName;
        boolean flag;
        flag = goods.stream().noneMatch(s -> (s.getName().equals(name)));
        if (flag == true) {
            throw new GoodException("This good is absent");
        } else {
            goodName = goods.stream().filter(s -> s.getName().equals(name)).findFirst().get();
        }
        System.out.println(goodName.toString());
        return goodName;
    }

    @Override
    public List<Good> findAllGoods() {
        goods.stream().forEach(System.out::println);
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) throws GoodException {
        List<Good> goodSubsections;
        boolean flag;
        flag = goods.stream().noneMatch(s -> (s.getSubsection().equals(subsection)));
        if (flag == true) {
            throw new GoodException();
        } else {
            goodSubsections = goods.stream().filter(s -> s.getSubsection().equals(subsection)).collect(Collectors.toList());
        }
        System.out.println(goodSubsections.toString());
        return goodSubsections;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) throws GoodException {
        List<Good> goodPurposes;
        boolean flag;
        flag = goods.stream().noneMatch(s -> (s.getPurpose().equals(purpose)));
        if (flag == true) {
            throw new GoodException();
        } else {
            goodPurposes = goods.stream().filter(s -> s.getPurpose().equals(purpose)).collect(Collectors.toList());
        }
        System.out.println(goodPurposes.toString());
        return goodPurposes;
    }

    @Override
    public void deleteGood(int id) throws GoodException {
        if (goods.stream().noneMatch(good -> (good.getId() == id ))) {
            throw new GoodException("There is no such good");
        }
        Good good = goods.stream().filter(s -> s.getId() == id).findFirst().get();
        goods.remove(good);
        goods.stream().forEach(System.out::println);
    }

    @Override
    public Good updateGood(int id, Good good) {
        for (int i = 0; i <= goods.size() -1; i++) {
            Good good1 = goods.get(i);
            if (good1.getId() == id) {
                good.setId(id);
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
        //System.out.println(goods.get(id - 1));
        return goods.get(id-1);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxprice)  {
        List<Good>goodsByPrice;
        goodsByPrice=goods.stream().filter(s ->s.getPrice()<=maxprice&&s.getPrice()>=minPrice).collect(Collectors.toList());
        System.out.println(goodsByPrice+"\n");
        return goodsByPrice;
    }
}
