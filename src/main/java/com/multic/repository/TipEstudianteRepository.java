package com.multic.repository;

import com.multic.domain.TipEstudiante;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipEstudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipEstudianteRepository extends JpaRepository<TipEstudiante, Long> {

}
