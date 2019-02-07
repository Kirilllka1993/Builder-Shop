package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.SubsectionDao;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.SubsectionService;

import java.util.List;

public class SubsectionImpl implements SubsectionService {
    private List<Subsection>subsections;
    //private SubsectionDao subsectionDao; непонятно почему здесь не надо создавать поле, а в методе!!!!

    @Override
    public void addSubsection(int id, String title) {
        SubsectionDao subsectionDao=new SubsectionDao();
        subsectionDao.addSubsection(id, title);
    }
    public void showSubsections(){
        SubsectionDao subsectionDao=new SubsectionDao();
        subsectionDao.showSubsections();
    }
}
