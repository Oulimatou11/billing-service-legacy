package com.techsolutions.billingServiceLegacy.service;


import com.techsolutions.billingServiceLegacy.domain.InvoiceEntity;
import com.techsolutions.billingServiceLegacy.domain.PaymentMethod;
import com.techsolutions.billingServiceLegacy.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceEntity createInvoice(Long clientId, BigDecimal amount, String description){
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setClientId(clientId);
        invoiceEntity.setAmount(amount);
        invoiceEntity.setDescription(description);
        invoiceEntity.setDateEmission(LocalDate.now());
        invoiceEntity.setStatus("PENDING");
        return invoiceRepository.save(invoiceEntity);

    }

    public InvoiceEntity getInvoiceById(Long id){
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<InvoiceEntity> getClientInvoices(Long clientId){
        return invoiceRepository.findByClientId(clientId);
    }

    public InvoiceEntity payInvoice(Long id) {
        InvoiceEntity invoiceEntity = getInvoiceById(id);

        if ("PAID".equals(invoiceEntity.getStatus())) {
            throw new IllegalArgumentException("Facture déja payée");
        }

        invoiceEntity.setStatus("PAID");
        invoiceEntity.setDatePaiement(java.time.LocalDate.now());
        invoiceEntity.setPaymentMethod(PaymentMethod.CASH);

        return invoiceRepository.save(invoiceEntity);
    }

    public BigDecimal getClientTotal(Long clientId) {
        BigDecimal total = invoiceRepository.calculateTotalByClientId(clientId);
        return total != null ? total : BigDecimal.ZERO;
    }

//    public String generateReceipt(Long invoiceId) {
//        Invoice invoice = getInvoiceById(invoiceId);
//        if (invoice == null) {
//            throw new IllegalArgumentException("Facture non trouvée");
//        }
//        if (!"PAID".equals(invoice.getStatus())) {
//            throw new IllegalStateException("La facture n'a pas été payée");
//        }
//
//        StringBuilder recu = new StringBuilder();
//        recu.append("----- Reçu de Paiement -----\n");
//        recu.append("Numero Facture: ").append(invoice.getId()).append("\n");
//        recu.append("Client: ").append(invoice.getClientId()).append("\n");
//        recu.append("Montant payé: ").append(invoice.getAmount()).append("\n");
//        recu.append("Date de paiement: ").append(invoice.getDatePaiement()).append("\n");
//        recu.append("Méthode de paiement: ").append(invoice.getPaymentMethod()).append("\n");
//        recu.append("----------------------------\n");
//
//        return recu.toString();
//    }
}
