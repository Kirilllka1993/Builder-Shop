package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDaoImpl implements PurchaseDao {
    private static final Logger LOGGER = Logger.getLogger(PurposeDaoImpl.class.getName());
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

        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, localDateTime1, localDateTime1, "complited");
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, localDateTime2, "complited");
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, localDateTime3, "complited");
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, localDateTime4, "complited");
        Purchase purchase5 = new Purchase(5, 16.6, goods, client1, null, localDateTime1, "complited");

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
    public List<Good> findGoods(){
        return goods;
    }

    @Override
    public void chekout() {

    }


    @Override
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {//List<Good> goods, Client client, LocalDateTime registration, LocalDateTime purchase1, String status
        List<Good> goodsOfPurchase = purchase.getGoods();
        double sum = goodsOfPurchase.stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();
        if (sum < 0) {
            throw new PurchaseException("Our company don't work in minus");
        }
        //System.out.println(sum);
        purchases.add(purchase);

        purchase.setId(purchases.size());
        //System.out.println(purchase);

        return purchase;
    }

    @Override
    public List<Good> addIntoPurchase(int id, int amount) throws RepeatitionException {


        //Good goodFromList = goods.stream().filter(x->x.getId()==id).filter(x->x.getAmount()>=amount).findAny().orElseThrow(Exception::new);
        if (goods.get(id - 1).getAmount() < amount) {
            throw new RepeatitionException("The amount of this good in the store is" + " " + goods.get(id - 1).getAmount());
        }
        int oldAmount = goods.get(id - 1).getAmount();
        Good good = new Good();
        good.setId(id);
        good.setUnit(goods.get(id - 1).getUnit());
        good.setDiscount(goods.get(id - 1).getDiscount());
        good.setPrice(goods.get(id - 1).getPrice());
        good.setSubsection(goods.get(id - 1).getSubsection());
        good.setQuantity(goods.get(id - 1).getQuantity());
        good.setPurpose(goods.get(id - 1).getPurpose());
        good.setAmount(amount);
        good.setName(goods.get(id - 1).getName());
        good.setAmount(amount);
        purchasesCart.add(good);
        int newAmount = oldAmount - amount;
        goods.get(id - 1).setAmount(newAmount);
        return purchasesCart;
    }

    @Override
    public void deleteFromPurchase(int id) throws PurchaseException {
        if (purchasesCart.stream().anyMatch(s -> s.getId() == id) == false) {
            throw new PurchaseException("This purchase is absent in base. Yuo can't delete it");
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
