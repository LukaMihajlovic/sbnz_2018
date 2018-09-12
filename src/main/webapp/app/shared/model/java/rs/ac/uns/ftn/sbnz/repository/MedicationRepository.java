package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Medication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(value = "select distinct medication from Medication medication left join fetch medication.ingredients",
        countQuery = "select count(distinct medication) from Medication medication")
    Page<Medication> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct medication from Medication medication left join fetch medication.ingredients")
    List<Medication> findAllWithEagerRelationships();

    @Query("select medication from Medication medication left join fetch medication.ingredients where medication.id =:id")
    Optional<Medication> findOneWithEagerRelationships(@Param("id") Long id);

}
