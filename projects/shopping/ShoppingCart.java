// Code Written by Bryce Hagen

import java.util.*;

public class ShoppingCart {

    public static final double DISCOUNT_PERCENT = 0.9;
    private ArrayList<ItemOrder> orders;
    private boolean discount;

    // Constructor that creates an empty list of item orders
    public ShoppingCart() {
        orders = new ArrayList<>();
        discount = false;
    }

    // Adds an item order to the list, replacing any previous order for this item
    public void add(ItemOrder next) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getItem().equals(next.getItem())) {
                orders.set(i, next);
                return;
            }
        }
        orders.add(next);
    }

    // Sets whether or not this order gets a discount
    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    // Returns the total cost of the shopping cart
    public double getTotal() {
        double total = 0.0;
        for (ItemOrder order : orders) {
            total += order.getPrice();
        }
        if (discount) {
            total *= DISCOUNT_PERCENT;
        }
        return total;
    }
}