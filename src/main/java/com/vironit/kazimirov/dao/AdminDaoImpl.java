package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Client;
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
    private final String FIND_CLIENT_BY_LOGIN = "select a from Client a where a.login = :login";
    private final String FIND_CLIENTS = "select a from Client a";
    private final String FIND_CLIENT_BY_ID="select client from Client client where client.id=:clientId";

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
    public Client findClientById(int clientId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_CLIENT_BY_ID, Client.class);
        query.setParameter("clientId", clientId);
        Client client = query.getResultList().isEmpty() ? null : (Client) query.getResultList().get(0);
        session.close();
        return client;
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
    public List<Client> findAllClient() {
        Session session = sessionFactory.openSession();
        List<Client> clients = (List<Client>) session.createQuery(FIND_CLIENTS).list();
        session.close();
        return clients;
    }

    public void deleteClient(int clientId) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Client client = session.get(Client.class, clientId);
        session.delete(client);
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
