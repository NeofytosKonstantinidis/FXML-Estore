package sample.listeners;

import sample.controller.OrderItemController;

public interface OrderItemListener {
    public void orderClicked(int id, OrderItemController orderItemController);
}
