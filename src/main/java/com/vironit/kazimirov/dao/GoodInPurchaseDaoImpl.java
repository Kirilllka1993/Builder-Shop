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


    private final String FIND_GOODINPURCHASES = "select goodInPurchase from GoodInPurchase goodInPurchase";
    private final String FIND_GOOD_IN_PURCHASE = "select goodInPurchase from GoodInPurchase goodInPurchase where goodInPurchase.good.id=:goodId and goodInPurchase.purchase.id=:purchaseId";
    private final String FIND_GOOD_IN_PURCHASES_BY_GOOD_AND_PURCHASE = "select goodInPurchase from GoodInPurchase goodInPurchase where goodInPurchase.purchase=:purchase";
    private final String FIND_GOODS = "select goodInPurchase.good.name from GoodInPurchase goodInPurchase where goodInPurchase.purchase.id=:purchaseId";
    private final String FIND_PURCHASES = "select goodInPurchase.purchase from GoodInPurchase goodInPurchase where goodInPurchase.good.id=:goodId";

    @Override
    public void addInGoodInPurchase(Good good, int amount, Purchase purchase) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        GoodInPurchase goodInPurchase = new GoodInPurchase();
        goodInPurchase.setAmount(amount);
        goodInPurchase.setGood(good);
        goodInPurchase.setPurchase(purchase);
        session.save(goodInPurchase);
        tx1.commit();
        session.close();

    }

    @Override
    public List<GoodInPurchase> findGoodInPurchases() {
        Session session = sessionFactory.openSession();//getCurrentSession
        List<GoodInPurchase> purchases = (List<GoodInPurchase>) session.createQuery(FIND_GOODINPURCHASES).list();
        session.close();//не будет для управления транзакциями в Spring
        return purchases;
    }

    @Override
    public void deleteFromPurchase(Good good, Purchase purchase) throws PurchaseException {
        Session session = sessionFactory.openSession();
        int goodId = good.getId();
        int purchaseId = purchase.getId();
        Transaction tx1 = session.beginTransaction();
        GoodInPurchase goodInPurchase = session.createQuery(FIND_GOOD_IN_PURCHASE, GoodInPurchase.class)
                .setParameter("purchaseId", purchaseId).
                        setParameter("goodId", goodId).uniqueResult();
        session.delete(goodInPurchase);
        tx1.commit();
        session.close();
    }

    @Override
    public void deleteGoodInPurchase(int goodInPurchase) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        GoodInPurchase goodInPurchase1 = session.get(GoodInPurchase.class, goodInPurchase);
        session.delete(goodInPurchase1);
        tx1.commit();
        session.close();

    }

    @Override
    public List<Good> findGoodsByPurchase(int purchaseId) {
        Session session = sessionFactory.openSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_GOODS).setParameter("purchaseId",purchaseId).list();
        session.close();
        return goods;
    }

    @Override
    public List<Purchase> findPurchasesByGood(int goodId) {
        Session session = sessionFactory.openSession();
        List<Purchase> purchases = (List<Purchase>) session.createQuery(FIND_PURCHASES).setParameter("goodId",goodId).list();
        session.close();
        return purchases;

    }

    @Override
    public void deleteGoodInPurchasesWithCancelledStatus(Purchase purchase) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        List<GoodInPurchase> goodInPurchases = session.createQuery(FIND_GOOD_IN_PURCHASES_BY_GOOD_AND_PURCHASE, GoodInPurchase.class)
                .setParameter("purchase", purchase).list();
        for (int i=0;i<goodInPurchases.size();i++){
            session.delete(goodInPurchases.get(i));
        }
        tx1.commit();
        session.close();
    }

    @Override
    public void changeAmountInGoodInPurchase(int goodId, int amount, int purchaseId) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        GoodInPurchase goodInPurchase = session.createQuery(FIND_GOOD_IN_PURCHASE, GoodInPurchase.class)
                .setParameter("purchaseId", purchaseId).
                        setParameter("goodId", goodId).uniqueResult();
        goodInPurchase.setAmount(amount);
        session.save(goodInPurchase);
        tx1.commit();
        session.close();
    }
}
