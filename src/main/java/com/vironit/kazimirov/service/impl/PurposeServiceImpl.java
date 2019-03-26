package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurposeServiceImpl implements PurposeService {
    @Autowired
    private final PurposeDao purposeDao;
    @Autowired
    private final GoodDao goodDao;

    @Autowired
    public PurposeServiceImpl(PurposeDao purposeDao, GoodDao goodDao) {
        this.purposeDao = purposeDao;
        this.goodDao = goodDao;
    }

    @Override
    public void addPurpose(Purpose purpose) throws RepeatitionException {
        Optional<Purpose> checkPurpose = Optional.ofNullable(purposeDao.findPurposeByName(purpose.getPurpose()));
        if (checkPurpose.isPresent()==false) {
            purposeDao.addPurpose(purpose);
        } else {
            throw new RepeatitionException("such purpose is present");
        }
        //purposeDao.addPurpose(purpose);
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
    public void deletePurpose(int idPurpose) throws CantDeleteElement {
        Purpose purpose=purposeDao.findPurposeById(idPurpose);
        List<Good> goods=goodDao.findByPurpose(purpose);
        if (goods.size()==0) {
            purposeDao.deletePurpose(idPurpose);
        } else {
            throw new CantDeleteElement("this purpose is using with goods");
        }
        //purposeDao.deletePurpose(idPurpose);
    }

    @Override
    public Purpose findPurposeById(int idPurpose) {
        return purposeDao.findPurposeById(idPurpose);
    }


}
