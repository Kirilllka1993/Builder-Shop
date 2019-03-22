package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurposeServiceImpl implements PurposeService {
    @Autowired
    private final PurposeDao purposeDao;

    @Autowired
    public PurposeServiceImpl(PurposeDao purposeDao) {
        this.purposeDao = purposeDao;
    }

    @Override
    public void addPurpose(Purpose purpose) throws RepeatitionException {
        purposeDao.addPurpose(purpose);
    }

    @Override
    public List<Purpose> findPurposes() {
        return purposeDao.findPurposes();
    }

    @Override
    public Purpose findPurposeByName(String purposeName) {
        return purposeDao.findPurposeByName(purposeName);
    }

    @Override
    public void deletePurpose(int idPurpose) {
        purposeDao.deletePurpose(idPurpose);
    }

}
