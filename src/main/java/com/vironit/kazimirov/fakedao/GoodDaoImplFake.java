package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoodDaoImplFake implements GoodDao {
    private List<Good> goods = new ArrayList<>();

    public GoodDaoImplFake() {

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
    }

    @Override
    public Good findGoodById(int id)  {
        Good goodName = goods.stream().filter(good -> good.getId() == id).findFirst().get();
        return goodName;
    }

    @Override
    public void changeAmountOfGood(Good good, int amount) {

    }

    @Override
    public Good findByNameGood(String name){
        Good goodName;
            goodName = goods.stream().filter(s -> s.getName().equals(name)).findFirst().get();
        return goodName;
    }

    @Override
    public List<Good> findAllGoods() {
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        List<Good> goodSubsections;
        goodSubsections = goods.stream().filter(s -> s.getSubsection().equals(subsection)).collect(Collectors.toList());
        return goodSubsections;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        List<Good> goodPurposes;
        goodPurposes = goods.stream().filter(s -> s.getPurpose().equals(purpose)).collect(Collectors.toList());
        return goodPurposes;
    }

    @Override
    public void deleteGood(int idGood) throws GoodException {
        if (goods.stream().noneMatch(good -> (good.getId() == idGood))) {
            throw new GoodException("There is no such good");
        }
        Good good = goods.stream().filter(s -> s.getId() == idGood).findFirst().get();
        goods.remove(good);
    }

    @Override
    public void changePrice(int idGood, double price) {

    }

    @Override
    public void changeSubsection(int idGood, Subsection subsection) {

    }

    @Override
    public void changePurpose(int idGood, Purpose purpose) {

    }

    @Override
    public void changeUnit(int idGood, String unit) {

    }

    @Override
    public void changeQuantity(int idGood, int quantity) {

    }

    @Override
    public void changeAmount(int idGood, int amount) {

    }

    @Override
    public Good updateGood(int idGood, Good good)  {
//        if (goods.stream().noneMatch(good1 -> good1.getId() == idGood)) {
//            throw new GoodNotFountException("The is no such id in list");
//        }
        for (int i = 0; i <= goods.size() - 1; i++) {
            Good updateGood = goods.get(i);
            if (updateGood.getId() == idGood) {
                good.setId(idGood);
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
        return goods.get(idGood - 1);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        List<Good> goodsByPrice;
        goodsByPrice = goods.stream().filter(s -> s.getPrice() <= maxPrice && s.getPrice() >= minPrice).collect(Collectors.toList());
        return goodsByPrice;
    }
}
