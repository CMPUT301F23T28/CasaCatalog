package com.cmput301f23t28.casacatalog.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;

public class SquaredImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquaredImageView(@NonNull Context context) {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }
}
