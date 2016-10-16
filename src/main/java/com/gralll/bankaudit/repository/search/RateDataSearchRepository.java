package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.RateData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RateData entity.
 */
public interface RateDataSearchRepository extends ElasticsearchRepository<RateData, Long> {
}
