package com.cmput301f23t28.casacatalog.helpers;

import com.cmput301f23t28.casacatalog.models.Item;

import java.util.Comparator;

public class ItemSorting {
    private enum Type { date, description, make, value, tag }
    private enum Order { ascending, descending }
    private Type currentType = Type.date;
    private Order currentOrder = Order.ascending;

    /**
     *
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

    public Comparator<Item> getComparator(){
        // use switch
        return (a, b) -> {
            if (a.getPrice() == b.getPrice()) return 0;
            return a.getPrice() < b.getPrice() ? -1 : 1;
        };
    }
}
