// Code Written by Bryce Hagen

public class ItemOrder {
    private Item item;
    private int quantity;

    // Constructor that creates an item order for the given item and given quantity
    public ItemOrder(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // Returns the cost for this item order
    public double getPrice() {
        return item.priceFor(quantity);
    }

    // Returns a reference to the item in this order
    public Item getItem() {
        return item;
    }
}