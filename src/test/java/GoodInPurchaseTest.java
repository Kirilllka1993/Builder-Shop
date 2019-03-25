import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
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
    @Autowired
    private AdminService adminService;

    Good goodBeforeTest = null;
    Purchase purchaseBeforeTest = null;
    int amountBeforeTest = 0;

    @Before
    public void findElementsForTests() {
//        Purpose purpose = purposeService.findPurposes().get(0);
//        Subsection subsection = subsectionService.findSubsections().get(0);
//        GoodBuilder GoodBuilder = new GoodBuilder();
//        goodBeforeTest = GoodBuilder.withCost(6)
//                .withSubsection(subsection)
//                .withUnit("м3")
//                .withQuantity(5)
//                .withDiscount(0)
//                .withPurpose(purpose)
//                .withName("Пеноплекс")
//                .withAmount(20)
//                .build();


        goodBeforeTest = goodService.findAllGoods().get(0);
        List<Purchase> purchases = purchaseService.findPurchases();
        purchaseBeforeTest = purchases.get(purchases.size() - 1);

    }

    @Test
    public void addIntoGoodInPurchaseTest() throws RepeatitionException, PurchaseException {
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
    }
}
