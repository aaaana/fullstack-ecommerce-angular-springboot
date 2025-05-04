import { Customer } from './customer';
import { OrderItem } from './order-item';
import { Order } from './order';
import { Address } from './customerAddress';

export class Purchase {
  customer!: Customer;
  shippingAddress!: Address;
  billingAddress!: Address;
  order!: Order;
  orderItems!: OrderItem[];
}
