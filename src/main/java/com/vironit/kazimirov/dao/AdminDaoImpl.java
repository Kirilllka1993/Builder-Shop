package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

import static com.vironit.kazimirov.entity.Status.*;

@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_CLIENT_BY_LOGIN = "select a from User a where a.login = :login";
    private final String FIND_CLIENTS = "select a from User a";
    private final String FIND_CLIENT_BY_ID="select client from User client where client.id=:clientId";

    public AdminDaoImpl() {
    }

    public int addClient(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        int clientId= user.getId();
        tx1.commit();
        session.close();
        return clientId;
    }

    @Override
    public User findClientByLogin(String login) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_CLIENT_BY_LOGIN, User.class);
        query.setParameter("login", login);
        User user = query.getResultList().isEmpty() ? null : (User) query.getResultList().get(0);
        session.close();
        return user;
    }

    @Override
    public User findClientById(int clientId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_CLIENT_BY_ID, User.class);
        query.setParameter("clientId", clientId);
        User user = query.getResultList().isEmpty() ? null : (User) query.getResultList().get(0);
        session.close();
        return user;
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, goodId);
        good.setDiscount(discount);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> findAllClient() {
        Session session = sessionFactory.openSession();
        List<User> users = (List<User>) session.createQuery(FIND_CLIENTS).list();
        session.close();
        return users;
    }

    public void deleteClient(int clientId) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        User user = session.get(User.class, clientId);
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void updateStatus(Status status, Purchase purchase) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        switch (status){
            case NEW:
                purchase.setStatus(NEW);
                break;
            case IN_PROCESS:
                purchase.setStatus(IN_PROCESS);
                break;
            case REGISTRATE:
                purchase.setStatus(REGISTRATE);
                break;
            case CANCELED:
                purchase.setStatus(CANCELED);
                break;
                default:
                    break;
        }
        session.update(purchase);
        tx1.commit();
        session.close();
    }
}
