import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.entity.builder.Client.ClientBuilder;
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
public class UserTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    UserDto userBeforeTest = null;
    UserDto userBeforeExceptionTest = null;
    Review reviewBeforeTest = null;

    @Before
    public void createClient() {
        List<UserDto> allUsers = adminService.findAllClient();
        List<UserDto> users = new ArrayList<>();
        users.add(allUsers.get(0));
        users.add(allUsers.get(1));
        java.lang.String neverUseLogin = users.stream().map(UserDto::getLogin).collect(Collectors.joining());
//        ClientBuilder clientBuilder = new ClientBuilder();
//        userBeforeTest = clientBuilder.withId(0)
//                .withName("Artem")
//                .withSurname("Pupkin")
//                .withLogin(neverUseLogin)
//                .withPassword("artem15")
//                .withAdress("Nezavisimosti street")
//                .withPhoneNumber("5632398")
//                .build();
//        userBeforeTest.setUserRoleEnum(UserRoleEnum.ROLE_USER);
        userBeforeTest = new UserDto();
        userBeforeTest.setName("Artem");
        userBeforeTest.setSurname("Pupkin");
        userBeforeTest.setLogin(neverUseLogin);
        userBeforeTest.setPassword("artem15");
        userBeforeTest.setAddress("Nezavisimosti street");
        userBeforeTest.setPhoneNumber("5632398");
        userBeforeTest.setUserRoleEnum(UserRoleEnum.ROLE_USER.name());
    }

    @Before
    public void createClientForException() {
        List<UserDto> allUsers = adminService.findAllClient();
        userBeforeExceptionTest = allUsers.get(0);
    }

