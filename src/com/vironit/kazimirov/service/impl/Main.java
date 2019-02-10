package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.DaoInterface.ClientDao;
import com.vironit.kazimirov.dao.PurchaseDaoImpl;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.service.ClientService;

public class Main {

    public static void main(String [] args){
        Good good1 = new Good(1, 2.0, null, "м3", 5, 0, null, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, null, "м3", 5, 5, null, "Шпатлевка", 13);

        //GooDao
        GoodServiceImpl goodServiceImpl1=new GoodServiceImpl();
        //goodServiceImpl1.addGood(2.0,null, "м3",5,null,null,"Гидроизоляция",16);
        //goodServiceImpl1.findByNameGood("Утеплитель");
        //goodServiceImpl1.showAllGoods();
        //goodServiceImpl1.findBySubsection(new Subsection(6,"Утеплитель"));
        //goodServiceImpl1.findByPurpose(new Purpose(1,"Наружные работы"));
        //goodServiceImpl1.deleteGood(5);
        //goodServiceImpl1.updateGood(2, new Good(2, 2.0, null, "м3", 5, null, null, "Пеноплекс", 54));

        //Admindao
        AdminServiceImpl adminServiceImpl=new AdminServiceImpl();
        //adminServiceImpl.addClient("Sergei", "fedorov", "kirill15", "sergei15", "Lenina street", "896564321");
        //adminServiceImpl.deleteClient(4);
        //adminServiceImpl.showAllPurchases();
        //adminServiceImpl.showAllClient();
        //adminServiceImpl.searchClientByLogin("kirill12");
        //adminServiceImpl.searchPurchasebyId(2);
        //adminServiceImpl.changeDiscount(2,65.0);

        //SubsectionDao
        SubsectionServiceImpl subsectionDao=new SubsectionServiceImpl();
        //subsectionDao.addSubsection(1,"Ерунда");
        //subsectionDao.showSubsections();

        //PurposeDao
        PurposeServiceImpl purposeServiceImpl=new PurposeServiceImpl();
        //purposeServiceImpl.addPurpose("Земляные работы");
        //purposeServiceImpl.showPurposes();

        //ClientDao
        ClientService clientServiceImpl=new ClientServiceImpl();
        //clientServiceImpl.addReview(3,"Есть товары и получше");
        //clientServiceImpl.removeReview(3);
        //clientServiceImpl.signIn("Sergei", "fedorov", "sergei15", "sergei15", "Lenina street", "896564321");
        //clientServiceImpl.changeLogin(1,"avid15");
        //clientServiceImpl.changePassword(2,"166dsfs");
        //clientServiceImpl.logIn("andrei15","andrei15");

        //PurchaseDao
        PurchaseServiceImpl purchaseServiceImpl=new PurchaseServiceImpl();
        //purchaseServiceImpl.showPurchases();
        //purchaseServiceImpl.addIntoPurchase(2,10);
        purchaseServiceImpl.deleteFromPurchase(1);






    }
}
