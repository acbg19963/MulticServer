package com.multic.repository;

import com.multic.domain.Actividad;
import com.multic.domain.Planeta;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Planeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

    @Query("SELECT a FROM Planeta p JOIN p.actividades a WHERE p.id =?1")
    List<Actividad> actividadesPorPlaneta(Long planetaId);
}
