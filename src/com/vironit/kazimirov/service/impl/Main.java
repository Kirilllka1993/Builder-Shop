package com.vironit.kazimirov.service.impl;

public class Main {

    public static void main(String [] args){
        //GooDao
        GoodServiceImpl goodServiceImpl1=new GoodServiceImpl();
        //goodServiceImpl1.addGood(2,null, "м3",5,null,null,"Гидроизоляция");
        //goodServiceImpl1.findByNameGood("Утеплитель");
        //goodServiceImpl1.showAllGoods();
        //goodServiceImpl1.findBySubsection(new Subsection(6,"Утеплитель"));
        //goodServiceImpl1.findByPurpose(new Purpose(1,"Наружные работы"));
        //goodServiceImpl1.deleteGood(5);
        //goodServiceImpl1.updateGood(2, new Good(2, 2.0, null, "м3", 5, null, null, "Пеноплекс", 54));

        //Admindao
        AdminServiceImpl adminServiceImpl=new AdminServiceImpl();
        //adminServiceImpl.addClient("Sergei", "fedorov", "sergei15", "sergei15", "Lenina street", "896564321");
        //adminServiceImpl.deleteClient(4);
        //adminServiceImpl.showAllPurchases();
        //adminServiceImpl.showAllClient();
        //adminServiceImpl.searchClientByLogin("kirill12");
        //adminServiceImpl.searchPurchasebyId(2);

        //SubsectionDao
        SubsectionServiceImpl subsectionDao=new SubsectionServiceImpl();
        //subsectionDao.showSubsections();

        //PurposeDao
        PurposeServiceImpl purposeServiceImpl=new PurposeServiceImpl();
        purposeServiceImpl.addPurpose("Земляные работы");



    }
}
