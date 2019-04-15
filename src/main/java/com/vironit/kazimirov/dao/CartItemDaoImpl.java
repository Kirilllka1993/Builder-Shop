package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.CartItem;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.fakedao.DaoInterface.CartItemDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.Query;
import java.util.List;

@Repository
public class CartItemDaoImpl implements CartItemDao {
    @Autowired
    private SessionFactory sessionFactory;


    private final String FIND_CARTITEMS = "select cartitem from CartItem cartitem";
    private final String FIND_CARTITEM = "select cartitem from CartItem cartitem where cartitem.good.id=:goodId and cartitem.purchase.id=:purchaseId";
    private final String FIND_CARTITEMS_BY_GOOD_AND_PURCHASE = "select cartitem from CartItem cartitem where cartitem.purchase=:purchase";
    private final String FIND_GOODS = "select cartitem.good from CartItem cartitem where cartitem.purchase.id=:purchaseId";
    private final String FIND_PURCHASES = "select cartitem.purchase from CartItem cartitem where cartitem.good.id=:goodId";
    private final String FIND_CARTITEMS_BY_PURCHASE = "select cartitem from CartItem cartitem where cartitem.purchase.id=:purchaseId";
    private final String FIND_CARTITEMS_BY_GOOD = "select cartitem from CartItem cartitem where cartitem.good.id=:goodId";
    private final String FIND_CARTITEM_BY_ID = "select cartItem from CartItem cartItem where cartItem.id = :cartItemId";

    @Override
    public int addInCartItem(Good good, int amount, Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        CartItem cartItem = new CartItem();
        cartItem.setAmount(amount);
        cartItem.setGood(good);
        cartItem.setPurchase(purchase);
        session.save(cartItem);
        int cartItemId = cartItem.getId();
        return cartItemId;

    }

    @Override
    public List<CartItem> findCartItems() {
        Session session = sessionFactory.getCurrentSession();
        List<CartItem> purchases = (List<CartItem>) session.createQuery(FIND_CARTITEMS).list();
        return purchases;
    }

    @Override
    public void deleteFromPurchase(Good good, Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        int goodId = good.getId();
        int purchaseId = purchase.getId();
        CartItem cartItem = session.createQuery(FIND_CARTITEM, CartItem.class)
                .setParameter("purchaseId", purchaseId).
                        setParameter("goodId", goodId).uniqueResult();
        session.delete(cartItem);
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        Session session = sessionFactory.getCurrentSession();
        CartItem cartItem1 = session.get(CartItem.class, cartItemId);
        session.delete(cartItem1);
    }

    @Override
    public List<Good> findGoodsByPurchase(int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        List<Good> goods = (List<Good>) session.createQuery(FIND_GOODS).setParameter("purchaseId", purchaseId).list();
        return goods;
    }

    @Override
    public List<Purchase> findPurchasesByGood(int goodId) {
        Session session = sessionFactory.getCurrentSession();
        List<Purchase> purchases = (List<Purchase>) session.createQuery(FIND_PURCHASES).setParameter("goodId", goodId).list();
        return purchases;

    }

    @Override
    public void deleteCartItemsWithCancelledStatus(Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        // Transaction tx1 = session.beginTransaction();
        List<CartItem> cartItems = session.createQuery(FIND_CARTITEMS_BY_GOOD_AND_PURCHASE, CartItem.class)
                .setParameter("purchase", purchase).list();
        for (int i = 0; i < cartItems.size(); i++) {
            Good good = cartItems.get(i).getGood();
            good.setAmount(good.getAmount() + cartItems.get(i).getAmount());
            session.update(good);
            session.delete(cartItems.get(i));
        }
        //tx1.commit();
        //session.close();
    }

    @Override
    public void changeAmountInCartItem(int goodId, int amount, int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        //Transaction tx1 = session.beginTransaction();
        CartItem cartItem = session.createQuery(FIND_CARTITEM, CartItem.class)
                .setParameter("purchaseId", purchaseId).
                        setParameter("goodId", goodId).uniqueResult();
        cartItem.setAmount(amount);
        session.save(cartItem);
        //tx1.commit();
        //session.close();
    }

    @Override
    public CartItem findCartItem(int goodId, int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_CARTITEM, CartItem.class);
        query.setParameter("purchaseId", purchaseId).setParameter("goodId", goodId);
        CartItem cartItem = query.getResultList().isEmpty() ? null : (CartItem) query.getResultList().get(0);
        return cartItem;
    }

    @Override
    public CartItem findCartItemById(int cartItemId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_CARTITEM_BY_ID, CartItem.class);
        query.setParameter("cartItemId", cartItemId);
        CartItem cartItem = query.getResultList().isEmpty() ? null : (CartItem) query.getResultList().get(0);
        return cartItem;
    }

    @Override
    public void returnedAmountOfGood(CartItem cartItem) {
        Session session = sessionFactory.getCurrentSession();
        Good good = cartItem.getGood();
        good.setAmount(good.getAmount() + cartItem.getAmount());
        session.update(good);
    }

    @Override
    public void reduceAmount(int goodId, int amount) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setAmount(good.getAmount() - amount);
        session.update(good);
    }

    @Override
    public List<CartItem> findCartItemsByPurchase(int purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        List<CartItem> cartItems = (List<CartItem>) session.createQuery(FIND_CARTITEMS_BY_PURCHASE).setParameter("purchaseId", purchaseId).list();
        return cartItems;
    }

    @Override
    public List<CartItem> findCartItemsByGood(int goodId) {
        Session session = sessionFactory.getCurrentSession();
        List<CartItem> cartItems = (List<CartItem>) session.createQuery(FIND_CARTITEMS_BY_GOOD).setParameter("goodId", goodId).list();
        return cartItems;
    }
}
