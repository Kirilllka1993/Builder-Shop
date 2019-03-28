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
public class GoodInPurchaseTest {
    @Autowired
    private GoodInPurchaseService goodInPurchaseService;
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
    public void addIntoGoodInPurchaseTest() throws RepeatitionException, PurchaseException, GoodException {
        goodService.addGood(goodBeforeTest);
        amountBeforeTest = 3;
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        List<GoodInPurchase> findGoodsById = missingGoods.stream().filter(goodInPurchase -> goodInPurchase.getGood().getId() != goodBeforeTest.getId() ||
                goodInPurchase.getPurchase().getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(goodInPurchase -> goodInPurchase.getGood().getId() == goodBeforeTest.getId() ||
                goodInPurchase.getPurchase().getId() == purchaseBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodInPurchaseService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestSameGood() throws RepeatitionException, PurchaseException {
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        GoodInPurchase goodInPurchase = goodInPurchases.get(0);
        Purchase purchase = goodInPurchase.getPurchase();
        Good good = goodInPurchase.getGood();
        goodInPurchaseService.addInGoodInPurchase(good, amountBeforeTest, purchase);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestMoreAmount() throws RepeatitionException, PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = good.getAmount() + 50;
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        GoodInPurchase goodInPurchase = goodInPurchases.get(0);
        Purchase purchase = goodInPurchase.getPurchase();
        goodInPurchaseService.addInGoodInPurchase(good, newAmount, purchase);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestLessAmount() throws RepeatitionException, PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = -1;
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        GoodInPurchase goodInPurchase = goodInPurchases.get(0);
        Purchase purchase = goodInPurchase.getPurchase();
        goodInPurchaseService.addInGoodInPurchase(good, newAmount, purchase);
    }

    @Test
    public void deleteFromPurchaseTest() throws RepeatitionException, PurchaseException, GoodException {
        goodService.addGood(goodBeforeTest);
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        GoodInPurchase goodInPurchase = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        goodInPurchaseService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        Assert.assertTrue(goodInPurchases.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == goodInPurchase.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void deleteGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        GoodInPurchase goodInPurchase = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        goodInPurchaseService.deleteGoodInPurchase(goodInPurchase.getId());
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        Assert.assertTrue(goodInPurchases.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == goodInPurchase.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

//    @Test
//    public void findGoodsInPurchaseTest(){
//        List<Good> foundsGoodsByPurchase = goodInPurchaseService.findGoodsByPurchase(purchaseBeforeTest.getId());
//        List<GoodInPurchase> goodInPurchases=goodInPurchaseService.findGoodInPurchases();
//        List<GoodInPurchase> findGoodInPurchases=goodInPurchaseService.findGoodInPurchasesByPurchase(purchaseBeforeTest.getId());
//        goodInPurchaseService.deleteGoodInPurchasesWithCancelledStatus(purchaseBeforeTest);
//        Assert.assertEquals();
////        List<Good> missingGoods = goodService.findAllGoods();
////        missingGoods.removeAll(foundsGoodsBySubsection);
////        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection)));
//    }

    @Test
    public void deleteGoodInPurchasesWithCancelledStatus() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        GoodInPurchase goodInPurchase = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        goodInPurchaseService.deleteGoodInPurchasesWithCancelledStatus(purchaseBeforeTest);
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        Assert.assertTrue(goodInPurchases.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getId() == goodInPurchase.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void changeAmountInGoodInPurchaseTest() throws GoodException, RepeatitionException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        goodInPurchaseService.changeAmountInGoodInPurchase(goodBeforeTest.getId(), amountBeforeTest, purchaseBeforeTest.getId());
        GoodInPurchase goodInPurchase = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        List<GoodInPurchase> findGoodInPurchase = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != goodInPurchase.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodInPurchase);
        Assert.assertTrue(missingGoods.stream().anyMatch(goodInPurchase1 -> goodInPurchase1
                .getAmount() == goodInPurchase.getAmount()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodInPurchaseService.deleteGoodInPurchase(goodInPurchase.getId());
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionMoreAmountTest() throws PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = good.getAmount() + 50;
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        GoodInPurchase goodInPurchase = goodInPurchases.get(0);
        Purchase purchase = goodInPurchase.getPurchase();
        goodInPurchaseService.changeAmountInGoodInPurchase(good.getId(), newAmount, purchase.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionLessAmountTest() throws PurchaseException {
        List<Good> goods = goodService.findAllGoods();
        Good good = goods.get(0);
        int newAmount = -1;
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        GoodInPurchase goodInPurchase = goodInPurchases.get(0);
        Purchase purchase = goodInPurchase.getPurchase();
        goodInPurchaseService.changeAmountInGoodInPurchase(good.getId(), newAmount, purchase.getId());
    }

    @Test
    public void findGoodInPurchaseByIdTest() {
        List<GoodInPurchase> goodInPurchases = goodInPurchaseService.findGoodInPurchases();
        int id = goodInPurchases.get(0).getId();
        GoodInPurchase findGoodInPurchaseById = goodInPurchaseService.findGoodInPurchaseById(id);
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        List<GoodInPurchase> findGoodsById = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != findGoodInPurchaseById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test
    public void findGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        goodService.addGood(goodBeforeTest);
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        GoodInPurchase findGoodInPurchaseById = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        List<GoodInPurchase> findGoodsById = missingGoods.stream().filter(goodInPurchase1 -> goodInPurchase1.getId() != findGoodInPurchaseById.getId() ||
                goodInPurchase1.getPurchase().getId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodInPurchaseService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void returnedAmountOfGoodTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        goodService.addGood(goodBeforeTest);
        int amountInStore = goodBeforeTest.getAmount();
        goodInPurchaseService.addInGoodInPurchase(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        GoodInPurchase goodInPurchase = goodInPurchaseService.findGoodInPurchase(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        int amountAfterAddingInGoodInPurchase = goodInPurchase.getGood().getAmount();
        Assert.assertNotEquals(amountAfterAddingInGoodInPurchase, amountInStore);
        goodInPurchaseService.returnedAmountOfGood(goodInPurchase);
        Good good = goodService.findGoodById(goodBeforeTest.getId());
        Assert.assertEquals(amountInStore, good.getAmount());
        goodInPurchaseService.deleteFromPurchase(goodBeforeTest, purchaseBeforeTest);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void reduceAmountTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        goodService.addGood(goodBeforeTest);
        Good good = goodService.findGoodById(goodBeforeTest.getId());
        int amountInStore = good.getAmount();
        goodInPurchaseService.reduceAmount(goodBeforeTest.getId(), amountBeforeTest);
        Good good1 = goodService.findGoodById(goodBeforeTest.getId());
        int amountAfterReduce = good1.getAmount();
        Assert.assertNotEquals(amountAfterReduce, amountInStore);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void findGoodInPurchasesByPurchaseTest() {
        List<GoodInPurchase> foundsGoodsByPurchase = goodInPurchaseService.findGoodInPurchasesByPurchase(purchaseBeforeTest.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(goodInPurchase1 -> goodInPurchase1.getPurchase().equals(purchaseBeforeTest)));
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getPurchase().equals(purchaseBeforeTest)));
    }

    @Test
    public void findGoodInPurchasesByGoodTest() {
        Good good=goodService.findAllGoods().get(0);
        List<GoodInPurchase> foundsGoodsByPurchase = goodInPurchaseService.findGoodInPurchasesByGood(good.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(goodInPurchase1 -> goodInPurchase1.getGood().equals(good)));
        List<GoodInPurchase> missingGoods = goodInPurchaseService.findGoodInPurchases();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(goodInPurchase1 -> goodInPurchase1.getGood().equals(good)));//Доработать
    }


}
