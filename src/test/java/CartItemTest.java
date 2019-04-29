import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.*;
import com.vironit.kazimirov.entity.*;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
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
    @Autowired
    private AdminService adminService;

    GoodDto goodBeforeTest = new GoodDto();
    PurchaseDto purchaseBeforeTest = new PurchaseDto();
    int amountBeforeTest = 1;
    UserDto userDtoForTest = new UserDto();


    @Before
    public void findElementsForTests() throws ClientNotFoundException, PurchaseNotFoundException {
        PurposeDto purposeDto = purposeService.findPurposes().get(0);
        SubsectionDto subsectionDto = subsectionService.findSubsections().get(0);
        Subsection subsection = new Subsection();
        subsection.setId(subsectionDto.getId());
        subsection.setTitle(subsectionDto.getTitle());
        goodBeforeTest.setSubsectionId(subsection.getId());
        goodBeforeTest.setName("Пеноплекс");
        goodBeforeTest.setPurposeId(purposeDto.getId());
        goodBeforeTest.setQuantity(5);
        goodBeforeTest.setDiscount(0);
        goodBeforeTest.setAmount(20);
        goodBeforeTest.setUnit("м3");
        goodBeforeTest.setPrice(6);

        userDtoForTest = adminService.findAllClient().get(0);
        int purchaseId = purchaseService.createNewPurchase(userDtoForTest);
        purchaseBeforeTest = purchaseService.findPurchaseById(purchaseId);

    }

    @Test
    public void addIntoCartItemTest() throws RepeatitionException, PurchaseException, GoodException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        amountBeforeTest = 3;
        int cartItemId = cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findGoodsById = missingGoods.stream().filter(cartItemDto -> cartItemDto.getGoodId() != goodBeforeTest.getId() ||
                cartItemDto.getPurchaseId() != purchaseBeforeTest.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        Assert.assertTrue(missingGoods.stream().allMatch(cartItemDto -> cartItemDto.getGoodId() == goodBeforeTest.getId() ||
                cartItemDto.getPurchaseId() == purchaseBeforeTest.getId()));
        int size = missingGoods.size();
        assertEquals(size, 1);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteFromPurchase(cartItemDto);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestSameGood() throws PurchaseException, GoodNotFoundException, PurchaseNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        GoodDto goodDto = goodService.findGoodById(cartItemDto.getGoodId());
        cartItemService.addInCartItem(goodDto, amountBeforeTest, purchaseDto);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestMoreAmount() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = goodDto.getAmount() + 50;
        List<CartItemDto> cartItems = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItems.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.addInCartItem(goodDto, newAmount, purchaseDto);
    }

    @Test(expected = PurchaseException.class)
    public void addIntoGoodInPurchaseExceptionTestLessAmount() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = -1;
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.addInCartItem(goodDto, newAmount, purchaseDto);
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void addIntoCartExceptionTestLessAmount() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException {
        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int purchaseDtoId = purchaseDtos.stream().mapToInt(purchaseDto -> purchaseDto.getId()).sum();
        PurchaseDto purchaseDto = purchaseDtos.get(0);
        purchaseDto.setId(purchaseDtoId);
        cartItemService.addInCartItem(goodDto, 1, purchaseDto);
    }

    @Test(expected = GoodNotFoundException.class)
    public void addIntoCartGoodNotFoundExceptionTestLessAmount() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException {
        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        List<GoodDto> goodDtos = goodService.findAllGoods();
        PurchaseDto purchaseDto = purchaseDtos.get(0);
        int goodDtoId = goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        goodBeforeTest.setId(goodDtoId);
        cartItemService.addInCartItem(goodBeforeTest, 1, purchaseDto);
    }

    @Test
    public void deleteFromPurchaseTest() throws RepeatitionException, PurchaseException, GoodException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteFromPurchase(cartItemDto);
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void deleteGoodInPurchaseTest() throws RepeatitionException, GoodException, PurchaseException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItem(cartItemDto.getId());
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void deleteCartItemTest() throws RepeatitionException, PurposeNotFoundException, GoodException, SubsectionNotFoundException, PurchaseException, PurchaseNotFoundException, GoodNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        int cartItemId = cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItemById(cartItemId);
        int deleteId = cartItemDto.getId();
        cartItemService.deleteCartItem(cartItemId);
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == deleteId));
        goodService.deleteGood(goodId);
    }

    @Test(expected = CartItemNotFoundException.class)
    public void deleteCartItemExceptionTest() throws CartItemNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int cartItemId = cartItemDtos.stream().mapToInt(cartItemDto -> cartItemDto.getId()).sum()+1;
        cartItemService.deleteCartItem(cartItemId);
    }

    @Test
    public void deleteGoodInPurchasesWithCancelledStatus() throws RepeatitionException, GoodException, PurchaseException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId);
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseBeforeTest);
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        Assert.assertTrue(cartItemDtos.stream().noneMatch(cartItemDto1 -> cartItemDto1.getId() == cartItemDto.getId()));
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void deleteGoodInPurchasesWithCancelledStatusException() throws PurchaseNotFoundException {
        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        int purchaseDtoId = purchaseDtos.stream().mapToInt(purchaseDto -> purchaseDto.getId()).sum();
        PurchaseDto purchaseDto = purchaseDtos.get(0);
        purchaseDto.setId(purchaseDtoId);
        cartItemService.deleteCartItemsWithCancelledStatus(purchaseDto);
    }

    @Test
    public void changeAmountInGoodInPurchaseTest() throws GoodException, RepeatitionException, PurchaseException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
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
    public void changeAmountInGoodInPurchaseExceptionMoreAmountTest() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException, GoodException {
        List<GoodDto> goods = goodService.findAllGoods();
        GoodDto goodDto = goods.get(0);
        int newAmount = goodDto.getAmount() + 50;
        List<CartItemDto> cartItems = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItems.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.changeAmountInCartItem(goodDto.getId(), newAmount, purchaseDto.getId());
    }

    @Test(expected = PurchaseException.class)
    public void changeAmountInGoodInPurchaseExceptionLessAmountTest() throws PurchaseException, PurchaseNotFoundException, GoodNotFoundException, GoodException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        GoodDto goodDto = goodDtos.get(0);
        int newAmount = -1;
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        CartItemDto cartItemDto = cartItemDtos.get(0);
        PurchaseDto purchaseDto = purchaseService.findPurchaseById(cartItemDto.getPurchaseId());
        cartItemService.changeAmountInCartItem(goodDto.getId(), newAmount, purchaseDto.getId());
    }

    @Test
    public void findCartItemByIdTest() throws CartItemNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int id = cartItemDtos.get(0).getId();
        CartItemDto findCartItemById = cartItemService.findCartItemById(id);
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        List<CartItemDto> findGoodsById = missingGoods.stream().filter(cartItemDto1 -> cartItemDto1.getId() != findCartItemById.getId()).collect(Collectors.toList());
        missingGoods.removeAll(findGoodsById);
        int size = missingGoods.size();
        assertEquals(size, 1);
    }

    @Test(expected = CartItemNotFoundException.class)
    public void findCartItemByIdExceptionTest() throws CartItemNotFoundException {
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int cartItemId = cartItemDtos.stream().mapToInt(cartItemDto -> cartItemDto.getId()).sum()+1;
        cartItemService.findCartItemById(cartItemId);
    }

    @Test
    public void findCartItemTest() throws RepeatitionException, GoodException, PurchaseException, SubsectionNotFoundException, PurposeNotFoundException, GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
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

    @Test(expected = GoodNotFoundException.class)
    public void findCartItemTestExceptionGoodNotFound() throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId = goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int purchaseId = cartItemDtos.get(0).getPurchaseId();
        cartItemService.findCartItem(goodDtoId, purchaseId);
    }

    @Test(expected = CartItemNotFoundException.class)
    public void findCartItemTestExceptionCartItemNotFound() throws GoodNotFoundException, PurchaseNotFoundException, CartItemNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId = goodDtos.get(0).getId();
        cartItemService.findCartItem(goodDtoId, purchaseBeforeTest.getId());
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void findCartItemTestPurchaseNotFound() throws PurchaseNotFoundException, GoodNotFoundException, CartItemNotFoundException {
        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        int purchaseDtoId = purchaseDtos.stream().mapToInt(purchaseDto -> purchaseDto.getId()).sum();
        List<CartItemDto> cartItemDtos = cartItemService.findCartItems();
        int goodId = cartItemDtos.get(0).getGoodId();
        cartItemService.findCartItem(goodId, purchaseDtoId);
    }

    @Test
    public void returnedAmountOfGoodTest() throws GoodException, SubsectionNotFoundException, RepeatitionException, PurposeNotFoundException, PurchaseException, PurchaseNotFoundException, GoodNotFoundException, CartItemNotFoundException {
        int goodId1 = goodService.addGood(goodBeforeTest);
        goodBeforeTest.setId(goodId1);
        int amountInStore = goodBeforeTest.getAmount();
        cartItemService.addInCartItem(goodBeforeTest, amountBeforeTest, purchaseBeforeTest);
        CartItemDto cartItemDto = cartItemService.findCartItem(goodBeforeTest.getId(), purchaseBeforeTest.getId());
        int goodId = cartItemDto.getGoodId();
        GoodDto goodDto1 = goodService.findGoodById(goodId);
        int amountAfterAddingInGoodInPurchase = goodDto1.getAmount();
        Assert.assertNotEquals(amountAfterAddingInGoodInPurchase, amountInStore);
        cartItemService.returnedAmountOfGood(cartItemDto);
        GoodDto goodDto = goodService.findGoodById(goodBeforeTest.getId());
        Assert.assertEquals(amountInStore, goodDto.getAmount());
        cartItemService.deleteFromPurchase(cartItemDto);
        goodService.deleteGood(goodBeforeTest.getId());
    }

    @Test
    public void reduceAmountTest() throws RepeatitionException, GoodException, GoodNotFoundException, SubsectionNotFoundException, PurposeNotFoundException {
        int goodId = goodService.addGood(goodBeforeTest);
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
    public void findCartItemsByPurchaseTest() throws PurchaseNotFoundException {
        List<CartItemDto> foundsGoodsByPurchase = cartItemService.findCartItemsByPurchase(purchaseBeforeTest.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(cartItemDto -> cartItemDto.getPurchaseId() == purchaseBeforeTest.getId()));
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(cartItemDto -> cartItemDto.getPurchaseId() == purchaseBeforeTest.getId()));
    }

    @Test(expected = PurchaseNotFoundException.class)
    public void findCartItemsExceptionByPurchaseTest() throws PurchaseNotFoundException {
        List<PurchaseDto> purchaseDtos = purchaseService.findPurchases();
        int purchaseDtoId = purchaseDtos.stream().mapToInt(purchaseDto -> purchaseDto.getId()).sum();
        cartItemService.findCartItemsByPurchase(purchaseDtoId);
    }

    @Test
    public void findCartItemsByGoodTest() throws GoodNotFoundException {
        GoodDto goodDto = goodService.findAllGoods().get(0);
        List<CartItemDto> foundsGoodsByPurchase = cartItemService.findCartItemsByGood(goodDto.getId());
        Assert.assertTrue(foundsGoodsByPurchase.stream().allMatch(cartItemDto -> cartItemDto.getGoodId() == goodDto.getId()));
        List<CartItemDto> missingGoods = cartItemService.findCartItems();
        missingGoods.removeAll(foundsGoodsByPurchase);
        Assert.assertTrue(missingGoods.stream().noneMatch(cartItemDto -> cartItemDto.getGoodId() == goodDto.getId()));//Доработать
    }

    @Test(expected = GoodNotFoundException.class)
    public void findCartItemsExceptionByGoodTest() throws GoodNotFoundException {
        List<GoodDto> goodDtos = goodService.findAllGoods();
        int goodDtoId = goodDtos.stream().mapToInt(goodDto -> goodDto.getId()).sum();
        cartItemService.findCartItemsByGood(goodDtoId);
    }

    @After
    public void deleteAll() throws PurchaseNotFoundException, CantDeleteElement {
        purchaseService.removePurchase(purchaseBeforeTest.getId());
    }


}
