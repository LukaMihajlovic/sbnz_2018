package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Diagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    @Query("select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.drugs left join fetch diagnosis.symptoms left join fetch diagnosis.diseases")
    List<Diagnosis> findAllWithEagerRelationships();

    @Query("select diagnosis from Diagnosis diagnosis left join fetch diagnosis.drugs left join fetch diagnosis.symptoms left join fetch diagnosis.diseases where diagnosis.id =:id")
    Diagnosis findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.drugs left join fetch diagnosis.symptoms left join fetch diagnosis.diseases where diagnosis.anamnesis.id =:id")
    Set<Diagnosis> findAllWithEagerRelationshipsByAnamnesisId(@Param("id") Long id);

}
