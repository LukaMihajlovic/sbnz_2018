package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Physician;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Physician entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    Optional<Physician> findByUserId(Long id);


}
