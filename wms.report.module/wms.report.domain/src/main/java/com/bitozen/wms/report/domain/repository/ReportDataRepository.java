package com.bitozen.wms.report.domain.repository;

import com.bitozen.wms.report.domain.ReportData;
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
public interface ReportDataRepository extends PagingAndSortingRepository<ReportData, Long> {

    @Cacheable(value = "findOneByReportDataIDCache", key = "#p0")
    Optional<ReportData> findOneByReportDataID(String ReportDataID);

}
