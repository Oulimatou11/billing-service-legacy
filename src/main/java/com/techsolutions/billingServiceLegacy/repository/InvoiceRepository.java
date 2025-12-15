package com.techsolutions.billingServiceLegacy.repository;


import com.techsolutions.billingServiceLegacy.domain.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {

    List<InvoiceEntity> findByClientId(Long clientId);

    List<InvoiceEntity> findByStatus(String status);

    @Query("SELECT SUM(i.amount) FROM InvoiceEntity i WHERE i.clientId = :clientId")
    BigDecimal calculateTotalByClientId(@Param("clientId") Long clientId);
}
