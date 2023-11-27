package com.cmput301f23t28.casacatalog.models;
public class Photo {
    String url;
    Boolean isSelected = false;
    public Photo(String url) {
        this.url = url;
    }

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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
