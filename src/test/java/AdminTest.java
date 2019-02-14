import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.impl.AdminServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AdminTest {


    AdminService adminServiceImpl = new AdminServiceImpl();
    List<Client> clients = new ArrayList<>();
    private List<Good> goods = new ArrayList<>();

    Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
    Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
    Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
    Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");

    Purpose purpose1 = new Purpose(1, "Фундамент");
    Purpose purpose2 = new Purpose(2, "Внутренние работы");
    Purpose purpose3 = new Purpose(3, "Наружные работы");
    Purpose purpose4 = new Purpose(4, "Кровельные работы");

    Subsection subsection1 = new Subsection(1, "Утеплитель");
    Subsection subsection2 = new Subsection(2, "Сухие смеси");
    Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
    Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");


    Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
    Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 5, purpose2, "Шпатлевка", 13);
    Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 6, purpose3, "Краска для дерева", 15);
    Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

    Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, "complited");
    Purchase purchase2 = new Purchase(2, 18.0, goods, client2, null, null, "complited");
    Purchase purchase3 = new Purchase(3, 20.0, goods, client3, null, null, "complited");
    Purchase purchase4 = new Purchase(4, 16.9, goods, client4, null, null, "complited");


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

    @Test(expected = RepeatitionException.class)
    public void addClientException() throws RepeatitionException {
        adminServiceImpl.addClient("Sergei", "fedorov", "andrei15", "sergei15", "Lenina street", "896564321");
    }

    @Test
    public void deleteClientTest() {

        List<Client> clients1 = new ArrayList<>();
        clients1.add(client2);
        clients1.add(client3);
        clients1.add(client4);
        clients = adminServiceImpl.deleteClient(1);
        assertEquals(clients1, clients);
    }

    @Test
    public void searchClientByLoginTest() throws ClientNotFoundException {
        Client client = adminServiceImpl.searchClientByLogin("andrei15");
        assertEquals(client1, client);
    }

    @Test(expected = ClientNotFoundException.class)
    public void searchClientByLoginExceptionTest() throws ClientNotFoundException {
        adminServiceImpl.searchClientByLogin("andrei18");
    }

    @Test
    public void searchPurchaseByIdTest() throws PurchaseNotFoundException {
        Purchase purchase = adminServiceImpl.searchPurchasebyId(1);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, null, null, "complited");
        assertEquals(purchase, purchase1);
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void searchPurchaseByIdExceptionTest() throws PurchaseNotFoundException {
        adminServiceImpl.searchPurchasebyId(6);
    }

}





