package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.RateMatrix;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RateMatrix entity.
 */
@SuppressWarnings("unused")
public interface RateMatrixRepository extends JpaRepository<RateMatrix,Long> {

}
