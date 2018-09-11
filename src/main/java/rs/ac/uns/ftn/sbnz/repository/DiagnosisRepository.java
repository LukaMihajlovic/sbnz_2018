package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Diagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    @Query("select distinct diagnosis from Diagnosis diagnosis left join fetch diagnosis.drugs left join fetch diagnosis.symptoms")
    List<Diagnosis> findAllWithEagerRelationships();

    @Query("select diagnosis from Diagnosis diagnosis left join fetch diagnosis.drugs left join fetch diagnosis.symptoms where diagnosis.id =:id")
    Diagnosis findOneWithEagerRelationships(@Param("id") Long id);

}
