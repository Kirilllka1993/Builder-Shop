package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.fakedao.DaoInterface.PurchaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_GOOD_FOR_PURCHASE = "select a from CartItem a where a.purchase.id =:purchaseId";
    private final String FIND_PURCHASES = "select purchase from Purchase purchase";
    private final String FIND_BY_PURCHASES_BY_DATE = "select purchase from Purchase purchase where timeOfPurchase =:timeOfPurchase";
    private final String FIND_PURCHASE_BY_ID = "select purchase from Purchase purchase where purchase.id = :purchaseId";

    @Override
    public List<Purchase> findPurchases() {
        Session session = sessionFactory.getCurrentSession();
        List<Purchase> purchases = (List<Purchase>) session.createQuery(FIND_PURCHASES).list();
        return purchases;
    }

    @Override
    public int createNewPurchase(User user) {
        Session session = sessionFactory.getCurrentSession();
        Purchase purchase = new Purchase();
        LocalDateTime registration = LocalDateTime.now();
        purchase.setStatus(Status.NEW);
        purchase.setUser(user);
        purchase.setRegistration(registration);
        session.save(purchase);
        int purchaseId = purchase.getId();
        return purchaseId;
    }

    @Override
    public void makeAPurchase(int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        List<CartItem> cartItems = new ArrayList<>();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        cartItems = (List<CartItem>) session.createQuery(FIND_GOOD_FOR_PURCHASE)
                .setParameter("purchaseId", purchaseId)
                .list();
        purchase.setSum(cartItems.stream().mapToDouble(goodInPurchase -> ((goodInPurchase.getGood().getPrice() - goodInPurchase.getGood()
                .getDiscount()) * goodInPurchase.getAmount())).sum());
        purchase.setStatus(Status.REGISTRATE);
        purchase.setTimeOfPurchase(LocalDateTime.now());
        session.update(purchase);
    }

    @Override
    public Purchase findPurchaseById(int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        Purchase purchase = session.get(Purchase.class, purchaseId);
        return purchase;
    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime timeOfPurchase) {
        Session session = sessionFactory.getCurrentSession();
        List<Purchase> purchases = (List<Purchase>) session.createQuery(FIND_BY_PURCHASES_BY_DATE)
                .setParameter("timeOfPurchase", timeOfPurchase)
                .list();
        return purchases;

    }

    @Override
    public void removePurchase(int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_PURCHASE_BY_ID, Purchase.class);
        query.setParameter("purchaseId", purchaseId);
        Purchase purchase = query.getResultList().isEmpty() ? null : (Purchase) query.getResultList().get(0);
        session.delete(purchase);
    }

    @Override
    public void changeStatus(Purchase purchase, Status status) {
        Session session = sessionFactory.getCurrentSession();
        purchase.setStatus(status);
        session.update(purchase);
    }
}
