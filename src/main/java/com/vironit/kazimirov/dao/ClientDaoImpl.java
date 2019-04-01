package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {
    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_REVIEW_BY_GOOD_CLIENT = "select review from Review review where review.good.id =:goodId and review.client.id=:clientId";
    private final String SQL_CHECK_ACCOUNT = "select a from Client a where login =:login and password=:password";
    private final String FIND_REVIEWS_OF_CLIENTS = "select review from Review review where client =:client";

    @Override
    public void addReview(Review review) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(review);
        tx1.commit();
        session.close();
    }

    @Override
    public void removeReview(int clientId, int goodId) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Review review = session.createQuery(FIND_REVIEW_BY_GOOD_CLIENT, Review.class)
                .setParameter("clientId", clientId)
                .setParameter("goodId", goodId).uniqueResult();
        session.delete(review);
        tx1.commit();
        session.close();

    }

    @Override
    public Client logIn(String login, String password) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(SQL_CHECK_ACCOUNT, Client.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        Client client = query.getResultList().isEmpty() ? null : (Client) query.getResultList().get(0);
        session.close();
        return client;
    }

    @Override
    public void logOut() {

    }

    @Override
    public int signIn(Client client) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(client);
        int clientId=client.getId();
        tx1.commit();
        session.close();
        return clientId;
    }

    @Override
    public void changeLogin(int clientId, String newLogin) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, clientId);
        client.setLogin(newLogin);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public void changePassword(int clientId, String newPassword) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, clientId);
        client.setPassword(newPassword);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public void changePhoneNumber(int clientId, String newPhoneNumber) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, clientId);
        client.setPhoneNumber(newPhoneNumber);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeAddress(int clientId, String newAddress) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, clientId);
        client.setAddress(newAddress);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Review> findAllReviews(Client client) {
        Session session = sessionFactory.openSession();
        List<Review> reviews = session.createQuery(FIND_REVIEWS_OF_CLIENTS, Review.class)
                .setParameter("client", client).list();
        session.close();
        return reviews;
    }
}
