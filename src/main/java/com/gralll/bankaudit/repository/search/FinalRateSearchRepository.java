package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.FinalRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FinalRate entity.
 */
public interface FinalRateSearchRepository extends ElasticsearchRepository<FinalRate, Long> {
}
