package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.Audit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Audit entity.
 */
@SuppressWarnings("unused")
public interface AuditRepository extends JpaRepository<Audit,Long> {

}
