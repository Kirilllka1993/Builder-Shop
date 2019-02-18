package com.vironit.kazimirov.threads;

import com.vironit.kazimirov.dao.DaoInterface.GoodDao;
import com.vironit.kazimirov.dao.GoodDaoImpl;

public class GoodThread implements Runnable {
    private GoodDao goodDao;

    public GoodThread(){
        goodDao=new GoodDaoImpl();
    }

    @Override
    public void run() {
        System.out.println("Thread 3");
        System.out.println(Thread.currentThread().getName());
        goodDao.findAllGoods();
    }
}
