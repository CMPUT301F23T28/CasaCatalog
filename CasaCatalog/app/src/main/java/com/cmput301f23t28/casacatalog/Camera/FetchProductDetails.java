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

    /**
     * Constructor for FetchProductDetails.
     *
     * @param barcodeNumber The barcode number to lookup in the UPCitemdb.
     * @param newItem
     */
    public FetchProductDetails(String barcodeNumber, Item newItem) {
        this.barcodeNumber = barcodeNumber;
        this.fakeItem = newItem;
    }

    /**
     * Performs the network request in the background.
     * @param params Parameters of the task. Not used in this implementation.
     * @return The response from the UPCitemdb API as a String.
     */
    @Override
    protected Item doInBackground(String... params) {
        String apiKey = "YOUR_UPCITEMDB_API_KEY"; // Replace with your actual API key
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
     * @param result The JSON response string from the API.
     */
    /*
    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            Log.e("ProductInfo", "No response from the server or error occurred");
            return;
        }
        putResultIntoItem(result);
    }
     */

    private Item putResultIntoItem(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray items = jsonResponse.getJSONArray("items");
            if (items.length() > 0) {
                JSONObject item = items.getJSONObject(0);
                String productName = item.optString("title"); // Assuming the field name is "title"
                Double productValue = item.optDouble("value"); // Guessing it's called value i have no idea
                fakeItem.setName(productName);
                fakeItem.setPrice(productValue);
                // TODO: add whatever params this will return
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
}
