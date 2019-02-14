import com.vironit.kazimirov.dao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.dao.SubsectionDaoImpl;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import com.vironit.kazimirov.service.impl.SubsectionServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SubsectionTest {

    SubsectionService subsectionServiceImpl = new SubsectionServiceImpl();

    @Test(expected = RepeatitionException.class)
    public void addSubsectionExceptionTest() throws RepeatitionException {
        subsectionServiceImpl.addSubsection(1, "Утеплитель");
    }

}
