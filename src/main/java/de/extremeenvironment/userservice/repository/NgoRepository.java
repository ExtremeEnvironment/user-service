package de.extremeenvironment.userservice.repository;

import de.extremeenvironment.userservice.domain.Ngo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ngo entity.
 */
@SuppressWarnings("unused")
public interface NgoRepository extends JpaRepository<Ngo,Long> {

    @Query("select distinct ngo from Ngo ngo left join fetch ngo.users")
    List<Ngo> findAllWithEagerRelationships();

    @Query("select ngo from Ngo ngo left join fetch ngo.users where ngo.id =:id")
    Ngo findOneWithEagerRelationships(@Param("id") Long id);

}
