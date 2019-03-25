import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.SubsectionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
public class GoodTest {
    @Autowired
    private GoodService goodService;
    @Autowired
    private SubsectionService subsectionService;
    @Autowired
    private PurposeService purposeService;

    Good goodBeforeTest = null;
    Good goodBeforeExceptionTest = null;

    @Before
    public void createGood() {
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
    }

    @Before
    public void createGoodException() {
        Purpose purpose = purposeService.findPurposes().get(0);
        Subsection subsection = subsectionService.findSubsections().get(0);
        List<Good> goods = goodService.findAllGoods();
        //double discount=goods.stream().mapToDouble(Good::getPrice).count();
        GoodBuilder GoodBuilder = new GoodBuilder();
        goodBeforeExceptionTest = GoodBuilder.withCost(6)
                .withSubsection(subsection)
                .withUnit("м3")
                .withQuantity(5)
                .withDiscount(10000000)
                .withPurpose(purpose)
                .withName("Пеноплекс")
                .withAmount(15)
                .build();
    }

    @Test
    public void addGoodTest() throws GoodException, RepeatitionException {
        goodService.addGood(goodBeforeTest);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsById = missingGoods.stream().filter(good -> good.getId() != goodBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(good -> good.getId() == goodBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionTest() throws GoodException, RepeatitionException {
        goodService.addGood(goodBeforeExceptionTest);
    }

    @Test(expected = RepeatitionException.class)
    public void addGoodRepeatitonExceptionTest() throws GoodException, RepeatitionException {
        Good good = goodService.findAllGoods().get(0);
        goodService.addGood(good);
    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionWrongAmountTest() throws GoodException, RepeatitionException {
        goodBeforeTest.setAmount(-1);
        goodService.addGood(goodBeforeTest);
    }


    @Test
    public void findByNameTest() {
        String name = goodService.findAllGoods().get(0).getName();
        Good findGoodByName = goodService.findByNameGood(name);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsByName = missingGoods.stream().filter(good -> good.getName().equals(findGoodByName.getName())).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getName().equals(name)));
    }

    @Test
    public void findBySubsectionTest() {
        Subsection subsection = goodService.findAllGoods().get(0).getSubsection();
        List<Good> foundsGoodsBySubsection = goodService.findBySubsection(subsection);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsection().equals(subsection)));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection)));
    }

    @Test
    public void findByPurposeTest() {
        Purpose purpose = goodService.findAllGoods().get(0).getPurpose();
        List<Good> foundGoods = goodService.findByPurpose(purpose);
        Predicate<Good> purposePredicate = good -> good.getPurpose().equals(purpose);
        Assert.assertTrue(foundGoods.stream().allMatch(purposePredicate));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurpose().equals(purpose)));
    }

    @Test
    public void deleteGoodTest() throws GoodException, RepeatitionException {
        goodService.addGood(goodBeforeTest);
        int deleteId = goodBeforeTest.getId();
        goodService.deleteGood(deleteId);
        List<Good> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().noneMatch(client -> client.getId() == deleteId));
    }

//    @Test
//    public void updateTest() throws GoodNotFountException {
//        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
//        goodService.updateGood(idOfLastGood, goodBeforeTest);
//        Good updateGood = goodService.findGoodById(idOfLastGood);
//        List<Good> missingGoods = goodService.findAllGoods();
//        List<Good> findGoodsByName = missingGoods.stream().filter(good -> !good.equals(updateGood)).collect(Collectors.toList());
//        missingGoods.removeAll(findGoodsByName);
//        Assert.assertTrue(missingGoods.stream().anyMatch(good -> good.equals(updateGood)));
//        int size = missingGoods.size();
//        assertEquals(size, 1);
//    }

//    @Test(expected = GoodNotFountException.class)
//    public void updateExceptionTest() throws GoodNotFountException {
//        int sumGoodId = goodService.findAllGoods().stream().mapToInt(Good::getId).sum();
//        Good good = new Good();
//        goodService.updateGood(sumGoodId, good);
//    }

    @Test
    public void findGoodByPriceTest() {
        List<Good> allGoods = goodService.findAllGoods();
        double maxPrize = allGoods.get(allGoods.size() - 1).getPrice();
        List<Good> goodsByPrice = goodService.findGoodsByPrice(0, maxPrize);
        allGoods.removeAll(goodsByPrice);
        Assert.assertTrue(allGoods.stream().noneMatch(good -> good.getPrice() <= maxPrize && good.getPrice() >= 0));
    }

    @Test
    public void findGoodByIdTest() {
        List<Good> goods = goodService.findAllGoods();
        int id = goods.get(0).getId();
        Good findGoodById = goodService.findGoodById(id);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsById = missingGoods.stream().filter(good -> good.getId() != findGoodById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test
    public void changePriceTest() throws GoodException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        double newPrice = goodBeforeTest.getPrice();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        double oldPrice = oldGood.getPrice();
        goodService.changePrice(idOfLastGood, newPrice);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getPrice() == updateGood.getPrice()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changePrice(idOfLastGood, oldPrice);
    }

    @Test(expected = GoodException.class)
    public void changePriceException() throws GoodException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        goodService.changePrice(idOfLastGood, 0);
    }

    @Test
    public void changeSubsectionTest() {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        Subsection newSubsection = goodBeforeTest.getSubsection();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        Subsection oldSubsection = oldGood.getSubsection();
        goodService.changeSubsection(idOfLastGood, newSubsection);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getSubsection().equals(updateGood.getSubsection())));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeSubsection(idOfLastGood, oldSubsection);
    }

    @Test
    public void changePurposeTest() {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        Purpose newPurpose = goodBeforeTest.getPurpose();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        Purpose oldPurpose = oldGood.getPurpose();
        goodService.changePurpose(idOfLastGood, newPurpose);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getPurpose().equals(updateGood.getPurpose())));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changePurpose(idOfLastGood, oldPurpose);
    }

    @Test
    public void changeAmountTest() throws GoodException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int newAmount = goodBeforeTest.getAmount();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        int oldAmount = oldGood.getAmount();
        goodService.changeAmount(idOfLastGood, newAmount);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getAmount() == updateGood.getAmount()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeAmount(idOfLastGood, oldAmount);
    }

    @Test(expected = GoodException.class)
    public void changeAmountException() throws GoodException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        goodService.changeAmount(idOfLastGood, -1);
    }

    @Test
    public void changeUnitTest() {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        String newUnit = goodBeforeTest.getUnit();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        String oldUnit = oldGood.getUnit();
        goodService.changeUnit(idOfLastGood, newUnit);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getUnit().equals(updateGood.getUnit())));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeUnit(idOfLastGood, oldUnit);
    }

    @Test
    public void changeQuantityTest() {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int newQuantity = goodBeforeTest.getQuantity();
        Good oldGood = goodService.findGoodById(idOfLastGood);
        int oldQuantity = oldGood.getQuantity();
        goodService.changeQuantity(idOfLastGood, newQuantity);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getQuantity() == updateGood.getQuantity()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeQuantity(idOfLastGood, oldQuantity);
    }
}
