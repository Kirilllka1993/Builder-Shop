package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.fakedao.PurposeDaoImpl;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PurposeServiceImpl implements PurposeService {
    private PurposeDao purposeDao;

    @Autowired
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
