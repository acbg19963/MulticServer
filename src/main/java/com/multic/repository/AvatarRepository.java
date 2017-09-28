package com.multic.repository;

import com.multic.domain.Avatar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Avatar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
