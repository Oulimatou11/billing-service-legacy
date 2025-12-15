# Billing SOAP Web Service

## Description
Ce service SOAP permet l’exposition de fonctionnalités de facturation via des Web Services SOAP.

Les opérations proposées incluent :
- `Création de facture`
- `Consulter les details d'une facture` 
- `Liste des factures d'un client donné`
- `Paiement de facture ( Mettre à jour le status d'une facture )`
- `Calculer le total facturé pour un client`

## Technologies
- `Java`
- `SOAP Web Services`
- `WSDL`
- `JAXB`

## Accès au service
- WSDL :  
  `http://localhost:8080/service/billing.wsdl`

## Opérations SOAP
- `createInvoice`
- `getInvoice`
- `getClientInvoices`
- `payInvoice`
- `getClientTotal`

## Objectif académique
Démontrer la différence entre les architectures REST et SOAP
dans un contexte d’intégration de services.
