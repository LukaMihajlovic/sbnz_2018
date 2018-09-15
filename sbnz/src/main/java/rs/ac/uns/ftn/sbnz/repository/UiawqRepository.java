package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Uiawq;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Uiawq entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UiawqRepository extends JpaRepository<Uiawq, Long> {

}
