package ch.benzumbrunn.cigarettecounter.cigarettecounter.persistence;

import ch.benzumbrunn.cigarettecounter.cigarettecounter.domain.Cigarettes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;


public interface CigarettesRepository extends JpaRepository<Cigarettes, Date> {
}
