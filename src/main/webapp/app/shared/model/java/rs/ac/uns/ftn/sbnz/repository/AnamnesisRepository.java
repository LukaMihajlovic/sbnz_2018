package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Anamnesis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnamnesisRepository extends JpaRepository<Anamnesis, Long> {

    @Query(value = "select distinct anamnesis from Anamnesis anamnesis left join fetch anamnesis.ingredientAllergies left join fetch anamnesis.medicationAllergies",
        countQuery = "select count(distinct anamnesis) from Anamnesis anamnesis")
    Page<Anamnesis> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct anamnesis from Anamnesis anamnesis left join fetch anamnesis.ingredientAllergies left join fetch anamnesis.medicationAllergies")
    List<Anamnesis> findAllWithEagerRelationships();

    @Query("select anamnesis from Anamnesis anamnesis left join fetch anamnesis.ingredientAllergies left join fetch anamnesis.medicationAllergies left join fetch anamnesis.histories  where anamnesis.id =:id")
    Optional<Anamnesis> findOneWithEagerRelationships(@Param("id") Long id);

}
