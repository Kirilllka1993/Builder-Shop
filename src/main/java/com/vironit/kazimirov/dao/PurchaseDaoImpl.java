package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
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
    private final String FIND_PURCHASES = "select purchase from Purchase purchase";

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
    public Purchase makeAPurchase(Purchase purchase) throws PurchaseException {
        return null;
    }

    @Override
    public Purchase findPurchaseById(int purchaseId) {
        Session session = sessionFactory.openSession();
        Purchase purchase = session.load(Purchase.class, purchaseId);
        session.close();
        return purchase;
    }

    @Override
    public Purchase addIntoPurchase(Good good, int amount, Purchase purchase) throws RepeatitionException, GoodNotFountException {
        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        good.setAmount(amount);
        //List<Good> goods=purchase.getGoods();
        List<Good> goods=new ArrayList<>();
        goods.add(good);
        purchase.setGoods(goods);

        session.save(purchase);
        tx1.commit();
        session.close();
        return purchase;
    }

    @Override
    public void deleteFromPurchase(int goodId) throws PurchaseException {

    }

    @Override
    public List<Purchase> findPurchasesByDate(LocalDateTime localDateTime) throws PurchaseNotFoundException {
        return null;
    }

    @Override
    public List<Good> findGoods() {
        return null;
    }

    @Override
    public void removePurchase(int id) throws PurchaseException {

    }

    @Override
    public Purchase changeStatus(Purchase purchase, Status status) {
        return null;
    }
}
