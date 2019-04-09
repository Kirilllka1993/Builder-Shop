import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
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
    PurposeDto purposeBeforeTest = null;
    PurposeDto purposeForException = null;
    PurposeDto purposeForDeleteExceptionTest = null;

    @Before
    public void createPurpose() {
        List<PurposeDto> allpurposes = purposeService.findPurposes();
        List<PurposeDto> purposes = new ArrayList<>();
        purposes.add(allpurposes.get(0));
        purposes.add(allpurposes.get(1));
        purposeForException = allpurposes.get(0);
        String neverUseTitle = purposes.stream().map(PurposeDto::getPurpose).collect(Collectors.joining());
        purposeBeforeTest = purposes.get(purposes.size() - 1);
        purposeBeforeTest.setPurpose(neverUseTitle);
    }

    @Before
    public void createPurposeForException() throws PurposeNotFoundException {
        List<GoodDto> goods = goodService.findAllGoods();
        purposeForDeleteExceptionTest = purposeService.findPurposeById(goods.get(0).getPurposeId());
    }

    @Test
    public void addPurposeTest() throws RepeatitionException, CantDeleteElement, PurposeNotFoundException {
        int purposeId=purposeService.addPurpose(purposeBeforeTest);
        purposeBeforeTest.setId(purposeId);
        List<PurposeDto> missingPurposes = purposeService.findPurposes();
        List<PurposeDto> findPurposeById = missingPurposes.stream().filter(purpose -> purpose.getId() != purposeBeforeTest.getId())
                .collect(Collectors.toList());
        missingPurposes.removeAll(findPurposeById);
        Assert.assertTrue(missingPurposes.stream().allMatch(purpose -> purpose.getId() == purposeBeforeTest.getId()));
        purposeService.deletePurpose(purposeBeforeTest.getId());
    }

    @Test
    public void findPurposeByName() throws RepeatitionException, CantDeleteElement, PurposeNotFoundException {
        int purposeId=purposeService.addPurpose(purposeBeforeTest);
        purposeBeforeTest.setId(purposeId);
        PurposeDto purpose = purposeService.findPurposeByName(purposeBeforeTest.getPurpose());
        List<PurposeDto> missingPurposes = purposeService.findPurposes();
        List<PurposeDto> allPurposesByTitle = missingPurposes.stream().filter(purpose1 -> !purpose1.getPurpose()
                .equals(purposeBeforeTest.getPurpose())).collect(Collectors.toList());
        missingPurposes.removeAll(allPurposesByTitle);
        Assert.assertTrue(missingPurposes.stream().allMatch((purpose1 -> purpose1.getPurpose().equals(purpose.getPurpose()))));
        purposeService.deletePurpose(purposeBeforeTest.getId());
    }

    @Test(expected = PurposeNotFoundException.class)
    public void subsectionFindByTitleExceptionTest() throws PurposeNotFoundException {
        List<PurposeDto>purposes=purposeService.findPurposes();
        String neverUsePurpose = purposes.stream().map(PurposeDto::getPurpose).collect(Collectors.joining());
        purposeService.findPurposeByName(neverUsePurpose);
    }

    @Test(expected = RepeatitionException.class)
    public void addPurposeExceptionTest() throws RepeatitionException {
        purposeService.addPurpose(purposeForException);
    }

    @Test(expected = CantDeleteElement.class)
    public void deletePurposeExceptionTest() throws CantDeleteElement, PurposeNotFoundException {
        purposeService.deletePurpose(purposeForDeleteExceptionTest.getId());
    }

    @Test
    public void deletePurposeTest() throws RepeatitionException, CantDeleteElement, PurposeNotFoundException {
        int purposeId=purposeService.addPurpose(purposeBeforeTest);
        purposeBeforeTest.setId(purposeId);
        int deleteId = purposeBeforeTest.getId();
        purposeService.deletePurpose(purposeBeforeTest.getId());
        List<PurposeDto> purposes = purposeService.findPurposes();
        Assert.assertTrue(purposes.stream().noneMatch(purpose -> purpose.getId() == deleteId));
    }

    @Test
    public void findPurposeById() throws PurposeNotFoundException {
        List<PurposeDto> purposes = purposeService.findPurposes();
        int id = purposes.get(0).getId();
        PurposeDto findPurposeById=purposeService.findPurposeById(id);
        List<PurposeDto> missingPurposes = purposeService.findPurposes();
        List<PurposeDto> findPurposesById = missingPurposes.stream().filter(purpose -> purpose.getId() != findPurposeById.getId()).collect(Collectors.toList());
        missingPurposes.removeAll(findPurposesById);
        int size = missingPurposes.size();
        assertEquals(size, 1);
    }

    @Test(expected = PurposeNotFoundException.class)
    public void findPurposeByIdExceptionTest() throws PurposeNotFoundException {
        List<PurposeDto> purposes=purposeService.findPurposes();
        int sum=1;
        for(int i=1;i<purposes.size();i++){
            sum=sum*purposes.get(i).getId();
        }
        purposeService.findPurposeById(sum);
    }
}
