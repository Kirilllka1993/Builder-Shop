package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.GoodDaoImpl;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {


    @Override
    public void addGood(double price, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name, int amount) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        goodDao.addGood(price, subsection, unit, quantity, discount, purpose, name,amount);
    }

    @Override
    public Good findByNameGood(String name) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        return goodDao.findByNameGood(name);
    }

    @Override
    public List<Good> showAllGoods() {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        return goodDao.showAllGoods();
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        return goodDao.findBySubsection(subsection);
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        return goodDao.findByPurpose(purpose);
    }

    @Override
    public void deleteGood(int id) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        goodDao.deleteGood(id);
    }

    @Override
    public void updateGood(int id, Good good) {
        GoodDaoImpl goodDao = new GoodDaoImpl();
        goodDao.updateGood(id, good);

    }
}
