import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class GoodTest {

    GoodService goodService = new GoodServiceImpl();

    Purpose purpose1 = new Purpose(1, "Фундамент");
    Purpose purpose2 = new Purpose(2, "Внутренние работы");
    Purpose purpose3 = new Purpose(3, "Наружные работы");
    Purpose purpose4 = new Purpose(4, "Кровельные работы");
    Purpose purpose5 = new Purpose(5, "Кровельные");

    Subsection subsection1 = new Subsection(1, "Утеплитель");
    Subsection subsection2 = new Subsection(2, "Сухие смеси");
    Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
    Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");
    Subsection subsection5 = new Subsection(5, "материалы");

    Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
    Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 1, purpose2, "Шпатлевка", 36);
    Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 1, purpose3, "Краска для дерева", 15);
    Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

    Good goodBeforeTest = null;
    Good goodBeforeExceptionTest = null;
    List<Good> goodsBeforeTest = new ArrayList<>();

    @Before
    public void createGood() {
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

        goodBeforeExceptionTest = GoodBuilder.withCost(6)
                .withSubsection(subsection1)
                .withUnit("м3")
                .withQuantity(5)
                .withDiscount(8)
                .withPurpose(purpose1)
                .withName("Пеноплекс")
                .withAmount(15)
                .build();

        goodsBeforeTest.add(good1);
        goodsBeforeTest.add(good2);
        goodsBeforeTest.add(good3);
        goodsBeforeTest.add(good4);


    }

    @Test
    public void addGoodTest() throws GoodException {
        //goodsBeforeTest.add(goodBeforeTest);
        goodService.addGood(goodBeforeTest);
        List<Good> missingGoods = goodService.findAllGoods();
        //assertEquals(goodsBeforeTest, missingGoods);
        List<Good> findGoodsById;
        findGoodsById = missingGoods.stream().filter(good -> good.getId() != goodBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(good -> good.getId() == goodBeforeTest.getId()));

    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionTest() throws GoodException {
        goodService.addGood(goodBeforeTest);
    }

    @Test
    public void findByNameTest() throws GoodException {
        //Good expGood = goodService.findByNameGood("Пеноплекс");
        //assertEquals(expGood, good1);
        Good findGoodByName = goodService.findByNameGood("Пеноплекс");
        Assert.assertTrue(findGoodByName.getName().equals("Пеноплекс"));
        List<Good> missingGoods = goodService.findAllGoods();
        List<Good> findGoodsByName = missingGoods.stream().filter(good -> good.getName().equals("Пеноплекс")).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getName().equals("Пеноплекс")));
    }

    @Test(expected = GoodException.class)
    public void findByNameExceptionTest() throws GoodException {
        goodService.findByNameGood("Электрика");
    }

    @Test
    public void findBySubsectionTest() throws GoodException {
        List<Good> foundsGoodsBySubsection = goodService.findBySubsection(subsection1);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsection().equals(subsection1)));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection1)));
    }

    @Test(expected = GoodException.class)
    public void findBySubsectionExceptionTest() throws GoodException {
        List<Good> foundsGoodsBySubsection = goodService.findBySubsection(subsection5);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsection().equals(subsection5)));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsection().equals(subsection5)));
    }

    @Test
    public void findByPurposeTest() throws GoodException {

        List<Good> foundGoods = goodService.findByPurpose(purpose1);
        Predicate<Good> purposePredicate = good -> good.getPurpose().equals(purpose1);
        Assert.assertTrue(foundGoods.stream().allMatch(purposePredicate));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurpose().equals(purpose1)));
    }

    @Test(expected = GoodException.class)
    public void findByPurposeExceptionTest() throws GoodException {
        List<Good> foundGoods = goodService.findByPurpose(purpose5);
        Predicate<Good> purposePredicate = good -> good.getPurpose().equals(purpose5);
        Assert.assertTrue(foundGoods.stream().allMatch(purposePredicate));
        List<Good> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurpose().equals(purpose5)));
    }

    @Test
    public void deleteTest() throws GoodException {
        goodService.deleteGood(2);
        List<Good> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().noneMatch(good -> good.getId() == 2));
    }

    @Test(expected = GoodException.class)
    public void deleteExceptionTest() throws GoodException {
        goodService.deleteGood(5);
    }

    @Test
    public void updateTest() {
        goodService.updateGood(2, goodBeforeTest);
        List<Good> allGoods = goodService.findAllGoods();
        Good actualGood=allGoods.get(1);
        assertEquals(goodBeforeTest, actualGood);
    }

    @Test
    public void findGoodByPriceTest(){
        List<Good> allGoods=goodService.findAllGoods();
        List<Good> goodsByPrize=goodService.findGoodsByPrice(0,2);
        List<Good> missingGoods=allGoods.stream().filter(s ->s.getPrice()<=2&&s.getPrice()>=0).collect(Collectors.toList());
        assertEquals(goodsByPrize,missingGoods);
    }

}
