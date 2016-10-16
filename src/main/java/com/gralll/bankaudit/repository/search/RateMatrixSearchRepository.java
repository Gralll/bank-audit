package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.RateMatrix;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RateMatrix entity.
 */
public interface RateMatrixSearchRepository extends ElasticsearchRepository<RateMatrix, Long> {
}
