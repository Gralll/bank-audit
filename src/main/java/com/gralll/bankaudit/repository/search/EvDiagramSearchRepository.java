package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.EvDiagram;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EvDiagram entity.
 */
public interface EvDiagramSearchRepository extends ElasticsearchRepository<EvDiagram, Long> {
}
