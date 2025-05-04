package com.annacode.eccomerce.dto;

import com.annacode.eccomerce.entity.Address;
import com.annacode.eccomerce.entity.Customer;
import com.annacode.eccomerce.entity.Order;
import com.annacode.eccomerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase  {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
