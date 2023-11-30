package com.cmput301f23t28.casacatalog.helpers;

import com.cmput301f23t28.casacatalog.models.Item;

import java.util.function.Predicate;

public class Filter {
    public enum Type { date, description, make, value, tag }
    public enum Order { ascending, descending }
    private ItemSorting.Type currentType;
    private ItemSorting.Order currentOrder;

    /**
     * Given strings for what to sort by and in what order,
     * prepares an item sorting.
     * @param type A String name for the item property to sort by
     * @param order A String sorting order, either "ascending" or "descending"
     */
    public Filter(String type, String order){
        for(ItemSorting.Type t : ItemSorting.Type.values()){
            if( type.equalsIgnoreCase(t.name()) ) currentType = t;
        }
        for(ItemSorting.Order o : ItemSorting.Order.values()){
            if( order.equalsIgnoreCase(o.name()) ) currentOrder = o;
        }
    }

    /**
     * Prepares an item sorting that, by default,
     * sorts by date in descending order.
     */
    public Filter(){
        this.currentType = ItemSorting.Type.date;
        this.currentOrder = ItemSorting.Order.descending;
    }
//    public Predicate<Item> getFilterPredicate(){
//    }
}
