package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;
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
    //private final UserRoleEnum FIND_PURCHASES = "select purchase from Purchase purchase join fetch CartItem goodInPurchase WHERE purchase.id= goodInPurchase.id";
    private final String FIND_GOOD_FOR_PURCHASE="select a from CartItem a where a.purchase.id =:purchaseId";
    private final String FIND_PURCHASES="select purchase from Purchase purchase";
    private final String FIND_BY_PURCHASES_BY_DATE = "select purchase from Purchase purchase where timeOfPurchase =:timeOfPurchase";
    //private final UserRoleEnum FIND_GOOD_FOR_PURCHASE="select goodInPurchase from CartItem goodInPurchase where goodInPurchase.purchase=:purchaseId";
    //private final UserRoleEnum FIND_PURCHASES = "select purchase from Purchase purchase";
    //private final UserRoleEnum FIND_P URCHASES = "select goodInPurchase from CartItem goodInPurchase join Purchase purchase WHERE goodInPurchase.purchase=purchase";

    @Override
    public List<Purchase> findPurchases() {
        Session session = sessionFactory.openSession();
        List<Purchase> purchases=(List<Purchase>) session.createQuery(FIND_PURCHASES).list();
        session.close();
       return purchases;
    }

    @Override
    public int createNewPurchase(User user) {
        Session session=sessionFactory.openSession();
        Purchase purchase=new Purchase();
        LocalDateTime registration=LocalDateTime.now();
        purchase.setStatus(Status.NEW);
        purchase.setUser(user);
        purchase.setRegistration(registration);
        Transaction tx1 = session.beginTransaction();
        session.save(purchase);
        int purchaseId=purchase.getId();
        tx1.commit();
        session.close();
        return purchaseId;
    }

    @Override
    public void makeAPurchase(int purchaseId) throws PurchaseException {
        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        List<CartItem> cartItems =new ArrayList<>();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        cartItems =(List<CartItem>) session.createQuery(FIND_GOOD_FOR_PURCHASE)
                .setParameter("purchaseId",purchaseId)
                .list();
        purchase.setSum(cartItems.stream().mapToDouble(goodInPurchase->((goodInPurchase.getGood().getPrice()-goodInPurchase.getGood()
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
    public List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase){
        Session session = sessionFactory.openSession();
        List<Purchase>purchases=(List<Purchase>) session.createQuery(FIND_BY_PURCHASES_BY_DATE)
                .setParameter("timeOfPurchase", timeOfPurchase)
                .list();
        session.close();
        return purchases;

    }

    @Override
    public void removePurchase(int purchaseId){
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
        Transaction tx1 = session.beginTransaction();
        purchase.setStatus(status);
        session.update(purchase);
        tx1.commit();
        session.close();
    }
}
