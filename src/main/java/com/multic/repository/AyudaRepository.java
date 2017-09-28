package com.multic.repository;

import com.multic.domain.Ayuda;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ayuda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AyudaRepository extends JpaRepository<Ayuda, Long> {

}
