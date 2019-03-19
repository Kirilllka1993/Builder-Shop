package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.config.HibernateConfiguration;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {
    private Connection connection;
    @Autowired
    private SessionFactory sessionFactory;

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
    public Client findClientByLogin(String login) throws ClientNotFoundException {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select a from Client a where a.login = :login", Client.class);
        query.setParameter("login", login);
        Client client = (Client) query.getSingleResult();
        session.close();
        return client;
    }

    @Override
    public Client findClientById(int id) throws ClientNotFoundException {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public void changeDiscount(int id, double discount) {

    }

    @Override
    public List<Purchase> findAllPurchases() {
        return null;
    }

    @Override
    public List<Client> findAllClient() {
        Session session = sessionFactory.openSession();
        String query = "select p from " + Client.class.getSimpleName() + " p";
        List<Client> clients = (List<Client>) session.createQuery(query).list();
        return clients;
    }

    @Override
    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
        return null;
    }

    @Override
    public List<Good> findAllGoods() {
        return null;
    }

    @Override
    public Purchase updateStatus(Status status, Purchase purchase) {
        return null;
    }

    public void deleteClient(int idClient) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Client client = session.get(Client.class, idClient);
        session.delete(client);
        tx1.commit();
        session.close();
    }
}
