package com.cmput301f23t28.casacatalog.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Represents a filter object used for filtering items.
 */
public class Filter implements Parcelable {
    public enum Type { date, description, make, value, tag }
    public enum FilterType {equals, notequals, contains, notcontains, lessthan, greaterthan,
        between, notbetween}
    private Filter.Type currentType;
    private Filter.FilterType currentFilterType;
    private String val1;
    private String val2;

    /**
     *
     * @return returns the current type of filter
     */
    public Type getCurrentType() {
        return currentType;
    }

    /**
     * Sets the current type of filter
     * @param type an enum of filter
     */
    public void setCurrentType(String type) {
        for(Filter.Type t : Filter.Type.values()){
            if( type.equalsIgnoreCase(t.name()) ) this.currentType = t;
        }
    }

    /**
     *
     * @return Gets the type of operation the filter is performing
     */
    public FilterType getCurrentFilterType() {
        return currentFilterType;
    }

    /**
     * Sets the type of operation for the filter
     * @param filterType type of operation enum
     */
    public void setCurrentFilterType(String filterType) {
        for(Filter.FilterType o : Filter.FilterType.values()){
            if( filterType.equalsIgnoreCase(o.name()) ) currentFilterType = o;
        }
    }

    /**
     *
     * @return val of first operand
     */
    public String getVal1() {
        return val1;
    }

    /**
     * Sets the value of the first operand
     * @param val1 the value to set
     */
    public void setVal1(String val1) {
        this.val1 = val1;
    }

    /**
     *
     * @return gets the value of the second operand
     */
    public String getVal2() {
        return val2;
    }

    /**
     * sets the value of the second operand
     * @param val2 the value to set
     */
    public void setVal2(String val2) {
        this.val2 = val2;
    }

    /**
     * Given strings for what to filter by and in what way,
     * prepares an item filtering.
     * @param type A String name for the item property to filter by
     * @param order A String for the operation to be done
     * @param val1 the first operand
     * @param val2 the second operand
     */
    public Filter(String type, String order, String val1, String val2){
        this.val1 = val1;
        this.val2 = val2;
        for(Filter.Type t : Filter.Type.values()){
            if( type.equalsIgnoreCase(t.name()) ) currentType = t;
        }
        for(Filter.FilterType o : Filter.FilterType.values()){
            if( order.equalsIgnoreCase(o.name()) ) currentFilterType = o;
        }
    }

    /**
     * Prepares an item filtering that, by default,
     * filters between two dates
     */
    public Filter(){
        this.val1 = "";
        this.val2 = "";
        this.currentType = Type.date;
        this.currentFilterType = FilterType.between;
    }

    /**
     * Gets a predicate to filter items based on the current filter settings.
     * @return A predicate for filtering items.
     */
    public Predicate<Item> getFilterPredicate(){
                Item item = new Item();
                switch (this.currentType) {
                    case date:
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate startDate = LocalDate.parse(val1, formatter);
                        if (this.currentFilterType == FilterType.between) {
                            LocalDate endDate = LocalDate.parse(val2, formatter);
                            return x -> !x.getDate().isBefore(startDate) && !x.getDate().isAfter(endDate);
                        } else if (this.currentFilterType == FilterType.equals) {
                            return x -> x.getDate().isEqual(startDate);
                        }

                    case description:
                        if (this.currentFilterType == FilterType.contains){
                            return x -> x.getDescription().contains(val1);
                        } else if (this.currentFilterType == FilterType.notcontains){
                            return x -> !x.getDescription().contains(val1);
                        }
                    case make:
                        if (this.currentFilterType == FilterType.contains){
                            return x -> x.getMake().contains(val1);
                        }else if (this.currentFilterType == FilterType.notcontains){
                            return x -> !x.getMake().contains(val1);
                        }
                    case value:
                        final Double item_value;
                        try{
                            item_value = Double.parseDouble(val1);
                        }catch (NumberFormatException e){
                            Log.e("FILTER", "item value/price is not a double " + val1);
                            return Predicate.isEqual(0);
                        }
                        if (this.currentFilterType == FilterType.equals){
//                            item_value = 123.0;
                            return x -> (x.getPrice() == item_value.doubleValue());
                        } else if (this.currentFilterType == FilterType.notequals){
                            return x -> x.getPrice() != item_value.doubleValue();
                        }
                    case tag:
                        Log.i("PREDICATE","Tag is equal to " + val1);
                        if (this.currentFilterType == FilterType.contains){
                            return x -> x.getTagsAsStrings().contains(val1);
                        } else if (this.currentFilterType == FilterType.notcontains){
                            return x -> !x.getTagsAsStrings().contains(val1);
                        }

            };
                Log.e("PREDICATE","Failed to return a predicate");
            return Predicate.isEqual(0);
    }

    /**
     * override function for parcelable
     * @return
     */
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * override function for parcelable
     * @param dest The Parcel in which the object should be written.
     * @param arg1 Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        // TODO Auto-generated method stub
        dest.writeString(val1);
        dest.writeString(val2);
        dest.writeString(currentType.name());
        dest.writeString(currentFilterType.name());

    }

    /**
     * override function for parcelable
     * @param in
     */
    public Filter(Parcel in) {
        val1 = in.readString();
        val2 = in.readString();

        String strcurrentType = in.readString();
        String strcurrentFilterType = in.readString();
        for(Filter.Type t : Filter.Type.values()){
            if( strcurrentType.equalsIgnoreCase(t.name()) ) currentType = t;
        }
        for(Filter.FilterType o : Filter.FilterType.values()){
            if( strcurrentFilterType.equalsIgnoreCase(o.name()) ) currentFilterType = o;
        }
    }

    /**
     * override function for parcelable
     */
    public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}
