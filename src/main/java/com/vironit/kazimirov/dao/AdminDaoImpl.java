package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.AdminDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    private SessionFactory sessionFactory;
    private final String FIND_CLIENT_BY_LOGIN = "select a from Client a where a.login = :login";
    private final String FIND_CLIENTS = "select a from Client a";

    public AdminDaoImpl() {
    }

    public void addClient(Client client) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(client);
        tx1.commit();
        session.close();
    }

    @Override
    public Client findClientByLogin(String login) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_CLIENT_BY_LOGIN, Client.class);
        query.setParameter("login", login);
        Client client = query.getResultList().isEmpty() ? null : (Client) query.getResultList().get(0);
        session.close();
        return client;
    }

    @Override
    public Client findClientById(int id) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public void changeDiscount(int id, double discount) {
        Session session = sessionFactory.openSession();
        Good good = session.get(Good.class, id);
        good.setDiscount(discount);
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }

      @Override
    public List<Client> findAllClient() {
        Session session = sessionFactory.openSession();
        List<Client> clients=(List<Client>) session.createQuery(FIND_CLIENTS).list();
        session.close();
        return clients;
    }

    public void deleteClient(int idClient) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Client client = session.get(Client.class, idClient);
        session.delete(client);
        tx1.commit();
        session.close();
    }

    @Override
    public Purchase updateStatus(Status status, Purchase purchase) {
        return null;
    }

    @Override
    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {// В purchaseService
        return null;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return null;
    }

    @Override
    public List<Good> findAllGoods() {
        return null;
    }
}
