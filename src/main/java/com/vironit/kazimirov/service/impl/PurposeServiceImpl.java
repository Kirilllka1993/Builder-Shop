package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.PurposeDto;
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
import java.util.stream.Collectors;

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
    public int addPurpose(PurposeDto purposeDto) throws RepeatitionException {
        Optional<Purpose> checkPurpose = Optional.ofNullable(purposeDao.findPurposeByName(purposeDto.getPurpose()));
        if (checkPurpose.isPresent() == false) {
            Purpose purpose = new Purpose();
            purpose.setPurpose(purposeDto.getPurpose());
            return purposeDao.addPurpose(purpose);
        } else {
            throw new RepeatitionException("such purpose is present");
        }
    }

    @Override
    public List<PurposeDto> findPurposes() {
        List<Purpose> purposes = purposeDao.findPurposes();
        List<PurposeDto> purposeDtos = purposes.stream().map(PurposeDto::new).collect(Collectors.toList());
        return purposeDtos;
    }

    @Override
    public PurposeDto findPurposeByName(String purposeName) throws PurposeNotFoundException {
        Optional<Purpose> checkNamePurpose = Optional.ofNullable(purposeDao.findPurposeByName(purposeName));
        if (checkNamePurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        } else {
            Purpose purpose = purposeDao.findPurposeByName(purposeName);
            PurposeDto purposeDto = new PurposeDto(purpose);
            return purposeDto;
        }
    }

    @Override
    public void deletePurpose(int purposeId) throws CantDeleteElement, PurposeNotFoundException {
        Optional<Purpose> checkIdPurpose = Optional.ofNullable(purposeDao.findPurposeById(purposeId));
        if (checkIdPurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        }
        Purpose purpose = purposeDao.findPurposeById(purposeId);
        List<Good> goods = goodDao.findByPurpose(purpose);
        if (goods.size() == 0) {
            purposeDao.deletePurpose(purposeId);
        } else {
            throw new CantDeleteElement("this purpose is using with goods");
        }
    }

    @Override
    public PurposeDto findPurposeById(int purposeId) throws PurposeNotFoundException {
        Optional<Purpose> checkIdPurpose = Optional.ofNullable(purposeDao.findPurposeById(purposeId));
        if (checkIdPurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        } else {
            Purpose purpose = purposeDao.findPurposeById(purposeId);
            PurposeDto purposeDto = new PurposeDto(purpose);
            return purposeDto;
        }
    }
}
