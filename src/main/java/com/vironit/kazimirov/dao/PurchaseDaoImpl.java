package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.GoodDao;
import com.vironit.kazimirov.dao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDaoImpl implements PurchaseDao {
    private List<Purchase> purchases = new ArrayList<>();
    private List<Good> goods = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Good> purchasesCart = new ArrayList<>();


    public PurchaseDaoImpl() {

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.FEBRUARY, 14, 12, 56);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, Month.AUGUST, 20, 20, 55);
        LocalDateTime localDateTime3 = LocalDateTime.of(2018, Month.JANUARY, 15, 02, 56);
        LocalDateTime localDateTime4 = LocalDateTime.of(2017, Month.JULY, 14, 12, 8);


        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, localDateTime1, localDateTime1, Status.NEW);
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, localDateTime2, Status.NEW);
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, localDateTime3, Status.NEW);
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, localDateTime4, Status.REGISTRATE);
        Purchase purchase5 = new Purchase(5, 16.6, goods, client1, null, localDateTime1, Status.IN_PROCESS);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);

        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);
        purchases.add(purchase5);

        //purchasesCart.add(good2);
        //purchasesCart.add(good3);

    }

    @Override
    public List<Purchase> findPurchases() {
        purchases.stream().forEach(System.out::println);
        return purchases;
    }

    public List<Good> findGoods() {
        return goods;
    }

    @Override
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {
        double cost = purchase.getGoods().stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();
        if (cost < 0) {
            throw new PurchaseException("Our company don't work in minus");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        purchase.setPurchase(localDateTime);
        purchase.setStatus(Status.IN_PROCESS);
        purchase.setCost(cost);
        //System.out.println(sum);
        purchases.add(purchase);


        purchase.setId(purchases.size());
        System.out.println(purchase);
        return purchase;
    }

    public void removePurchase(int id) throws PurchaseException {
        if (purchases.stream().noneMatch(good -> (good.getId() == id))) {
            throw new PurchaseException("There is no such purchase");
        }
        Purchase purchase = purchases.stream().filter(s -> s.getId() == id).findFirst().get();
        purchases.remove(purchase);
        purchases.stream().forEach(System.out::println);
    }

    @Override
    public Purchase createNewPurchase(Client client) {
        LocalDateTime registration = LocalDateTime.now();
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setStatus(Status.NEW);
        purchase.setRegistration(registration);
        System.out.println(purchase);
        return purchase;
    }

    @Override
    public Purchase changeStatus(Purchase purchase, Status status) {
        purchase.setStatus(status);
        System.out.println(purchase);
        return purchase;
    }

    @Override
    public Purchase addIntoPurchase(int goodId, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
        GoodDao dao = new GoodDaoImpl();
        Good good = dao.findGoodById(goodId);
        if (good.getAmount() < amount) {
            throw new RepeatitionException("The amount of this good in the store is" + " " + good.getAmount());
        }
        Good newPurchaseGood=new Good();
        newPurchaseGood.setUnit(good.getUnit());
        newPurchaseGood.setId(good.getId());
        newPurchaseGood.setName(good.getName());
        newPurchaseGood.setQuantity(good.getQuantity());
        newPurchaseGood.setPurpose(good.getPurpose());
        newPurchaseGood.setSubsection(good.getSubsection());
        newPurchaseGood.setDiscount(good.getDiscount());
        newPurchaseGood.setAmount(amount);
        newPurchaseGood.setPrice(good.getPrice());
        good.setAmount(good.getAmount() - amount);
        dao.updateGood(goodId, good);
        //good.setAmount(amount);
        purchasesCart.add(newPurchaseGood);
        //goods.get(goodId - 1).setAmount(newAmount);
        purchase.setGoods(purchasesCart);
        double cost = purchase.getGoods().stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();
        purchase.setCost(cost);
        //System.out.println("Cost of Purchase="+" "+cost);
        System.out.println(purchase);
        //dao.findAllGoods();
        return purchase;
    }


    @Override
    public void deleteFromPurchase(int id) throws PurchaseException {
        if (purchasesCart.stream().anyMatch(s -> s.getId() == id) == false) {
            throw new PurchaseException("This purchase is absent in base. You can't delete it");
        }
        Good good = purchasesCart.stream().filter(s -> s.getId() == id).findFirst().get();
        purchasesCart.remove(good);
    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException {
        List<Purchase> purchasesByDate;
        if (purchases.stream().anyMatch(s -> (s.getPurchase().equals(localDateTime))) == false) {
            throw new PurchaseNotFoundException("There are no purchases in that period");

        } else {
            purchasesByDate = purchases.stream().filter(s -> (s.getPurchase().equals(localDateTime))).collect(Collectors.toList());
        }
        return purchasesByDate;
    }


}
