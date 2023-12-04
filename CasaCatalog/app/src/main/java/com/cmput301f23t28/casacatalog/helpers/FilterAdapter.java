package com.cmput301f23t28.casacatalog.helpers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Filter;
import com.cmput301f23t28.casacatalog.views.FilterActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private List<Filter> dataList;
    Spinner filtertype;
    Spinner filtercomp;

    /**
     * ViewHolder class representing each filter in the RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public Spinner spinner;
//        public TextInputEditText textInput;
        TextInputLayout val1_input;
        TextInputLayout val2_input;
        Filter filter;

        /**
         * Constructor for MyViewHolder.
         * @param v The view representing the item in the RecyclerView.
         */
        public MyViewHolder(View v) {
            super(v);
//            spinner = v.findViewById(v.R.id.spinner);
            val1_input = v.findViewById(R.id.filter_value_1);
            val2_input = v.findViewById(R.id.filter_value_2);

        }

        /**
         * Gets the Filter object associated with this ViewHolder.
         * @return The Filter object containing filter values.
         */
        public Filter getFilter() {
            filter.setVal1(val1_input.getEditText().getText().toString());
            filter.setVal2(val2_input.getEditText().getText().toString());
            return filter;
        }

        /**
         * Binds data to the ViewHolder.
         * @param fil The Filter object containing data to bind.
         */
        public void bindData(Filter fil){
            filter = fil;
        }
    }

    /**
     * Constructor for the FilterAdapter.
     * @param dataList The list of Filter objects to be managed by the adapter.
     */
    public FilterAdapter(List<Filter> dataList) {
        this.dataList = dataList;
    }

    /**
     * Creates a new filter UI object.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given viewType.
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);

        return new MyViewHolder(v);
    }

    /**
     * Binds filter to the View based on the filter type.
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Filter dataObject = dataList.get(position);
        // Set up spinner and text input based on dataObject
        filtertype = holder.itemView.findViewById(R.id.spinner_filter_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.filtertype.getContext(),
                R.array.type_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtertype.setAdapter(adapter);

        holder.itemView.findViewById(R.id.delete_filter_button).setOnClickListener(view -> {
            dataList.remove(position);
            adapter.notifyDataSetChanged();
        });


        List<String> compChoices = new ArrayList<>();
        filtercomp = holder.itemView.findViewById(R.id.spinner_filter_comp);
        ArrayAdapter<String> comp_adapter = new ArrayAdapter<>(this.filtercomp.getContext(),
                        android.R.layout.simple_spinner_item,compChoices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtercomp.setAdapter(comp_adapter);

        filtercomp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called to change UI on filter based on selected parameters.
             *
             * @param parent The AdapterView where the selection happened.
             * @param view The view within the AdapterView that was clicked.
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that is selected.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. Retrieve the selected item using parent.getItemAtPosition(pos)
                String strfiltercomp = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                dataObject.setCurrentFilterType(strfiltercomp);

                comp_adapter.notifyDataSetChanged();

                if (filtertype.getSelectedItem().toString().toLowerCase().equals("date")
                    && strfiltercomp.equals("between")){
                    ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_2)).setVisibility(View.VISIBLE);
                } else{
                    ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_2)).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback

            }
        });

        filtertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called to changed UI for filter based on selected parameters.
             *
             * @param parent The AdapterView where the selection happened.
             * @param view The view within the AdapterView that was clicked.
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that is selected.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. Retrieve the selected item using parent.getItemAtPosition(pos)
                String strfiltertype = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                dataObject.setCurrentType(strfiltertype);
                Log.e("DATE", strfiltertype.toLowerCase() + (strfiltertype.toLowerCase() != "date"));

                String filterType = dataObject.getCurrentType().toString().toLowerCase();
                switch (filterType){
                    case ("date"):
                        comp_adapter.clear();
                        dataObject.setCurrentFilterType("between");
                        comp_adapter.add("between");
                        comp_adapter.add("equals");
                        break;
                    case ("make"):
                        comp_adapter.clear();
                        dataObject.setCurrentFilterType("contains");
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
                    case ("tag"):
                        comp_adapter.clear();
                        dataObject.setCurrentFilterType("contains");
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
                    case("description"):
                        comp_adapter.clear();
                        dataObject.setCurrentFilterType("contains");
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
                    case("value"):
                        comp_adapter.clear();
                        dataObject.setCurrentFilterType("equals");
                        comp_adapter.add("equals");
                        comp_adapter.add("notequals");
                }
                comp_adapter.notifyDataSetChanged();
                if (!strfiltertype.toLowerCase().equals("date")){
                    ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_2)).setVisibility(View.GONE);
                } else{
                    ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_2)).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback

            }
        });



        ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_1)).getEditText().setText(dataObject.getVal1());
        ((TextInputLayout) holder.itemView.findViewById(R.id.filter_value_2)).getEditText().setText(dataObject.getVal2());


        String[] typeChoices =
                holder.itemView.getResources().getStringArray(R.array.type_choices);



        int pos = -1;
        for (int i = 0; i < typeChoices.length; i++) {
            if (typeChoices[i].toLowerCase().equals(dataObject.getCurrentType().toString())) {
                pos = i;
                break;
            }
        }
        ((Spinner) holder.itemView.findViewById(R.id.spinner_filter_type)).setSelection(pos);
        pos = -1;
        for (int i = 0; i < compChoices.size(); i++) {
            if (compChoices.get(i).toLowerCase().equals(dataObject.getCurrentFilterType().toString())) {
                pos = i;
                break;
            }
        }

        holder.bindData(dataObject);
    }

    /**
     * Returns the total number of items in the dataset.
     * @return The total number of items in the dataset.
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
