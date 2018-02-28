package ch.benzumbrunn.cigarettecounter.cigarettecounter;

import ch.benzumbrunn.cigarettecounter.cigarettecounter.domain.Cigarettes;
import ch.benzumbrunn.cigarettecounter.cigarettecounter.web.CigaretteController;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CigaretteControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLast30() throws JSONException {
        String body = this.restTemplate.getForObject("/cigarettes/last30", String.class);
        JSONArray array = new JSONArray(body);
        System.out.println(body);
        assertTrue(array.length() == 30);
    }

    @Test
    public void testGetByDate() {
        Date today = java.sql.Date.valueOf(LocalDate.now());
        Cigarettes cigarettes = this.restTemplate
                .getForEntity("/cigarettes?date=" + today.toString(), Cigarettes.class)
                .getBody();

        assertEquals(today.toString(), cigarettes.getDate().toString());
    }

    @Test
    public void testAddToday() {
        //Cigarettes before = this.restTemplate.getForEntity("/cigarettes/last30", Cigarettes.class).getBody();
        //assertEquals("0", before.getCount());

        //ResponseEntity<Cigarettes> responseEntity = this.restTemplate.postForLocation("/cigarettes/today", );
        assertTrue(true);
    }
}
