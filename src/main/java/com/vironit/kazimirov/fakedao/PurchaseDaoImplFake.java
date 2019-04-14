package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDaoImplFake implements PurchaseDao {
    private List<Purchase> purchases = new ArrayList<>();
    private List<Good> goods = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Good> purchasesCart = new ArrayList<>();


    public PurchaseDaoImplFake() {

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


        User user1 = new User(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689", UserRoleEnum.ROLE_USER);
        User user2 = new User(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635", UserRoleEnum.ROLE_USER);
        User user3 = new User(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974", UserRoleEnum.ROLE_USER);
        User user4 = new User(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333", UserRoleEnum.ROLE_USER);

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

//        Purchase purchase1 = new Purchase(1, 16.6, goods, user1, localDateTime1, localDateTime1, Status.NEW);
//        Purchase purchase2 = new Purchase(2, 18.0, goods, user2, null, localDateTime2, Status.NEW);
//        Purchase purchase3 = new Purchase(3, 20.0, goods, user3, null, localDateTime3, Status.NEW);
//        Purchase purchase4 = new Purchase(4, 16.9, goods, user4, null, localDateTime4, Status.REGISTRATE);
//        Purchase purchase5 = new Purchase(5, 16.6, goods, user1, null, localDateTime1, Status.IN_PROCESS);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);

//        purchases.add(purchase1);
//        purchases.add(purchase2);
//        purchases.add(purchase3);
//        purchases.add(purchase4);
//        purchases.add(purchase5);

        //purchasesCart.add(good2);
        //purchasesCart.add(good3);

    }

    @Override
    public List<Purchase> findPurchases() {
        return purchases;
    }

    public List<Good> findGoods() {
        return goods;
    }

    @Override
    public void makeAPurchase(int purchaseId) throws PurchaseException {
//        double cost = purchase.getGoods().stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();
//        if (cost < 0) {
//            throw new PurchaseException("Our company don't work in minus");
//        }
//        LocalDateTime localDateTime = LocalDateTime.now();
//        purchase.setTimeOfPurchase(localDateTime);
//        purchase.setStatus(Status.IN_PROCESS);
//        purchase.setSumma(cost);
//        purchases.add(purchase);
//        purchase.setId(purchases.size());
        //return purchase;
    }

    @Override
    public Purchase findPurchaseById(int purchaseId) {
        return null;
    }

    public void removePurchase(int purchaseId)  {
//        if (purchases.stream().noneMatch(goodId -> (goodId.getId() == purchaseId))) {
//            throw new PurchaseException("There is no such purchase");
//        }
        Purchase purchase = purchases.stream().filter(s -> s.getId() == purchaseId).findFirst().get();
        purchases.remove(purchase);
    }

    @Override
    public int createNewPurchase(User user) {
        LocalDateTime registration = LocalDateTime.now();
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setStatus(Status.NEW);
        purchase.setRegistration(registration);
        return purchase.getId();
    }

    @Override
    public void changeStatus(Purchase purchase, Status status) {
        purchase.setStatus(status);
        //return purchase;
    }


//    public void addIntoPurchase(Good goodId, int amount, Purchase purchase) throws RepeatitionException, GoodNotFoundException {
//        GoodDao dao = new GoodDaoImplFake();
//        //Good goodId = dao.findGoodById(goodId);
//        if (goodId.getAmount() < amount) {
//            throw new RepeatitionException("The amount of this goodId in the store is" + " " + goodId.getAmount());
//        }
//        Good newPurchaseGood=new Good();
//        newPurchaseGood.setUnit(goodId.getUnit());
//        newPurchaseGood.setId(goodId.getId());
//        newPurchaseGood.setName(goodId.getName());
//        newPurchaseGood.setQuantity(goodId.getQuantity());
//        newPurchaseGood.setPurpose(goodId.getPurpose());
//        newPurchaseGood.setSubsection(goodId.getSubsection());
//        newPurchaseGood.setDiscount(goodId.getDiscount());
//        newPurchaseGood.setAmount(amount);
//        newPurchaseGood.setPrice(goodId.getPrice());
//        goodId.setAmount(goodId.getAmount() - amount);
//        //dao.updateGood(goodId, goodId);
//        purchasesCart.add(newPurchaseGood);
//        purchase.setGoods(purchasesCart);
//        double cost = purchase.getGoods().stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();
//        purchase.setSumma(cost);
//        //return purchase;
//    }


//    public void deleteFromPurchase(int goodId) throws PurchaseException {
//        if (purchasesCart.stream().anyMatch(s -> s.getId() == goodId) == false) {
//            throw new PurchaseException("This purchase is absent in base. You can't delete it");
//        }
//        Good goodId = purchasesCart.stream().filter(s -> s.getId() == goodId).findFirst().get();
//        purchasesCart.remove(goodId);
//    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) {
        List<Purchase> purchasesByDate;
//        if (purchases.stream().anyMatch(s -> (s.getTimeOfPurchase().equals(localDateTime))) == false) {
//            throw new PurchaseNotFoundException("There are no purchases in that period");
//
//        } else {
            purchasesByDate = purchases.stream().filter(s -> (s.getTimeOfPurchase().equals(localDateTime))).collect(Collectors.toList());
//        }
        return purchasesByDate;
    }


}
