import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class AdminTest {


    AdminService adminService = new AdminServiceImpl();


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

    /*Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, "complited");
    Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, "complited");
    Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, "complited");
    Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, "complited");*/

    Client clientBeforeForExceptionTest = null;
    Client clientBeforeTest = null;
    List<Good> goods = new ArrayList<>();


    @Before
    public void createClient() {

        ClientBuilder clientBuilder1 = new ClientBuilder();
        clientBeforeTest = clientBuilder1.withName("Artem")
                .withSurname("Pupkin")
                .withLogin("artem15")
                .withPassword("artem15")
                .withAdress("Nezavisimosti street")
                .withPhoneNumber("5632398")
                .build();

        /*clientsBeforeTest = new ArrayList<>();
        clientsBeforeTest.add(client1);
        clientsBeforeTest.add(client2);
        clientsBeforeTest.add(client3);
        clientsBeforeTest.add(client4);*/
    }

    @Before
    public void createClientForException() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBeforeForExceptionTest = clientBuilder.withName("Andrei")
                .withSurname("Stelmach")
                .withLogin("andrei15")
                .withPassword("andrei15")
                .withAdress("Majkovski street")
                .withPhoneNumber("1225689")
                .build();
    }

    @Before
    public void createGoods() {
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
    }


    @Test
    public void findAllClientsTest() {
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        List<Client> actualClients = adminService.findAllClient();
        assertEquals(clients, actualClients);
    }

    @Test
    public void findAllPurchasesTest() throws PurchaseNotFoundException {
        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, "complited");
        Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, "complited");
        Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, "complited");
        Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, "complited");
        //Purchase expPurchase1=adminService.findPurchasebyId(1);
        //Purchase expPurchase2=adminService.findPurchasebyId(2);
        List<Purchase> purchases = adminService.findAllPurchases();
        List<Purchase> missingPurchases = purchases.stream().filter(purchase -> !purchase.equals(purchase1) && !purchase.equals(purchase2)).collect(Collectors.toList());
        purchases.removeAll(missingPurchases);
        List<Purchase> expPurchases = new ArrayList<>();

        expPurchases.add(purchase1);
        expPurchases.add(purchase2);
        assertEquals(expPurchases, purchases);
    }


    @Test
    public void addClientTest() throws RepeatitionException {
        adminService.addClient(clientBeforeTest);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById;
        findClientsById = missingClients.stream().filter(client -> client.getId() != clientBeforeTest.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        Assert.assertTrue(missingClients.stream().allMatch(client -> client.getId() == clientBeforeTest.getId()));
    }

    @Test(expected = RepeatitionException.class)
    public void addClientException() throws RepeatitionException {
        adminService.addClient(clientBeforeForExceptionTest);
    }


    @Test
    public void deleteClientTest() {
        adminService.deleteClient(2);
        List<Client> allClients = adminService.findAllClient();
        Assert.assertTrue(allClients.stream().noneMatch(client -> client.getId() == 2));
    }

    @Test
    public void findClientByLoginTest() throws ClientNotFoundException {
        Client findClientByLogin = adminService.findClientByLogin("kirill12");
        Assert.assertTrue(findClientByLogin.getLogin().equals("kirill12"));
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsByLogin = missingClients.stream().filter(client -> client.getLogin().equals("kirill12")).collect(Collectors.toList());
        missingClients.removeAll(findClientsByLogin);
        Assert.assertTrue(missingClients.stream().noneMatch(client -> client.getLogin().equals("kirill12")));
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByLoginExceptionTest() throws ClientNotFoundException {
        adminService.findClientByLogin("andrei18");
    }

    @Test
    public void findClientByIdTest() throws ClientNotFoundException {
        Client findClientByID = adminService.findClientById(2);
        Assert.assertTrue(findClientByID.getId() == 2);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById = missingClients.stream().filter(client -> client.getId() != 2).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByIdExceptionTest() throws ClientNotFoundException {
        adminService.findClientById(9);
    }

    @Test
    public void findPurchaseByIdTest() throws PurchaseNotFoundException {

        Purchase findPurchaseById = adminService.findPurchasebyId(2);
        Assert.assertTrue(findPurchaseById.getId() == 2);
        List<Purchase> missingPurchases = adminService.findAllPurchases();
        List<Purchase> findPurchasesById = missingPurchases.stream().filter(purchase -> purchase.getId() != 2).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        int size = missingPurchases.size();
        assertEquals(size, 1);


    }

    @Test(expected = PurchaseNotFoundException.class)
    public void findPurchaseByIdExceptionTest() throws PurchaseNotFoundException {
        adminService.findPurchasebyId(6);
    }

    @Test
    public void changeDiscountTest(){
        int id=2;
        int discount=4;

        adminService.changeDiscount(id,discount);
        List<Good> allGoods=adminService.findAllGoods();
        Assert.assertTrue(allGoods.stream().anyMatch(good -> good.getId()==id && good.getDiscount()==discount));

    }

}





