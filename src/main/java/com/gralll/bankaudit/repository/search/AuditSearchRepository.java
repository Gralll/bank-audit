package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.Audit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Audit entity.
 */
public interface AuditSearchRepository extends ElasticsearchRepository<Audit, Long> {
}
