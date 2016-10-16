package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.AdditionalRate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AdditionalRate entity.
 */
@SuppressWarnings("unused")
public interface AdditionalRateRepository extends JpaRepository<AdditionalRate,Long> {

}
