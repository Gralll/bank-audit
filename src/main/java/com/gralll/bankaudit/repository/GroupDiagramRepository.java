package com.gralll.bankaudit.repository;

import com.gralll.bankaudit.domain.GroupDiagram;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupDiagram entity.
 */
@SuppressWarnings("unused")
public interface GroupDiagramRepository extends JpaRepository<GroupDiagram,Long> {

}
