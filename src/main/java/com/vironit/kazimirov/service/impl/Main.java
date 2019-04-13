package com.vironit.kazimirov.service.impl;


import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.exception.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

public class Main extends Thread {


    //private static final Logger LOGGER = Logger.getLogger(Main.class.getName());



    public static void main(java.lang.String[] args) throws RepeatitionException, ClientNotFoundException, PurchaseNotFoundException, PurchaseException, GoodException, IOException, SQLException {
        //LOGGER.info("method is started");
        List<Good> goods = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Purchase> purchases = new ArrayList<>();


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
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);
        Good good5 = new Good(5, 6.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);
        Good good6 = new Good(5, 25.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

//        Purchase purchase1 = new Purchase(1, 16.6, goods, user1, null, null, Status.IN_PROCESS);
//        Purchase purchase2 = new Purchase(2, 18.0, goods, user2, null, null, Status.NEW);
//        Purchase purchase3 = new Purchase(3, 20.0, goods, user3, null, null, Status.REGISTRATE);
//        Purchase purchase4 = new Purchase(4, 16.9, goods, user4, null, null, Status.CANCELED);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        goods.add(good1);
        goods.add(good2);


//        purchases.add(purchase1);
//        purchases.add(purchase2);
//        purchases.add(purchase3);
//        purchases.add(purchase4);


        //GooDao
        //GoodServiceImpl goodServiceImpl1 = new GoodServiceImpl();
        try {
            //goodServiceImpl1.addGood(good5);
            //goodServiceImpl1.addGood(good6);
            //goodServiceImpl1.findByNameGood("Пеноплекс");
            //goodServiceImpl1.findAllGoods();
            //goodServiceImpl1.findBySubsection(new Subsection(2,"Сухие смеси"));
            //goodServiceImpl1.findByPurpose(new Purpose(3, "Наружные работы"));
            //goodServiceImpl1.deleteGood(10);
            //goodServiceImpl1.updateGood(2, new Good(3, 5.0, null, "м3", 5, 1, null, "Шпатлевка", 36));
            //goodServiceImpl1.findGoodsByPrice(0, 2);
        } catch (Exception ex) {
           // LOGGER.error(ex);
        }

        //Admindao
        //AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        try {
            User user =new User(0, "Andreiq", "Stelmach", "andrei1q", "andrei15", "Majkovski street", "1225689", UserRoleEnum.ROLE_USER);
            //adminServiceImpl.addClient(user);
            //adminServiceImpl.deleteClient(4);
            //adminServiceImpl.showAllPurchases();
            //adminServiceImpl.showAllClient();
            //adminServiceImpl.searchClientByLogin("kirill12");
            //adminServiceImpl.searchClientById(2);
            //adminServiceImpl.searchPurchasebyId(6);
            //adminServiceImpl.changeDiscount(2,65.0);
        } catch (Exception ex) {
           // LOGGER.error(ex);
        }

        //SubsectionDao
        //SubsectionServiceImpl subsectionDao = new SubsectionServiceImpl();
        try {

            //subsectionDao.addSubsection(1,"Утеплитель");
            //subsectionDao.showSubsections();
        } catch (Exception ex) {
            //LOGGER.error(ex);
        }

        //PurposeDao
        try {
            //PurposeServiceImpl purposeServiceImpl=new PurposeServiceImpl();
            //purposeServiceImpl.addPurpose("Кровельные работы");
            //purposeServiceImpl.showPurposes();
        } catch (Exception ex) {
           // LOGGER.error(ex);
        }

        //ClientDao
        //ClientService clientServiceImpl = new ClientServiceImpl();
        int id = 1;
        java.lang.String login = "andrei15";

        try {
            Review review = new Review(5, "Мне этот товар не понравился", 5, user3, good2);
            //clientServiceImpl.addReview(review);
            //clientServiceImpl.removeReview(3,user1);
            //clientServiceImpl.signIn("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
            //clientServiceImpl.changePassword(10,"166dsfs");
            //clientServiceImpl.logIn("andrei15","andrei15");
            //clientServiceImpl.changeLogin(id, login);
            //clientServiceImpl.changeAdress(1, "Puschkina street");
            //clientServiceImpl.changePhoneNumber(1,"5698532");
            //clientServiceImpl.removeReview(1,user1);
            //clientServiceImpl.findAllReviewsByUser(user1);
            //clientServiceImpl.findAllClients();
        } catch (Exception ex) {
            //LOGGER.error(ex);
        }


        //PurchaseDao
//        PurchaseServiceImpl purchaseServiceImpl = new PurchaseServiceImpl();
//        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.FEBRUARY, 14, 12, 56);
//        LocalDateTime localDateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 18, 12, 56);
        try {
            //purchaseServiceImpl.addIntoPurchase(2, 100);
            //purchaseServiceImpl.deleteFromPurchase(2);
            //purchaseServiceImpl.searchPurchasesByDate(localDateTime1);
            //purchaseServiceImpl.showPurchases();
            //purchaseServiceImpl.makeAPurchase(goods, user1, localDateTime2, localDateTime2, "Оформлен");
            //purchaseServiceImpl.removePurchase(1);
            //System.out.println(purchase1.getStatus());
            //Purchase purchase=purchaseServiceImpl.createNewPurchase(user1);
            //purchaseServiceImpl.addIntoPurchase(2,10,purchase);
            //purchaseServiceImpl.addIntoPurchase(1,5,purchase);
            //purchaseServiceImpl.changeStatus(purchase,Status.CANCELED);
             //purchaseServiceImpl.makeAPurchase(purchase);
             //goodServiceImpl1.findAllGoods();
        } catch (Exception ex) {
           // LOGGER.error(ex);
        }
       // LOGGER.info("The programm end work");

        //Jdbc
//        User user=new User(0, "Andreiq", "Stelmach", "andrei1q", "andrei15", "Majkovski street", "1225689");
//        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
//        adminDaoImplJdbs.addClient(user);

        //Spring
//        ApplicationContext ctx1=new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        GoodService goodService=ctx1.getBean(GoodServiceImpl.class);
//        goodService.findAllGoods();
//        System.out.println(goodService.findGoodById(2));
//        ApplicationContext ctx1=new AnnotationConfigApplicationContext(ServiceComponentConfig.class);
//        PurchaseService purchaseService=ctx1.getBean(PurchaseServiceImpl.class);
//        purchaseService.findPurchases();





    }
}

