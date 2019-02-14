import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Review;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.ClientService;
import com.vironit.kazimirov.service.impl.ClientServiceImpl;
import org.junit.Test;

public class ClientTest {
    ClientService clientService=new ClientServiceImpl();
    Review review1 = new Review(1, "Классный товар", 5);
    Review review2 = new Review(2, "Ужасный товар", 1);
    Review review3 = new Review(3, "Неплохой товар", 3);
    Review review4 = new Review(4, "Хороший товар", 4);

    Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
    Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
    Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
    Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

    @Test(expected = RepeatitionException.class)
    public void logInExceptionTest() throws RepeatitionException {
        clientService.logIn("dfds","dsfds");
    }

    @Test(expected = RepeatitionException.class)
    public void signInExceptionTest() throws RepeatitionException {
        clientService.signIn("Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
    }

    @Test(expected = RepeatitionException.class)
    public void changeLoginExceptionTest() throws RepeatitionException {
        clientService.changeLogin(1,"andrei15");
    }


}
