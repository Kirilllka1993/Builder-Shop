
import com.vironit.kazimirov.config.WebApplicationConfig;
import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.SubsectionService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class SubsectionTest {
    @Autowired
    private SubsectionService subsectionService;
    @Autowired
    private GoodService goodService;

    SubsectionDto subsectionBeforeTest = null;
    SubsectionDto subsectionForExceptionTest = null;
    SubsectionDto subsectionForDeleteExceptionTest = new SubsectionDto();

    @Before
    public void createForExceptionSubsection() {
        List<SubsectionDto> allSubsections = subsectionService.findSubsections();
        List<SubsectionDto> subsections = new ArrayList<>();
        subsections.add(allSubsections.get(0));
        subsections.add(allSubsections.get(1));
        subsectionForExceptionTest = allSubsections.get(0);
        String neverUseTitle = subsections.stream().map(SubsectionDto::getTitle).collect(Collectors.joining());
        subsectionBeforeTest = subsections.get(subsections.size() - 1);
        subsectionBeforeTest.setTitle(neverUseTitle);
    }

    @Before
    public void createForDeletingTest() throws SubsectionNotFoundException {
        List<GoodDto> goods = goodService.findAllGoods();
        subsectionForDeleteExceptionTest = subsectionService.findSubsectionById(goods.get(0).getId());
    }

    @Test
    public void addSubsection() throws RepeatitionException, CantDeleteElement, SubsectionNotFoundException {
        int subsectionId=subsectionService.addSubsection(subsectionBeforeTest);
        subsectionBeforeTest.setId(subsectionId);
        List<SubsectionDto> missingSubsections = subsectionService.findSubsections();
        List<SubsectionDto> findSubsectionById = missingSubsections.stream().filter(subsection -> subsection.getId() != subsectionBeforeTest.getId())
                .collect(Collectors.toList());
        missingSubsections.removeAll(findSubsectionById);
        Assert.assertTrue(missingSubsections.stream().allMatch(subsection -> subsection.getId() == subsectionBeforeTest.getId()));
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
    }

    @Test
    public void findSubsectionByTitle() throws RepeatitionException, CantDeleteElement, SubsectionNotFoundException {
        int subsectionId=subsectionService.addSubsection(subsectionBeforeTest);
        subsectionBeforeTest.setId(subsectionId);
        SubsectionDto subsection = subsectionService.findSubsectionByName(subsectionBeforeTest.getTitle());
        List<SubsectionDto> missingSubsections = subsectionService.findSubsections();
        List<SubsectionDto> allsubsectionsByTitle = missingSubsections.stream().filter(subsection1 -> !subsection1.getTitle()
                .equals(subsectionBeforeTest.getTitle())).collect(Collectors.toList());
        missingSubsections.removeAll(allsubsectionsByTitle);
        Assert.assertTrue(missingSubsections.stream().allMatch((subsection1 -> subsection1.getTitle().equals(subsection.getTitle()))));
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
    }

    @Test(expected = SubsectionNotFoundException.class)
    public void subsectionFindByTitleExceptionTest() throws SubsectionNotFoundException {
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        String neverUseTitle = subsections.stream().map(SubsectionDto::getTitle).collect(Collectors.joining());
        subsectionService.findSubsectionByName(neverUseTitle);
    }

    @Test(expected = RepeatitionException.class)
    public void addSubsectionExceptionTest() throws RepeatitionException {
        subsectionService.addSubsection(subsectionForExceptionTest);
    }

    @Test(expected = CantDeleteElement.class)
    public void deleteSubsectionExceptionTest() throws CantDeleteElement, SubsectionNotFoundException {
        subsectionService.deleteSubsection(subsectionForDeleteExceptionTest.getId());
    }

    @Test
    public void deleteSubcetionTest() throws RepeatitionException, CantDeleteElement, SubsectionNotFoundException {
        int subsectionId=subsectionService.addSubsection(subsectionBeforeTest);
        subsectionBeforeTest.setId(subsectionId);
        int deleteId = subsectionBeforeTest.getId();
        subsectionService.deleteSubsection(subsectionBeforeTest.getId());
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        Assert.assertTrue(subsections.stream().noneMatch(subsection -> subsection.getId() == deleteId));
    }

    @Test
    public void findSubsectionById() throws SubsectionNotFoundException {
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        int id = subsections.get(0).getId();
        SubsectionDto findSubsectionById = subsectionService.findSubsectionById(id);
        List<SubsectionDto> missingSubsections = subsectionService.findSubsections();
        List<SubsectionDto> findSubsectionsById = missingSubsections.stream().filter(subsection -> subsection.getId() != findSubsectionById.getId()).collect(Collectors.toList());
        missingSubsections.removeAll(findSubsectionsById);
        int size = missingSubsections.size();
        assertEquals(size, 1);
    }

    @Test(expected = SubsectionNotFoundException.class)
    public void findSubsectionByIdExceptionTest() throws SubsectionNotFoundException {
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        int sum = 1;
        for (int i = 1; i < subsections.size(); i++) {
            sum = sum * subsections.get(i).getId();
        }
        subsectionService.findSubsectionById(sum);
    }
}
