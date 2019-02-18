package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.dao.PurposeDaoImpl;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;

import java.util.List;

public class PurposeServiceImpl implements PurposeService {
    private PurposeDao purposeDao;

    public PurposeServiceImpl(){
        purposeDao=new PurposeDaoImpl();
    }

    @Override
    public void addPurpose(Purpose purpose) throws RepeatitionException {
        purposeDao.addPurpose(purpose);

    }

    @Override
    public List<Purpose> findPurposes() {
        return purposeDao.findPurposes();
    }

}
