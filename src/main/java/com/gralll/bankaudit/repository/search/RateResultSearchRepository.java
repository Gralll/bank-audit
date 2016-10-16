package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.RateResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RateResult entity.
 */
public interface RateResultSearchRepository extends ElasticsearchRepository<RateResult, Long> {
}
