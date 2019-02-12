package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.service.ClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args) throws RepeatitionException, ClientNotFoundException, PurchaseNotFoundException {
        List<Good>goods=new ArrayList<>();
        List<Client>clients=new ArrayList<>();
        List <Purchase> purchases=new ArrayList<>();

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
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 5, purpose2, "Шпатлевка", 13);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 6, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, "complited");
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, "complited");
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, "complited");
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, "complited");

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
        //GooDao
        GoodServiceImpl goodServiceImpl1=new GoodServiceImpl();
        //goodServiceImpl1.addGood(2.0,null, "м3",5,5,null,"Гидроизоляция",16);
        //goodServiceImpl1.findByNameGood("Пеноплекс");
        //goodServiceImpl1.showAllGoods();
        //goodServiceImpl1.findBySubsection(new Subsection(2,"Сухие смеси"));
        //goodServiceImpl1.findByPurpose(new Purpose(3,"Наружные работы"));
        //goodServiceImpl1.deleteGood(2);
        //goodServiceImpl1.updateGood(2, new Good(2, 2.0, null, "м3", 5, null, null, "Пеноплекс", 54));

        //Admindao
        AdminServiceImpl adminServiceImpl=new AdminServiceImpl();
        //adminServiceImpl.addClient("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
        //adminServiceImpl.deleteClient(4);
        //adminServiceImpl.showAllPurchases();
        //adminServiceImpl.showAllClient();
        //adminServiceImpl.searchClientByLogin("kirill12");
        //adminServiceImpl.searchClientById(2);
        //adminServiceImpl.searchPurchasebyId(6);
        //adminServiceImpl.changeDiscount(2,65.0);

        //SubsectionDao
        SubsectionServiceImpl subsectionDao=new SubsectionServiceImpl();
        //subsectionDao.addSubsection(1,"Утеплитель");
        //subsectionDao.showSubsections();

        //PurposeDao
        PurposeServiceImpl purposeServiceImpl=new PurposeServiceImpl();
        //purposeServiceImpl.addPurpose("Кровельные работы");
        //purposeServiceImpl.showPurposes();

        //ClientDao
        ClientService clientServiceImpl=new ClientServiceImpl();
        //clientServiceImpl.addReview(3,"Есть товары и получше");
        //clientServiceImpl.removeReview(3);
        //clientServiceImpl.signIn("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
        //clientServiceImpl.changeLogin(1,"andrei15");
        //clientServiceImpl.changePassword(2,"166dsfs");
        //clientServiceImpl.logIn("andrei15","andrei15");

        //PurchaseDao
        PurchaseServiceImpl purchaseServiceImpl=new PurchaseServiceImpl();
        LocalDateTime registration=LocalDateTime.now();
        LocalDateTime purchase=LocalDateTime.now();
        //purchaseServiceImpl.showPurchases();
        //purchaseServiceImpl.addIntoPurchase(2,39);
        //purchaseServiceImpl.deleteFromPurchase(2);
        //purchaseServiceImpl.makeAPurchase(goods,client1,registration,purchase,"Оформлен");







    }
}
