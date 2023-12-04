package com.cmput301f23t28.casacatalog.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;

/**
 * Represents a tag that can be associated with an item. This class implements Serializable,
 * allowing tag objects to be serialized for easy storage and transmission.
 */
public class Tag implements Comparable<Tag>, Parcelable {

    private final String name;
    private int uses;

    /**
     * Constructs a Tag with the specified name and initializes its use count to 1.
     *
     * @param name The name of the tag.
     */
    public Tag(String name) {
        this.name = name;
        this.uses = 0;
    }

    /**
     * Gets the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the number of uses of the tag.
     * Note: This method is currently not in use and is intended as a quality of life feature for future implementation.
     *
     * @return The number of times the tag has been used.
     */
    public int getUses() {
        return this.uses;
    }

    /**
     * Sets the number of uses of the tag.
     * Note: This feature is currently out of scope and is intended for future quality of life enhancements.
     *
     * @param uses The number of uses to set for the tag.
     */
    public void setUses(int uses) {
        this.uses = uses;
    }

    // Parcelable implementations

    /**
     * Creates parcelable object using passed data
     */
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };


    /**
     * A description of the contents of the Parcelable
     * @return Zero
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Special Tag constructor used by Parcelable logic only
     * @param in A parcel containing the Tag data
     */
    protected Tag(Parcel in) {
        name = in.readString();
        uses = in.readInt();
    }

    /**
     * Defines which values should be serialized into the parceable
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.uses);
    }

    @Override
    public int compareTo(Tag a) {
        return Integer.compare(a.getUses(), this.getUses());
    }
}