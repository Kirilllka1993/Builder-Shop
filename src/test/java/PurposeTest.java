import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.builder.Purpose.PurposeBuilder;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.fakedao.PurposeDaoImplFake;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.impl.PurposeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class PurposeTest  {
    PurposeService purposeService=new PurposeServiceImpl(new PurposeDaoImplFake());

    Purpose purposeBeforeTest=null;
    Purpose purposeForException=null;

    @Before
    public void createPurpose() {

        PurposeBuilder purposeBuilder = new PurposeBuilder();
        purposeBeforeTest = purposeBuilder
                .withTitle("Отделочные работы")
                .build();
    }

    @Before
    public void createPurposeForException() {

        PurposeBuilder purposeBuilder = new PurposeBuilder();
        purposeForException = purposeBuilder
                .withTitle("Внутренние работы")
                .build();
    }

    @Test
    public void addPurpose() throws RepeatitionException{
        purposeService.addPurpose(purposeBeforeTest);
        List<Purpose> missingPurposes= purposeService.findPurposes();
        List<Purpose> findPurposesById;
        findPurposesById = missingPurposes.stream().filter(subsection -> subsection.getId() != purposeBeforeTest.getId()).collect(Collectors.toList());
        missingPurposes.removeAll(findPurposesById);
        Assert.assertTrue(missingPurposes.stream().allMatch(subsection -> subsection.getId() == purposeBeforeTest.getId()));
    }

    @Test(expected = RepeatitionException.class)
    public void addPurposeExceptionTest() throws RepeatitionException {
        purposeService.addPurpose( purposeForException);
    }
}
