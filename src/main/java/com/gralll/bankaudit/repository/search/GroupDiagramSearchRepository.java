package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.GroupDiagram;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupDiagram entity.
 */
public interface GroupDiagramSearchRepository extends ElasticsearchRepository<GroupDiagram, Long> {
}
