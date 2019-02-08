package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.SubsectionDaoImpl;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.SubsectionService;

import java.util.List;

public class SubsectionServiceImpl implements SubsectionService {

    @Override
    public void addSubsection(int id, String title) {
        SubsectionDaoImpl subsectionDao = new SubsectionDaoImpl();
        subsectionDao.addSubsection(id, title);
    }

    @Override
    public void showSubsections() {
        SubsectionDaoImpl subsectionDao = new SubsectionDaoImpl();
        subsectionDao.showSubsections();
    }
}
