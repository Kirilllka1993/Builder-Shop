import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.entity.builder.Review.ReviewBuilder;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.AdminDaoImplFake;
import com.vironit.kazimirov.fakedao.ClientDaoImplFake;
import com.vironit.kazimirov.fakedao.PurchaseDaoImplFake;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import com.vironit.kazimirov.service.impl.ClientServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class ClientTest {
    ClientService clientService = new ClientServiceImpl(new ClientDaoImplFake());
    AdminService adminService=new AdminServiceImpl(new AdminDaoImplFake(), new PurchaseDaoImplFake());
    Client clientBeforeTest = null;
    Client clientBeforeExceptionTest = null;
    Review reviewBeforeTest = null;

    @Before
    public void createClient() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBeforeTest = clientBuilder.withName("Andrei")
                .withSurname("Stelmach")
                .withLogin("andrei")
                .withPassword("andrei15")
                .withAdress("Majkovski street")
                .withPhoneNumber("1225689")
                .build();
    }

    @Before
    public void createClientForException() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBeforeExceptionTest = clientBuilder.withName("Andrei")
                .withSurname("Stelmach")
                .withLogin("andrei15")
                .withPassword("andrei15")
                .withAdress("Majkovski street")
                .withPhoneNumber("1225689")
                .build();
    }

    @Before
    public void createReview() {
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        ReviewBuilder reviewBuilder = new ReviewBuilder();
        reviewBeforeTest = reviewBuilder.withMark(5)
                .withText("Мне этот товар не понравился")
                .withClient(client3)
                .withGood(good2)
                .build();
    }


//    @Test
//    public void signInTest() throws RepeatitionException {
//
//        clientService.signIn(clientBeforeTest);
//        List<Client> missingClients = adminService.findAllClient();
//        List<Client> findClientsById;
//        findClientsById = missingClients.stream().filter(client -> client.getId() != clientBeforeTest.getId()).collect(Collectors.toList());
//        missingClients.removeAll(findClientsById);
//        Assert.assertTrue(missingClients.stream().allMatch(client -> client.getId() == clientBeforeTest.getId()));
//        int size = missingClients.size();
//        assertEquals(size, 1);
//    }

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

    @Test(expected = RepeatitionException.class)
    public void logInExceptionTest() throws RepeatitionException {
        clientService.logIn("dfds", "dsfds");
    }

    @Test
    public void changeLogin() throws RepeatitionException {
        int idOfLastGood = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newLogin = clientBeforeTest.getLogin();
        clientService.changeLogin(idOfLastGood, newLogin);
        Client updateClient = adminService.findAllClient().get(idOfLastGood - 1);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId() && !client
                .getLogin().equals(updateClient.getLogin())).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client.getId() == updateClient.getId() && client
                .getLogin().equals(updateClient.getLogin())));
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test(expected = RepeatitionException.class)
    public void changeLoginExceptionTest() throws RepeatitionException {
        clientService.changeLogin(1, clientBeforeExceptionTest.getLogin());

    }

    @Test
    public void addReviewTest() {
        Client clientForTest = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        clientService.addReview(reviewBeforeTest);
        List<Review> missingReviews = clientService.findAllReviews(clientForTest);
        List<Review> findReviewsById = missingReviews.stream().filter(review -> review.getId() != reviewBeforeTest.getId() &&
                !review.getClient().equals(clientForTest)).collect(Collectors.toList());
        missingReviews.removeAll(findReviewsById);
        Assert.assertTrue(missingReviews.stream().anyMatch(review -> review.getId() == reviewBeforeTest.getId() && review.getClient().equals(clientForTest)));
    }

    @Test
    public void removeReviewTest() {
//        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
//        int id = 2;
//        clientService.removeReview(id, client1);
//        List<Review> allReviews = clientService.findAllReviews(client1);
//        Assert.assertTrue(allReviews.stream().noneMatch(review -> review.getId() == id));
    }

    @Test
    public void changePasswordTest() {
        int idOfLastGood = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newPassword = clientBeforeTest.getPassword();
        clientService.changePassword(idOfLastGood, newPassword);
        Client updateClient = adminService.findAllClient().get(idOfLastGood - 1);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client.getId() == updateClient.getId() && client
                .getPassword().equals(updateClient.getPassword())));
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test
    public void changeAddressTest() {
        int idOfLastGood = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newAddress = clientBeforeTest.getAddress();
        clientService.changeAddress(idOfLastGood, newAddress);
        Client updateClient = adminService.findAllClient().get(idOfLastGood - 1);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client.getId() == updateClient.getId() && client
                .getAddress().equals(updateClient.getAddress())));
        int size = missingClients.size();
        assertEquals(size, 1);
    }

    @Test
    public void changePhoneNumberTest() {
        int idOfLastGood = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        String newPhone = clientBeforeTest.getPhoneNumber();
        clientService.changePhoneNumber(idOfLastGood, newPhone);
        Client updateClient = adminService.findAllClient().get(idOfLastGood - 1);
        List<Client> missingClients = adminService.findAllClient();
        List<Client> findClients = missingClients.stream().filter(client -> client.getId() != updateClient.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClients);
        Assert.assertTrue(missingClients.stream().anyMatch(client -> client.getId() == updateClient.getId() && client
                .getPhoneNumber().equals(updateClient.getPhoneNumber())));
        int size = missingClients.size();
        assertEquals(size, 1);
    }
}
