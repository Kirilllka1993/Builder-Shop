import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurposeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
public class PurposeTest {
    @Autowired
    private PurposeService purposeService;
    @Autowired
    private GoodService goodService;
    Purpose purposeBeforeTest = null;
    Purpose purposeForException = null;
    Purpose purposeForDeleteExceptionTest = null;

    @Before
    public void createPurpose() {
        List<Purpose> allpurposes = purposeService.findPurposes();
        List<Purpose> purposes = new ArrayList<>();
        purposes.add(allpurposes.get(0));
        purposes.add(allpurposes.get(1));
        purposeForException = allpurposes.get(0);
        String neverUseTitle = purposes.stream().map(Purpose::getPurpose).collect(Collectors.joining());
        purposeBeforeTest = purposes.get(purposes.size() - 1);
        purposeBeforeTest.setPurpose(neverUseTitle);
    }

    @Before
    public void createPurposeForException() {
        List<Good> goods = goodService.findAllGoods();
        purposeForDeleteExceptionTest = goods.get(0).getPurpose();
    }

    @Test
    public void addPurposeTest() throws RepeatitionException, CantDeleteElement {
        purposeService.addPurpose(purposeBeforeTest);
        List<Purpose> missingPurposes = purposeService.findPurposes();
        List<Purpose> findPurposeById = missingPurposes.stream().filter(purpose -> purpose.getId() != purposeBeforeTest.getId())
                .collect(Collectors.toList());
        missingPurposes.removeAll(findPurposeById);
        Assert.assertTrue(missingPurposes.stream().allMatch(purpose -> purpose.getId() == purposeBeforeTest.getId()));
        purposeService.deletePurpose(purposeBeforeTest.getId());
    }

    @Test
    public void findPurposeByName() throws RepeatitionException, CantDeleteElement {
        purposeService.addPurpose(purposeBeforeTest);
        Purpose purpose = purposeService.findPurposeByName(purposeBeforeTest.getPurpose());
        List<Purpose> missingPurposes = purposeService.findPurposes();
        List<Purpose> allPurposesByTitle = missingPurposes.stream().filter(purpose1 -> !purpose1.getPurpose()
                .equals(purposeBeforeTest.getPurpose())).collect(Collectors.toList());
        missingPurposes.removeAll(allPurposesByTitle);
        Assert.assertTrue(missingPurposes.stream().allMatch((purpose1 -> purpose1.getPurpose().equals(purpose.getPurpose()))));
        purposeService.deletePurpose(purposeBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void addPurposeExceptionTest() throws RepeatitionException {
        purposeService.addPurpose(purposeForException);
    }

    @Test(expected = CantDeleteElement.class)
    public void deletePurposeExceptionTest() throws CantDeleteElement {
        purposeService.deletePurpose(purposeForDeleteExceptionTest.getId());
    }

    @Test
    public void deletePurposeTest() throws RepeatitionException, CantDeleteElement {
        purposeService.addPurpose(purposeBeforeTest);
        int deleteId = purposeBeforeTest.getId();
        purposeService.deletePurpose(purposeBeforeTest.getId());
        List<Purpose> purposes = purposeService.findPurposes();
        Assert.assertTrue(purposes.stream().noneMatch(purpose -> purpose.getId() == deleteId));
    }

    @Test
    public void findPurposeById() {
        List<Purpose> purposes = purposeService.findPurposes();
        int id = purposes.get(0).getId();
        List<Purpose> missingPurposes = purposeService.findPurposes();
        List<Purpose> findPurposesById = missingPurposes.stream().filter(purpose -> purpose.getId() != id).collect(Collectors.toList());
        missingPurposes.removeAll(findPurposesById);
        int size = missingPurposes.size();
        assertEquals(size, 1);
    }
}
