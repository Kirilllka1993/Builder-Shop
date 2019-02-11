package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.ClientDao;
import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.entity.builder.Review.ReviewBuilder;

import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private List<Review> reviews = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();

    public ClientDaoImpl() {
        Review review1 = new Review(1, "Классный товар", 5);
        Review review2 = new Review(2, "Ужасный товар", 1);
        Review review3 = new Review(3, "Неплохой товар", 3);
        Review review4 = new Review(4, "Хороший товар", 4);

        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);

        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

    }

    @Override
    public void addReview(int mark, String text) {
        ReviewBuilder reviewBuilder = new ReviewBuilder();
        Review review = reviewBuilder.withMark(mark)
                .withText(text)
                .build();
        int lastIndex = reviews.size();
        review.setId(lastIndex + 1);
        reviews.add(review);
        reviews.stream().forEach(System.out::println);
       /* for (Review review1 : reviews) {
            System.out.println(review1 + "\n");
        }*/
    }


    @Override
    public void removeReview(int id) {
        Review review = reviews.stream().filter(s -> s.getId() == id).findFirst().get();
        reviews.remove(review);
        /*for (int i = 0; i <= reviews.size() - 1; i++) {
            Review good = reviews.get(i);
            if (good.getId() == id)
                reviews.remove(reviews.get(i));
        }
        for (Review good : reviews) {
            System.out.println(good + "\n");
        }*/
        reviews.stream().forEach(System.out::println);
    }

    @Override
    public void logIn(String login, String password) {
        try {
            if (clients.stream().anyMatch(s -> s.getLogin().equals(login) && s.getPassword().equals(password)) == false){
                throw new Exception();
            }
            System.out.println("Hello"+" "+login);

            /*for (Client client : clients) {
                if (login.equals(client.getLogin()) && (password.equals(client.getPassword()))) {
                    flag = true;
                }
                break;
            }
            if (flag == false) {
                throw new Exception();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Неверно введены логин или пароль");
        }
    }

    @Override
    public void logOut() {//Это будет не в Dao

    }

    @Override
    public void signIn(String name, String surname, String login, String password, String adress, String phoneNumber) {
        ClientBuilder clientBuilder = new ClientBuilder();
        Client client = clientBuilder.withName(name)
                .withSurname(surname)
                .withLogin(login)
                .withPassword(password)
                .withAdress(adress)
                .withPhoneNumber(phoneNumber)
                .build();
        int lastIndex = clients.size();
        try {
            if (clients.stream().anyMatch(s->s.getLogin().equals(login))==true){
                throw new Exception();
            }
            /*for (Client client1 : clients) {
                if (client.getLogin().equals(client1.getLogin())) {
                    throw new Exception();
                }
            }*/
            clients.add(client);
            client.setId(lastIndex + 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Пользователь с таким логином уже существует");
        } finally {
            for (Client client2 : clients) {
                System.out.println(client2 + "\n");
            }
        }
    }

    @Override
    public void changeLogin(int id, String login) {
        try {
            if (clients.stream().anyMatch(s->s.getLogin().equals(login))==true){
                throw new Exception();
            }
           /* for (Client client : clients) {
                if (login.equals(client.getLogin())) {
                    throw new Exception();
                }
            }*/
            clients.get(id - 1).setLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Пользователь с таким логином уже существует");
        } finally {
            for (Client client2 : clients) {
                System.out.println(client2 + "\n");
            }
        }
    }

    @Override
    public void changePassword(int id, String password) {
        clients.get(id - 1).setPassword(password);
        clients.stream().forEach(System.out::println);
       /* for (Client client2 : clients) {
            System.out.println(client2 + "\n");
        }*/
    }
}
