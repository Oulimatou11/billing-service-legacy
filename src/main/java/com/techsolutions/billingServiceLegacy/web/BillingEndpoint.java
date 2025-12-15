package com.techsolutions.billingServiceLegacy.web;

import com.techsolutions.billingServiceLegacy.domain.InvoiceEntity;
import com.techsolutions.billingServiceLegacy.dto.*;
import com.techsolutions.billingServiceLegacy.service.InvoiceService;
import com.techsolutions.billingServiceLegacy.service.mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

@Endpoint
public class BillingEndpoint {

    private static final String NAMESPACE_URI = "http://techsolutions.com/schema";

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceMapper invoiceMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInvoiceRequest")
    @ResponsePayload
    public CreateInvoiceResponse createInvoice(@RequestPayload CreateInvoiceRequest request) {
        InvoiceEntity invoiceEntity = invoiceService.createInvoice(
            request.getClientId(),
            request.getAmount(),
            request.getDescription()
        );

        CreateInvoiceResponse response = new CreateInvoiceResponse();
        response.setInvoiceId(invoiceEntity.getId());
        response.setStatus(invoiceEntity.getStatus());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
    @ResponsePayload
    public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request) {
        InvoiceEntity invoiceEntity = invoiceService.getInvoiceById(request.getInvoiceId());

        GetInvoiceResponse response = new GetInvoiceResponse();
        response.setInvoice(invoiceMapper.entityToDto(invoiceEntity));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClientInvoicesRequest")
    @ResponsePayload
    public GetClientInvoicesResponse getClientInvoices(@RequestPayload GetClientInvoicesRequest request) {
        List<InvoiceEntity> invoiceEntities = invoiceService.getClientInvoices(request.getClientId());

        GetClientInvoicesResponse response = new GetClientInvoicesResponse();
        invoiceEntities.forEach(e -> response.getInvoice().add(invoiceMapper.entityToDto(e)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "payInvoiceRequest")
    @ResponsePayload
    public PayInvoiceResponse payInvoice(@RequestPayload PayInvoiceRequest request) {
        InvoiceEntity invoiceEntity = invoiceService.payInvoice(request.getInvoiceId());

        PayInvoiceResponse response = new PayInvoiceResponse();
        response.setStatus(invoiceEntity.getStatus());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClientTotalRequest")
    @ResponsePayload
    public GetClientTotalResponse getClientTotal(@RequestPayload GetClientTotalRequest request) {
        BigDecimal total = invoiceService.getClientTotal(request.getClientId());

        GetClientTotalResponse response = new GetClientTotalResponse();
        response.setTotalAmount(total);
        return response;
    }




}
