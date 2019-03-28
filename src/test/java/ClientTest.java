import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.entity.builder.Review.ReviewBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
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
public class ClientTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    Client clientBeforeTest = null;
    Client clientBeforeExceptionTest = null;
    Review reviewBeforeTest = null;

    @Before
    public void createClient() {
        List<Client> allClients = adminService.findAllClient();
        List<Client> clients = new ArrayList<>();
        clients.add(allClients.get(0));
        clients.add(allClients.get(1));
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
        clientBeforeExceptionTest = allClients.get(0);
    }

//    @Before
//    public void createReview() {
//        Subsection subsection2 = new Subsection(2, "Сухие смеси");
//        Purpose purpose2 = new Purpose(2, "Внутренние работы");
//        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
//        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
//        ReviewBuilder reviewBuilder = new ReviewBuilder();
//        reviewBeforeTest = reviewBuilder.withMark(5)
//                .withText("Мне этот товар не понравился")
//                .withClient(client3)
//                .withGood(good2)
//                .build();
//    }


    @Test
    public void signInTest() throws RepeatitionException {
        clientService.signIn(clientBeforeTest);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsById = missingClients.stream().filter(client -> client.getId() != clientBeforeTest.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        Assert.assertTrue(missingClients.stream().allMatch(client -> client.getId() == clientBeforeTest.getId()));
        int size = missingClients.size();
        assertEquals(size, 1);
        adminService.deleteClient(clientBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void signInExceptionTest() throws RepeatitionException {
        clientService.signIn(clientBeforeExceptionTest);
    }

    @Test
    public void logInTest() {
        Client findClientByLogin = adminService.findAllClient().get(0);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClientsByLogin = missingClients.stream().filter(client -> !client.getLogin().equals(findClientByLogin
                .getLogin()) && !client.getPassword().equals(findClientByLogin.getPassword())).collect(Collectors.toList());
        missingClients.removeAll(findClientsByLogin);
        Assert.assertTrue(missingClients.stream().anyMatch((client -> client.getLogin().equals(findClientByLogin
                .getLogin()) && client.getPassword().equals(findClientByLogin.getPassword()))));
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void logInExceptionTest() throws ClientNotFoundException {
        clientService.logIn(clientBeforeTest.getLogin(), clientBeforeTest.getPassword());
    }

    @Test
    public void changeLogin() throws RepeatitionException, ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newLogin = clientBeforeTest.getLogin();
        Client oldClient = adminService.findClientById(idOfLastClient);
        String oldLogin = oldClient.getLogin();
        clientService.changeLogin(idOfLastClient, newLogin);
        Client updateClient = adminService.findClientByLogin(newLogin);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> !client
                .getLogin().equals(updateClient.getLogin())).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client
                .getLogin().equals(updateClient.getLogin())));
        int size = missingClients.size();
        assertEquals(size, 1);
        clientService.changeLogin(idOfLastClient, oldLogin);
    }

    @Test(expected = RepeatitionException.class)
    public void changeLoginExceptionTest() throws RepeatitionException {
        Client client = adminService.findAllClient().get(0);
        clientService.changeLogin(client.getId(), clientBeforeExceptionTest.getLogin());
    }

    //    @Test
//    public void addReviewTest() {
//        Client clientForTest = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
//        clientService.addReview(reviewBeforeTest);
//        List<Review> missingReviews = clientService.findAllReviews(clientForTest);
//        List<Review> findReviewsById = missingReviews.stream().filter(review -> review.getId() != reviewBeforeTest.getId() &&
//                !review.getClient().equals(clientForTest)).collect(Collectors.toList());
//        missingReviews.removeAll(findReviewsById);
//        Assert.assertTrue(missingReviews.stream().anyMatch(review -> review.getId() == reviewBeforeTest.getId() && review.getClient().equals(clientForTest)));
//    }
//
//    @Test
//    public void removeReviewTest() {
//        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");не правильный
//        int id = 2;
//        clientService.removeReview(id, client1);
//        List<Review> allReviews = clientService.findAllReviews(client1);
//        Assert.assertTrue(allReviews.stream().noneMatch(review -> review.getId() == id));
//    }
//
    @Test
    public void changePasswordTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newPassword = clientBeforeTest.getPassword();
        Client oldClient = adminService.findClientById(idOfLastClient);
        String oldPassword = oldClient.getPassword();
        clientService.changePassword(idOfLastClient, newPassword);
        Client updateClient = adminService.findClientById(idOfLastClient);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client
                .getPassword().equals(updateClient.getPassword())));
        int size = missingClients.size();
        assertEquals(size, 1);
        clientService.changePassword(idOfLastClient, oldPassword);
    }

    @Test
    public void changeAddressTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newAddress = clientBeforeTest.getAddress();
        Client oldClient = adminService.findClientById(idOfLastClient);
        String oldAddress = oldClient.getAddress();
        clientService.changeAddress(idOfLastClient, newAddress);
        Client updateClient = adminService.findClientById(idOfLastClient);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client
                .getAddress().equals(updateClient.getAddress())));
        int size = missingClients.size();
        assertEquals(size, 1);
        clientService.changeAddress(idOfLastClient, oldAddress);
    }

    @Test
    public void changePhoneNumberTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newPhone = clientBeforeTest.getPhoneNumber();
        Client oldClient = adminService.findClientById(idOfLastClient);
        String oldPhoneNumber = oldClient.getPhoneNumber();
        clientService.changePhoneNumber(idOfLastClient, newPhone);
        Client updateClient = adminService.findClientById(idOfLastClient);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client
                .getPhoneNumber().equals(updateClient.getPhoneNumber())));
        int size = missingClients.size();
        assertEquals(size, 1);
        clientService.changePhoneNumber(idOfLastClient, oldPhoneNumber);
    }
}
