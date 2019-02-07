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
        GoodDao goodDao=new GoodDao();
        goodDao.addGood(cost, subsection, unit, quantity, discount,purpose, name);
    }

    @Override
    public Good findByNameGood(String name) {
        return null;
    }

    @Override
    public List<Good> showAllGoods() {
        return null;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        return null;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        return null;
    }
}
