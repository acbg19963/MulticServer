package com.multic.repository;

import com.multic.domain.Planeta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Planeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

}
