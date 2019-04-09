import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.*;
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

    GoodDto goodBeforeTest = null;
    GoodDto goodBeforeExceptionTest = null;

    @Before
    public void createGood() {
        PurposeDto purposeDto = purposeService.findPurposes().get(0);
        SubsectionDto subsectionDto = subsectionService.findSubsections().get(0);
        Subsection subsection=new Subsection();
        subsection.setId(subsectionDto.getId());
        subsection.setTitle(subsectionDto.getTitle());
//        GoodBuilder GoodBuilder = new GoodBuilder();
//        goodBeforeTest = GoodBuilder.withCost(6)
//                .withSubsection(subsection)
//                .withUnit("м3")
//                .withQuantity(5)
//                .withDiscount(0)
//                .withPurpose(purpose)
//                .withName("Пеноплексcccccccccccccccccccccccc")
//                .withAmount(20)
//                .build();
        goodBeforeTest = new GoodDto();
        goodBeforeTest.setSubsectionId(subsection.getId());
        goodBeforeTest.setName("Пеноплексcccccccccccccccccccccccc");
        goodBeforeTest.setPurposeId(purposeDto.getId());
        goodBeforeTest.setQuantity(5);
        goodBeforeTest.setDiscount(0);
        goodBeforeTest.setAmount(20);
        goodBeforeTest.setUnit("м3");
        goodBeforeTest.setPrice(6);
    }

    @Before
    public void createGoodException() {
        PurposeDto purposeDto = purposeService.findPurposes().get(0);
        SubsectionDto subsectionDto = subsectionService.findSubsections().get(0);
        Subsection subsection=new Subsection();
        subsection.setId(subsectionDto.getId());
        subsection.setTitle(subsectionDto.getTitle());

        //List<Good> goods = goodService.findAllGoods();
        //double discount=goods.stream().mapToDouble(Good::getPrice).count();
//        GoodBuilder GoodBuilder = new GoodBuilder();
//        goodBeforeExceptionTest = GoodBuilder.withCost(6)
//                .withSubsection(subsection)
//                .withUnit("м3")
//                .withQuantity(5)
//                .withDiscount(10000000)
//                .withPurpose(purpose)
//                .withName("Пеноплексcccccccccccccccc")
//                .withAmount(15)
//                .build();
        goodBeforeExceptionTest = new GoodDto();
        goodBeforeExceptionTest.setSubsectionId(subsection.getId());
        goodBeforeExceptionTest.setName("Пеноплексcccccccccccccccccccccccc");
        goodBeforeExceptionTest.setPurposeId(purposeDto.getId());
        goodBeforeExceptionTest.setQuantity(5);
        goodBeforeExceptionTest.setDiscount(10000000);
        goodBeforeExceptionTest.setAmount(20);
        goodBeforeExceptionTest.setUnit("м3");
        goodBeforeExceptionTest.setPrice(6);

    }

    @Test
    public void addGoodTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoodsById = missingGoods.stream().filter(good -> good.getId() != goodBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(good -> good.getId() == goodBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException {
        goodService.addGood(goodBeforeExceptionTest);
    }

    @Test(expected = RepeatitionException.class)
    public void addGoodRepeatitonExceptionTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException {
        GoodDto good = goodService.findAllGoods().get(0);
        goodService.addGood(good);
    }

    @Test(expected = GoodException.class)
    public void addGoodExceptionWrongAmountTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException {
        goodBeforeTest.setAmount(-1);
        goodService.addGood(goodBeforeTest);
    }


    @Test
    public void findByNameTest() throws GoodNotFoundException {
        String name = goodService.findAllGoods().get(0).getName();
        GoodDto findGoodByName = goodService.findByNameGood(name);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoodsByName = missingGoods.stream().filter(good -> good.getName().equals(findGoodByName.getName())).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getName().equals(name)));
    }

    @Test(expected = GoodNotFoundException.class)
    public void goodFindByNameExceptionTest() throws GoodNotFoundException {
        List<GoodDto>goods=goodService.findAllGoods();
        String neverUseName = goods.stream().map(GoodDto::getName).collect(Collectors.joining());
        goodService.findByNameGood(neverUseName);
    }

    @Test
    public void findBySubsectionTest() throws SubsectionNotFoundException {
        int subsectionId = goodService.findAllGoods().get(0).getSubsectionId();
        SubsectionDto subsectionDto=subsectionService.findSubsectionById(subsectionId);
        List<GoodDto> foundsGoodsBySubsection = goodService.findBySubsection(subsectionDto);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsectionId()==subsectionId));
        List<GoodDto> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsectionId()==subsectionId));
    }

    @Test
    public void findByPurposeTest() throws PurposeNotFoundException {
        int purposeId = goodService.findAllGoods().get(0).getPurposeId();
        PurposeDto purposeDto=purposeService.findPurposeById(purposeId);
        List<GoodDto> foundGoods = goodService.findByPurpose(purposeDto);
        Assert.assertTrue(foundGoods.stream().allMatch(good -> good.getPurposeId()==purposeId));
        List<GoodDto> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurposeId()==purposeId));
    }

    @Test
    public void deleteGoodTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        int deleteId = goodBeforeTest.getId();
        goodService.deleteGood(deleteId);
        List<GoodDto> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().noneMatch(client -> client.getId() == deleteId));
    }

