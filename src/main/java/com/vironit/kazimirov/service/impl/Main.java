package com.vironit.kazimirov.service.impl;


import com.vironit.kazimirov.config.ApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.ClientService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vironit.kazimirov.service.GoodService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Thread {


    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws RepeatitionException, ClientNotFoundException, PurchaseNotFoundException, PurchaseException, GoodException, IOException, SQLException {
        LOGGER.info("method is started");
        List<Good> goods = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Purchase> purchases = new ArrayList<>();


        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);
        Good good5 = new Good(5, 6.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);
        Good good6 = new Good(5, 25.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, Status.IN_PROCESS);
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, Status.NEW);
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, Status.REGISTRATE);
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, Status.CANCELED);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        goods.add(good1);
        goods.add(good2);


        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);


       /* BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String command = bf.readLine();
        System.out.println(Thread.currentThread().getName());
        switch (command) {
            case "Show Subsections":
                SubsectionThread subsectionThread = new SubsectionThread();
                subsectionThread.start();
                break;
            case "Show Purposes":
                PurposeThread purposeThread = new PurposeThread();
                purposeThread.start();
                break;
            default:
                GoodThread goodThread = new GoodThread();
                Thread goodService = new Thread(goodThread);
                goodService.start();
                break;
        }*/


        //GooDao
        GoodServiceImpl goodServiceImpl1 = new GoodServiceImpl();
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
            LOGGER.error(ex);
        }

        //Admindao
        //AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        try {
            Client client=new Client(0, "Andreiq", "Stelmach", "andrei1q", "andrei15", "Majkovski street", "1225689");
            //adminServiceImpl.addClient(client);
            //adminServiceImpl.deleteClient(4);
            //adminServiceImpl.showAllPurchases();
            //adminServiceImpl.showAllClient();
            //adminServiceImpl.searchClientByLogin("kirill12");
            //adminServiceImpl.searchClientById(2);
            //adminServiceImpl.searchPurchasebyId(6);
            //adminServiceImpl.changeDiscount(2,65.0);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

        //SubsectionDao
        SubsectionServiceImpl subsectionDao = new SubsectionServiceImpl();
        try {
            //subsectionDao.addSubsection(1,"Утеплитель");
            //subsectionDao.showSubsections();
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

        //PurposeDao
        try {
            //PurposeServiceImpl purposeServiceImpl=new PurposeServiceImpl();
            //purposeServiceImpl.addPurpose("Кровельные работы");
            //purposeServiceImpl.showPurposes();
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

        //ClientDao
        ClientService clientServiceImpl = new ClientServiceImpl();
        int id = 1;
        String login = "andrei15";

        try {
            Review review = new Review(5, "Мне этот товар не понравился", 5, client3, good2);
            //clientServiceImpl.addReview(review);
            //clientServiceImpl.removeReview(3,client1);
            //clientServiceImpl.signIn("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
            //clientServiceImpl.changePassword(10,"166dsfs");
            //clientServiceImpl.logIn("andrei15","andrei15");
            //clientServiceImpl.changeLogin(id, login);
            //clientServiceImpl.changeAdress(1, "Puschkina street");
            //clientServiceImpl.changePhoneNumber(1,"5698532");
            //clientServiceImpl.removeReview(1,client1);
            //clientServiceImpl.findAllReviews(client1);
            //clientServiceImpl.findAllClients();
        } catch (Exception ex) {
            LOGGER.error(ex);
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
            //purchaseServiceImpl.makeAPurchase(goods, client1, localDateTime2, localDateTime2, "Оформлен");
            //purchaseServiceImpl.removePurchase(1);
            //System.out.println(purchase1.getStatus());
            //Purchase purchase=purchaseServiceImpl.createNewPurchase(client1);
            //purchaseServiceImpl.addIntoPurchase(2,10,purchase);
            //purchaseServiceImpl.addIntoPurchase(1,5,purchase);
            //purchaseServiceImpl.changeStatus(purchase,Status.CANCELED);
             //purchaseServiceImpl.makeAPurchase(purchase);
             //goodServiceImpl1.findAllGoods();
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        LOGGER.info("The programm end work");

        //Jdbc
//        Client client=new Client(0, "Andreiq", "Stelmach", "andrei1q", "andrei15", "Majkovski street", "1225689");
//        AdminDaoImplJdbs adminDaoImplJdbs=new AdminDaoImplJdbs();
//        adminDaoImplJdbs.addClient(client);

        //Spring
        ApplicationContext ctx1=new AnnotationConfigApplicationContext(ApplicationConfig.class);
        GoodService goodService=ctx1.getBean(GoodServiceImpl.class);
        goodService.findAllGoods();
        System.out.println(goodService.findGoodById(2));
//        ApplicationContext ctx1=new AnnotationConfigApplicationContext(ServiceComponentConfig.class);
//        PurchaseService purchaseService=ctx1.getBean(PurchaseServiceImpl.class);
//        purchaseService.findPurchases();





    }
}

