package com.gralll.bankaudit.repository.search;

import com.gralll.bankaudit.domain.GroupRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupRate entity.
 */
public interface GroupRateSearchRepository extends ElasticsearchRepository<GroupRate, Long> {
}