//    @Before
//    public void createReview() {
//        Subsection subsection2 = new Subsection(2, "Сухие смеси");
//        Purpose purpose2 = new Purpose(2, "Внутренние работы");
//        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
//        User client3 = new User(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
//        ReviewBuilder reviewBuilder = new ReviewBuilder();
//        reviewBeforeTest = reviewBuilder.withMark(5)
//                .withText("Мне этот товар не понравился")
//                .withClient(client3)
//                .withGood(good2)
//                .build();
//    }


    @Test
    public void signInTest() throws RepeatitionException, ClientNotFoundException {
        int clientId=clientService.signIn(userBeforeTest);
        userBeforeTest.setId(clientId);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findClientsById = missingUsers.stream().filter(client -> client.getId() != userBeforeTest.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findClientsById);
        Assert.assertTrue(missingUsers.stream().allMatch(client -> client.getId() == userBeforeTest.getId()));
        int size = missingUsers.size();
        assertEquals(size, 1);
        adminService.deleteClient(userBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void signInExceptionTest() throws RepeatitionException {
        clientService.signIn(userBeforeExceptionTest);
    }

    @Test
    public void logInTest() {
        UserDto findUserByLogin = adminService.findAllClient().get(0);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findClientsByLogin = missingUsers.stream().filter(client -> !client.getLogin().equals(findUserByLogin
                .getLogin()) && !client.getPassword().equals(findUserByLogin.getPassword())).collect(Collectors.toList());
        missingUsers.removeAll(findClientsByLogin);
        Assert.assertTrue(missingUsers.stream().anyMatch((client -> client.getLogin().equals(findUserByLogin
                .getLogin()) && client.getPassword().equals(findUserByLogin.getPassword()))));
        int size = missingUsers.size();
        assertEquals(size, 1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void logInExceptionTest() throws ClientNotFoundException {
        clientService.logIn(userBeforeTest.getLogin(), userBeforeTest.getPassword());
    }

    @Test
    public void changeLogin() throws RepeatitionException, ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        java.lang.String newLogin = userBeforeTest.getLogin();
        UserDto oldUser = adminService.findClientById(idOfLastClient);
        java.lang.String oldLogin = oldUser.getLogin();
        clientService.changeLogin(idOfLastClient, newLogin);
        UserDto updateUser = adminService.findClientByLogin(newLogin);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findUsers = missingUsers.stream().filter(client -> !client
                .getLogin().equals(updateUser.getLogin())).collect(Collectors.toList());
        missingUsers.removeAll(findUsers);
        Assert.assertTrue(missingUsers.stream().anyMatch(client -> client
                .getLogin().equals(updateUser.getLogin())));
        int size = missingUsers.size();
        assertEquals(size, 1);
        clientService.changeLogin(idOfLastClient, oldLogin);
    }

    @Test(expected = RepeatitionException.class)
    public void changeLoginExceptionTest() throws RepeatitionException, ClientNotFoundException {
        UserDto user = adminService.findAllClient().get(0);
        clientService.changeLogin(user.getId(), userBeforeExceptionTest.getLogin());
    }

    //    @Test
//    public void addReviewTest() {
//        User clientForTest = new User(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
//        clientService.addReview(reviewBeforeTest);
//        List<Review> missingReviews = clientService.findAllReviews(clientForTest);
//        List<Review> findReviewsById = missingReviews.stream().filter(review -> review.getId() != reviewBeforeTest.getId() &&
//                !review.getUser().equals(clientForTest)).collect(Collectors.toList());
//        missingReviews.removeAll(findReviewsById);
//        Assert.assertTrue(missingReviews.stream().anyMatch(review -> review.getId() == reviewBeforeTest.getId() && review.getUser().equals(clientForTest)));
//    }
//
//    @Test
//    public void removeReviewTest() {
//        User client1 = new User(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");не правильный
//        int id = 2;
//        clientService.removeReview(id, client1);
//        List<Review> allReviews = clientService.findAllReviews(client1);
//        Assert.assertTrue(allReviews.stream().noneMatch(review -> review.getId() == id));
//    }
//
    @Test
    public void changePasswordTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        java.lang.String newPassword = userBeforeTest.getPassword();
        UserDto oldUser = adminService.findClientById(idOfLastClient);
        java.lang.String oldPassword = oldUser.getPassword();
        clientService.changePassword(idOfLastClient, newPassword);
        UserDto updateUser = adminService.findClientById(idOfLastClient);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findUsers = missingUsers.stream().filter(client -> client.getId() != updateUser.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findUsers);
        Assert.assertTrue(missingUsers.stream().anyMatch(client -> client
                .getPassword().equals(updateUser.getPassword())));
        int size = missingUsers.size();
        assertEquals(size, 1);
        clientService.changePassword(idOfLastClient, oldPassword);
    }

    @Test
    public void changeAddressTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        java.lang.String newAddress = userBeforeTest.getAddress();
        UserDto oldUser = adminService.findClientById(idOfLastClient);
        java.lang.String oldAddress = oldUser.getAddress();
        clientService.changeAddress(idOfLastClient, newAddress);
        UserDto updateUser = adminService.findClientById(idOfLastClient);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findUsers = missingUsers.stream().filter(client -> client.getId() != updateUser.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findUsers);
        Assert.assertTrue(missingUsers.stream().anyMatch(client -> client
                .getAddress().equals(updateUser.getAddress())));
        int size = missingUsers.size();
        assertEquals(size, 1);
        clientService.changeAddress(idOfLastClient, oldAddress);
    }

    @Test
    public void changePhoneNumberTest() throws ClientNotFoundException {
        int idOfLastClient = adminService.findAllClient().get(adminService.findAllClient().size() - 1).getId();
        java.lang.String newPhone = userBeforeTest.getPhoneNumber();
        UserDto oldUser = adminService.findClientById(idOfLastClient);
        java.lang.String oldPhoneNumber = oldUser.getPhoneNumber();
        clientService.changePhoneNumber(idOfLastClient, newPhone);
        UserDto updateUser = adminService.findClientById(idOfLastClient);
        List<UserDto> missingUsers = adminService.findAllClient();
        List<UserDto> findUsers = missingUsers.stream().filter(client -> client.getId() != updateUser.getId()).collect(Collectors.toList());
        missingUsers.removeAll(findUsers);
        Assert.assertTrue(missingUsers.stream().anyMatch(client -> client
                .getPhoneNumber().equals(updateUser.getPhoneNumber())));
        int size = missingUsers.size();
        assertEquals(size, 1);
        clientService.changePhoneNumber(idOfLastClient, oldPhoneNumber);
    }
}
