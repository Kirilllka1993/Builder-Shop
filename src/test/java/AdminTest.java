import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.AdminDaoImplFake;
import com.vironit.kazimirov.fakedao.GoodDaoImplFake;
import com.vironit.kazimirov.fakedao.PurchaseDaoImplFake;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class AdminTest {

    AdminService adminService = new AdminServiceImpl(new AdminDaoImplFake(), new PurchaseDaoImplFake());
    PurchaseService purchaseService=new PurchaseServiceImpl(new PurchaseDaoImplFake(),new GoodDaoImplFake());
    GoodService goodService=new GoodServiceImpl(new GoodDaoImplFake());

    Client clientBeforeForExceptionTest = null;
    Client clientBeforeTest = null;

    @Before
    public void createClient() {

        ClientBuilder clientBuilder1 = new ClientBuilder();
        clientBeforeTest = clientBuilder1.withId(0)
                .withName("Artem")
                .withSurname("Pupkin")
                .withLogin("artem15")
                .withPassword("artem15")
                .withAdress("Nezavisimosti street")
                .withPhoneNumber("5632398")
                .build();
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

    @Test
    public void addClientTest() throws RepeatitionException, SQLException, ClientNotFoundException {
        adminService.addClient(clientBeforeTest);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById;
        findClientsById = missingClients.stream().filter(client -> client.getId() != clientBeforeTest.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        Assert.assertTrue(missingClients.stream().allMatch(client -> client.getId() == clientBeforeTest.getId()));
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = RepeatitionException.class)
    public void addClientExceptionTest() throws RepeatitionException, SQLException, ClientNotFoundException {
        adminService.addClient(clientBeforeForExceptionTest);

    }


    @Test
    public void deleteClientTest() throws SQLException {
        int deleteId=adminService.findAllClient().get(0).getId();
        adminService.deleteClient(deleteId);
        List<Client> allClients = adminService.findAllClient();
        Assert.assertTrue(allClients.stream().noneMatch(client -> client.getId() == deleteId));
    }

    @Test
    public void findClientByLoginTest() throws RepeatitionException, ClientNotFoundException, SQLException {

        adminService.addClient(clientBeforeTest);
        Client clientByLogin = adminService.findClientByLogin(clientBeforeTest.getLogin());
        Assert.assertTrue(clientByLogin.getLogin().equals(clientBeforeTest.getLogin()));
        List<Client> missingClients = adminService.findAllClient();
        List<Client> allClientsByLogin = missingClients.stream().filter(client -> !client.getLogin().equals(clientByLogin.getLogin())).collect(Collectors.toList());
        missingClients.removeAll(allClientsByLogin);
        Assert.assertTrue(missingClients.stream().allMatch((client -> client.getLogin().equals(clientByLogin.getLogin()))));
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByLoginExceptionTest() throws ClientNotFoundException {
        adminService.findClientByLogin("andrei18");
    }

    @Test
    public void findClientByIdTest() throws Exception {
        if (adminService.findAllClient().size() == 0) {
            throw new Exception("The list of clients is empty");
        }
        Client clientByID = adminService.findAllClient().get(0);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById = missingClients.stream().filter(client -> client.getId() != clientByID.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByIdExceptionTest() throws ClientNotFoundException, SQLException {
        int sumClientId=adminService.findAllClient().stream().mapToInt(Client::getId).sum();
        adminService.findClientById(sumClientId);
    }

//    @Test
//    public void findPurchaseByIdTest() throws PurchaseNotFoundException {
//
//        List<Purchase> purchases = adminService.findAllPurchases();
//        if (purchases.size() == 0) {
//            throw new PurchaseNotFoundException("This list of purchase is empty");
//        }
//        int id = purchases.get(0).getId();
//        Purchase purchaseById = adminService.findPurchasebyId(id);
//        Assert.assertTrue(purchaseById.getId() == id);
//        List<Purchase> missingPurchases = adminService.findAllPurchases();
//        List<Purchase> purchasesById = missingPurchases.stream().filter(purchase -> purchase.getId() != id).collect(Collectors.toList());
//        missingPurchases.removeAll(purchasesById);
//        int size = missingPurchases.size();
//        assertEquals(size, 1);
//
//
//    }

//    @Test(expected = PurchaseNotFoundException.class)
//    public void findPurchaseByIdExceptionTest() throws PurchaseNotFoundException {
//        int sumPurchaseId = purchaseService.getClass().stream().mapToInt(Purchase::getId).sum();
//        purchaseService.findPurchaseById(sumPurchaseId);
//    }

    @Test
    public void changeDiscountTest() throws RepeatitionException {
        List<Good> goods = goodService.findAllGoods();
        int id = goods.get(0).getId();
        double discount = goods.get(3).getDiscount();
        adminService.changeDiscount(id, discount);
        List<Good> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().anyMatch(good -> good.getId() == id && good.getDiscount() == discount));
    }
}





