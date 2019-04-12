package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;


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
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        int clientId= user.getId();
        return clientId;
    }

    @Override
    public User findClientByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_CLIENT_BY_LOGIN, User.class);
        query.setParameter("login", login);
        User user = query.getResultList().isEmpty() ? null : (User) query.getResultList().get(0);
        return user;
    }

    @Override
    public User findClientById(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_CLIENT_BY_ID, User.class);
        query.setParameter("clientId", clientId);
        User user = query.getResultList().isEmpty() ? null : (User) query.getResultList().get(0);
        return user;
    }

    @Override
    public void changeDiscount(int goodId, double discount) {
        Session session = sessionFactory.getCurrentSession();
        Good good = session.get(Good.class, goodId);
        good.setDiscount(discount);
        session.update(good);
    }

    @Override
    public List<User> findAllClient() {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = (List<User>) session.createQuery(FIND_CLIENTS).list();
        return users;
    }

    public void deleteClient(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, clientId);
        session.delete(user);
    }
}
