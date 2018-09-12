package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Disease;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Disease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    @Query("select distinct disease from Disease disease left join fetch disease.symptoms")
    List<Disease> findAllWithEagerRelationships();

    @Query("select disease from Disease disease left join fetch disease.symptoms where disease.id =:id")
    Disease findOneWithEagerRelationships(@Param("id") Long id);

}
