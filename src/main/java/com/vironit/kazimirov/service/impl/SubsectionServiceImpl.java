package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.dao.SubsectionDaoImpl;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;

import java.util.List;

public class SubsectionServiceImpl extends Thread implements SubsectionService {

    private SubsectionDao subsectionDao;


    public SubsectionServiceImpl() {
        subsectionDao = new SubsectionDaoImpl();
    }

    @Override
    public void addSubsection(Subsection subsection) throws RepeatitionException {
        subsectionDao.addSubsection(subsection);
    }

    @Override
    public List<Subsection> findSubsections() {
        return subsectionDao.findSubsections();
    }

    @Override
    public void run() {
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


