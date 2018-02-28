package ch.benzumbrunn.cigarettecounter.cigarettecounter.web;

import ch.benzumbrunn.cigarettecounter.cigarettecounter.domain.Cigarettes;
import ch.benzumbrunn.cigarettecounter.cigarettecounter.domain.Month;
import ch.benzumbrunn.cigarettecounter.cigarettecounter.persistence.CigarettesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/cigarettes")
public class CigaretteController {

    @Autowired
    CigarettesRepository cigarettesRepository;

    /**
     * Returns a cigarettes JSON object from a specific date.
     * If the date is not in the database, it will be created.
     * @param date
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "date")
    public ResponseEntity<Cigarettes> getByDate(@RequestParam Date date) {
        Cigarettes cigarettes = cigarettesRepository.findOne(date);
        cigarettes = getOrCreateCigarettes(cigarettes, date);
        return new ResponseEntity<>(cigarettes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, params = {"date", "number"})
    public ResponseEntity<Cigarettes> setCigarettesToDate(@RequestParam Date date, @RequestParam int number) {
        Cigarettes cigarettes = cigarettesRepository.findOne(date);
        cigarettes = getOrCreateCigarettes(cigarettes, date);
        cigarettes.setCount(number);
        Cigarettes updatedCigarettes = cigarettesRepository.save(cigarettes);
        return new ResponseEntity<>(updatedCigarettes, HttpStatus.OK);
    }

    /**
     * Returns all cigarettes consumed in the last 30 days.
     * @return
     */
    @RequestMapping(value = "/last30", method = RequestMethod.GET)
    public ResponseEntity<List<Cigarettes>> last30() {
        List<Cigarettes> last30 = getLast30Cigarettes();
        return new ResponseEntity<>(last30, HttpStatus.OK);
    }

    /**
     * Returns all cigarettes consumed today.
     * @return
     */
    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public ResponseEntity today() {
        Cigarettes today = getTodayCigarettes();
        if (today == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cigarettes>(today, HttpStatus.OK);
    }

    /**
     * Returns all cigarettes consumed yesterday.
     * @return
     */
    @RequestMapping(value = "/yesterday", method = RequestMethod.GET)
    public ResponseEntity yesterday() {
        Cigarettes yesterday = getYesterdayCigarettes();
        if (yesterday == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cigarettes>(yesterday, HttpStatus.OK);
    }

    /**
     * Adds a cigarette to today.
     * @return
     */
    @RequestMapping(value = "/today", method = RequestMethod.POST)
    //public ResponseEntity<Cigarettes> addOneToday() {
    public RedirectView addOneToday() {
        Cigarettes todayCigarettes = getTodayCigarettes();
        todayCigarettes.addOne();
        cigarettesRepository.save(todayCigarettes);
        return new RedirectView("/cigarettecounter/cigarettes/today");
    }

    /**
     * Adds a cigarette to yesterday.
     * @return
     */
    @RequestMapping(value = "/yesterday", method = RequestMethod.POST)
    public RedirectView addOneYesterday() {
        Cigarettes yesterdayCigarettes = getYesterdayCigarettes();
        yesterdayCigarettes.addOne();
        cigarettesRepository.save(yesterdayCigarettes);
        return new RedirectView("/cigarettecounter/cigarettes/yesterday");
    }

    @RequestMapping(value = "/months", method = RequestMethod.GET)
    public ResponseEntity months() {
        List<Month> months = null;
        try {
            months = getMonths();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Month>>(months, HttpStatus.OK);
    }


    /**
     * Helper method to get todays instance from the cigarette repo.
     * This also creates a new entry for today if none does exist up to this point and returns it.
     * @return
     */
    private Cigarettes getTodayCigarettes() {
        Date todayDate = DateUtils.getTodayDate();
        Cigarettes todayCigarettes = cigarettesRepository.findOne(todayDate);
        return getOrCreateCigarettes(todayCigarettes, todayDate);
    }

    private Cigarettes getYesterdayCigarettes() {
        Date yesterdayDate = DateUtils.getYesterdayDate();
        Cigarettes yesterdayCigarettes = cigarettesRepository.findOne(DateUtils.getYesterdayDate());
        return getOrCreateCigarettes(yesterdayCigarettes, yesterdayDate);
    }

    private List<Cigarettes> getLast30Cigarettes() {
        List<Date> last30Dates = DateUtils.getLast30Dates();
        List<Cigarettes> last30Cigarettes = new ArrayList<>();
        for (Date date : last30Dates) {
            Cigarettes cigarettes = cigarettesRepository.findOne(date);
            Cigarettes updatedCigarettes = getOrCreateCigarettes(cigarettes, date);
            last30Cigarettes.add(updatedCigarettes);
        }
        Collections.reverse(last30Cigarettes);
        return last30Cigarettes;
    }

    private Cigarettes getOrCreateCigarettes(Cigarettes cigarettes, Date date) {
        if (cigarettes == null) {
            cigarettes = new Cigarettes();
            cigarettes.setDate(date);
            Cigarettes updatedCigarettes = cigarettesRepository.save(cigarettes);
            return updatedCigarettes;
        } else {
            return cigarettes;
        }
    }

    private List<Month> getMonths() throws Exception {
        HashMap<String, Month> months = new HashMap<>();

        List<Cigarettes> all = cigarettesRepository.findAll();
        for (Cigarettes c : all) {
            LocalDate localDate = c.getDate().toLocalDate();
            localDate.getMonthValue();
            localDate.getYear();

            Month month = new Month();
            month.setMonth(localDate.getMonthValue(), localDate.getYear());

            if (months.containsKey(month.getMonth())) {
                month = months.get(month.getMonth());
            } else {
                months.put(month.getMonth(), month);
            }

            month.setCount(month.getCount() + c.getCount());
        }

        ArrayList<Month> monthArray = new ArrayList<>(months.values());
        Collections.sort(monthArray);
        Collections.reverse(monthArray);

        return monthArray;
    }

}
