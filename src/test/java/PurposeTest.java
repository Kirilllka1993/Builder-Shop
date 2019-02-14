import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.impl.PurposeServiceImpl;
import org.junit.Test;

public class PurposeTest  {
    PurposeService purposeService=new PurposeServiceImpl();

    @Test(expected = RepeatitionException.class)
    public void addPurposeExceptionTest() throws RepeatitionException {
        purposeService.addPurpose( "Кровельные работы");
    }
}
