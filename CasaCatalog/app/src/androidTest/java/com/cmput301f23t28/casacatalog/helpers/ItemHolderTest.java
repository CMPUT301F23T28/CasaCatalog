package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.apps.common.testing.accessibility.framework.utils.contrast.Color;

import junit.framework.TestCase;

import org.junit.Before;
import static org.mockito.Mockito.mock;


public class ItemHolderTest extends TestCase {

    private View view;
    private ItemListClickListener clickListener;
    @Override @Before
    public void setUp() throws Exception {
        super.setUp();
        view = mock(View.class);
        clickListener = mock(ItemListClickListener.class);
    }

    public void testSetSelectedStyle() {
        ItemHolder holder = new ItemHolder(view, clickListener);
        holder.setSelectedStyle(Color.BLACK);

        assertEquals(Color.BLACK, holder.getSelectedStyle());
    }

    public void testOnLongClick() {
    }

    public void testOnClick() {
    }

}