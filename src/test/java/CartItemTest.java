import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.*;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.entity.builder.Good.GoodBuilder;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.PurchaseException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
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

    GoodDto goodBeforeTest = new GoodDto();
    PurchaseDto purchaseBeforeTest = null;
    int amountBeforeTest = 1;

    @Before
    public void findElementsForTests() {
        PurposeDto purposeDto = purposeService.findPurposes().get(0);
//        Subsection subsection = subsectionService.findSubsections().get(0);
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
//                .withPurpose(purposeDto)
//                .withName("Пеноплекс")
//                .withAmount(20)
//                .build();
        goodBeforeTest.setSubsectionId(subsection.getId());
        goodBeforeTest.setName("Пеноплекс");
        goodBeforeTest.setPurposeId(purposeDto.getId());
        goodBeforeTest.setQuantity(5);
        goodBeforeTest.setDiscount(0);
        goodBeforeTest.setAmount(20);
        goodBeforeTest.setUnit("м3");
        goodBeforeTest.setPrice(6);

        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        purchaseBeforeTest = purchaseDtos.get(purchaseDtos.size() - 1);

    }

    @Test
    public void addIntoCartItemTest() throws RepeatitionException, PurchaseException, GoodException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        amountBeforeTest = 3;
        int cartItemId=cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findGoodsById = missingGoods.stream().filter(cartItemDto -> cartItemDto.getGoodId() != goodBeforeTest.getId() ||
                cartItemDto.getPurchaseId()!= purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(cartItemDto -> cartItemDto.getGoodId() == goodBeforeTest.getId() ||
                cartItemDto.getPurchaseId() == purchaseBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        CartItemDto cartItemDto=cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteFromPurchase(cartItemDto);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestSameGood() throws RepeatitionException, PurchaseException, GoodNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        GoodDto goodDto = goodService.findGoodById(cartItemDto.getGoodId());
        cartItemService.addInCartItem(goodDto, amountBeforeTest, purchaseDto);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestMoreAmount() throws RepeatitionException, PurchaseException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = goodDto.getAmount() + 50;
        List<CartItemDto> cartItems = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItems.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.addInCartItem(goodDto, newAmount, purchaseDto);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestLessAmount() throws RepeatitionException, PurchaseException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = -1;
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto =purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.addInCartItem(goodDto, newAmount, purchaseDto);
    }

    @Test
    public void deleteFromPurchaseTest() throws RepeatitionException, PurchaseException, GoodException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteFromPurchase(cartItemDto);
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void deleteGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItem(cartItemDto.getId());
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
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
////        Assert.assertTrue(missingGoods.stream().noneMatch(goodId -> goodId.getSubsection().equals(subsection)));
//    }

    @Test
    public void deleteGoodInPurchasesWithCancelledStatus() throws RepeatitionException, GoodException, PurchaseException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseBeforeTest);
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void changeAmountInGoodInPurchaseTest() throws GoodException, RepeatitionException, PurchaseException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        cartItemService.changeAmountInCartItem(goodBeforeTest.getId(), amountBeforeTest, purchaseBeforeTest.getId());
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findCartItem = missingGoods.stream().filter(cartItemDto1 -> cartItemDto1.getId() != cartItemDto.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findCartItem);
        Assert.assertTrue(missingGoods.stream().anyMatch(cartItemDto1 -> cartItemDto1
                .getAmount() == cartItemDto.getAmount()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        cartItemService.deleteCartItem(cartItemDto.getId());
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionMoreAmountTest() throws PurchaseException, RepeatitionException {
        List<GoodDto> goods = goodService.findAllGoods();
        GoodDto goodDto = goods.get(0);
        int newAmount = goodDto.getAmount() + 50;
        List<CartItemDto> cartItems = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItems.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.changeAmountInCartItem(goodDto.getId(), newAmount, purchaseDto.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionLessAmountTest() throws PurchaseException, RepeatitionException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = -1;
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.changeAmountInCartItem(goodDto.getId(), newAmount, purchaseDto.getId());
    }

    @Test
    public void findGoodInPurchaseByIdTest() {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int id = cartItemDtos.get(0).getId();
        CartItemDto findCartItemById = cartItemService.findCartItemById(id);
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findGoodsById = missingGoods.stream().filter(cartItemDto1 -> cartItemDto1.getId() != findCartItemById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test
    public void findGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto findCartItemById = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findGoodsById = missingGoods.stream().filter(cartItemDto -> cartItemDto.getId() != findCartItemById.getId() ||
                cartItemDto.getPurchaseId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
        cartItemService.deleteFromPurchase(findCartItemById);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void returnedAmountOfGoodTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        int goodId1=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId1);
        int amountInStore = goodBeforeTest.getAmount();
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        int goodId=cartItemDto.getGoodId();
        GoodDto goodDto1=goodService.findGoodById(goodId);
        int amountAfterAddingInGoodInPurchase = goodDto1.getAmount();
        Assert.assertNotEquals(amountAfterAddingInGoodInPurchase, amountInStore);
        cartItemService.returnedAmountOfGood(cartItemDto);
        GoodDto goodDto = goodService.findGoodById(goodBeforeTest.getId());
        Assert.assertEquals(amountInStore, goodDto.getAmount());
        cartItemService.deleteFromPurchase(cartItemDto);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void reduceAmountTest() throws RepeatitionException, GoodException, PurchaseException, GoodNotFoundException {
        int goodId=goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        GoodDto goodDto = goodService.findGoodById(goodBeforeTest.getId());
        int amountInStore = goodDto.getAmount();
        cartItemService.reduceAmount(goodBeforeTest.getId(), amountBeforeTest);
        GoodDto goodDto1 = goodService.findGoodById(goodBeforeTest.getId());
        int amountAfterReduce = goodDto1.getAmount();
        Assert.assertNotEquals(amountAfterReduce, amountInStore);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void findGoodInPurchasesByPurchaseTest() {
        List<CartItemDto> foundsGoodsByPurchase = cartItemService.findCartItemsByPurchase(purchaseBeforeTest.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(cartItemDto -> cartItemDto.getPurchaseId()==purchaseBeforeTest.getId()));
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(cartItemDto -> cartItemDto.getPurchaseId()==purchaseBeforeTest.getId()));
    }

    @Test
    public void findGoodInPurchasesByGoodTest() {
        GoodDto goodDto=goodService.findAllGoods().get(0);
        List<CartItemDto> foundsGoodsByPurchase = cartItemService.findCartItemsByGood(goodDto.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(cartItemDto -> cartItemDto.getGoodId()==goodDto.getId()));
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(cartItemDto -> cartItemDto.getGoodId()==goodDto.getId()));//Доработать
    }


}
