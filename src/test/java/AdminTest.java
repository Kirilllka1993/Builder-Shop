import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AdminTest {


    AdminService adminServiceImpl = new AdminServiceImpl();

    @Test
    public void searchClientByIdTest() throws ClientNotFoundException {
        Client client = adminServiceImpl.searchClientById(1);
        Client expClient = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        assertEquals(expClient, client);
    }

    @Test(expected = ClientNotFoundException.class)
    public void searchClientByIdExceptionTest() throws ClientNotFoundException {
       adminServiceImpl.searchClientById(9);
    }

    @Test
    public void addClientException() throws RepeatitionException {
        adminServiceImpl.addClient("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
    }
}



