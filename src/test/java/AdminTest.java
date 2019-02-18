import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class AdminTest {

    AdminService adminService = new AdminServiceImpl();
    Client clientBeforeForExceptionTest = null;
    Client clientBeforeTest = null;

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
    public void addClientTest() throws RepeatitionException {
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
    public void addClientExceptionTest() throws RepeatitionException {
        adminService.addClient(clientBeforeForExceptionTest);

    }


    @Test
    public void deleteClientTest() {
        adminService.deleteClient(2);
        List<Client> allClients = adminService.findAllClient();
        Assert.assertTrue(allClients.stream().noneMatch(client -> client.getId() == 2));
    }

    @Test
    public void findClientByLoginTest() throws RepeatitionException, ClientNotFoundException {

        adminService.addClient(clientBeforeTest);
        Client findClientByLogin = adminService.findClientByLogin(clientBeforeTest.getLogin());
        Assert.assertTrue(findClientByLogin.getLogin().equals(clientBeforeTest.getLogin()));
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsByLogin = missingClients.stream().filter(client -> !client.getLogin().equals(findClientByLogin.getLogin())).collect(Collectors.toList());
        missingClients.removeAll(findClientsByLogin);
        Assert.assertTrue(missingClients.stream().allMatch((client -> client.getLogin().equals(findClientByLogin.getLogin()))));
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
        Client findClientByID = adminService.findAllClient().get(0);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById = missingClients.stream().filter(client -> client.getId() != findClientByID.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByIdExceptionTest() throws ClientNotFoundException {
        int id = 1;
        int i = 0;
        List<Client> clientList = adminService.findAllClient();
        while (i < clientList.size()) {
            if (id != clientList.get(i).getId()) {
                break;
            } else {
                ++id;
            }
            ++i;
        }
        ++id;
       /* for (int i = 0; i < clientList.size(); i++) {
            if (id != clientList.get(i).getId()) {
                break;
            } else {
                ++id;
            }
        }*/
        adminService.findClientById(id);
    }

    @Test
    public void findPurchaseByIdTest() throws PurchaseNotFoundException {

        List<Purchase> purchases = adminService.findAllPurchases();
        if (purchases.size() == 0) {
            throw new PurchaseNotFoundException("This list of purchase is empty");
        }
        int id = purchases.get(0).getId();
        Purchase findPurchaseById = adminService.findPurchasebyId(id);
        Assert.assertTrue(findPurchaseById.getId() == id);
        List<Purchase> missingPurchases = adminService.findAllPurchases();
        List<Purchase> findPurchasesById = missingPurchases.stream().filter(purchase -> purchase.getId() != id).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        int size = missingPurchases.size();
        assertEquals(size, 1);


    }

    @Test(expected = PurchaseNotFoundException.class)
    public void findPurchaseByIdExceptionTest() throws PurchaseNotFoundException {
        int id = 1;
        int i = 0;
        List<Purchase> purchaseList = adminService.findAllPurchases();
        while (i < purchaseList.size()) {
            if (id != purchaseList.get(i).getId()) {
                break;
            } else {
                ++id;
            }
            ++i;
        }
        ++id;
        adminService.findPurchasebyId(id);
    }

    @Test
    public void changeDiscountTest() throws RepeatitionException {
        List<Good> goods = adminService.findAllGoods();
        int id = goods.get(0).getId();
        double discount = goods.get(3).getDiscount();
        adminService.changeDiscount(id, discount);
        List<Good> allGoods = adminService.findAllGoods();
        Assert.assertTrue(allGoods.stream().anyMatch(good -> good.getId() == id && good.getDiscount() == discount));
    }
}





