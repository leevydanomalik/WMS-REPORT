package com.bitozen.wms.report.domain.repository;

import com.bitozen.wms.report.domain.ReportTemplate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MBRRepository extends PagingAndSortingRepository<ReportTemplate, Long> {
    
    @Query(value = "select * from povspr()", nativeQuery = true)
    List<Object[]> findPOvsPR();
    
    @Query(value = "select * from grvspr()", nativeQuery = true)
    List<Object[]> findGRvsPR();
    
}
