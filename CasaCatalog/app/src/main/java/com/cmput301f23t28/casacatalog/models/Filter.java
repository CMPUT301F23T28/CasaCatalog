package com.cmput301f23t28.casacatalog.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class Filter implements Parcelable {
    public enum Type { date, description, make, value, tag }
    public enum FilterType {equals, notequals, contains, notcontains, lessthan, greaterthan,
        between, notbetween}
    private Filter.Type currentType;
    private Filter.FilterType currentFilterType;
    private String val1;
    private String val2;

    public Type getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String type) {
        for(Filter.Type t : Filter.Type.values()){
            if( type.equalsIgnoreCase(t.name()) ) this.currentType = t;
        }
    }

    public FilterType getCurrentFilterType() {
        return currentFilterType;
    }

    public void setCurrentFilterType(String filterType) {
        for(Filter.FilterType o : Filter.FilterType.values()){
            if( filterType.equalsIgnoreCase(o.name()) ) currentFilterType = o;
        }
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    /**
     * Given strings for what to sort by and in what order,
     * prepares an item sorting.
     * @param type A String name for the item property to sort by
     * @param order A String sorting order, either "ascending" or "descending"
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
     * Prepares an item sorting that, by default,
     * sorts by date in descending order.
     */
    public Filter(){
        this.val1 = "";
        this.val2 = "";
        this.currentType = Type.date;
        this.currentFilterType = FilterType.between;
    }
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

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        // TODO Auto-generated method stub
        dest.writeString(val1);
        dest.writeString(val2);
        dest.writeString(currentType.name());
        dest.writeString(currentFilterType.name());

    }

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

    public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}
