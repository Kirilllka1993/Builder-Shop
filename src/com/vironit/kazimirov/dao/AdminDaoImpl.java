package com.vironit.kazimirov.dao;

import com.vironit.kazimirov.dao.DaoInterface.AdminDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;

import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    private List<Client> clients = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Good> goods=new ArrayList<>();

    public AdminDaoImpl() {
        Discount discount1 = new Discount(50);
        Discount discount2 = new Discount(60);
        Discount discount3 = new Discount(10);
        Discount discount4 = new Discount(0);

        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");

        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

        Client client1=new Client(1,  "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Client client2=new Client(2,  "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
        Client client3=new Client(3,  "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        Client client4=new Client(4,  "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

        Good good1=new Good(2, 2.0, subsection1, "м3", 5, discount4, purpose1, "Пеноплекс", 54);
        Good good2=new Good(3, 2.0, subsection2, "м3", 5, discount1, purpose2, "Шпатлевка", 13);
        Good good3=new Good(4, 2.0, subsection3, "м3", 5, discount2, purpose3, "Краска для дерева", 15);
        Good good4=new Good(5, 2.0, subsection4, "м3", 5, discount4, purpose4, "Техноэласт", 18);

        Purchase purchase1=new Purchase(1, 16.6, goods, client1, null, null, "complited");
        Purchase purchase2=new Purchase(2, 18.0, goods, client2, null, null, "complited");
        Purchase purchase3=new Purchase(3, 20.0, goods, client3, null, null, "complited");
        Purchase purchase4=new Purchase(4, 16.9, goods, client4, null, null, "complited");

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        goods.add(good1);
        goods.add(good2);

        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);
    }


    @Override
    public void addClient(String name, String surname, String login, String password, String adress, String phoneNumber) {
        ClientBuilder clientBuilder = new ClientBuilder();
        Client client = clientBuilder.withName(name)
                .withSurname(surname)
                .withLogin(login)
                .withPassword(password)
                .withAdress(adress)
                .withPhoneNumber(phoneNumber)
                .build();
        clients.add(client);
        for (Client client1 : clients) {
            System.out.println(client1 + "\n");
        }
    }



    @Override
    public void deleteClient(int id) {
        for (int i = 0; i <= clients.size() - 1; i++) {
            Client client = clients.get(i);
            if (client.getId() == id)
                clients.remove(clients.get(i));
        }
        for (Client client : clients) {
            System.out.println(client + "\n");
        }

    }

    @Override
    public Client searchClientByLogin(String login) {
        Client clientName = null;


        for (Client client : clients) {
            if (client.getLogin().equals(login)) {
                clientName = client;
            }
        }
        System.out.println(clientName);
        return clientName;


    }

    @Override
    public void makeADiscount(Discount discount) {

    }

    @Override
    public List<Purchase> showAllPurchases() {
        for (Purchase purchase: purchases) {
            System.out.println(purchase + "\n");
        }
        return purchases;
    }

    @Override
    public List<Client> showAllClient() {
        for (Client client: clients) {
            System.out.println(client + "\n");
        }
        return clients;

    }

    @Override
    public Purchase searchPurchasebyId(int id) {
        Purchase purchaseName = null;


        for (Purchase purchase : purchases) {
            if (purchase.getId()==(id)) {
                purchaseName= purchase;
            }
        }
        System.out.println(purchaseName);
        return purchaseName;

    }
}
