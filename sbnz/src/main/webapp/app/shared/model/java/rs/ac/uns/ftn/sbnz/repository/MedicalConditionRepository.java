package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.MedicalCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MedicalCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {

    @Query(value = "select distinct medical_condition from MedicalCondition medical_condition left join fetch medical_condition.symptoms",
        countQuery = "select count(distinct medical_condition) from MedicalCondition medical_condition")
    Page<MedicalCondition> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct medical_condition from MedicalCondition medical_condition left join fetch medical_condition.symptoms")
    List<MedicalCondition> findAllWithEagerRelationships();

    @Query("select medical_condition from MedicalCondition medical_condition left join fetch medical_condition.symptoms where medical_condition.id =:id")
    Optional<MedicalCondition> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select medical_condition from MedicalCondition medical_condition left join fetch medical_condition.symptoms where medical_condition.name =:name")
    Optional<MedicalCondition> findOneByNameWithEagerRelationships(@Param("name") String name);

}
