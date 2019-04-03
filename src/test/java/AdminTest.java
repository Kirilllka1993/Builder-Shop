import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
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
    @Autowired
    private PurchaseService purchaseService;

    User userBeforeForExceptionTest = null;
    User userBeforeTest = new User();

    @Before
    public void createClient() {
        List<User> allUsers = adminService.findAllClient();
        List<User> users = new ArrayList<>();
        users.add(allUsers.get(0));
        users.add(allUsers.get(1));
        String neverUseLogin = users.stream().map(User::getLogin).collect(Collectors.joining());
        ClientBuilder clientBuilder = new ClientBuilder();
        userBeforeTest = clientBuilder.withId(0)
                .withName("Artem")
                .withSurname("Pupkin")
                .withLogin(neverUseLogin)
                .withPassword("artem15")
                .withAdress("Nezavisimosti street")
                .withPhoneNumber("5632398")
                .build();
        userBeforeTest.setUserRoleEnum(UserRoleEnum.USER);
    }

    @Before
    public void createClientForException() {
        List<User> allUsers = adminService.findAllClient();
        userBeforeForExceptionTest = allUsers.get(0);
    }

    @Test
    public void addClientTest() throws RepeatitionException {
        adminService.addClient(userBeforeTest);
        List<User> missingUsers = adminService.findAllClient();
        List<User> findClientsById;
        findClientsById = missingUsers.stream().filter(client -> client.getId() != userBeforeTest.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findClientsById);
        Assert.assertTrue(missingUsers.stream().allMatch(client -> client.getId() == userBeforeTest.getId()));
        int size = missingUsers.size();
        assertEquals(size, 1);
        adminService.deleteClient(userBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void addClientExceptionTest() throws RepeatitionException {
        adminService.addClient(userBeforeForExceptionTest);

    }

    @Test
    public void deleteClientTest() throws RepeatitionException {
        adminService.addClient(userBeforeTest);
        int deleteId = userBeforeTest.getId();
        adminService.deleteClient(userBeforeTest.getId());
        List<User> clients1 = adminService.findAllClient();
        Assert.assertTrue(clients1.stream().noneMatch(client -> client.getId() == deleteId));
    }

    @Test
    public void findClientByLoginTest() throws RepeatitionException, ClientNotFoundException {
        adminService.addClient(userBeforeTest);
        User userByLogin = adminService.findClientByLogin(userBeforeTest.getLogin());
        List<User> missingUsers = adminService.findAllClient();
        List<User> allClientsByLogin = missingUsers.stream().filter(client -> !client.getLogin().equals(userByLogin.getLogin())).collect(Collectors.toList());
        missingUsers.removeAll(allClientsByLogin);
        Assert.assertTrue(missingUsers.stream().allMatch((client -> client.getLogin().equals(userByLogin.getLogin()))));
        adminService.deleteClient(userBeforeTest.getId());
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByLoginExceptionTest() throws ClientNotFoundException {
        adminService.findClientByLogin(userBeforeTest.getLogin());
    }

    @Test
    public void findClientByIdTest() {
        User userByID = adminService.findAllClient().get(0);
        List<User> missingUsers = adminService.findAllClient();
        List<User> findClientsById = missingUsers.stream().filter(client -> client.getId() != userByID.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findClientsById);
        int size = missingUsers.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void findClientByIdExceptionTest() throws ClientNotFoundException {
        List<User> users =adminService.findAllClient();
        int sum=1;
        for(int i = 1; i< users.size(); i++){
            sum=sum* users.get(i).getId();
        }
        adminService.findClientById(sum);
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
//    public void updateStatusTest() {
//        List<Purchase> purchases = purchaseService.findPurchases();
//        Purchase purchase = purchases.get(0);
//    }
}





