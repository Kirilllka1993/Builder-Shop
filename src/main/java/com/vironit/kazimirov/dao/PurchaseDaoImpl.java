package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_PURCHASES = "select purchase from Purchase purchase join fetch GoodInPurchase goodInPurchase WHERE purchase.id= goodInPurchase.id";
    private final String FIND_GOOD_FOR_PURCHASE="select a from GoodInPurchase a where a.purchase.id =:purchaseId";
    //private final String FIND_GOOD_FOR_PURCHASE="select goodInPurchase from GoodInPurchase goodInPurchase where goodInPurchase.purchase=:purchaseId";
    //private final String FIND_PURCHASES = "select purchase from Purchase purchase";
    //private final String FIND_PURCHASES = "select goodInPurchase from GoodInPurchase goodInPurchase join Purchase purchase WHERE goodInPurchase.purchase=purchase";

    @Override
    public List<Purchase> findPurchases() {
        Session session = sessionFactory.openSession();
        List<Purchase> purchases=(List<Purchase>) session.createQuery(FIND_PURCHASES).list();
        session.close();
       return purchases;
    }

    @Override
    public Purchase createNewPurchase(Client client) {
        Session session=sessionFactory.openSession();
        Purchase purchase=new Purchase();
        LocalDateTime registration=LocalDateTime.now();
        purchase.setStatus(Status.NEW);
        purchase.setClient(client);
        purchase.setRegistration(registration);
        Transaction tx1 = session.beginTransaction();
        session.save(purchase);
        tx1.commit();
        session.close();
        return purchase;
    }

    @Override
    public void makeAPurchase(int purchaseId) throws PurchaseException {
        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        List<GoodInPurchase>goodInPurchases=new ArrayList<>();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        goodInPurchases=(List<GoodInPurchase>) session.createQuery(FIND_GOOD_FOR_PURCHASE)
                .setParameter("purchaseId",purchaseId)
                .list();
        purchase.setSum(goodInPurchases.stream().mapToDouble(goodInPurchase->((goodInPurchase.getGood().getPrice()-goodInPurchase.getGood()
                .getDiscount())*goodInPurchase.getAmount())).sum());
        purchase.setStatus(Status.REGISTRATE);
        purchase.setTimeOfPurchase(LocalDateTime.now());
        session.update(purchase);
        tx1.commit();
        session.close();
    }

    @Override
    public Purchase findPurchaseById(int purchaseId) {
        Session session = sessionFactory.openSession();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        session.close();
        return purchase;
    }

    @Override
    public void addIntoPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
//        Session session=sessionFactory.openSession();
//        Transaction tx1 = session.beginTransaction();
//        good.setAmount(amount);
//        List<Good> goods=new ArrayList<>();
//        goods.add(good);
//        purchase.setGoods(goods);
//        session.update(purchase);
//        tx1.commit();
//        session.close();
//        //return purchase;
    }

    @Override
    public void deleteFromPurchase(int goodId) throws PurchaseException {

    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime registration) throws PurchaseNotFoundException {
        return null;
    }

    @Override
    public List<Good> findGoods() {
        return null;
    }

    @Override
    public void removePurchase(int purchaseId) throws PurchaseException {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        session.delete(purchase);
        tx1.commit();
        session.close();

    }

    @Override
    public void changeStatus(Purchase purchase, Status status) {
        Session session = sessionFactory.openSession();
        //Purchase purchase = session.get(Purchase.class, purchaseId);
        purchase.setStatus(status);
        session.update(purchase);
        session.close();
    }
}
