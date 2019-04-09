package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.RepeatitionException;


import java.util.ArrayList;
import java.util.List;


public class AdminDaoImplFake implements AdminDao {
    private List<User> users = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Good> goods = new ArrayList<>();


    public AdminDaoImplFake() {


        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        User user1 = new User(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689", UserRoleEnum.ROLE_USER);
        User user2 = new User(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635", UserRoleEnum.ROLE_USER);
        User user3 = new User(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974", UserRoleEnum.ROLE_USER);
        User user4 = new User(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333", UserRoleEnum.ROLE_USER);

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 5, purpose2, "Шпатлевка", 13);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 6, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

//        Purchase purchase1 = new Purchase(1, 16.6, goods, user1, null, null, Status.NEW);
//        Purchase purchase2 = new Purchase(2, 18.0, goods, user2, null, null, Status.NEW);
//        Purchase purchase3 = new Purchase(3, 20.0, goods, user3, null, null, Status.NEW);
//        Purchase purchase4 = new Purchase(4, 16.9, goods, user4, null, null, Status.NEW);


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
    }


    @Override
    public int addClient(User user) throws RepeatitionException {
        int lastIndex = users.size();
        if (users.stream().anyMatch(s -> s.getLogin().equals(user.getLogin()))) {
            throw new RepeatitionException("Such login is using");
        }
        users.add(user);
        user.setId(lastIndex + 1);
        return user.getId();
    }

    @Override
    public void deleteClient(int clientId) {

        User user = users.stream().filter(s -> s.getId() == clientId).findFirst().get();
        users.remove(user);
    }

    @Override
    public User findClientByLogin(java.lang.String login) {
        //User userName = users.stream().filter(s -> s.getLogin().equals(login)).findFirst().orElseThrow(() -> new ClientNotFoundException("Such login is absent"));
        User userName = users.stream().filter(s -> s.getLogin().equals(login)).findFirst().get();
        return userName;
    }

    @Override
    public User findClientById(int clientId) {
        //User userName = users.stream().filter(s -> s.getId() == userId).findFirst().orElseThrow(() -> new ClientNotFoundException("Such id is absent"));
        User userName = users.stream().filter(s -> s.getId() == clientId).findFirst().get();
        return userName;
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        goods.get(goodId - 1).setDiscount(discount);
    }

//    @Override
//    public List<Purchase> findAllPurchases() {
//        return purchases;
//    }

    @Override
    public List<User> findAllClient() {
        return users;
    }


//    @Override
//    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
//        Purchase purchase = null;
//        try {
//            purchase = purchases.stream().filter(s -> s.getId() == id).findFirst().get();
//            System.out.println(purchase);
//        } catch (NoSuchElementException e) {
//            throw new PurchaseNotFoundException("This purchase is absent in base", e.getCause());
//        }
//        return purchase;
//
//    }

//    @Override
//    public List<Good> findAllGoods() {
//        return goods;
//    }

    @Override
    public void updateStatus(Status status, Purchase purchase) {
//        purchase.setStatus(status);
//        return purchase;
    }
}
