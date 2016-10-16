package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.FinalRate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FinalRate entity.
 */
@SuppressWarnings("unused")
public interface FinalRateRepository extends JpaRepository<FinalRate,Long> {

}
