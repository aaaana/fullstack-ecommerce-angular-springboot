package com.annacode.eccomerce.service;

import com.annacode.eccomerce.dto.PaymentInfo;
import com.annacode.eccomerce.dto.Purchase;
import com.annacode.eccomerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;


public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
