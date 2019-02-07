package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.GoodDao;
import com.vironit.kazimirov.entity.Discount;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {


    @Override
    public void addGood(double cost, Subsection subsection, String unit, int quantity, Discount discount, Purpose purpose, String name) {
        GoodDao goodDao = new GoodDao();
        goodDao.addGood(cost, subsection, unit, quantity, discount, purpose, name);
    }

    @Override
    public Good findByNameGood(String name) {
        GoodDao goodDao = new GoodDao();
        return goodDao.findByNameGood(name);
    }

    @Override
    public List<Good> showAllGoods() {
        GoodDao goodDao = new GoodDao();
        return goodDao.showAllGoods();
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        GoodDao goodDao = new GoodDao();
        return goodDao.findBySubsection(subsection);
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        GoodDao goodDao = new GoodDao();
        return goodDao.findByPurpose(purpose);
    }
}
