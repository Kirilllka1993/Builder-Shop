package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.entity.UserRoleEnum;
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
    private final String FIND_REVIEW_BY_GOOD_CLIENT = "select review from Review review where review.good.id =:goodId and review.user.id=:clientId";
    private final String SQL_CHECK_ACCOUNT = "select a from User a where login =:login and password=:password";
    private final String FIND_REVIEWS_OF_CLIENTS = "select review from Review review where user =:client";
    private final String FIND_REVIEW_BY_ID="select review from Review review where review.id =:reviewId";
    private final String FIND_ALL_REVIEWS="select review from Review review";

    @Override
    public int addReview(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.save(review);
        int reviewId=review.getId();
        return reviewId;
    }

    @Override
    public void removeReview(int clientId, int goodId) {
        Session session = sessionFactory.getCurrentSession();
        Review review = session.createQuery(FIND_REVIEW_BY_GOOD_CLIENT, Review.class)
                .setParameter("clientId", clientId)
                .setParameter("goodId", goodId).uniqueResult();
        session.delete(review);
    }

    @Override
    public Review findReview(int clientId, int goodId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_REVIEW_BY_GOOD_CLIENT, Review.class);
        query.setParameter("clientId", clientId)
                .setParameter("goodId", goodId);
        Review review = query.getResultList().isEmpty() ? null : (Review) query.getResultList().get(0);
        return review;

    }

    @Override
    public User logIn(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(SQL_CHECK_ACCOUNT, User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        User user = query.getResultList().isEmpty() ? null : (User) query.getResultList().get(0);
        return user;
    }

    @Override
    public void logOut() {

    }

    @Override
    public int signIn(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setUserRoleEnum(UserRoleEnum.ROLE_USER);
        session.save(user);
        int clientId = user.getId();
        return clientId;
    }

    @Override
    public void changeLogin(int clientId, String newLogin) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, clientId);
        user.setLogin(newLogin);
        session.update(user);
    }

    @Override
    public void changePassword(int clientId, String newPassword) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, clientId);
        user.setPassword(newPassword);
        session.update(user);
    }

    @Override
    public void changePhoneNumber(int clientId, String newPhoneNumber) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, clientId);
        user.setPhoneNumber(newPhoneNumber);
        session.update(user);
    }

    @Override
    public void changeAddress(int clientId, String newAddress) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, clientId);
        user.setAddress(newAddress);
        session.update(user);
    }

    @Override
    public List<Review> findAllReviewsByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        List<Review> reviews = session.createQuery(FIND_REVIEWS_OF_CLIENTS, Review.class)
                .setParameter("client", user).list();
        return reviews;
    }

    @Override
    public Review findReviewById(int reviewId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(FIND_REVIEW_BY_ID, Review.class);
        query.setParameter("reviewId", reviewId);
        Review review = query.getResultList().isEmpty() ? null : (Review) query.getResultList().get(0);
        return review;
    }

    @Override
    public List<Review> findAllReviews() {
        Session session = sessionFactory.getCurrentSession();
        List<Review> reviews = (List<Review>) session.createQuery(FIND_ALL_REVIEWS).list();
        return reviews;
    }
}
