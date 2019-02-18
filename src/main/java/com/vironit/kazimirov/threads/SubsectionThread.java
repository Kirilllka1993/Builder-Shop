package com.vironit.kazimirov.threads;

import com.vironit.kazimirov.dao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.dao.SubsectionDaoImpl;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;

public class SubsectionThread extends Thread {

    private SubsectionDao subsectionDao;

    public SubsectionThread() {

        subsectionDao = new SubsectionDaoImpl();
    }

    @Override
    public void run() {
        System.out.println("Thread 1");
        System.out.println(Thread.currentThread().getName());
        subsectionDao.findSubsections();
        Subsection subsection=new Subsection();
        try {
            subsectionDao.addSubsection(subsection);
        } catch (RepeatitionException e) {
            e.printStackTrace();
        }

    }
}
