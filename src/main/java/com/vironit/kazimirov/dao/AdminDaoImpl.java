package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AdminDaoImpl implements AdminDao {
    private List<Client> clients = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Good> goods = new ArrayList<>();


    public AdminDaoImpl() {


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
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 5, purpose2, "Шпатлевка", 13);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 6, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, Status.NEW);
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, Status.NEW);
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, Status.NEW);
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, Status.NEW);


        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);

        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);
    }


    @Override
    public void addClient(Client client) throws RepeatitionException {
        int lastIndex = clients.size();
        if (clients.stream().anyMatch(s -> s.getLogin().equals(client.getLogin()))) {
            throw new RepeatitionException("Such login is using");
        }
        clients.add(client);
        client.setId(lastIndex + 1);
    }

    @Override
    public void deleteClient(int id) {

        Client client = clients.stream().filter(s -> s.getId() == id).findFirst().get();
        clients.remove(client);
    }

    @Override
    public Client findClientByLogin(String login) throws ClientNotFoundException {
        Client clientName = clients.stream().filter(s -> s.getLogin().equals( login)).findFirst().orElseThrow(() -> new ClientNotFoundException("Such login is absent"));
        return clientName;
    }

    @Override
    public Client findClientById(int id) throws ClientNotFoundException {
        Client clientName = clients.stream().filter(s -> s.getId() == id).findFirst().orElseThrow(() -> new ClientNotFoundException("Such id is absent"));
        return clientName;
    }

    @Override
    public void changeDiscount(int id, double discount) {
        goods.get(id - 1).setDiscount(discount);
        goods.stream().forEach(System.out::println);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        purchases.stream().forEach(System.out::println);
        return purchases;
    }

    @Override
    public List<Client> findAllClient() {
        clients.stream().forEach(System.out::println);
        return clients;
    }


    @Override
    public Purchase findPurchasebyId(int id) throws PurchaseNotFoundException {
        Purchase purchase = null;
        try {
            purchase = purchases.stream().filter(s -> s.getId() == id).findFirst().get();
            System.out.println(purchase);
        } catch (NoSuchElementException e) {
            throw new PurchaseNotFoundException("This purchase is absent in base", e.getCause());
        }
        System.out.println(purchase);
        return purchase;

    }

    @Override
    public List<Good> findAllGoods() {
        return goods;
    }

    @Override
    public Purchase updateStatus(Status status, Purchase purchase) {
        purchase.setStatus(status);
        return purchase;
    }
}
