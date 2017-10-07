package com.multic.repository;

import com.multic.domain.Estudiante;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	
	@Query("SELECT e FROM Estudiante e LEFT JOIN e.usuario u WHERE u.login = ?1")
	Optional<Estudiante> getByLogin(String login);
	
}
