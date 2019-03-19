package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class GoodDaoImpl implements GoodDao {

    @Autowired
    private SessionFactory sessionFactory;



    public List<Subsection> showGoods() throws SQLException {
//        final String SHOW_GOODS = "SELECT*FROM shop.good";
//        PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GOODS);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List<Good> goods=new ArrayList<>();
//        while (resultSet.next()) {
//            Good good = new Good();
//            good.setId(resultSet.getInt("id"));
//            good.setQuantity(resultSet.getInt("quantity"));
//            good.setDiscount(resultSet.getInt("Discount_id"));
//            good.setName(resultSet.getString("name"));
//            good.setPrice(resultSet.getDouble("price"));
//            good.setPurpose(resultSet.getInt();
//            good.setSubsection(resultSet.getString("Subsection_id"));
//            goods.add(good);
//        }
//        final String SHOW_SUBSECTIONS = "SELECT*FROM shop.subsection";
//        PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SUBSECTIONS);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List<Subsection> subsections =new ArrayList<>();
//        while (resultSet.next()){
//            Subsection subsection=new Subsection();
//            subsection.setId(resultSet.getInt("id"));
//            subsection.setTitle(resultSet.getString("title"));
//            subsections.add(subsection);
//        }
        return null;

    }

    @Override
    public void addGood(Good good) throws GoodException {

        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(good);
        tx1.commit();
        session.close();

    }

    @Override
    public Good findByNameGood(String name) {
        return null;
    }

    @Override
    public List<Good> findAllGoods() {
        Session session = sessionFactory.openSession();
        String query = "select p from " + Good.class.getSimpleName() + " p";
        List<Good> goods = (List<Good>) session.createQuery(query).list();
        return goods;
    }

    @Override
    public List<Good> findBySubsection(Subsection subsection) {
        return null;
    }

    @Override
    public List<Good> findByPurpose(Purpose purpose) {
        return null;
    }

    @Override
    public void deleteGood(int id) throws GoodException {

    }

    @Override
    public Good updateGood(int id, Good good) throws GoodNotFountException {
        return null;
    }

    @Override
    public List<Good> findGoodsByPrice(double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public Good findGoodById(int id) {
        return null;
    }
}
