package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    private final GoodDao goodDao;

    @Autowired
    public GoodServiceImpl(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    @Override
    public void addGood(Good good) throws GoodException {
        goodDao.addGood(good);
    }

    @Override
    public Good findByNameGood(String goodName) {
        return goodDao.findByNameGood(goodName);
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
    public void deleteGood(int goodId) throws GoodException {
        goodDao.deleteGood(goodId);
    }

    @Override
    public void changePrice(int goodId, double price) {
        goodDao.changePrice(goodId,price);
    }

    @Override
    public void changeSubsection(int goodId, Subsection subsection) {
        goodDao.changeSubsection(goodId,subsection);
    }

    @Override
    public void changePurpose(int goodId, Purpose purpose) {
        goodDao.changePurpose(goodId,purpose);
    }

    @Override
    public void changeUnit(int goodId, String unit) {
        goodDao.changeUnit(goodId,unit);
    }

    @Override
    public void changeQuantity(int goodId, int quantity) {
        goodDao.changeQuantity(goodId,quantity);
    }

    @Override
    public void changeAmount(int goodId, int amount) {
        goodDao.changeAmount(goodId,amount);
    }

    @Override
    public Good updateGood(int goodId, Good good) {
        return goodDao.updateGood(goodId,good);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        return goodDao.findGoodsByPrice(minPrice, maxPrice);
    }

    @Override
    public Good findGoodById(int goodId) {
        return goodDao.findGoodById(goodId);
    }
}
