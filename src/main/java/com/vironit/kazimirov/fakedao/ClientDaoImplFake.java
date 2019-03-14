package com.vironit.kazimirov.fakedao;

import com.vironit.kazimirov.fakedao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.RepeatitionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientDaoImplFake implements ClientDao {
    private List<Review> reviews = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();

    public ClientDaoImplFake() {

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

        Review review1 = new Review(1, "Классный товар", 5, client1, good1);
        Review review2 = new Review(2, "Ужасный товар", 1, client1, good2);
        Review review3 = new Review(3, "Неплохой товар", 3, client3, good2);
        Review review4 = new Review(4, "Хороший товар", 4, client3, good4);
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

    }

    @Override
    public void addReview(Review review) {

        int lastIndex = reviews.size();
        review.setId(lastIndex + 1);
        reviews.add(review);
    }


    @Override
    public void removeReview(int id, Client client) {
        Review review = reviews.stream().filter(review1 -> review1.getId() == id && review1.getClient().equals(client)).findFirst().get();
        reviews.remove(review);
    }

    @Override
    public void logIn(String login, String password) throws RepeatitionException {
        if (clients.stream().anyMatch(s -> s.getLogin().equals(login) && s.getPassword().equals(password)) == false) {
            throw new RepeatitionException("It is doesnt't correct entered login or password");
        }
    }

    @Override
    public void logOut() {//Это будет не в Dao

    }

    @Override
    public void signIn(Client client) throws RepeatitionException {

        int lastIndex = clients.size();
        if (clients.stream().anyMatch(s -> s.getLogin().equals(client.getLogin())) == true) {
            throw new RepeatitionException("Such login is using");
        }
        clients.add(client);
        client.setId(lastIndex + 1);
    }


    @Override
    public void changeLogin(int id, String login) throws RepeatitionException {

        if (clients.stream().anyMatch(s -> s.getLogin().equals(login)) == true) {
            throw new RepeatitionException("Such login is using");
        }
        clients.get(id - 1).setLogin(login);
    }

    @Override
    public void changePassword(int id, String password) {
        clients.get(id - 1).setPassword(password);
    }

    @Override
    public void changePhoneNumber(int id, String phoneNumber) {
        clients.get(id - 1).setPhoneNumber(phoneNumber);
    }

    @Override
    public void changeAddress(int id, String adress) {

        clients.get(id - 1).setAddress(adress);
    }

    @Override
    public List<Client> findAllClients() {
        return clients;
    }

    @Override
    public List<Review> findAllReviews(Client client) {
        List<Review> reviewsByClient = new ArrayList<>();
        reviewsByClient = reviews.stream().filter(review -> review.getClient().equals(client)).collect(Collectors.toList());
        return reviewsByClient;
    }
}
