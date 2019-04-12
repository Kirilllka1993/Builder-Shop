package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.fakedao.DaoInterface.PurposeDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private final String FIND_PURPOSE_BY_ID = "select purpose from Purpose purpose where purpose.id = :purposeId";
    @Override
    public int addPurpose(Purpose purpose) {
        Session session=sessionFactory.getCurrentSession();
        session.save(purpose);
        int purposeId=purpose.getId();
        return purposeId;
    }

    @Override
    public List<Purpose> findPurposes() {
        Session session = sessionFactory.getCurrentSession();
        List<Purpose> purposes=(List<Purpose>) session.createQuery(FIND_PURPOSES).list();
        return purposes;
    }

    @Override
    public Purpose findPurposeByName(String purposeName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_PURPOSE_BY_NAME, Purpose.class);
        query.setParameter("purpose", purposeName);
        Purpose purpose= query.getResultList().isEmpty() ? null : (Purpose) query.getResultList().get(0);
        return purpose;
    }

    @Override
    public void deletePurpose(int purposeId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_PURPOSE_BY_ID, Purpose.class);
        query.setParameter("purposeId", purposeId);
        Purpose purpose = query.getResultList().isEmpty() ? null : (Purpose) query.getResultList().get(0);
        session.delete(purpose);
    }

    @Override
    public Purpose findPurposeById(int purposeId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_PURPOSE_BY_ID, Purpose.class);
        query.setParameter("purposeId", purposeId);
        Purpose purpose = query.getResultList().isEmpty() ? null : (Purpose) query.getResultList().get(0);
        return purpose;
    }
}
