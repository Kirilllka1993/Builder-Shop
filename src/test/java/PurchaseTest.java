import com.vironit.kazimirov.dao.DaoInterface.PurchaseDao;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Purchase.PurchaseBuilder;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.PurchaseNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurchaseService;
import com.vironit.kazimirov.service.impl.PurchaseServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class PurchaseTest {
    PurchaseService purchaseService = new PurchaseServiceImpl();

    //Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);

    List<Good> goods = new ArrayList<>();
    Purchase purchaseBeforeTest = null;

    @Before
    public void createPurchase() {
        Purpose purpose1 = new Purpose(1, "Фундамент");
        Purpose purpose2 = new Purpose(2, "Внутренние работы");
        Purpose purpose3 = new Purpose(3, "Наружные работы");
        Purpose purpose4 = new Purpose(4, "Кровельные работы");
        Subsection subsection1 = new Subsection(1, "Утеплитель");
        Subsection subsection2 = new Subsection(2, "Сухие смеси");
        Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
        Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.FEBRUARY, 14, 12, 56);
        Client client1 = new Client(1, "Andrei", "Stelmach", "andrei15", "andrei15", "Majkovski street", "1225689");
        Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
        Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
        Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
        Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose4, "Техноэласт", 18);
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        double sum = goods.stream().mapToDouble(s -> (s.getPrice() * s.getAmount() - s.getDiscount() * s.getAmount())).sum();

        PurchaseBuilder purchaseBuilder = new PurchaseBuilder();
        purchaseBeforeTest = purchaseBuilder.withGoods(goods)
                .withClient(client1)
                .withRegistration(localDateTime1)
                .withPurchase(localDateTime1)
                .withCost(sum)
                .withStatus("Оформлен")
                .build();
    }


    @Test
    public void makeAPurchaseTest() throws PurchaseException {
        Purchase purchase1 = purchaseService.makeAPurchase(purchaseBeforeTest);
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> findPurchaseById = missingPurchases.stream().filter(purchase -> purchase.getId() != purchase1.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchaseById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase -> purchase.getId() == purchase1.getId()));
        int size = missingPurchases.size();
        assertEquals(size, 1);
    }

    @Test
    public void addIntoPurchaseTest() throws RepeatitionException {
        Good good = purchaseService.findGoods().get(0);
        Good good1 = purchaseService.findGoods().get(purchaseService.findPurchases().size() - 2);
        purchaseService.addIntoPurchase(good.getId(), good.getAmount());
        List<Good> purchaseCart = purchaseService.addIntoPurchase(good1.getId(), good1.getAmount());
        List<Good> missingGoods = purchaseService.findGoods();
        //List<Good> deleteGoods=missingGoods.stream().filter(good->good.eq)
        int size = purchaseCart.size();
        assertEquals(size, 2);

       /* List<Purchase> findPurchaseById = missingGoods.stream().filter(purchase -> purchase.getId() != purchase1.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchaseById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase -> purchase.getId() == purchase1.getId()));
        List<Good> m = new ArrayList<>();
        expPurchaseCart.add(good);
        assertEquals(expPurchaseCart, purchaseCart);*/
    }

    @Test(expected = RepeatitionException.class)
    public void addIntoPurchaseExceptionTest() throws RepeatitionException {
        purchaseService.addIntoPurchase(1, 86);
    }

    @Test(expected = PurchaseException.class)
    public void deleteFromPurchaseTest() throws PurchaseException {
        purchaseService.deleteFromPurchase(6);

    }

    @Test
    public void findPurchasesByDateTest() throws PurchaseNotFoundException {
        LocalDateTime localDateTime = purchaseService.findPurchases().get(0).getPurchase();
        List<Purchase> purchasesByDate = purchaseService.findPurchasesByDate(localDateTime);
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> findPurchaseByDate = missingPurchases.stream().filter(purchase -> purchase.getPurchase().equals(localDateTime)).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchaseByDate);
        Assert.assertTrue(missingPurchases.stream().noneMatch(purchase -> purchase.getPurchase().equals(localDateTime)));
        assertEquals(purchasesByDate, findPurchaseByDate);
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void findPurchasesByDateExceptionTest() throws PurchaseNotFoundException {
        LocalDateTime localDateTime5 = LocalDateTime.of(2010, Month.JULY, 16, 12, 8);
        purchaseService.findPurchasesByDate(localDateTime5);
    }
}
