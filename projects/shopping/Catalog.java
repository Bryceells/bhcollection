// Code Written by Bryce Hagen

import java.util.*;

public class Catalog {
    private String name;
    private ArrayList<Item> items;

    // Constructor that takes the name of this catalog as a parameter
    public Catalog(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    // Adds an Item at the end of this list
    public void add(Item next) {
        items.add(next);
    }

    // Returns the number of items in this list
    public int size() {
        return items.size();
    }

    // Returns the Item with the given index (0-based)
    public Item get(int index) {
        return items.get(index);
    }

    // Returns the name of this catalog
    public String getName() {
        return name;
    }
}
