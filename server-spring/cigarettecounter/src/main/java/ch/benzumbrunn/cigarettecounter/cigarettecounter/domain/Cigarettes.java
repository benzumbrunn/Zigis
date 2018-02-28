package ch.benzumbrunn.cigarettecounter.cigarettecounter.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Cigarettes {

    @Id
    private Date date;

    private int count;

    public Cigarettes() {}

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void addOne() {
        count++;
    }

    public void add(int numberOfCigarettes) {
        this.count += numberOfCigarettes;
    }


    @Override
    public int hashCode(){
        return Objects.hash(date, count);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Cigarettes) {
            final Cigarettes other = (Cigarettes)obj;
            return Objects.equals(date, other.getDate()) && Objects.equals(count, other.getCount());
        } else {
            return false;
        }
    }
}
