package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Anamnesis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnamnesisRepository extends JpaRepository<Anamnesis, Long> {
    @Query("select distinct anamnesis from Anamnesis anamnesis left join fetch anamnesis.allergiesIngredients")
    List<Anamnesis> findAllWithEagerRelationships();

    @Query("select anamnesis from Anamnesis anamnesis left join fetch anamnesis.allergiesIngredients where anamnesis.id =:id")
    Anamnesis findOneWithEagerRelationships(@Param("id") Long id);

}
