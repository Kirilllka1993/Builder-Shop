
import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.SubsectionService;
import org.junit.*;
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
public class SubsectionTest {
    @Autowired
    private SubsectionService subsectionService;
    @Autowired
    private GoodService goodService;

    Subsection subsectionBeforeTest = null;
    Subsection subsectionForExceptionTest = null;
    Subsection subsectionForDeleteExceptionTest = null;

    @Before
    public void createForExceptionSubsection() {
        List<Subsection> allSubsections = subsectionService.findSubsections();
        List<Subsection> subsections = new ArrayList<>();
        subsections.add(allSubsections.get(0));
        subsections.add(allSubsections.get(1));
        subsectionForExceptionTest = allSubsections.get(0);
        String neverUseTitle = subsections.stream().map(Subsection::getTitle).collect(Collectors.joining());
        subsectionBeforeTest = subsections.get(subsections.size() - 1);
        subsectionBeforeTest.setTitle(neverUseTitle);
    }

    @Before
    public void createForDeletingTest() {
        List<Good> goods = goodService.findAllGoods();
        subsectionForDeleteExceptionTest = goods.get(0).getSubsection();
    }

    @Test
    public void addSubsection() throws RepeatitionException, CantDeleteElement {
        subsectionService.addSubsection(subsectionBeforeTest);
        List<Subsection> missingSubsections = subsectionService.findSubsections();
        List<Subsection> findSubsectionById = missingSubsections.stream().filter(subsection -> subsection.getId() != subsectionBeforeTest.getId())
                .collect(Collectors.toList());
        missingSubsections.removeAll(findSubsectionById);
        Assert.assertTrue(missingSubsections.stream().allMatch(subsection -> subsection.getId() == subsectionBeforeTest.getId()));
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
    }

    @Test
    public void findSubsectionByName() throws RepeatitionException, CantDeleteElement {
        subsectionService.addSubsection(subsectionBeforeTest);
        Subsection subsection = subsectionService.findSubsectionByName(subsectionBeforeTest.getTitle());
        List<Subsection> missingSubsections = subsectionService.findSubsections();
        List<Subsection> allsubsectionsByTitle = missingSubsections.stream().filter(subsection1 -> !subsection1.getTitle()
                .equals(subsectionBeforeTest.getTitle())).collect(Collectors.toList());
        missingSubsections.removeAll(allsubsectionsByTitle);
        Assert.assertTrue(missingSubsections.stream().allMatch((subsection1 -> subsection1.getTitle().equals(subsection.getTitle()))));
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
    }

    @Test(expected = RepeatitionException.class)
    public void addSubsectionExceptionTest() throws RepeatitionException {
        subsectionService.addSubsection(subsectionForExceptionTest);
    }

    @Test(expected = CantDeleteElement.class)
    public void deleteSubsectionExceptionTest() throws CantDeleteElement {
        subsectionService.deleteSubsection(subsectionForDeleteExceptionTest.getId());
    }

    @Test
    public void deleteSubcetionTest() throws RepeatitionException, CantDeleteElement {
        subsectionService.addSubsection(subsectionBeforeTest);
        int deleteId = subsectionBeforeTest.getId();
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
        List<Subsection> subsections = subsectionService.findSubsections();
        Assert.assertTrue(subsections.stream().noneMatch(subsection -> subsection.getId() == deleteId));
    }

    @Test
    public void findSubsectionById() {
        List<Subsection> subsections = subsectionService.findSubsections();
        int id = subsections.get(0).getId();
        List<Subsection> missingSubsections = subsectionService.findSubsections();
        List<Subsection> findSubsectionsById = missingSubsections.stream().filter(subsection -> subsection.getId() != id).collect(Collectors.toList());
        missingSubsections.removeAll(findSubsectionsById);
        int size = missingSubsections.size();
        assertEquals(size, 1);
    }
}
