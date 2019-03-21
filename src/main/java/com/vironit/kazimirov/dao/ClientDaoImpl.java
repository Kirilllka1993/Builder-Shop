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
    private final String SQL_CHECK_ACCOUNT = "select a from Client a where login =:login and password=:password";
    private final String FIND_CLIENT_BY_LOGIN = "select a.login from Client a where login =:login";

    @Override
    public void addReview(Review review) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(review);
        tx1.commit();
        session.close();
    }

    @Override
    public void removeReview(int id, Client client) {

    }

    @Override
    public Client logIn(String login, String password)  {
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
    public void signIn(Client client) throws RepeatitionException {
        Session session = sessionFactory.openSession();
        String login = client.getLogin();
        int query = session.createQuery(FIND_CLIENT_BY_LOGIN, String.class).
                setParameter("login", login).list().size();
        if (query != 0) {
            throw new RepeatitionException();//доработать
        }
        Transaction tx1 = session.beginTransaction();
        session.save(client);
        tx1.commit();
        session.close();

    }

    @Override
    public void changeLogin(int id, String login) throws RepeatitionException {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        int query = session.createQuery(FIND_CLIENT_BY_LOGIN, String.class).
                setParameter("login", login).list().size();
        if (query == 0) {
            client.setLogin(login);
            Transaction tx1 = session.beginTransaction();
            session.update(client);
            tx1.commit();
            session.close();
        } else {
            throw new RepeatitionException();//доработать
        }

    }

    @Override
    public void changePassword(int id, String password) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        client.setPassword(password);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public void changePhoneNumber(int id, String phoneNumber) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        client.setPhoneNumber(phoneNumber);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();

    }

    @Override
    public void changeAddress(int id, String address) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        client.setAddress(address);
        Transaction tx1 = session.beginTransaction();
        session.update(client);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Client> findAllClients() {
        return null;
    }

    @Override
    public List<Review> findAllReviews(Client client) {

        return null;
    }
}
