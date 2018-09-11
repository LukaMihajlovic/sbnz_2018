package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Symptom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Symptom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

}
