package com.cmput301f23t28.casacatalog.Camera;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f23t28.casacatalog.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * AsyncTask for fetching product details based on a barcode number using UPCitemdb API.
 */
public class FetchProductDetails extends AsyncTask<String, Void, Item> {

    private String barcodeNumber;
    private Item fakeItem;
    private FetchProductDetailsCallback callback;

    /**
     * Constructor for FetchProductDetails.
     *
     * @param barcodeNumber The barcode number to lookup in the UPCitemdb.
     * @param newItem The new item
     */
    public FetchProductDetails(String barcodeNumber, Item newItem, FetchProductDetailsCallback callback) {
        this.barcodeNumber = barcodeNumber;
        this.fakeItem = newItem;
        this.callback = callback;
    }

    /**
     * Performs the network request in the background.
     * @param params Parameters of the task. Not used in this implementation.
     * @return The response from the UPCitemdb API as a String.
     */
    @Override
    protected Item doInBackground(String... params) {
        String requestUrl = "https://api.upcitemdb.com/prod/trial/lookup?upc=" + barcodeNumber;

        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("ProductInfo", "HTTP error code: " + responseCode);
                return null;
            }

            InputStream in = conn.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            String result =  scanner.hasNext() ? scanner.next() : null;

            if (result != null) {
                fakeItem = putResultIntoItem(result);
                return fakeItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }


    /**
     * Processes the API response after the network request is complete.
     * Parses the JSON response and logs the product name.
     * @param newItem Item filled from JSON response.
     */
    @Override
    protected void onPostExecute(Item newItem) {
        super.onPostExecute(newItem);

        if (newItem != null) {
            callback.onDetailsFetched(newItem);
        } else {
            callback.onDetailsFetchFailed("Failed to fetch details");
        }
    }

    /**
     * Parses the JSON response from the UPCitemdb API and populates an Item object with the retrieved details.
     *
     * @param result The JSON response from the UPCitemdb API.
     * @return The Item object populated with details from the API response.
     */
    private Item putResultIntoItem(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray items = jsonResponse.getJSONArray("items");
            if (items.length() > 0) {
                JSONObject item = items.getJSONObject(0);
                String productName = item.optString("title");
                Double productValue = Double.valueOf(item.optString("highest_recorded_price"));
                String productDesc = item.optString("description");
                String productMake = item.optString("brand");
                String productModel = item.optString("model");
                fakeItem.setName(productName);
                fakeItem.setPrice(productValue);
                fakeItem.setDescription(productDesc);
                fakeItem.setMake(productMake);
                fakeItem.setModel(productModel);
                return fakeItem;

                //Log.d("ProductInfo", "Product Name: " + productName);
            } else {
                Log.d("ProductInfo", "No products found.");
                return new Item();
            }
        } catch (JSONException e) {
            Log.e("ProductInfo", "Error parsing JSON", e);
        }
        return null;
    }

    /**
     * Callback interface for handling the results of the FetchProductDetails AsyncTask.
     */
    public interface FetchProductDetailsCallback {
        /**
         * Called when product details are successfully fetched.
         *
         * @param newItem The Item object containing the fetched details.
         */
        void onDetailsFetched(Item newItem);

        /**
         * Called when an error occurs while fetching product details.
         *
         * @param errorMessage A message describing the error.
         */
        void onDetailsFetchFailed(String errorMessage);
    }
}
