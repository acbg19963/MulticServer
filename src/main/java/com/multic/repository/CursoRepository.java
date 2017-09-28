package com.multic.repository;

import com.multic.domain.Curso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Curso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Query("select distinct curso from Curso curso left join fetch curso.profesores")
    List<Curso> findAllWithEagerRelationships();

    @Query("select curso from Curso curso left join fetch curso.profesores where curso.id =:id")
    Curso findOneWithEagerRelationships(@Param("id") Long id);

}
