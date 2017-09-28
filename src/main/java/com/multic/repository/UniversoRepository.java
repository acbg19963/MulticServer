package com.multic.repository;

import com.multic.domain.Universo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Universo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversoRepository extends JpaRepository<Universo, Long> {

}
