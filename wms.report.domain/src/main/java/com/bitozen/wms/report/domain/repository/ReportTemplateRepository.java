package com.bitozen.wms.report.domain.repository;

import com.bitozen.wms.report.domain.ReportTemplate;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author kori
 */
@Repository
@Transactional
public interface ReportTemplateRepository extends PagingAndSortingRepository<ReportTemplate, Long>{

    @Cacheable(value = "findOneByReportTemplateIDCache", key = "#p0")
    Optional <ReportTemplate> findOneByReportTemplateID (String reportTemplateID);
    
}