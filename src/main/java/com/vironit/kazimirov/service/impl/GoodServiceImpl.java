package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Autowired
    public GoodServiceImpl(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    @Override
    public int addGood(Good good) throws GoodException, RepeatitionException {
        if (good.getPrice() <= good.getDiscount() || good.getAmount() < 0) {
            throw new GoodException("The discount can't be more then price");
        }
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findByNameGood(good.getName()));
        if (checkNameGood.isPresent() == false) {
            return goodDao.addGood(good);
        } else {
            throw new RepeatitionException("such good is present");
        }
    }

    @Override
    public Good findByNameGood(String goodName) throws GoodNotFoundException {
        Optional<Good> checkNameGood = Optional.ofNullable(goodDao.findByNameGood(goodName));
        if (checkNameGood.isPresent() == false) {
            throw new GoodNotFoundException("such good is absent");
        } else {
            return goodDao.findByNameGood(goodName);
        }
    }

    @Override
    public List<Good> findAllGoods() {
        return goodDao.findAllGoods();
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        return goodDao.findBySubsection(subsection);
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        return goodDao.findByPurpose(purpose);
    }

    @Override
    public void deleteGood(int goodId) {
        goodDao.deleteGood(goodId);
    }

    @Override
    public void changePrice(int goodId, double price) throws GoodException {
        Good good = goodDao.findGoodById(goodId);
        if (price <= good.getDiscount()) {
            throw new GoodException("The discount can't be more then price");
        } else {
            goodDao.changePrice(goodId, price);
        }
    }

    @Override
    public void changeSubsection(int goodId, Subsection subsection) {
        goodDao.changeSubsection(goodId, subsection);
    }

    @Override
    public void changePurpose(int goodId, Purpose purpose) {
        goodDao.changePurpose(goodId, purpose);
    }

    @Override
    public void changeUnit(int goodId, String unit) {
        goodDao.changeUnit(goodId, unit);
    }

    @Override
    public void changeQuantity(int goodId, int quantity) {
        goodDao.changeQuantity(goodId, quantity);
    }

    @Override
    public void changeAmount(int goodId, int amount) throws GoodException {
        if (amount < 0) {
            throw new GoodException("The amount can't be less then 0");
        } else {
            goodDao.changeAmount(goodId, amount);
        }
    }

    @Override
    public void updateGood(int goodId, Good good) {
        goodDao.updateGood(goodId, good);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        return goodDao.findGoodsByPrice(minPrice, maxPrice);
    }

    @Override
    public Good findGoodById(int goodId) throws GoodNotFoundException {
        Optional<Good> checkIdGood = Optional.ofNullable(goodDao.findGoodById(goodId));
        if (checkIdGood.isPresent() == false) {
            throw new GoodNotFoundException("such good is absent");
        } else {
            return goodDao.findGoodById(goodId);
        }

    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        goodDao.reduceAmount(goodId, amount);
    }
}
