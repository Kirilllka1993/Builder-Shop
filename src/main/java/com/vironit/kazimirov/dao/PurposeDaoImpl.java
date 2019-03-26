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
    private final String FIND_PURPOSES="select a from Purpose a";
    private final String FIND_PURPOSE_BY_NAME="select a from Purpose a where a.purpose = :purpose";
    @Override
    public void addPurpose(Purpose purpose) {
        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(purpose);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Purpose> findPurposes() {
        Session session = sessionFactory.openSession();
        List<Purpose> purposes=(List<Purpose>) session.createQuery(FIND_PURPOSES).list();
        session.close();
        return purposes;
    }

    @Override
    public Purpose findPurposeByName(String purposeName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_PURPOSE_BY_NAME, Purpose.class);
        query.setParameter("purpose", purposeName);
        Purpose purpose= query.getResultList().isEmpty() ? null : (Purpose) query.getResultList().get(0);
        //Purpose purpose = (Purpose) query.getSingleResult();
        session.close();
        return purpose;
    }

    @Override
    public void deletePurpose(int idPurpose) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Purpose purpose = session.get(Purpose.class, idPurpose);
        session.delete(purpose);
        tx1.commit();
        session.close();
    }

    @Override
    public Purpose findPurposeById(int idPurpose) {
        Session session = sessionFactory.openSession();
        Purpose purpose = session.get(Purpose.class, idPurpose);
        session.close();
        return purpose;
    }
}
