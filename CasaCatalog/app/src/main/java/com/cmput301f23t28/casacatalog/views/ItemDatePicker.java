package com.cmput301f23t28.casacatalog.views;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.TextView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItemDatePicker implements View.OnClickListener {

    private Item newItem;
    private TextView dateText;
    public ItemDatePicker(Item item, TextView datePreview){
        this.newItem = item;
        this.dateText = datePreview;
    }

    @Override
    public void onClick(View v) {
        LocalDate selectedDate = newItem.getDate();
        DatePickerDialog dialog = new DatePickerDialog(this.dateText.getContext(), android.R.style.Theme_Material_Dialog_Alert, (picker, year, month, dayOfMonth) -> {
            newItem.setDate(LocalDate.of(year, month+1, dayOfMonth));
            dateText.setText(newItem.getFormattedDate());
        }, selectedDate.getYear(), selectedDate.getMonth().getValue(), selectedDate.getDayOfMonth());
        dialog.show();
    }
}
