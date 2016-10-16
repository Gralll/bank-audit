package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.RateResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RateResult entity.
 */
@SuppressWarnings("unused")
public interface RateResultRepository extends JpaRepository<RateResult,Long> {

}
