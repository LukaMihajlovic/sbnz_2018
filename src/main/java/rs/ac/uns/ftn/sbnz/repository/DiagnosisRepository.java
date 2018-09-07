package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Diagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

}
