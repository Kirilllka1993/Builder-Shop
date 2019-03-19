package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
@Repository
public class PurposeDaoImpl implements PurposeDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void addPurpose(Purpose purpose) throws RepeatitionException {

        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(purpose);
        tx1.commit();
        session.close();

    }

    @Override
    public List<Purpose> findPurposes() {
        Session session = sessionFactory.openSession();
        String query = "select b from " + Purpose.class.getSimpleName() + " b";
        List<Purpose> purposes = (List<Purpose>) session.createQuery(query).list();
        return purposes;
    }

    @Override
    public Purpose findPurposeByName(String purposeName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select a from Purpose a where a.purpose = :purpose", Purpose.class);
        query.setParameter("purpose", purposeName);
        Purpose purpose = (Purpose) query.getSingleResult();
        session.close();
        return purpose;
    }
}
