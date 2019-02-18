package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.DaoInterface.GoodDao;
import com.vironit.kazimirov.dao.GoodDaoImpl;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {
    private GoodDao goodDao;

    public GoodServiceImpl() {
        goodDao = new GoodDaoImpl();
    }


    @Override
    public void addGood(Good good) throws GoodException {//Good good
        goodDao.addGood(good);
    }

    @Override
    public Good findByNameGood(String name)  {
        return goodDao.findByNameGood(name);
    }

    @Override
    public List<Good> findAllGoods() {
        return goodDao.findAllGoods();
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection){
        return goodDao.findBySubsection(subsection);
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose){
        return goodDao.findByPurpose(purpose);
    }

    @Override
    public void deleteGood(int id) throws GoodException {
        goodDao.deleteGood(id);
    }

    @Override
    public Good updateGood(int id, Good good) throws GoodNotFountException {
        return goodDao.updateGood(id, good);

    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice)  {
        return goodDao.findGoodsByPrice(minPrice, maxPrice);
    }

    @Override
    public Good findGoodById(int id){
        return goodDao.findGoodById(id);
    }
}
