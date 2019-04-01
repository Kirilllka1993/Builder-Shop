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

    private final String FIND_SUBSECTIONS = "select a from Subsection a";
    private final String FIND_SUBSECTION_BY_NAME = "select subsection from Subsection subsection where subsection.title = :title";
    private final String FIND_SUBSECTION_BY_ID = "select subsection from Subsection subsection where subsection.id = :subsectionId";

    @Override
    public int addSubsection(Subsection subsection) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(subsection);
        int subsectionId=subsection.getId();
        tx1.commit();
        session.close();
        return subsectionId;
    }

    @Override
    public List<Subsection> findSubsections() {
        Session session = sessionFactory.openSession();
        List<Subsection> subsections = (List<Subsection>) session.createQuery(FIND_SUBSECTIONS).list();
        session.close();
        return subsections;
    }

    @Override
    public Subsection findSubsectionByName(String subsectionTitle) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_SUBSECTION_BY_NAME, Subsection.class);
        query.setParameter("title", subsectionTitle);
        Subsection subsection = query.getResultList().isEmpty() ? null : (Subsection) query.getResultList().get(0);
        session.close();
        return subsection;
    }

    @Override
    public void deleteSubsection(int subsectionId) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Subsection subsection = session.get(Subsection.class, subsectionId);
        session.delete(subsection);
        tx1.commit();
        session.close();
    }

    @Override
    public Subsection findSubsectionById(int subsectionId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(FIND_SUBSECTION_BY_ID, Subsection.class);
        query.setParameter("subsectionId", subsectionId);
        Subsection subsection = query.getResultList().isEmpty() ? null : (Subsection) query.getResultList().get(0);
        session.close();
        return subsection;
    }
}
