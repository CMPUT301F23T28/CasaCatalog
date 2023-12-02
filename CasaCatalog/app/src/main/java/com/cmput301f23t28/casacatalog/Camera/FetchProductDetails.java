package com.cmput301f23t28.casacatalog.Camera;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class FetchProductDetails extends AsyncTask<String, Void, String> {

    private String barcodeNumber;


    public FetchProductDetails(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    @Override
    protected String doInBackground(String... params) {
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

            return scanner.hasNext() ? scanner.next() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            Log.e("ProductInfo", "No response from the server or error occurred");
            return;
        }

        try {
            JSONObject jsonResponse = new JSONObject(result);
            // Parsing logic might change depending on UPCitemdb's response structure
            JSONArray items = jsonResponse.getJSONArray("items");
            if (items.length() > 0) {
                JSONObject item = items.getJSONObject(0);
                String productName = item.optString("title"); // Assuming the field name is "title"

                // Log the productName in Logcat
                Log.d("ProductInfo", "Product Name: " + productName);
            } else {
                Log.d("ProductInfo", "No products found.");
            }
        } catch (JSONException e) {
            Log.e("ProductInfo", "Error parsing JSON", e);
        }
    }
}
