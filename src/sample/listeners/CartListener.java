package sample.listeners;

import sample.controller.CartItemController;
import sample.model.Item;

public interface CartListener {
    public void addCartQuantity(Item item, CartItemController cartItemController);
    public void reduceCartQuantity(Item item, CartItemController cartItemController);
    public void removeCartItem(Item item);
}
