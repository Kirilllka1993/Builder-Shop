import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Subsection;
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
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
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
        Subsection subsection = new Subsection();
        subsection.setId(subsectionDto.getId());
        subsection.setTitle(subsectionDto.getTitle());
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
        Subsection subsection = new Subsection();
        subsection.setId(subsectionDto.getId());
        subsection.setTitle(subsectionDto.getTitle());
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
        int goodId = goodService.addGood(goodBeforeTest);
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
        List<GoodDto> goods = goodService.findAllGoods();
        String neverUseName = goods.stream().map(GoodDto::getName).collect(Collectors.joining());
        goodService.findByNameGood(neverUseName);
    }

    @Test
    public void findBySubsectionTest() throws SubsectionNotFoundException {
        int subsectionId = goodService.findAllGoods().get(0).getSubsectionId();
        SubsectionDto subsectionDto = subsectionService.findSubsectionById(subsectionId);
        List<GoodDto> foundsGoodsBySubsection = goodService.findBySubsection(subsectionDto);
        Assert.assertTrue(foundsGoodsBySubsection.stream().allMatch(good -> good.getSubsectionId() == subsectionId));
        List<GoodDto> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundsGoodsBySubsection);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getSubsectionId() == subsectionId));
    }

    @Test(expected = SubsectionNotFoundException.class)
    public void findBySubsectionExceptionTest() throws SubsectionNotFoundException {
         List<SubsectionDto>subsectionDtos= subsectionService.findSubsections();
        SubsectionDto subsectionDto=subsectionDtos.get(0);
        String neverUseTitle= subsectionDtos.stream().map(SubsectionDto::getTitle).collect(Collectors.joining());
        subsectionDto.setTitle(neverUseTitle);
        goodService.findBySubsection(subsectionDto);
    }

    @Test
    public void findByPurposeTest() throws PurposeNotFoundException {
        int purposeId = goodService.findAllGoods().get(0).getPurposeId();
        PurposeDto purposeDto = purposeService.findPurposeById(purposeId);
        List<GoodDto> foundGoods = goodService.findByPurpose(purposeDto);
        Assert.assertTrue(foundGoods.stream().allMatch(good -> good.getPurposeId() == purposeId));
        List<GoodDto> missingGoods = goodService.findAllGoods();
        missingGoods.removeAll(foundGoods);
        Assert.assertTrue(missingGoods.stream().noneMatch(good -> good.getPurposeId() == purposeId));
    }

    @Test(expected = PurposeNotFoundException.class)
    public void findByPurposeExceptionTest() throws PurposeNotFoundException {
        List<PurposeDto>purposeDtos= purposeService.findPurposes();
        PurposeDto purposeDto=purposeDtos.get(0);
        String neverUsePurpose= purposeDtos.stream().map(PurposeDto::getPurpose).collect(Collectors.joining());
        purposeDto.setPurpose(neverUsePurpose);
        goodService.findByPurpose(purposeDto);
    }

    @Test
    public void deleteGoodTest() throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        int deleteId = goodBeforeTest.getId();
        goodService.deleteGood(deleteId);
        List<GoodDto> allGoods = goodService.findAllGoods();
        Assert.assertTrue(allGoods.stream().noneMatch(client -> client.getId() == deleteId));
    }

    @Test(expected = GoodNotFoundException.class)
    public void deleteGoodExceptionTest() throws GoodNotFoundException{
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.deleteGood(goodDtoId);
    }

    @Test
    public void updateTest() throws GoodNotFoundException, GoodException, SubsectionNotFoundException, RepeatitionException, PurposeNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        GoodDto updateGood = goodService.findAllGoods().get(0);
        List<GoodDto> goodDtos=goodService.findAllGoods();
        List<GoodDto>newGoods=new ArrayList<>();
        newGoods.add(goodDtos.get(0));
        newGoods.add(goodDtos.get(goodDtos.size()-1));
        String neverUseTitle= newGoods.stream().map(GoodDto::getName).collect(Collectors.joining());
        updateGood.setName(neverUseTitle);
        goodService.updateGood(goodBeforeTest.getId(),updateGood);
        goodBeforeTest=goodService.findGoodById(goodBeforeTest.getId());
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoodsByName = missingGoods.stream().filter(good -> good.getId()!=goodBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsByName);
        Assert.assertTrue(missingGoods.stream().anyMatch(good -> good.equals(goodBeforeTest)));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = GoodNotFoundException.class)
    public void updateGoodNotFoundExceptionTest() throws GoodNotFoundException, GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.updateGood(goodDtoId,goodBeforeTest);
    }

    @Test(expected = GoodException.class)
    public void updateGoodExceptionTest() throws GoodNotFoundException, GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        goodService.updateGood(goodDtos.get(0).getId(),goodBeforeExceptionTest);
    }

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
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.findGoodById(goodDtoId);
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

    @Test(expected = GoodNotFoundException.class)
    public void changePriceGoodNotFoundException() throws GoodNotFoundException, GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changePrice(goodDtoId,2);
    }

    @Test
    public void changeSubsectionTest() throws GoodNotFoundException, SubsectionNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int subsectionId = goodService.findAllGoods().get(0).getSubsectionId();
        SubsectionDto subsectionDto = subsectionService.findSubsectionById(subsectionId);
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        int oldSubsection = oldGood.getSubsectionId();
        SubsectionDto oldSubsectionDto = subsectionService.findSubsectionById(oldSubsection);

        goodService.changeSubsection(idOfLastGood, subsectionDto);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getSubsectionId() == updateGood.getSubsectionId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changeSubsection(idOfLastGood, oldSubsectionDto);
    }

    @Test(expected = GoodNotFoundException.class)
    public void changeSubsectionGoodNotExceptionTest() throws GoodNotFoundException, SubsectionNotFoundException {
        SubsectionDto subsectionDto=subsectionService.findSubsections().get(0);
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changeSubsection(goodDtoId,subsectionDto);
    }

    @Test(expected = SubsectionNotFoundException.class)
    public void changeSubsectionExceptionTest() throws SubsectionNotFoundException, GoodNotFoundException {
        List<SubsectionDto>subsectionDtos= subsectionService.findSubsections();
        SubsectionDto subsectionDto=subsectionDtos.get(0);
        String neverUseTitle= subsectionDtos.stream().map(SubsectionDto::getTitle).collect(Collectors.joining());
        subsectionDto.setTitle(neverUseTitle);
        int goodId=goodService.findAllGoods().get(0).getId();
        goodService.changeSubsection(goodId,subsectionDto);
    }

    @Test
    public void changePurposeTest() throws GoodNotFoundException, PurposeNotFoundException {
        int idOfLastGood = goodService.findAllGoods().get(goodService.findAllGoods().size() - 1).getId();
        int purposeId = goodService.findAllGoods().get(0).getPurposeId();
        PurposeDto newPurposeDto = purposeService.findPurposeById(purposeId);
        GoodDto oldGood = goodService.findGoodById(idOfLastGood);
        int oldPurpose = oldGood.getPurposeId();
        PurposeDto oldPurposeDto = purposeService.findPurposeById(oldPurpose);
        goodService.changePurpose(idOfLastGood, newPurposeDto);
        GoodDto updateGood = goodService.findGoodById(idOfLastGood);
        List<GoodDto> missingGoods = goodService.findAllGoods();
        List<GoodDto> findGoods = missingGoods.stream().filter(good -> good.getId() != updateGood.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoods);
        Assert.assertTrue(missingGoods.stream().anyMatch(client -> client
                .getPurposeId() == updateGood.getPurposeId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        goodService.changePurpose(idOfLastGood, oldPurposeDto);
    }

    @Test(expected = GoodNotFoundException.class)
    public void changePurposeGoodNotExceptionTest() throws GoodNotFoundException, PurposeNotFoundException {
        PurposeDto purposeDto=purposeService.findPurposes().get(0);
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changePurpose(goodDtoId,purposeDto);
    }

    @Test(expected = PurposeNotFoundException.class)
    public void changePurposeExceptionTest() throws PurposeNotFoundException, GoodNotFoundException {
        List<PurposeDto>purposeDtos= purposeService.findPurposes();
        PurposeDto purposeDto=purposeDtos.get(0);
        String neverUsePurpose= purposeDtos.stream().map(PurposeDto::getPurpose).collect(Collectors.joining());
        purposeDto.setPurpose(neverUsePurpose);
        int goodDtoId=goodService.findAllGoods().get(0).getId();
        goodService.changePurpose(goodDtoId,purposeDto);
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

    @Test(expected = GoodNotFoundException.class)
    public void changeAmountGoodNotFoundException() throws GoodNotFoundException,GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changeAmount(goodDtoId,1);
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

    @Test(expected = GoodNotFoundException.class)
    public void changeUnitTestGoodNotFoundException() throws GoodNotFoundException,GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changeUnit(goodDtoId,"м3");
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

    @Test(expected = GoodNotFoundException.class)
    public void changeQuantityGoodNotFoundException() throws GoodNotFoundException,GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId=goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodService.changeQuantity(goodDtoId,1);
    }
}
