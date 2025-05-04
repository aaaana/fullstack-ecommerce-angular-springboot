package com.annacode.eccomerce.service;

import com.annacode.eccomerce.dao.CustomerRepository;
import com.annacode.eccomerce.dto.PaymentInfo;
import com.annacode.eccomerce.dto.Purchase;
import com.annacode.eccomerce.dto.PurchaseResponse;
import com.annacode.eccomerce.entity.Customer;
import com.annacode.eccomerce.entity.Order;
import com.annacode.eccomerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;


    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey){
        this.customerRepository = customerRepository;


        // initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase){

        // Retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //Populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // Populate order with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail((theEmail));
if(customerFromDB !=null){

    // we found them  assign them accordingly
    customer = customerFromDB;
}

        customer.add(order);

        //save to the database
        customerRepository.save(customer);


        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String,Object> params = new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description","AnnaShop purchase");
        params.put("receipt_email",paymentInfo.getReceiptEmail());


        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {

        // Generate a random UUID number(UUID version-4)
        // For details see....online source
        return UUID.randomUUID().toString();
    }
}





