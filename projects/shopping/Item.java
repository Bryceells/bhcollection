// Code Written by Bryce Hagen

import java.text.*;

public class Item {
    // Instance variables
    private String name;
    private double price;
    private int bulkQuantity;
    private double bulkPrice;
    private boolean hasBulkPrice;
    private NumberFormat formatter;

    // Constructor for single item price
    public Item(String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.bulkQuantity = 0;
        this.bulkPrice = 0.0;
        this.hasBulkPrice = false;
        this.formatter = NumberFormat.getCurrencyInstance();
    }

    // Constructor for bulk pricing
    public Item(String name, double price, int bulkQuantity, double bulkPrice) {
        this(name, price);
        if (bulkQuantity < 0 || bulkPrice < 0) {
            throw new IllegalArgumentException("Bulk quantity and bulk price cannot be negative");
        }
        this.bulkQuantity = bulkQuantity;
        this.bulkPrice = bulkPrice;
        this.hasBulkPrice = true;
    }

    // Method to calculate price for a given quantity
    public double priceFor(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (hasBulkPrice && quantity >= bulkQuantity) {
            int bulkCount = quantity / bulkQuantity;
            int remaining = quantity % bulkQuantity;
            return (bulkCount * bulkPrice) + (remaining * price);
        } else {
            return quantity * price;
        }
    }

    // Method to return a string representation of the item
    @Override
    public String toString() {
        String itemString = name + ", " + formatter.format(price);
        if (hasBulkPrice) {
            itemString += " (" + bulkQuantity + " for " + formatter.format(bulkPrice) + ")";
        }
        return itemString;
    }

    // Overriding equals method to compare items based on name and price
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Item item = (Item) obj;
        return Double.compare(item.price, price) == 0 &&
                name.equals(item.name);
    }
}