package com.cmput301f23t28.casacatalog.helpers;

import android.content.res.Resources;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301f23t28.casacatalog.R;

public class NonEmptyInputWatcher implements TextWatcher {

    private EditText input;
    private Button button;

    /**
     * A TextWatcher that only checks for the input being non-empty.
     * @param input The EditText that must be non-empty.
     * @param submitButton A button that is disabled if the input is empty.
     */
    public NonEmptyInputWatcher(EditText input, Button submitButton){
        this.input = input;
        this.button = submitButton;
    }


    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setEnabled(false);

        if( s.toString().trim().isEmpty() ){
            input.setError("This field is required.");
        }else{
            button.setEnabled(true);
        }
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override public void afterTextChanged(Editable s) {}
}
