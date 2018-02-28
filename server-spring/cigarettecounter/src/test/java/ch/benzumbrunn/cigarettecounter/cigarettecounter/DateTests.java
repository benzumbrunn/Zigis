package ch.benzumbrunn.cigarettecounter.cigarettecounter;

import ch.benzumbrunn.cigarettecounter.cigarettecounter.web.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTests {

    @MockBean
    MockServletContext context;

    @Test
    public void testLast30() {
        List<Date> last30 = DateUtils.getLast30Dates();
        for (Date date : last30) {
            System.out.println(date.toString());
        }
        assertTrue(last30.size() == 30);
    }

}
