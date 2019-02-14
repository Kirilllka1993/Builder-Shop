import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.impl.GoodServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GoodTest {

    GoodService goodService=new GoodServiceImpl();

    Purpose purpose1 = new Purpose(1, "Фундамент");
    Purpose purpose2 = new Purpose(2, "Внутренние работы");
    Purpose purpose3 = new Purpose(3, "Наружные работы");
    Purpose purpose4 = new Purpose(4, "Кровельные работы");
    Purpose purpose5 = new Purpose(5, "Кровельные");

    Subsection subsection1 = new Subsection(1, "Утеплитель");
    Subsection subsection2 = new Subsection(2, "Сухие смеси");
    Subsection subsection3 = new Subsection(3, "Лакокрасочные покрытия");
    Subsection subsection4 = new Subsection(4, "Гидроизоляционные материалы");
    Subsection subsection5 = new Subsection(5, "материалы");

    Good good1 = new Good(1, 2.0, subsection1, "м3", 5, 0, purpose1, "Пеноплекс", 54);
    Good good2 = new Good(2, 2.0, subsection2, "м3", 5, 5, purpose2, "Шпатлевка", 13);
    Good good3 = new Good(3, 2.0, subsection3, "м3", 5, 6, purpose3, "Краска для дерева", 15);
    Good good4 = new Good(4, 2.0, subsection4, "м3", 5, 0, purpose1, "Техноэласт", 18);

    @Test(expected = GoodException.class)
    public void addGoodExceptionTest() throws GoodException {
        goodService.addGood(2.0, subsection1, "м3", 5, 15, purpose1, "Пеноплекс", 54);
    }
    @Test
    public void findByNameTest() throws GoodException {
        Good expGood=goodService.findByNameGood("Пеноплекс");
        assertEquals(expGood,good1);
    }

    @Test(expected = GoodException.class)
    public void findByNameExceptionTest() throws GoodException {
        goodService.findByNameGood("Электрика");
    }

    @Test
    public void findBySubsectionTest() throws GoodException {
        List<Good> expGood=goodService.findBySubsection(subsection1);
        List<Good>goods=new ArrayList<>();
        goods.add(good1);
        assertEquals(expGood,goods);
    }

    @Test(expected = GoodException.class)
    public void findBySubsectionExceptionTest() throws GoodException {
        goodService.findBySubsection(subsection5);
    }

    @Test
    public void findByPurposeTest() throws GoodException {
        List<Good> expGood=goodService.findByPurpose(purpose1);
        List<Good>goods=new ArrayList<>();
        goods.add(good1);
        goods.add(good4);
        assertEquals(expGood,goods);
    }

    @Test(expected = GoodException.class)
    public void findByPurposeExceptionTest() throws GoodException {
        goodService.findByPurpose(purpose5);
    }

    @Test(expected = GoodException.class)
    public void deleteTest() throws GoodException {
        goodService.deleteGood(5);
    }
}
