package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Diagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    @Query(value = "select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.prescriptions left join fetch diagnosis.manifestedSymptoms left join fetch diagnosis.diagnosedConditions",
        countQuery = "select count(distinct diagnosis) from Diagnosis diagnosis")
    Page<Diagnosis> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.prescriptions left join fetch diagnosis.manifestedSymptoms left join fetch diagnosis.diagnosedConditions")
    List<Diagnosis> findAllWithEagerRelationships();

    @Query(value = "select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.prescriptions left join fetch diagnosis.manifestedSymptoms left join fetch diagnosis.diagnosedConditions where diagnosis.anamnesis.id =:id")
    Set<Diagnosis> findAllWithEagerRelationshipsByAnamnesisId(@Param("id") Long id);

    @Query("select diagnosis from Diagnosis diagnosis left join fetch diagnosis.prescriptions left join fetch diagnosis.manifestedSymptoms left join fetch diagnosis.diagnosedConditions where diagnosis.id =:id")
    Optional<Diagnosis> findOneWithEagerRelationships(@Param("id") Long id);

}
