package com.cmput301f23t28.casacatalog.helpers;

import com.cmput301f23t28.casacatalog.models.Item;

public interface BarcodeCallback {
    void onBarcodeScanned(Item newItem);
    void onBarcodeScanFailed(String errorMessage);
}
