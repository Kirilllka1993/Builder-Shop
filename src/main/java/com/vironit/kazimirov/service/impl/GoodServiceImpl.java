package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import com.vironit.kazimirov.fakedao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;
    @Autowired
    private SubsectionDao subsectionDao;
    @Autowired
    private PurposeDao purposeDao;
    @Autowired
    private PurposeService purposeService;
    @Autowired
    private SubsectionService subsectionService;

    @Autowired
    public GoodServiceImpl(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    @Override
    public int addGood(GoodDto goodDto) throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException {
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findByNameGood(goodDto.getName()));
        if (goodDto.getPrice() <= goodDto.getDiscount() || goodDto.getAmount() < 0) {
            throw new GoodException("The discount can't be more then price");
        } else if (checkNameGood.isPresent() == true) {
            throw new RepeatitionException("such goodId is present");
        } else {
            subsectionService.findSubsectionById(goodDto.getSubsectionId());
            purposeService.findPurposeById(goodDto.getPurposeId());
            Subsection subsection = subsectionDao.findSubsectionById(goodDto.getSubsectionId());
            Purpose purpose = purposeDao.findPurposeById(goodDto.getPurposeId());
            Good good = new Good();
            good.setName(goodDto.getName());
            good.setPrice(goodDto.getPrice());
            good.setAmount(goodDto.getAmount());
            good.setQuantity(goodDto.getQuantity());
            good.setUnit(goodDto.getUnit());
            good.setDiscount(goodDto.getDiscount());
            good.setPurpose(purpose);
            good.setSubsection(subsection);
            return goodDao.addGood(good);
        }
    }

    @Override
    public GoodDto findByNameGood(String goodName) throws GoodNotFoundException {
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findByNameGood(goodName));
        if (checkNameGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Good good = goodDao.findByNameGood(goodName);
            GoodDto goodDto = new GoodDto(good);
            return goodDto;
        }
    }

    @Override
    public List<GoodDto> findAllGoods() {
        List<Good> goods = goodDao.findAllGoods();
        List<GoodDto> goodDtos = goods.stream().map(GoodDto::new).collect(Collectors.toList());
        return goodDtos;
    }

    @Override
    public List<GoodDto> findBySubsection(SubsectionDto subsectionDto) throws SubsectionNotFoundException {
        Optional<Subsection> checkNameSubsection = Optional.ofNullable(subsectionDao.findSubsectionByName(subsectionDto.getTitle()));
        if (checkNameSubsection.isPresent() == false) {
            throw new SubsectionNotFoundException("such subsection is absent");
        } else {
            Subsection subsection = subsectionDao.findSubsectionByName(subsectionDto.getTitle());
            List<Good> goods = goodDao.findBySubsection(subsection);
            List<GoodDto> goodDtos = goods.stream().map(GoodDto::new).collect(Collectors.toList());
            return goodDtos;
        }
    }

    @Override
    public List<GoodDto> findByPurpose(PurposeDto purposeDto) throws PurposeNotFoundException {
        Optional<Purpose> checkNamePurpose = Optional.ofNullable(purposeDao.findPurposeByName(purposeDto.getPurpose()));
        if (checkNamePurpose.isPresent() == false) {
            throw new PurposeNotFoundException("such purpose is absent");
        } else {
            Purpose purpose = purposeDao.findPurposeByName(purposeDto.getPurpose());
            List<Good> goods = goodDao.findByPurpose(purpose);
            List<GoodDto> goodDtos = goods.stream().map(GoodDto::new).collect(Collectors.toList());
            return goodDtos;
        }
    }

    @Override
    public void deleteGood(int goodId) throws GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            goodDao.deleteGood(goodId);
        }
    }

    @Override
    public void changePrice(int goodId, double price) throws GoodException, GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        Good good = goodDao.findGoodById(goodId);
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else if (price <= good.getDiscount()) {
            throw new GoodException("The discount can't be more then price");
        } else {
            goodDao.changePrice(goodId, price);
        }
    }

    @Override
    public void changeSubsection(int goodId, SubsectionDto subsectionDto) throws SubsectionNotFoundException, GoodNotFoundException {
        subsectionService.findSubsectionByName(subsectionDto.getTitle());
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Subsection subsection = new Subsection(subsectionDto.getId(), subsectionDto.getTitle());
            goodDao.changeSubsection(goodId, subsection);
        }
    }

    @Override
    public void changePurpose(int goodId, PurposeDto purposeDto) throws PurposeNotFoundException, GoodNotFoundException {
        purposeService.findPurposeByName(purposeDto.getPurpose());
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Purpose purpose = new Purpose(purposeDto.getId(), purposeDto.getPurpose());
            goodDao.changePurpose(goodId, purpose);
        }
    }

    @Override
    public void changeUnit(int goodId, String unit) throws GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            goodDao.changeUnit(goodId, unit);
        }
    }

    @Override
    public void changeQuantity(int goodId, int quantity) throws GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            goodDao.changeQuantity(goodId, quantity);
        }
    }

    @Override
    public void changeAmount(int goodId, int amount) throws GoodException, GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (amount < 0) {
            throw new GoodException("The amount can't be less then 0");
        } else if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            goodDao.changeAmount(goodId, amount);
        }
    }

    @Override
    public void updateGood(int goodId, GoodDto goodDto) throws GoodException, GoodNotFoundException {
        if (goodDto.getPrice() <= goodDto.getDiscount() || goodDto.getAmount() < 0) {
            throw new GoodException("The discount can't be more then price");
        }
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkNameGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Subsection subsection = subsectionDao.findSubsectionById(goodDto.getSubsectionId());
            Purpose purpose = purposeDao.findPurposeById(goodDto.getPurposeId());
            Good good = new Good();
            good.setName(goodDto.getName());
            good.setPrice(goodDto.getPrice());
            good.setAmount(goodDto.getAmount());
            good.setQuantity(goodDto.getQuantity());
            good.setUnit(goodDto.getUnit());
            good.setDiscount(goodDto.getDiscount());
            good.setPurpose(purpose);
            good.setSubsection(subsection);
            goodDao.updateGood(goodId, good);
        }
    }

    @Override
    public List<GoodDto> findGoodsByPrice(double minPrice, double maxPrice) {
        List<Good> goods = goodDao.findGoodsByPrice(minPrice, maxPrice);
        List<GoodDto> goodDtos = goods.stream().map(GoodDto::new).collect(Collectors.toList());
        return goodDtos;
    }

    @Override
    public GoodDto findGoodById(int goodId) throws GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such goodId is absent");
        } else {
            Good good = goodDao.findGoodById(goodId);
            GoodDto goodDto = new GoodDto(good);
            return goodDto;
        }
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        goodDao.reduceAmount(goodId, amount);
    }
}
