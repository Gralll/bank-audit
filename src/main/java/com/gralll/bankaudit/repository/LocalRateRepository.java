package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.LocalRate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalRate entity.
 */
@SuppressWarnings("unused")
public interface LocalRateRepository extends JpaRepository<LocalRate,Long> {

}
