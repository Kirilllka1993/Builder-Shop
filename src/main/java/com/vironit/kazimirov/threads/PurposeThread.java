package com.vironit.kazimirov.threads;

import com.vironit.kazimirov.dao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.dao.PurposeDaoImpl;
import com.vironit.kazimirov.service.PurposeService;

public class PurposeThread extends Thread {
    private PurposeDao purposeDao;
    public PurposeThread(){

        purposeDao=new PurposeDaoImpl();

    }
    @Override
    public void run() {
        System.out.println("Thread 2");
        System.out.println(Thread.currentThread().getName());
        purposeDao.findPurposes();
    }
}
