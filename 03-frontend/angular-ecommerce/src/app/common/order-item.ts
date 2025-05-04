import { CartItem } from './cart-item';

export class OrderItem {
  imgUrl!: string;
  unitPirce!: number;
  quantity!: number;
  productId!: string;

  constructor(cartItem: CartItem) {
    this.imgUrl = cartItem.imgUrl;
    this.unitPirce = cartItem.unitPrice;
    this.quantity = cartItem.quantity;
    this.productId = cartItem.id;
  }
}
