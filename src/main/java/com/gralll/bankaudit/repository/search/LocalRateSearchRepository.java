package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.LocalRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LocalRate entity.
 */
public interface LocalRateSearchRepository extends ElasticsearchRepository<LocalRate, Long> {
}
