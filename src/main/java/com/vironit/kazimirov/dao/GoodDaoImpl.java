package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    private final String FIND_GOODS_BY_PRICE = "select a from Good a where price>=:minPrice and price<=:maxPrice";



    @Override
    public void addGood(Good good){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(good);
        tx1.commit();
        session.close();
    }

    @Override
    public Good findByNameGood(String goodName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_GOOD_BY_NAME, Good.class);
        query.setParameter("name", goodName);
        Good good = (Good) query.getSingleResult();
        session.close();
        return good;
    }

    @Override
    public List<Good> findAllGoods() {
        Session session = sessionFactory.openSession();
        List<Good> goods=(List<Good>) session.createQuery(FIND_GOODS).list();
        session.close();
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        Session session = sessionFactory.openSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_BY_GOODS_SUBSECTIONS)
                .setParameter("subsection", subsection)
                .list();
        session.close();
        return goods;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        Session session = sessionFactory.openSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_BY_GOODS_PURPOSES)
                .setParameter("purpose", purpose)
                .list();
        session.close();
        return goods;
    }

    @Override
    public void deleteGood(int goodId){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Good good = session.get(Good.class, goodId);
        session.delete(good);
        tx1.commit();
        session.close();

    }

    @Override
    public void changePrice(int goodId, double price) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setPrice(price);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeSubsection(int goodId, Subsection subsection) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setSubsection(subsection);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public void changePurpose(int goodId, Purpose purpose) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setPurpose(purpose);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeUnit(int goodId, String unit) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setUnit(unit);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeQuantity(int goodId, int quantity) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setQuantity(quantity);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeAmount(int goodId, int amount) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setAmount(amount);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        Session session = sessionFactory.openSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_GOODS_BY_PRICE)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice",maxPrice)
                .list();
        session.close();
        return goods;
    }

    @Override
    public Good findGoodById(int goodId) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        session.close();
        return good;
    }

    public void changeAmountOfGood(Good good,int amount){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        good.setAmount(good.getAmount()-amount);
        session.update(good);
        tx1.commit();
        session.close();
    }


    @Override
    public Good updateGood(int goodId, Good good) {
        return null;
    }
}
