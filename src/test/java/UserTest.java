import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.ReviewDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.ReviewNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class UserTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    UserDto userBeforeTest = null;
    UserDto userBeforeExceptionTest = null;
    ReviewDto reviewBeforeTest = null;

    @Before
    public void createClient() {
        List<UserDto> allUsers = adminService.findAllClient();
        List<UserDto> users = new ArrayList<>();
        users.add(allUsers.get(0));
        users.add(allUsers.get(1));
        String neverUseLogin = users.stream().map(UserDto::getLogin).collect(Collectors.joining());
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

    @Before
    public void createReview() {
        GoodDto goodDto = goodService.findAllGoods().get(0);
        UserDto userDto = adminService.findAllClient().get(0);
        reviewBeforeTest = new ReviewDto();
        reviewBeforeTest.setComment("Мне этот товар не понравился");
        reviewBeforeTest.setGoodId(goodDto.getId());
        reviewBeforeTest.setUserId(userDto.getId());
        reviewBeforeTest.setMark(5);
    }


    @Test
    public void signInTest() throws RepeatitionException, ClientNotFoundException {
        int clientId = clientService.signIn(userBeforeTest);
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
        String newLogin = userBeforeTest.getLogin();
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

    @Test(expected = ClientNotFoundException.class)
    public void changeLoginNotFoundExceptionTest() throws RepeatitionException, ClientNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        clientService.changeLogin(clientId,userBeforeTest.getLogin());
    }

    @Test
    public void addReviewTest() throws GoodNotFoundException, ClientNotFoundException, RepeatitionException, ReviewNotFoundException {
        int clientId = adminService.addClient(userBeforeTest);
        userBeforeTest.setId(clientId);
        reviewBeforeTest.setUserId(userBeforeTest.getId());
        int reviewId = clientService.addReview(reviewBeforeTest);
        reviewBeforeTest.setId(reviewId);
        UserDto userDto = adminService.findClientById(reviewBeforeTest.getUserId());
        List<ReviewDto> missingReviews = clientService.findAllReviewsByUser(userDto);
        List<ReviewDto> findReviewsById = missingReviews.stream().filter(review -> review.getId() != reviewBeforeTest.getId() &&
                review.getUserId() != userDto.getId()).collect(Collectors.toList());
        missingReviews.removeAll(findReviewsById);
        Assert.assertTrue(missingReviews.stream().anyMatch(review -> review.getId() == reviewBeforeTest.getId() && review.getUserId() == userDto.getId()));
        clientService.removeReview(reviewBeforeTest.getUserId(), reviewBeforeTest.getGoodId());
        adminService.deleteClient(userBeforeTest.getId());
    }

    @Test(expected = ClientNotFoundException.class)
    public void addReviewExceptionNotFoundClientTest() throws ClientNotFoundException, GoodNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        reviewBeforeTest.setUserId(clientId);
        clientService.addReview(reviewBeforeTest);
    }

    @Test(expected = GoodNotFoundException.class)
    public void addReviewExceptionNotFoundGoodTest() throws ClientNotFoundException, GoodNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodId = goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        reviewBeforeTest.setGoodId(goodId);
        clientService.addReview(reviewBeforeTest);
    }

    @Test
    public void removeReviewTest() throws RepeatitionException, ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException {
        int clientId = adminService.addClient(userBeforeTest);
        userBeforeTest.setId(clientId);
        reviewBeforeTest.setUserId(userBeforeTest.getId());
        int reviewId = clientService.addReview(reviewBeforeTest);
        int deleteId = userBeforeTest.getId();
        clientService.removeReview(userBeforeTest.getId(), reviewBeforeTest.getGoodId());
        List<ReviewDto> reviewDtos = clientService.findAllReviewsByUser(userBeforeTest);
        Assert.assertTrue(reviewDtos.stream().noneMatch(client -> client.getId() == deleteId));
        adminService.deleteClient(userBeforeTest.getId());
    }

    @Test(expected = ClientNotFoundException.class)
    public void removeReviewExceptionNotFoundClientTest() throws ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        int goodId=goodService.findAllGoods().get(0).getId();
        clientService.removeReview(clientId,goodId);
    }

    @Test(expected = GoodNotFoundException.class)
    public void removeReviewExceptionNotFoundGoodTest() throws ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodId = goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        ReviewDto reviewDto=clientService.findAllReviews().get(0);
        userBeforeTest.setId(adminService.findAllClient().get(0).getId());
        clientService.removeReview(reviewDto.getUserId(),goodId);
    }

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

    @Test(expected = ClientNotFoundException.class)
    public void changePasswordNotFoundClientTest() throws ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        clientService.changePassword(clientId,"newPasswod");
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

    @Test(expected = ClientNotFoundException.class)
    public void changeAddressNotFoundClientTest() throws ClientNotFoundException, GoodNotFoundException, ReviewNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        clientService.changeAddress(clientId,"newAddress");
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

    @Test(expected = ClientNotFoundException.class)
    public void changePhoneNumberNotFoundClientTest() throws ClientNotFoundException {
        List<UserDto> userDtos = adminService.findAllClient();
        int clientId = userDtos.stream().mapToInt(userDto -> userDto.getId()).sum();
        clientService.changePhoneNumber(clientId,"newPhoneNumber");
    }

    @Test
    public void findReviewByIdTest() throws ReviewNotFoundException {
        ReviewDto reviewByID = clientService.findReviewById(clientService.findAllReviews().get(0).getId());
        List<ReviewDto> missingReviews = clientService.findAllReviews();
        List<ReviewDto> findReviewsById = missingReviews.stream().filter(reviewDto -> reviewDto.getId() != reviewByID.getId()).collect(Collectors.toList());
        missingReviews.removeAll(findReviewsById);
        int size = missingReviews.size();
        assertEquals(size, 1);
    }

    @Test(expected = ReviewNotFoundException .class)
    public void findReviewByIdExceptionTest() throws  ReviewNotFoundException {
        List<ReviewDto> reviewDtos = clientService.findAllReviews();
        int reviewId = reviewDtos.stream().mapToInt(reviewDto -> reviewDto.getId()).sum();
        clientService.findReviewById(reviewId);
    }
}