//    @Test
//    public void updateTest() throws GoodNotFoundException {
//        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
//        goodService.updateGood(idOfLastGood, goodBeforeTest);
//        Good updateGood = goodService.findGoodById(idOfLastGood);
//        List<Good> missingGoods = goodService.findAllGoods();
//        List<Good> findGoodsByName = missingGoods.stream().filter(goodId -> !goodId.equals(updateGood)).collect(Collectors.toList());
//        missingGoods.removeAll(findGoodsByName);
//        Assert.assertTrue(missingGoods.stream().anyMatch(goodId -> goodId.equals(updateGood)));
//        int size = missingGoods.size();
//        assertEquals(size, 1);
//    }

//    @Test(expected = GoodNotFoundException.class)
//    public void updateExceptionTest() throws GoodNotFoundException {
//        int sumGoodId = goodService.findAllGoods().stream().mapToInt(Good::getId).sum();
//        Good goodId = new Good();
//        goodService.updateGood(sumGoodId, goodId);
//    }

    @Test
    public void findGoodByPriceTest() {
        List<GoodDto> allGoods = goodService.findAllGoods();
        double maxPrize = allGoods.get(allGoods.size() - 1).getPrice();
        List<GoodDto> goodsByPrice = goodService.findGoodsByPrice(0, maxPrize);
        allGoods.removeAll(goodsByPrice);
        Assert.assertTrue(allGoods.stream().noneMatch(good -> good.getPrice() <= maxPrize && good.getPrice() >= 0));
    }

    @Test
    public void findGoodByIdTest() throws GoodNotFoundException {
        List<GoodDto> goods = goodService.findAllGoods();
        int id = goods.get(0).getId();
        GoodDto findGoodById = goodService.findGoodById(id);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoodsById = missingGoods.stream().filter(good -> good.getId() != findGoodById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test(expected = GoodNotFoundException.class)
    public void findGoodByIdExceptionTest() throws GoodNotFoundException {
        List<GoodDto> goods=goodService.findAllGoods();
        int sum=1;
        for(int i=1;i<goods.size();i++){
            sum=sum*goods.get(i).getId();
        }
        goodService.findGoodById(sum);
    }

    @Test
    public void changePriceTest() throws GoodException, GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        double newPrice = goodBeforeTest.getPrice();
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        double oldPrice = oldGood.getPrice();
        goodService.changePrice(idOfLastGood, newPrice);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getPrice() == updateGood.getPrice()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changePrice(idOfLastGood, oldPrice);
    }

    @Test(expected = GoodException.class)
    public void changePriceException() throws GoodException, GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        goodService.changePrice(idOfLastGood, 0);
    }

    @Test
    public void changeSubsectionTest() throws GoodNotFoundException, SubsectionNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();

//        Subsection newSubsection = goodBeforeTest.getSubsection();
        int subsectionId = goodService.findAllGoods().get(0).getSubsectionId();
        SubsectionDto subsectionDto=subsectionService.findSubsectionById(subsectionId);

        GoodDto oldGood = goodService.findGoodById(idOfLastGood);

//        Subsection oldSubsection = oldGood.getSubsection();
        int oldSubsection=oldGood.getSubsectionId();
        SubsectionDto oldSubsectionDto=subsectionService.findSubsectionById(oldSubsection);

        goodService.changeSubsection(idOfLastGood, subsectionDto);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getSubsectionId()==updateGood.getSubsectionId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeSubsection(idOfLastGood, oldSubsectionDto);
    }

    @Test
    public void changePurposeTest() throws GoodNotFoundException, PurposeNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
       // Purpose newPurpose = goodBeforeTest.getPurpose();
        int purposeId = goodService.findAllGoods().get(0).getPurposeId();
        PurposeDto newPurposeDto=purposeService.findPurposeById(purposeId);

        GoodDto oldGood = goodService.findGoodById(idOfLastGood);

//        Purpose oldPurpose = oldGood.getPurpose();
        int oldPurpose=oldGood.getPurposeId();
        PurposeDto oldPurposeDto=purposeService.findPurposeById(oldPurpose);

        goodService.changePurpose(idOfLastGood, newPurposeDto);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getPurposeId()==updateGood.getPurposeId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changePurpose(idOfLastGood, oldPurposeDto);
    }

    @Test
    public void changeAmountTest() throws GoodException, GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int newAmount = goodBeforeTest.getAmount();
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        int oldAmount = oldGood.getAmount();
        goodService.changeAmount(idOfLastGood, newAmount);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getAmount() == updateGood.getAmount()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeAmount(idOfLastGood, oldAmount);
    }

    @Test(expected = GoodException.class)
    public void changeAmountException() throws GoodException, GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        goodService.changeAmount(idOfLastGood, -1);
    }

    @Test
    public void changeUnitTest() throws GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        String newUnit = goodBeforeTest.getUnit();
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        String oldUnit = oldGood.getUnit();
        goodService.changeUnit(idOfLastGood, newUnit);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getUnit().equals(updateGood.getUnit())));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeUnit(idOfLastGood, oldUnit);
    }

    @Test
    public void changeQuantityTest() throws GoodNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int newQuantity = goodBeforeTest.getQuantity();
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        int oldQuantity = oldGood.getQuantity();
        goodService.changeQuantity(idOfLastGood, newQuantity);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getQuantity() == updateGood.getQuantity()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeQuantity(idOfLastGood, oldQuantity);
    }
}
