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
//    private final SubsectionDao subsectionDao;
//    private final PurposeDao purposeDao;

    @Autowired
    public GoodServiceImpl(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

//    @Autowired
//    public GoodServiceImpl(GoodDao goodDao, SubsectionDao subsectionDao, PurposeDao purposeDao) {
//        this.goodDao = goodDao;
//        this.subsectionDao = subsectionDao;
//        this.purposeDao = purposeDao;
//    }

    @Override
    public void addGood(Good good) throws GoodException {//Good good
        goodDao.addGood(good);
    }

    @Override
    public Good findByNameGood(String name) {
        return goodDao.findByNameGood(name);
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
    public void deleteGood(int idGood) throws GoodException {
        goodDao.deleteGood(idGood);
    }

    @Override
    public void changePrice(int idGood, double price) {
        goodDao.changePrice(idGood,price);
    }

    @Override
    public void changeSubsection(int idGood, Subsection subsection) {
        goodDao.changeSubsection(idGood,subsection);
    }

    @Override
    public void changePurpose(int idGood, Purpose purpose) {
        goodDao.changePurpose(idGood,purpose);
    }

    @Override
    public void changeUnit(int idGood, String unit) {
        goodDao.changeUnit(idGood,unit);
    }

    @Override
    public void changeQuantity(int idGood, int quantity) {
        goodDao.changeQuantity(idGood,quantity);
    }

    @Override
    public void changeAmount(int idGood, int amount) {
        goodDao.changeAmount(idGood,amount);
    }

    @Override
    public Good updateGood(int idGood, Good good) {
        return goodDao.updateGood(idGood,good);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        return goodDao.findGoodsByPrice(minPrice, maxPrice);
    }

    @Override
    public Good findGoodById(int id) {
        return goodDao.findGoodById(id);
    }
}
