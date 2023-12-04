package com.cmput301f23t28.casacatalog.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Photos are used with an Items photo collection
 */
public class Photo implements Parcelable {
    String url;
    Boolean isSelected = false;
    public Photo(String url) {
        this.url = url;
    }

    protected Photo(Parcel in) {
        url = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
    }

    /**
     *
     * @return returns the selected status of the photo
     */
    public Boolean getSelected() {
        return isSelected;
    }

    /**
     * Sets the selection status of the item.
     * @param selected A Boolean containing the new selection status of the item.
     */
    public void setSelected(Boolean selected) {
        this.isSelected = selected;
    }

    /**
     * Toggles the selected state.
     */
    public void toggleSelected() {
        this.isSelected = !this.isSelected;
    }

    /**
     *
     * @return gets the URL of the photo
     */
    public String getUrl() {
        return url;
    }

    /**
     * Magic code to implement the parcelable interface.
     */
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    /**
     * Parcelable implementation details
     * @return parcelable contents return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable implementation details
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
    }
}
