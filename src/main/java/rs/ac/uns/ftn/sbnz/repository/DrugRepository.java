package rs.ac.uns.ftn.sbnz.repository;

import rs.ac.uns.ftn.sbnz.domain.Drug;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Drug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    @Query("select distinct drug from Drug drug left join fetch drug.anamneses")
    List<Drug> findAllWithEagerRelationships();

    @Query("select drug from Drug drug left join fetch drug.anamneses where drug.id =:id")
    Drug findOneWithEagerRelationships(@Param("id") Long id);

}
