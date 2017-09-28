package com.multic.repository;

import com.multic.domain.Estudiante;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

}
