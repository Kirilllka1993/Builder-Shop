import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.GoodService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;

    Client clientBeforeForExceptionTest = null;
    Client clientBeforeTest = new Client();

    @Before
    public void createClient() {
        List<Client> allClients = adminService.findAllClient();
        List<Client> clients = new ArrayList<>();
        clients.add(allClients.get(0));
        clients.add(allClients.get(1));
        clientBeforeForExceptionTest = allClients.get(0);
        String neverUseLogin = clients.stream().map(Client::getLogin).collect(Collectors.joining());
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBeforeTest = clientBuilder.withId(0)
                .withName("Artem")
                .withSurname("Pupkin")
                .withLogin(neverUseLogin)
                .withPassword("artem15")
                .withAdress("Nezavisimosti street")
                .withPhoneNumber("5632398")
                .build();
    }

    @Before
    public void createClientForException() {
        List<Client> allClients = adminService.findAllClient();
        clientBeforeForExceptionTest = allClients.get(0);
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
        adminService.deleteClient(clientBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void addClientExceptionTest() throws RepeatitionException {
        adminService.addClient(clientBeforeForExceptionTest);

    }

    @Test
    public void deleteClientTest() throws RepeatitionException {
        adminService.addClient(clientBeforeTest);
        int deleteId = clientBeforeTest.getId();
        adminService.deleteClient(clientBeforeTest.getId());
        List<Client> clients1 = adminService.findAllClient();
        Assert.assertTrue(clients1.stream().noneMatch(client -> client.getId() == deleteId));
    }

    @Test
    public void findClientByLoginTest() throws RepeatitionException {
        adminService.addClient(clientBeforeTest);
        Client clientByLogin = adminService.findClientByLogin(clientBeforeTest.getLogin());
        List<Client> missingClients = adminService.findAllClient();
        List<Client> allClientsByLogin = missingClients.stream().filter(client -> !client.getLogin().equals(clientByLogin.getLogin())).collect(Collectors.toList());
        missingClients.removeAll(allClientsByLogin);
        Assert.assertTrue(missingClients.stream().allMatch((client -> client.getLogin().equals(clientByLogin.getLogin()))));
        adminService.deleteClient(clientBeforeTest.getId());
    }

    @Test
    public void findClientByIdTest() {
        Client clientByID = adminService.findAllClient().get(0);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById = missingClients.stream().filter(client -> client.getId() != clientByID.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test
    public void changeDiscountTest() {
        List<Good> goods = goodService.findAllGoods();
        int id = goods.get(0).getId();
        double discountfirst = goods.get(0).getDiscount();
        double discount = goods.get(goods.size() - 1).getDiscount();
        adminService.changeDiscount(id, discount);
        List<Good> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().anyMatch(good -> good.getId() == id && good.getDiscount() == discount));
        adminService.changeDiscount(id, discountfirst);
    }

//    @Test
//    public void updateStatusTest(){
//
//    }
}





