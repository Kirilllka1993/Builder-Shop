package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.GoodInPurchase;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodInPurchaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class GoodInPurchaseDaoImpl implements GoodInPurchaseDao {
    @Autowired
    private SessionFactory sessionFactory;

    private final String FIND_PURCHASES = "select goodInPurchase from GoodInPurchase goodInPurchase join Purchase purchase WHERE goodInPurchase.purchase=purchase";
    private final String FIND_GOODINPURCHASES = "select goodInPurchase from GoodInPurchase goodInPurchase";

    @Override
    public void addInGoodInPurchase(Good good, int amount, Purchase purchase) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        GoodInPurchase goodInPurchase=new GoodInPurchase();
        goodInPurchase.setAmount(amount);
        goodInPurchase.setGood(good);
        goodInPurchase.setPurchase(purchase);
        session.save(goodInPurchase);
        tx1.commit();
        session.close();

    }

    @Override
    public List<GoodInPurchase> findGoodInPurchases() {
        Session session = sessionFactory.openSession();
        List<GoodInPurchase> purchases=(List<GoodInPurchase>) session.createQuery(FIND_GOODINPURCHASES).list();
        session.close();
        return purchases;
    }

    @Override
    public void deleteFromPurchase(int goodId) throws PurchaseException {

    }

    @Override
    public void deleteGoodInPurchase() {

    }

    @Override
    public List<GoodInPurchase> findByPurchase(int purchaseId) {
        return null;
    }

    @Override
    public List<GoodInPurchase> findByGoods(int goodId) {
        return null;
    }
}
