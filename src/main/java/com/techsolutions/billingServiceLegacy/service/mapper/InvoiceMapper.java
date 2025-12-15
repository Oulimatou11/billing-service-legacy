package com.techsolutions.billingServiceLegacy.service.mapper;


import com.techsolutions.billingServiceLegacy.domain.InvoiceEntity;
import com.techsolutions.billingServiceLegacy.dto.Invoice;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@Service
public class InvoiceMapper {

    public static Invoice entityToDto(InvoiceEntity invoiceEntity){

        Invoice dtoInvoice = new Invoice();
        dtoInvoice.setInvoiceId(invoiceEntity.getId());
        dtoInvoice.setClientId(invoiceEntity.getClientId());
        dtoInvoice.setAmount(invoiceEntity.getAmount());
        dtoInvoice.setDescription(invoiceEntity.getDescription());
        dtoInvoice.setDateEmission(toXMLGregorianCalendar(invoiceEntity.getDateEmission()));
        dtoInvoice.setDatePaiement(toXMLGregorianCalendar(invoiceEntity.getDatePaiement()));
        dtoInvoice.setStatus(invoiceEntity.getStatus());
        if (invoiceEntity.getPaymentMethod() != null) {
            dtoInvoice.setPaymentMethod(String.valueOf(invoiceEntity.getPaymentMethod()));
        }
        return dtoInvoice;
    }

    //convertir localdate en xmlgregoriancalendar
    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate localDate) {
        if (localDate == null) return null;
        try {
            GregorianCalendar gCal = GregorianCalendar.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de conversion LocalDate -> XMLGregorianCalendar", e);
        }
    }
}
