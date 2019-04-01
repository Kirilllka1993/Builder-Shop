import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
public class CartItemTest {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurposeService purposeService;
    @Autowired
    private SubsectionService subsectionService;

    Good goodBeforeTest = null;
    Purchase purchaseBeforeTest = null;
    int amountBeforeTest = 1;

    @Before
    public void findElementsForTests() {
        Purpose purpose = purposeService.findPurposes().get(0);
        Subsection subsection = subsectionService.findSubsections().get(0);
        GoodBuilder GoodBuilder = new GoodBuilder();
        goodBeforeTest = GoodBuilder.withCost(6)
                .withSubsection(subsection)
                .withUnit("м3")
                .withQuantity(5)
                .withDiscount(0)
                .withPurpose(purpose)
                .withName("Пеноплекс")
                .withAmount(20)
                .build();

        List<Purchase> purchases = purchaseService.findPurchases();
        purchaseBeforeTest = purchases.get(purchases.size() - 1);

    }

    @Test
    public void addIntoCartItemTest() throws RepeatitionException, PurchaseException, GoodException {
        goodService.addGood(goodBeforeTest);
        amountBeforeTest = 3;
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        List<CartItem> missingGoods = cartItemService.findCartItems();
        List<CartItem> findGoodsById = missingGoods.stream().filter(goodInPurchase -> goodInPurchase.getGood().getId() != goodBeforeTest.getId() ||
                goodInPurchase.getPurchase().getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(goodInPurchase -> goodInPurchase.getGood().getId() == goodBeforeTest.getId() ||
                goodInPurchase.getPurchase().getId() == purchaseBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        cartItemService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestSameGood() throws RepeatitionException, PurchaseException {
        List<CartItem> cartItems = cartItemService.findCartItems();
        CartItem cartItem = cartItems.get(0);
        Purchase purchase = cartItem.getPurchase();
        Good good = cartItem.getGood();
        cartItemService.addInCartItem(good, amountBeforeTest, purchase);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestMoreAmount() throws RepeatitionException, PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = good.getAmount() + 50;
        List<CartItem> cartItems = cartItemService.findCartItems();
        CartItem cartItem = cartItems.get(0);
        Purchase purchase = cartItem.getPurchase();
        cartItemService.addInCartItem(good, newAmount, purchase);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestLessAmount() throws RepeatitionException, PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = -1;
        List<CartItem> cartItems = cartItemService.findCartItems();
        CartItem cartItem = cartItems.get(0);
        Purchase purchase = cartItem.getPurchase();
        cartItemService.addInCartItem(good, newAmount, purchase);
    }

    @Test
    public void deleteFromPurchaseTest() throws RepeatitionException, PurchaseException, GoodException {
        goodService.addGood(goodBeforeTest);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItem cartItem = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        List<CartItem> cartItems = cartItemService.findCartItems();
        Assert.assertTrue(cartItems.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == cartItem.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void deleteGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItem cartItem = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItem(cartItem.getId());
        List<CartItem> cartItems = cartItemService.findCartItems();
        Assert.assertTrue(cartItems.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == cartItem.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

//    @Test
//    public void findGoodsInPurchaseTest(){
//        List<Good> foundsGoodsByPurchase = cartItemService.findGoodsByPurchase(purchaseBeforeTest.getId());
//        List<CartItem> goodInPurchases=cartItemService.findCartItems();
//        List<CartItem> findCartItems=cartItemService.findCartItemsByPurchase(purchaseBeforeTest.getId());
//        cartItemService.deleteCartItemsWithCancelledStatus(purchaseBeforeTest);
//        Assert.assertEquals();
////        List<Good> missingGoods = goodService.findAllGoods();
////        missingGoods.removeAll(foundsGoodsBySubsection);
////        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection)));
//    }

    @Test
    public void deleteGoodInPurchasesWithCancelledStatus() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItem cartItem = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseBeforeTest);
        List<CartItem> cartItems = cartItemService.findCartItems();
        Assert.assertTrue(cartItems.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == cartItem.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void changeAmountInGoodInPurchaseTest() throws GoodException, RepeatitionException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        cartItemService.changeAmountInCartItem(goodBeforeTest.getId(), amountBeforeTest, purchaseBeforeTest.getId());
        CartItem cartItem = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<CartItem> missingGoods = cartItemService.findCartItems();
        List<CartItem> findCartItem = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != cartItem.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findCartItem);
        Assert.assertTrue(missingGoods.stream().anyMatch(goodInPurchase1 -> goodInPurchase1
                .getAmount() == cartItem.getAmount()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        cartItemService.deleteCartItem(cartItem.getId());
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionMoreAmountTest() throws PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = good.getAmount() + 50;
        List<CartItem> cartItems = cartItemService.findCartItems();
        CartItem cartItem = cartItems.get(0);
        Purchase purchase = cartItem.getPurchase();
        cartItemService.changeAmountInCartItem(good.getId(), newAmount, purchase.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionLessAmountTest() throws PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = -1;
        List<CartItem> cartItems = cartItemService.findCartItems();
        CartItem cartItem = cartItems.get(0);
        Purchase purchase = cartItem.getPurchase();
        cartItemService.changeAmountInCartItem(good.getId(), newAmount, purchase.getId());
    }

    @Test
    public void findGoodInPurchaseByIdTest() {
        List<CartItem> cartItems = cartItemService.findCartItems();
        int id = cartItems.get(0).getId();
        CartItem findCartItemById = cartItemService.findCartItemById(id);
        List<CartItem> missingGoods = cartItemService.findCartItems();
        List<CartItem> findGoodsById = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != findCartItemById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test
    public void findGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItem findCartItemById = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<CartItem> missingGoods = cartItemService.findCartItems();
        List<CartItem> findGoodsById = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != findCartItemById.getId() ||
                goodInPurchase1.getPurchase().getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
        cartItemService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void returnedAmountOfGoodTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        goodService.addGood(goodBeforeTest);
        int amountInStore = goodBeforeTest.getAmount();
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItem cartItem = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        int amountAfterAddingInGoodInPurchase = cartItem.getGood().getAmount();
        Assert.assertNotEquals(amountAfterAddingInGoodInPurchase, amountInStore);
        cartItemService.returnedAmountOfGood(cartItem);
        Good good = goodService.findGoodById(goodBeforeTest.getId());
        Assert.assertEquals(amountInStore, good.getAmount());
        cartItemService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void reduceAmountTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        goodService.addGood(goodBeforeTest);
        Good good = goodService.findGoodById(goodBeforeTest.getId());
        int amountInStore = good.getAmount();
        cartItemService.reduceAmount(goodBeforeTest.getId(), amountBeforeTest);
        Good good1 = goodService.findGoodById(goodBeforeTest.getId());
        int amountAfterReduce = good1.getAmount();
        Assert.assertNotEquals(amountAfterReduce, amountInStore);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void findGoodInPurchasesByPurchaseTest() {
        List<CartItem> foundsGoodsByPurchase = cartItemService.findCartItemsByPurchase(purchaseBeforeTest.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(goodInPurchase1 -> goodInPurchase1.getPurchase().equals(purchaseBeforeTest)));
        List<CartItem> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getPurchase().equals(purchaseBeforeTest)));
    }

    @Test
    public void findGoodInPurchasesByGoodTest() {
        Good good=goodService.findAllGoods().get(0);
        List<CartItem> foundsGoodsByPurchase = cartItemService.findCartItemsByGood(good.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(goodInPurchase1 -> goodInPurchase1.getGood().equals(good)));
        List<CartItem> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getGood().equals(good)));//Доработать
    }


}