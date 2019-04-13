package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientDaoImplFake implements ClientDao {
    private List<Review> reviews = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public ClientDaoImplFake() {

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        User user1 = new User(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689", UserRoleEnum.ROLE_USER);
        User user2 = new User(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635", UserRoleEnum.ROLE_USER);
        User user3 = new User(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974", UserRoleEnum.ROLE_USER);
        User user4 = new User(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333", UserRoleEnum.ROLE_USER);

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

        Review review1 = new Review(1, "Классный товар", 5, user1, good1);
        Review review2 = new Review(2, "Ужасный товар", 1, user1, good2);
        Review review3 = new Review(3, "Неплохой товар", 3, user3, good2);
        Review review4 = new Review(4, "Хороший товар", 4, user3, good4);
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

    }

    @Override
    public int addReview(Review review) {

        int lastIndex = reviews.size();
        review.setId(lastIndex + 1);
        reviews.add(review);
        return review.getId();
    }


    @Override
    public void removeReview(int clientId,int goodId) {
//        Review review = reviews.stream().filter(review1 -> review1.getId() == reviewId && review1.getUser().equals(user)).findFirst().get();
//        reviews.remove(review);
    }

    @Override
    public Review findReview(int clientId, int goodId) {
        return null;
    }

    @Override
    public User logIn(java.lang.String login, java.lang.String password) throws ClientNotFoundException {
        if (users.stream().anyMatch(s -> s.getLogin().equals(login) && s.getPassword().equals(password)) == false) {
            throw new ClientNotFoundException("It is doesnt't correct entered login or password");
        }
        return null;//Исправить
    }

    @Override
    public void logOut() {//Это будет не в Dao

    }

    @Override
    public int signIn(User user) throws RepeatitionException {

        int lastIndex = users.size();
        if (users.stream().anyMatch(s -> s.getLogin().equals(user.getLogin())) == true) {
            throw new RepeatitionException("Such login is using");
        }
        users.add(user);
        user.setId(lastIndex + 1);
        return user.getId();
    }


    @Override
    public void changeLogin(int clientId, java.lang.String newLogin) throws RepeatitionException {

        if (users.stream().anyMatch(s -> s.getLogin().equals(newLogin)) == true) {
            throw new RepeatitionException("Such login is using");
        }
        users.get(clientId - 1).setLogin(newLogin);
    }

    @Override
    public void changePassword(int clientId, java.lang.String newPassword) {
        users.get(clientId - 1).setPassword(newPassword);
    }

    @Override
    public void changePhoneNumber(int clientId, java.lang.String newPhoneNumber) {
        users.get(clientId - 1).setPhoneNumber(newPhoneNumber);
    }

    @Override
    public void changeAddress(int clientId, java.lang.String newAddress) {

        users.get(clientId - 1).setAddress(newAddress);
    }

//    @Override
//    public List<User> findAllClients() {
//        return users;
//    }

    @Override
    public List<Review> findAllReviewsByUser(User user) {
        List<Review> reviewsByClient = new ArrayList<>();
        reviewsByClient = reviews.stream().filter(review -> review.getUser().equals(user)).collect(Collectors.toList());
        return reviewsByClient;
    }

    @Override
    public Review findReviewById(int reviewId) {
        return null;
    }

    @Override
    public List<Review> findAllReviews() {
        return null;
    }
}
