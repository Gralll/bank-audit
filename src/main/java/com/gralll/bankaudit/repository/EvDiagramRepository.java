package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.EvDiagram;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EvDiagram entity.
 */
@SuppressWarnings("unused")
public interface EvDiagramRepository extends JpaRepository<EvDiagram,Long> {

}
