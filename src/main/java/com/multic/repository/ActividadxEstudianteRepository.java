package com.multic.repository;

import com.multic.domain.Actividad;
import com.multic.domain.ActividadxEstudiante;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActividadxEstudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadxEstudianteRepository extends JpaRepository<ActividadxEstudiante, Long> {

    @Query("SELECT a FROM ActividadxEstudiante axc "
            + "JOIN axc.actividad a "
            + "LEFT JOIN axc.estudiante e "
            + "LEFT JOIN e.usuario u "
            + "WHERE a.id = ?1 AND u.login = ?2 "
            + "AND axc.acerto = true")
    Optional<Actividad> estaCompletada(Long actividadId, String login);
    
    
     @Query("SELECT axc FROM ActividadxEstudiante axc "
            + "JOIN axc.actividad a "
            + "LEFT JOIN axc.estudiante e "
            + "LEFT JOIN e.usuario u "
            + "WHERE a.id = ?1 AND u.login = ?2 ")      
    Optional<ActividadxEstudiante> getProgreso(Long actividadId, String login); 
    
    
}
