package com.multic.repository;

import com.multic.domain.ActividadxEstudiante;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActividadxEstudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadxEstudianteRepository extends JpaRepository<ActividadxEstudiante, Long> {

}
