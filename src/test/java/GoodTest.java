import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFountException;
import com.vironit.kazimirov.fakedao.GoodDaoImplFake;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class GoodTest {

    GoodService goodService = new GoodServiceImpl(new GoodDaoImplFake());
    Good goodBeforeTest = null;
    Good goodBeforeExceptionTest = null;

    @Before
    public void createGood() {
        Purpose purpose1 = new Purpose(1, "Фундамент");
        Subsection subsection1 = new Subsection(1, "Утеплитель");
        GoodBuilder GoodBuilder = new GoodBuilder();
        goodBeforeTest = GoodBuilder.withCost(6)
                .withSubsection(subsection1)
                .withUnit("м3")
                .withQuantity(5)
                .withDiscount(0)
                .withPurpose(purpose1)
                .withName("Пеноплекс")
                .withAmount(15)
                .build();
    }

    @Before
    public void createGoodException() {
        Purpose purpose1 = new Purpose(1, "Фундамент");
        Subsection subsection1 = new Subsection(1, "Утеплитель");
        GoodBuilder GoodBuilder = new GoodBuilder();
        goodBeforeExceptionTest = GoodBuilder.withCost(6)
                .withSubsection(subsection1)
                .withUnit("м3")
                .withQuantity(5)
                .withDiscount(8)
                .withPurpose(purpose1)
                .withName("Пеноплекс")
                .withAmount(15)
                .build();
    }

    @Test
    public void addGoodTest() throws GoodException {

        goodService.addGood(goodBeforeTest);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsById;
        findGoodsById = missingGoods.stream().filter(good -> good.getId() != goodBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(good -> good.getId() == goodBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);

    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionTest() throws GoodException {
        goodService.addGood(goodBeforeExceptionTest);
    }

    @Test
    public void findByNameTest() throws GoodNotFountException {
        if (goodService.findAllGoods().size() == 0) {
            throw new GoodNotFountException("The list of goods is empty");
        }
        String name = goodService.findAllGoods().get(0).getName();

        Good findGoodByName = goodService.findByNameGood(name);
        Assert.assertTrue(findGoodByName.getName().equals(name));
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsByName = missingGoods.stream().filter(good -> good.getName().equals(name)).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getName().equals(name)));
    }

    @Test
    public void findBySubsectionTest() throws GoodNotFountException {
        if (goodService.findAllGoods().size() == 0) {
            throw new GoodNotFountException("The list of goods is empty");
        }
        Subsection subsection = goodService.findAllGoods().get(0).getSubsection();
        List<Good> foundsGoodsBySubsection = goodService.findBySubsection(subsection);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsection().equals(subsection)));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection)));
    }

    @Test
    public void findByPurposeTest() throws GoodNotFountException {
        if (goodService.findAllGoods().size() == 0) {
            throw new GoodNotFountException("The list of goods is empty");
        }
        Purpose purpose = goodService.findAllGoods().get(0).getPurpose();
        List<Good> foundGoods = goodService.findByPurpose(purpose);
        Predicate<Good> purposePredicate = good -> good.getPurpose().equals(purpose);
        Assert.assertTrue(foundGoods.stream().allMatch(purposePredicate));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurpose().equals(purpose)));
    }

    @Test
    public void deleteGoodTest() throws GoodException {
        int deleteId = goodService.findAllGoods().get(0).getId();
        goodService.deleteGood(deleteId);
        List<Good> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().noneMatch(client -> client.getId() == deleteId));
    }

    @Test(expected = GoodException.class)
    public void deleteExceptionTest() throws GoodException {
        int sumGoodId = goodService.findAllGoods().stream().mapToInt(Good::getId).sum();
        goodService.deleteGood(sumGoodId);
    }

    @Test
    public void updateTest() throws GoodNotFountException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        goodService.updateGood(idOfLastGood, goodBeforeTest);
        Good updateGood = goodService.findGoodById(idOfLastGood);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsByName = missingGoods.stream().filter(good -> !good.equals(updateGood)).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().anyMatch(good -> good.equals(updateGood)));
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test(expected = GoodNotFountException.class)
    public void updateExceptionTest() throws GoodNotFountException {
        int sumGoodId = goodService.findAllGoods().stream().mapToInt(Good::getId).sum();
        Good good = new Good();
        goodService.updateGood(sumGoodId, good);
    }

    @Test
    public void findGoodByPriceTest() throws GoodNotFountException {
        if (goodService.findAllGoods().size() == 0) {
            throw new GoodNotFountException("The list of goods is empty");
        }
        List<Good> allGoods = goodService.findAllGoods();
        double maxPrize = allGoods.get(0).getPrice();
        List<Good> goodsByPrize = goodService.findGoodsByPrice(0, maxPrize);
        List<Good> missingGoods = allGoods.stream().filter(s -> s.getPrice() <= 2 && s.getPrice() >= 0).collect(Collectors.toList());
        assertEquals(goodsByPrize, missingGoods);
    }

    @Test
    public void findGoodByIdTest() throws GoodNotFountException {
        List<Good> goods = goodService.findAllGoods();
        if (goods.size() == 0) {
            throw new GoodNotFountException("This list of purchase is empty");
        }
        int id = goods.get(0).getId();
        Good findPurchaseById = goodService.findGoodById(id);
        Assert.assertTrue(findPurchaseById.getId() == id);
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsById = missingGoods.stream().filter(good -> good.getId() != id).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }
}
