import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.CartItemDto;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurchaseDto;
import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.CartItemService;
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
    private CartItemService cartItemService;
    @Autowired
    private GoodService goodService;

    UserDto userBeforeTest = null;
    PurchaseDto purchaseBeforeTest = null;

    @Before
    public void createPurchase() throws ClientNotFoundException {
        userBeforeTest = adminService.findAllClient().get(0);
        purchaseService.createNewPurchase(userBeforeTest);
        List<PurchaseDto> purchases = purchaseService.findPurchases();
        purchaseBeforeTest = purchases.get(purchases.size() - 1);

    }

    @Test
    public void createNewPurchaseTest() {

        List<PurchaseDto> missingPurchases = purchaseService.findPurchases();
        List<PurchaseDto> findPurchasesById = missingPurchases.stream().filter(purchase1 -> purchase1.getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase1 -> purchase1.getId() == purchaseBeforeTest.getId()));
        int size = missingPurchases.size();
        assertEquals(size, 1);
    }

    @Test
    public void findPurchaseByIdTest() throws PurchaseNotFoundException {
        List<PurchaseDto> purchases = purchaseService.findPurchases();
        int id = purchases.get(0).getId();
        PurchaseDto findPurchaseById = purchaseService.findPurchaseById(id);
        List<PurchaseDto> missingPurchases = purchaseService.findPurchases();
        List<PurchaseDto> findPurchasesById = missingPurchases.stream().filter(purchase -> purchase.getId() != findPurchaseById.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchasesById);
        int size = missingPurchases.size();
        assertEquals(size, 1);
    }


    @Test
    public void makeAPurchaseTest() throws PurchaseException, RepeatitionException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        GoodDto good = goodService.findAllGoods().get(0);
        cartItemService.addInCartItem(good, 1, purchaseBeforeTest);
        CartItemDto cartItemDto=cartItemService.findCartItem(good.getId(),purchaseBeforeTest.getId());
        purchaseService.makeAPurchase(purchaseBeforeTest.getId());
        List<PurchaseDto> missingPurchases = purchaseService.findPurchases();
        List<PurchaseDto> findPurchaseById = missingPurchases.stream().filter(purchase1 -> purchase1.getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingPurchases.removeAll(findPurchaseById);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase1 -> purchase1.getId() == purchaseBeforeTest.getId()));
        int size = missingPurchases.size();
        assertEquals(size, 1);
        cartItemService.deleteFromPurchase(cartItemDto);
    }

    @Test(expected = PurchaseException.class)
    public void makeAPurchaseExceptionTest() throws PurchaseException, PurchaseNotFoundException {
        purchaseService.changeStatus(purchaseBeforeTest, Status.CANCELED);
        purchaseService.makeAPurchase(purchaseBeforeTest.getId());
    }


    @Test
    public void findPurchasesByDateTest() {
        LocalDateTime localDateTime = purchaseService.findPurchases().get(0).getTimeOfPurchase();
        List<PurchaseDto> missingPurchases = purchaseService.findPurchases();
        List<PurchaseDto> filtredPurchasesByDate = missingPurchases.stream().filter(purchase -> purchase.getTimeOfPurchase() != localDateTime).collect(Collectors.toList());
        missingPurchases.removeAll(filtredPurchasesByDate);
        Assert.assertTrue(missingPurchases.stream().allMatch(purchase -> purchase.getTimeOfPurchase().equals(localDateTime)));
    }


    @Test
    public void removePurchaseTest() throws ClientNotFoundException, PurchaseNotFoundException, CantDeleteElement {
        purchaseService.createNewPurchase(userBeforeTest);
        List<PurchaseDto> purchases = purchaseService.findPurchases();
        PurchaseDto purchase = purchases.get(purchases.size() - 1);
        purchaseService.removePurchase(purchase.getId());
        List<PurchaseDto> purchases1 = purchaseService.findPurchases();
        Assert.assertTrue(purchases1.stream().noneMatch(purchase1 -> purchase1.getId() == purchase.getId()));
    }

//    @Test
//    public void changeStatusPurchaseTest() {
////        purchaseService.changeStatus(purchaseBeforeTest, Status.IN_PROCESS);
////        PurchaseDto purchase1 = purchaseService.findPurchaseById(purchaseBeforeTest.getId());
////        Assert.assertEquals(Status.IN_PROCESS, purchase1.getStatus());
//    }rete

    @After
    public void deletePurchase() throws PurchaseNotFoundException, CantDeleteElement {
        purchaseService.removePurchase(purchaseBeforeTest.getId());
    }
}


