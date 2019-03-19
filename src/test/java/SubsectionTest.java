
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.entity.builder.Subsection.SubsectionBuilder;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.SubsectionDaoImplFake;
import com.vironit.kazimirov.service.SubsectionService;
import com.vironit.kazimirov.service.impl.SubsectionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.stream.Collectors;

public class SubsectionTest {

    SubsectionService subsectionService = new SubsectionServiceImpl(new SubsectionDaoImplFake());
    Subsection subsectionBeforeTest = null;
    Subsection subsectionForExceptionTest = null;

    @Before
    public void createForExceptionSubsection() {

        SubsectionBuilder subsectionBuilder = new SubsectionBuilder();
        subsectionForExceptionTest = subsectionBuilder
                .withTitle("Утеплитель")
                .build();
    }

    @Before
    public void createSubsection() {

        SubsectionBuilder subsectionBuilder = new SubsectionBuilder();
        subsectionBeforeTest = subsectionBuilder
                .withTitle("Битумные материалы")
                .build();
    }

    @Test
    public void addSubsection() throws RepeatitionException {
        subsectionService.addSubsection(subsectionBeforeTest);
        List<Subsection> missingSubsections= subsectionService.findSubsections();
        List<Subsection> findSubsectionById;
        findSubsectionById = missingSubsections.stream().filter(subsection -> subsection.getId() != subsectionBeforeTest.getId()).collect(Collectors.toList());
        missingSubsections.removeAll(findSubsectionById);
        Assert.assertTrue(missingSubsections.stream().allMatch(subsection -> subsection.getId() == subsectionBeforeTest.getId()));
    }


    @Test(expected = RepeatitionException.class)
    public void addSubsectionExceptionTest() throws RepeatitionException {
        subsectionService.addSubsection(subsectionForExceptionTest);
    }

}
