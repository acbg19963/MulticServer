package com.multic.repository;

import com.multic.domain.Profesor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Profesor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

}
