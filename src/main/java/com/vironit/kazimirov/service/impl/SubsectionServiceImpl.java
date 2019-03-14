package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.fakedao.SubsectionDaoImplFake;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubsectionServiceImpl implements SubsectionService {

    private SubsectionDao subsectionDao;

    @Autowired
    public SubsectionServiceImpl() {
        subsectionDao = new SubsectionDaoImplFake();
    }

    @Override
    public void addSubsection(Subsection subsection) throws RepeatitionException {
        subsectionDao.addSubsection(subsection);
    }

    @Override
    public List<Subsection> findSubsections() {
        return subsectionDao.findSubsections();
    }


}


