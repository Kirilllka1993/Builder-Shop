import com.vironit.kazimirov.dao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PurchaseTest {
    PurchaseService purchaseService = new PurchaseServiceImpl();

    Purpose purpose1 = new Purpose(1, "Фундамент");
    Purpose purpose2 = new Purpose(2, "Внутренние работы");
    Purpose purpose3 = new Purpose(3, "Наружные работы");
    Purpose purpose4 = new Purpose(4, "Кровельные работы");

    Subsection subsection1 = new Subsection(1, "Утеплитель");
    Subsection subsection2 = new Subsection(2, "Сухие смеси");
    Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
    Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");

    Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
    Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
    Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
    Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);

    LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.FEBRUARY, 14, 12, 56);
    LocalDateTime localDateTime2 = LocalDateTime.of(2019, Month.AUGUST, 20, 20, 55);
    LocalDateTime localDateTime3 = LocalDateTime.of(2018, Month.JANUARY, 15, 02, 56);
    LocalDateTime localDateTime4 = LocalDateTime.of(2017, Month.JULY, 14, 12, 8);

    Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
    Client client2 = new Client(2, "Kirill", "Kazimirov", "kirill12", "kirill12", "Suharevska street", "56689635");
    Client client3 = new Client(3, "Dem'an", "Gurski", "gurski93", "gurski93", "Odoevskogo street", "2568974");
    Client client4 = new Client(4, "David", "Bekcham", "david15", "david15", "Angarskaja street", "111222333");


    @Test
    public void makeAPurchaseTest() throws PurchaseException {
        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        Purchase purchase = purchaseService.makeAPurchase(goods, client1, localDateTime1, localDateTime1, "Оформлен");
        Purchase purchase1 = new Purchase(6, 195, goods, client1, localDateTime1, localDateTime1, "Оформлен");
        assertEquals(purchase, purchase1);
    }

    @Test
    public void addIntoPurchaseTest() throws RepeatitionException {

        List<Good> purchaseCart = new ArrayList<>();
        purchaseCart = purchaseService.addIntoPurchase(1, 54);
        List<Good> expPurchaseCart = new ArrayList<>();
        expPurchaseCart.add(good1);
        assertEquals(expPurchaseCart, purchaseCart);
    }

    @Test(expected = RepeatitionException.class)
    public void addIntoPurchaseExceptionTest() throws RepeatitionException {
        purchaseService.addIntoPurchase(1, 86);
    }

    @Test(expected = PurchaseException.class)
    public void deleteFromPurchaseTest() throws PurchaseException {
        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        purchaseService.deleteFromPurchase(6);
    }

    @Test
    public void searchPurchasesByDateTest() throws PurchaseNotFoundException {
        List<Purchase> purchases = new ArrayList<>();
        List<Purchase> expPurchases = new ArrayList<>();
        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        Purchase purchase1 = new Purchase(1, 16.6, goods, client1, localDateTime1, localDateTime1, "complited");
        purchaseService.searchPurchasesByDate(localDateTime1);
        assertEquals(expPurchases, purchases);
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void searchPurchasesByDateExceptionTest() throws PurchaseNotFoundException {
        LocalDateTime localDateTime5 = LocalDateTime.of(2010, Month.JULY, 16, 12, 8);
        purchaseService.searchPurchasesByDate(localDateTime5);
    }
}
