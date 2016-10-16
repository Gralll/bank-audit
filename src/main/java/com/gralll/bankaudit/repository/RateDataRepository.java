package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.RateData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RateData entity.
 */
@SuppressWarnings("unused")
public interface RateDataRepository extends JpaRepository<RateData,Long> {

}
