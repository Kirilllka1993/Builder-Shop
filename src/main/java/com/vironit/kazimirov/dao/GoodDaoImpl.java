package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GoodDaoImpl implements GoodDao {

    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_GOOD_BY_NAME = "select a from Good a where a.name = :name";
    private final String FIND_GOODS = "select a from Good a";
    private final String FIND_BY_GOODS_SUBSECTIONS = "select a from Good a where subsection =:subsection";
    private final String FIND_BY_GOODS_PURPOSES = "select a from Good a where purpose =:purpose";
    private final String FIND_GOODS_BY_PRICE = "select good from Good good where price>=:minPrice and price<=:maxPrice";
    private final String FIND_GOOD_BY_ID = "select good from Good good where good.id = :goodId";


    @Override
    public int addGood(Good good) {
        Session session = sessionFactory.getCurrentSession();
        session.save(good);
        int goodId=good.getId();
        return goodId;
    }

    @Override
    public Good findByNameGood(String goodName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_GOOD_BY_NAME, Good.class);
        query.setParameter("name", goodName);
        Good good = query.getResultList().isEmpty() ? null : (Good) query.getResultList().get(0);
        return good;
    }

    @Override
    public List<Good> findAllGoods() {
        Session session = sessionFactory.getCurrentSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_GOODS).list();
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        Session session = sessionFactory.getCurrentSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_BY_GOODS_SUBSECTIONS)
                .setParameter("subsection", subsection)
                .list();
        return goods;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        Session session = sessionFactory.getCurrentSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_BY_GOODS_PURPOSES)
                .setParameter("purpose", purpose)
                .list();
        return goods;
    }

    @Override
    public void deleteGood(int goodId) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        session.delete(good);
    }

    @Override
    public void changePrice(int goodId, double price) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setPrice(price);
        session.update(good);
    }

    @Override
    public void changeSubsection(int goodId, Subsection subsection) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setSubsection(subsection);
        session.update(good);
    }

    @Override
    public void changePurpose(int goodId, Purpose purpose) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setPurpose(purpose);
        session.update(good);
    }

    @Override
    public void changeUnit(int goodId, String unit) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setUnit(unit);
        session.update(good);
    }

    @Override
    public void changeQuantity(int goodId, int quantity) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setQuantity(quantity);
        session.update(good);
    }

    @Override
    public void changeAmount(int goodId, int amount) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setAmount(amount);
        session.update(good);
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        Session session = sessionFactory.getCurrentSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_GOODS_BY_PRICE)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .list();
        return goods;
    }

    @Override
    public Good findGoodById(int goodId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_GOOD_BY_ID, Good.class);
        query.setParameter("goodId", goodId);
        Good good = query.getResultList().isEmpty() ? null : (Good) query.getResultList().get(0);
        return good;
    }

    public void changeAmountOfGood(Good good, int amount) {
        Session session = sessionFactory.getCurrentSession();
        good.setAmount(amount);
        session.update(good);
    }


    @Override
    public void updateGood(int goodId, Good good) {
        Session session = sessionFactory.getCurrentSession();
        Good findGood = session.get(Good.class, goodId);
        findGood.setAmount(good.getAmount());
        findGood.setName(good.getName());
        findGood.setPurpose(good.getPurpose());
        findGood.setQuantity(good.getQuantity());
        findGood.setUnit(good.getUnit());
        findGood.setSubsection(good.getSubsection());
        findGood.setPrice(good.getPrice());
        findGood.setDiscount(good.getDiscount());
        session.update(findGood);
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setAmount(good.getAmount() - amount);
        session.update(good);
    }
}
