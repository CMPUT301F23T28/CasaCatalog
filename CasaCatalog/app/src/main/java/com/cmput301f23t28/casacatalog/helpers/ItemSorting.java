package com.cmput301f23t28.casacatalog.helpers;

import android.util.Log;

import com.cmput301f23t28.casacatalog.models.Item;

import java.util.Comparator;

/**
 * A helper class that associates string inputs for sorting type and order into enums,
 * as well as provides a comparator that compares item properties for sorting.
 */
public class ItemSorting {
    public enum Type { date, description, make, value, tag }
    public enum Order { ascending, descending }
    private Type currentType;
    private Order currentOrder;

    /**
     * Given strings for what to sort by and in what order,
     * prepares an item sorting.
     * @param type A String name for the item property to sort by
     * @param order A String sorting order, either "ascending" or "descending"
     */
    public ItemSorting(String type, String order){
        for(Type t : Type.values()){
            if( type.equalsIgnoreCase(t.name()) ) currentType = t;
        }
        for(Order o : Order.values()){
            if( order.equalsIgnoreCase(o.name()) ) currentOrder = o;
        }
    }

    /**
     * Prepares an item sorting that, by default,
     * sorts by date in descending order.
     */
    public ItemSorting(){
        this.currentType = Type.date;
        this.currentOrder = Order.descending;
    }

    /**
     * Generates an Item comparator that sorts based on the currentType and currentOrder.
     * Use this as a parameter for ArrayList#sort in order to sort Items correctly.
     * @return A comparator for Items
     */
    public Comparator<Item> getComparator() {
        Log.i("SORTER", "Sorting by '" + this.currentType.name() + "' in '" + this.currentOrder.name() + "' order.");
        return (a, b) -> {
            switch (this.currentType) {
                case date:
                    if (a.getDate() != null && b.getDate() != null &&
                            !a.getDate().equals(b.getDate())) {
                        return a.getDate().after(b.getDate()) ? -1 : 1;
                    }
                case description:
                    if (a.getDescription() != null && b.getDescription() != null &&
                            !a.getDescription().equals(b.getDescription())) {
                        return a.getDescription().compareTo(b.getDescription()) < 0 ? -1 : 1;
                    }
                case make:
                    if (a.getMake() != null && b.getMake() != null &&
                            !a.getMake().equals(b.getMake())) {
                        return a.getMake().compareTo(b.getMake()) < 0 ? -1 : 1;
                    }
                case value:
                    if (a.getPrice() != null && b.getPrice() != null &&
                            !a.getPrice().equals(b.getPrice())) {
                        return a.getPrice() < b.getPrice() ? -1 : 1;
                    }
                case tag:
                    // TODO: implement this correctly
                    if (a.getTags() != null && b.getTags() != null &&
                            !a.getTags().equals(b.getTags())) {
                        return 1;
                    }
                default:
                    return 0;
            }
        };
    }
}
