package com.example.ead_assignment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class VolleyRequest {

    // Set static fields for routing
    public static String GET_BOOKINGS = "/user/Reservation";
    public static String MAKE_RESERVATION = "/user/Reservation";
    public static String CANCEL_RESERVATION = "/user/CancelReservation";
    public static String GET_HISTORY = "/user/History";
    public static String SIGNUP = "/user";
    public static String LOGIN = "/user/login";
    public static String MANAGE_PROFILE = "/user/updateTrveller";
    public static String GET_SCHEDULES = "/user/Train";
    public static String BASE_URL = "http://192.168.0.100:81/api";
//    public static String BASE_URL = "http://192.168.8.101:8082/api";

    // Send post request to the server
    public void sendData(Context context, String URL, JSONObject jsonBody, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        CompletableFuture<String> future = new CompletableFuture<>();
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, BASE_URL + URL, responseListener, errorListener
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    // Return the response body
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//                    responseString = String.valueOf(response.statusCode);
//                    // can get more details such as response.headers
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }
        };

        requestQueue.add(stringRequest);
    }

    // Send update request to the server
    public void updateData(Context context, String URL, Map<String, String> queryParams, JSONObject requestData,
                           Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        try {
            // Encode the query parameters
            StringBuilder urlWithParams = new StringBuilder(BASE_URL + URL);
            // Set query parameters
            if (queryParams != null && !queryParams.isEmpty()) {
                urlWithParams.append("?");
                for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                    String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                    String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                    urlWithParams.append(key).append("=").append(value).append("&");
                }
                // Remove the trailing "&"
                urlWithParams.setLength(urlWithParams.length() - 1);
            }

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(
                    Request.Method.PUT,
                    urlWithParams.toString(),
                    responseListener,
                    errorListener
            ) {
                @Override
                public byte[] getBody() {
                    // Set the request body with the JSON data
                    return requestData.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            // Add the request to the queue
            requestQueue.add(stringRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle encoding exceptions
        }
    }

    // Send get request to the server
    public void getData(Context context, String URL, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
//        CompletableFuture<String> future = new CompletableFuture<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(new StringRequest(
                Request.Method.GET,
                BASE_URL + URL,
                responseListener,
                errorListener
        ));
        System.out.println(BASE_URL + URL);
    }

    // Get saved data in embedded database
    public String getSQLiteDBData(Context context, String column) {
        String[] projection = {column};
        try {
            SQLiteDBHelper SQLiteDbHelper = new SQLiteDBHelper(context);
            SQLiteDatabase db = SQLiteDbHelper.getWritableDatabase();
            Cursor cursor = db.query("User", projection, null, null, null, null, null);
            if (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(column));
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {

        } catch (Exception e) {

        }
        return null;
    }

    // Update saved data in embedded database
    public String updateSQLiteDBData(Context context, String column, String response) {
        try {
            SQLiteDBHelper dbHelper = new SQLiteDBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(column, response);
            String reply = getSQLiteDBData(context, "user_profile");
            JSONObject nic = new JSONObject(reply);
            db.update("User", values, "nic = ?", new String[]{nic.getString("nic")});
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
