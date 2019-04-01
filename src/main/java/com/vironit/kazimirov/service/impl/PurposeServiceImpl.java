package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
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
    public int addPurpose(Purpose purpose) throws RepeatitionException {
        Optional<Purpose> checkPurpose = Optional.ofNullable(purposeDao.findPurposeByName(purpose.getPurpose()));
        if (checkPurpose.isPresent() == false) {
            return purposeDao.addPurpose(purpose);
        } else {
            throw new RepeatitionException("such purpose is present");
        }
    }

    @Override
    public List<Purpose> findPurposes() {
        return purposeDao.findPurposes();
    }

    @Override
    public Purpose findPurposeByName(String purposeName) throws PurposeNotFoundException {
        Optional<Purpose> checkNamePurpose = Optional.ofNullable(purposeDao.findPurposeByName(purposeName));
        if (checkNamePurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        } else {
            return purposeDao.findPurposeByName(purposeName);
        }
    }

    @Override
    public void deletePurpose(int purposeId) throws CantDeleteElement {
        Purpose purpose = purposeDao.findPurposeById(purposeId);
        List<Good> goods = goodDao.findByPurpose(purpose);
        if (goods.size() == 0) {
            purposeDao.deletePurpose(purposeId);
        } else {
            throw new CantDeleteElement("this purpose is using with goods");
        }
    }

    @Override
    public Purpose findPurposeById(int purposeId) throws PurposeNotFoundException {
        Optional<Purpose> checkIdPurpose = Optional.ofNullable(purposeDao.findPurposeById(purposeId));
        if (checkIdPurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        } else {
            return purposeDao.findPurposeById(purposeId);
        }
    }
}
