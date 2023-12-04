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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private List<Filter> dataList;
    Spinner filtertype;
    Spinner filtercomp;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public Spinner spinner;
//        public TextInputEditText textInput;
        TextInputLayout val1_input;
        TextInputLayout val2_input;
        Filter filter;

        public MyViewHolder(View v) {
            super(v);
//            spinner = v.findViewById(v.R.id.spinner);
            val1_input = v.findViewById(R.id.filter_value_1);
            val2_input = v.findViewById(R.id.filter_value_2);

        }

        public Filter getFilter() {
            filter.setVal1(val1_input.getEditText().getText().toString());
            filter.setVal2(val2_input.getEditText().getText().toString());
            return filter;
        }

        public void bindData(Filter fil){
            filter = fil;
        }
    }

    public FilterAdapter(List<Filter> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);

        return new MyViewHolder(v);
    }

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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. Retrieve the selected item using parent.getItemAtPosition(pos)
                String strfiltertype = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                dataObject.setCurrentType(strfiltertype);
                Log.e("DATE", strfiltertype.toLowerCase() + (strfiltertype.toLowerCase() != "date"));

                switch (dataObject.getCurrentType().toString().toLowerCase()){
                    case ("date"):
                        comp_adapter.clear();
                        comp_adapter.add("between");
                        comp_adapter.add("equals");
                        break;
                    case ("make"):
                        comp_adapter.clear();
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
                    case ("tags"):
                        comp_adapter.clear();
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
                    case("description"):
                        comp_adapter.clear();
                        comp_adapter.add("contains");
                        comp_adapter.add("notcontains");
                        break;
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
