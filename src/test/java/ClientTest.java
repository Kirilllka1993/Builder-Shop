import com.vironit.kazimirov.dao.ClientDaoImpl;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
import com.vironit.kazimirov.entity.builder.Review.ReviewBuilder;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.impl.ClientServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ClientTest {
    ClientService clientService = new ClientServiceImpl();
    ClientDaoImpl clientDao = new ClientDaoImpl();
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
    Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
    Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
    Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

    Review review1 = new Review(1, "Классный товар", 5, client1, good1);
    Review review2 = new Review(2, "Ужасный товар", 1, client1, good2);
    Review review3 = new Review(3, "Неплохой товар", 3, client1, good2);
    Review review4 = new Review(4, "Хороший товар", 4, client1, good4);

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
        ReviewBuilder reviewBuilder = new ReviewBuilder();
        reviewBeforeTest = reviewBuilder.withMark(5)
                .withText("Мне этот товар не понравился")
                .withClient(client3)
                .withGood(good2)
                .build();
    }


    @Test
    public void signInTest() throws RepeatitionException {

        clientService.signIn(clientBeforeTest);
        List<Client> missingClients = clientService.findAllClients();
        List<Client> findClientsById;
        findClientsById = missingClients.stream().filter(client -> client.getId() != clientBeforeTest.getId()).collect(Collectors.toList());
        missingClients.removeAll(findClientsById);
        Assert.assertTrue(missingClients.stream().allMatch(client -> client.getId() == clientBeforeTest.getId()));
    }

    @Test(expected = RepeatitionException.class)
    public void signInExceptionTest() throws RepeatitionException {
        clientService.signIn(clientBeforeExceptionTest);
    }


    @Test(expected = RepeatitionException.class)
    public void logInExceptionTest() throws RepeatitionException {
        clientService.logIn("dfds", "dsfds");
    }

    @Test(expected = RepeatitionException.class)
    public void changeLoginExceptionTest() throws RepeatitionException {
        clientService.changeLogin(1, "andrei15");
    }

    @Test
    public void addReviewTest() {
        Client clientForTest = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
        clientService.addReview(reviewBeforeTest);
        List<Review> missingReviews = clientService.findAllReviews(clientForTest);
        List<Review> findReviewsById;
        findReviewsById = missingReviews.stream().filter(review -> review.getId() != reviewBeforeTest.getId() && !review.getClient().equals(clientForTest)).collect(Collectors.toList());
        missingReviews.removeAll(findReviewsById);
        Assert.assertTrue(missingReviews.stream().anyMatch(review -> review.getId() == reviewBeforeTest.getId() && review.getClient().equals(clientForTest)));
    }


    @Test
    public void removeReviewTest() {
        int id = 2;
        clientService.removeReview(id, client1);
        List<Review> allReviews = clientService.findAllReviews(client1);
        Assert.assertTrue(allReviews.stream().noneMatch(review -> review.getId() == id));
    }

    @Test
    public void changePasswordTest() {
        int id = 2;
        String password = "hello046";
        clientService.changePassword(id, password);
        List<Client> allClients = clientService.findAllClients();
        Assert.assertTrue(allClients.stream().anyMatch(client -> client.getId() == id && client.getPassword().equals(password)));
    }
    @Test
    public void changeAdressTest() {
        int id = 2;
        String adress = "berbery street";
        clientService.changeAdress(id, adress);
        List<Client> allClients = clientService.findAllClients();
        Assert.assertTrue(allClients.stream().anyMatch(client -> client.getId() == id && client.getAddress().equals(adress)));
    }

    @Test
    public void changePhoneNumberTest() {
        int id = 2;
        String phoneNumber = "5666556";
        clientService.changePhoneNumber(id, phoneNumber);
        List<Client> allClients = clientService.findAllClients();
        Assert.assertTrue(allClients.stream().anyMatch(client -> client.getId() == id && client.getPhoneNumber().equals(phoneNumber)));
    }
}
