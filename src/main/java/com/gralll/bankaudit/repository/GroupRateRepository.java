package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.GroupRate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupRate entity.
 */
@SuppressWarnings("unused")
public interface GroupRateRepository extends JpaRepository<GroupRate,Long> {

}
