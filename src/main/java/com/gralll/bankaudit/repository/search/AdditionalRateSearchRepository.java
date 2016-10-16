package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.AdditionalRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AdditionalRate entity.
 */
public interface AdditionalRateSearchRepository extends ElasticsearchRepository<AdditionalRate, Long> {
}
