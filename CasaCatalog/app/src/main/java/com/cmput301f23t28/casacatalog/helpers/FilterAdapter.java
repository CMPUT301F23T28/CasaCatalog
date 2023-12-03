package com.cmput301f23t28.casacatalog.helpers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.helpers.Filter;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
import com.cmput301f23t28.casacatalog.views.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private List<Filter> dataList;
    Spinner filtertype;
    Spinner filtercomp;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public Spinner spinner;
//        public TextInputEditText textInput;

        public MyViewHolder(View v) {
            super(v);
//            spinner = v.findViewById(v.R.id.spinner);
//            textInput = v.findViewById(v.R.id.textInputEditText);
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

        filtertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. Retrieve the selected item using parent.getItemAtPosition(pos)
                String strfiltertype = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback

            }
        });


        filtercomp = holder.itemView.findViewById(R.id.spinner_filter_comp);
        ArrayAdapter<CharSequence> comp_adapter =
                ArrayAdapter.createFromResource(this.filtercomp.getContext(),
                R.array.filter_comp_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtercomp.setAdapter(comp_adapter);

        filtercomp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. Retrieve the selected item using parent.getItemAtPosition(pos)
                String strfiltercomp = parent.getItemAtPosition(position).toString();
                // Do something with the selected item

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
