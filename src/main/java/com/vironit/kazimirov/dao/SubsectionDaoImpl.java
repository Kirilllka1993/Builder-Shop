package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.SubsectionDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
@Repository
public class SubsectionDaoImpl implements SubsectionDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void addSubsection(Subsection subsection) throws RepeatitionException {
        Session session=sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(subsection);
        tx1.commit();
        session.close();

    }

    @Override
    public List<Subsection> findSubsections() {
        Session session = sessionFactory.openSession();
        String query = "select p from " + Subsection.class.getSimpleName() + " p";
        List<Subsection> subsections = (List<Subsection>) session.createQuery(query).list();
        return subsections;
    }

    @Override
    public Subsection findSubsectionByName(String title) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select a from Subsection a where a.title = :title", Subsection.class);
        query.setParameter("title", title);
        Subsection subsection = (Subsection) query.getSingleResult();
        session.close();
        return subsection;
    }
}
