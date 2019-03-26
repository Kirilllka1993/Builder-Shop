import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.GoodInPurchaseService;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurchaseService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
public class PurchaseTest {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodInPurchaseService goodInPurchaseService;
    @Autowired
    private GoodService goodService;

    Client clientBeforeTest = null;
    Purchase purchaseBeforeTest=null;

    @Before
    public void createPurchase() {
        clientBeforeTest = adminService.findAllClient().get(0);
        purchaseBeforeTest = purchaseService.createNewPurchase(clientBeforeTest);

    }

    @Test
    public void createNewPurchaseTest() {
        Purchase purchase = purchaseService.createNewPurchase(clientBeforeTest);
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> findPurchasesById = missingPurchases.stream().filter(purchase1 -> purchase1.getId() != purchase.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase1 -> purchase1.getId() == purchase.getId()));
        int size = missingPurchases.size();
        assertEquals(size, 1);
        purchaseService.removePurchase(purchase.getId());
    }

    @Test
    public void findPurchaseByIdTest() {
        List<Purchase> purchases = purchaseService.findPurchases();
        int id = purchases.get(0).getId();
        Purchase findPurchaseById = purchaseService.findPurchaseById(id);
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> findPurchasesById = missingPurchases.stream().filter(purchase -> purchase.getId() != findPurchaseById.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        int size = missingPurchases.size();
        assertEquals(size, 1);
    }


    @Test
    public void makeAPurchaseTest() throws PurchaseException, RepeatitionException {
        Purchase purchase = purchaseService.createNewPurchase(clientBeforeTest);
        Good good = goodService.findAllGoods().get(0);
        goodInPurchaseService.addInGoodInPurchase(good, 1, purchase);
        purchaseService.makeAPurchase(purchase.getId());
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> findPurchaseById = missingPurchases.stream().filter(purchase1 -> purchase1.getId() != purchase.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchaseById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase1 -> purchase1.getId() == purchase.getId()));
        int size = missingPurchases.size();
        assertEquals(size, 1);
        goodInPurchaseService.deleteFromPurchase(good, purchase);
        purchaseService.removePurchase(purchase.getId());
    }

    @Test(expected = PurchaseException.class)
    public void makeAPurchaseExceptionTest() throws PurchaseException {
        purchaseService.changeStatus(purchaseBeforeTest,Status.CANCELED);
        purchaseService.makeAPurchase(purchaseBeforeTest.getId());
    }


    @Test
    public void findPurchasesByDateTest() {
        LocalDateTime localDateTime = purchaseService.findPurchases().get(0).getTimeOfPurchase();
        List<Purchase> missingPurchases = purchaseService.findPurchases();
        List<Purchase> filtredPurchasesByDate = missingPurchases.stream().filter(purchase -> purchase.getTimeOfPurchase() != localDateTime).collect(Collectors.toList());
        missingPurchases.removeAll(filtredPurchasesByDate);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase -> purchase.getTimeOfPurchase().equals(localDateTime)));
    }


    @Test
    public void removePurchaseTest() {
        Purchase purchase = purchaseService.createNewPurchase(clientBeforeTest);
        purchaseService.removePurchase(purchase.getId());
        List<Purchase> purchases = purchaseService.findPurchases();
        Assert.assertTrue(purchases.stream().noneMatch(purchase1 -> purchase1.getId() == purchase.getId()));
    }

    @Test
    public void changeStatusPurchaseTest() {
        Purchase purchase = purchaseService.createNewPurchase(clientBeforeTest);
        purchaseService.changeStatus(purchase,Status.IN_PROCESS);
        Purchase purchase1=purchaseService.findPurchaseById(purchase.getId());
        Assert.assertEquals(Status.IN_PROCESS,purchase1.getStatus());
        purchaseService.removePurchase(purchase.getId());
    }

    @After
    public void deletePurchase(){
        purchaseService.removePurchase(purchaseBeforeTest.getId());
    }
}


