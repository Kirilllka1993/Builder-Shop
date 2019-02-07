package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.SubsectionDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args){
        GoodServiceImpl goodServiceImpl1=new GoodServiceImpl();
        //goodServiceImpl1.addGood(2,null, "м3",5,null,null,"Гидроизоляция");
       // goodServiceImpl1.findByNameGood("Утеплитель");
        //goodServiceImpl1.showAllGoods();
        //goodServiceImpl1.findBySubsection(new Subsection(6,"Утеплитель"));
        goodServiceImpl1.findByPurpose(new Purpose(1,"Наружные работы"));


        //SubsectionDao subsectionDao=new SubsectionDao();
        //subsectionDao.showSubsections();



    }
}
