# Payment System Documentation

This directory contains comprehensive documentation for the GharSaathi payment system, including Khalti integration, payment processing workflows, and verification procedures.

## Available Documentation

### Core Documentation
- **[Payment Module Documentation](./payment-module-documentation.md)** - Complete payment system architecture, features, and API reference
- **[Payment Quick Start](./payment-quick-start.md)** - Getting started guide for developers integrating payment functionality
- **[Payment Integration Verification](./payment-verification.md)** - Testing procedures and verification checklist

## Payment System Overview

The GharSaathi payment system provides:
- **Khalti Integration** - Nepal's leading digital payment gateway
- **Rent Payment Processing** - Automated monthly rent collection
- **Security Deposit Handling** - Deposit collection and refund management
- **Transaction History** - Complete payment records and receipts
- **Payment Notifications** - Automated reminders and confirmations
- **Refund Processing** - Security deposit refund workflow

## Key Features

### For Tenants
- One-click rent payment via Khalti
- Payment history and receipts
- Automated payment reminders
- Multiple payment methods (wallet, bank, card)

### For Landlords
- Real-time payment notifications
- Transaction dashboard
- Automated rent collection
- Security deposit management

### For Admins
- Complete transaction oversight
- Payment analytics
- Dispute resolution tools
- Financial reporting

## Integration Resources

### Khalti API Integration
```java
// Initialize payment
PaymentInitiateRequest request = PaymentInitiateRequest.builder()
    .amount(amount)
    .purchaseOrderId(transactionId)
    .returnUrl(KHALTI_RETURN_URL)
    .build();
```

### Environment Configuration
```properties
# Khalti Configuration
khalti.api.url=https://a.khalti.com/api/v2/
khalti.secret.key=${KHALTI_SECRET_KEY}
khalti.public.key=${KHALTI_PUBLIC_KEY}
```

## Testing & Verification

Refer to [payment-verification.md](./payment-verification.md) for:
- Test credentials
- Sandbox environment setup
- Verification checklist
- Common issues and solutions

## Related Documentation
- [Implementation Summary](../implementations/payment-implementation.md) - Technical implementation details
- [Module Plan](../../01-requirements/modules/payment-system-plan.md) - Original planning document
- [API Overview](../api-overview.md) - Complete API reference
- [Security](../../05-security/) - Payment security measures

## Quick Links
- [Khalti Developer Docs](https://docs.khalti.com/)
- [Khalti Sandbox](https://test-admin.khalti.com/)
- [Payment API Endpoints](./payment-module-documentation.md#api-endpoints)

---
[← Back to API Documentation](../README.md) | [Home](../../README.md)
