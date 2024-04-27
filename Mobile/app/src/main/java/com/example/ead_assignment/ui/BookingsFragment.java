package com.example.ead_assignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.R;
import com.example.ead_assignment.adapter.BookingsAdapter;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.db.VolleyRequest;
import com.example.ead_assignment.model.Booking;
import com.example.ead_assignment.model.TrainInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {

    private View view;
    private Button btnReservedCount;
    private RecyclerView recyclerView;
    private BookingsAdapter bookingsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Load the fragment
        view = inflater.inflate(R.layout.fragment_bookings, container, false);

        // Initialize the elements to variables
        btnReservedCount = view.findViewById(R.id.btn_bookings_reserved_count);

        loadBookings();

        return view;
    }

    //view all bookings
    private void loadBookings() {
        List<Booking> bookingsInfoList = new ArrayList<>();
        // Set the recycler view
        recyclerView = view.findViewById(R.id.recycler_bookings);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        bookingsAdapter = new BookingsAdapter(this);
        bookingsAdapter.setContext(getContext());
        VolleyRequest volleyRequest = new VolleyRequest();
        try {
            // Get user data from embedded db
            String reply = volleyRequest.getSQLiteDBData(getContext(), "user_profile");
            JSONObject jsonObject = new JSONObject(reply);
            // Get seat bookings data from embedded db
            String seatBookings = new VolleyRequest().getSQLiteDBData(getContext(), "seat_bookings");
            if (seatBookings != null) {
                setData(seatBookings, bookingsInfoList);
            }
            // Send request to the server
            volleyRequest.getData(getContext(), VolleyRequest.GET_BOOKINGS + "/" + URLEncoder.encode(jsonObject.getString("nic"), "UTF-8"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Refresh the seat bookings data in embedded db
                    new VolleyRequest().updateSQLiteDBData(getContext(), "seat_bookings", response);
                    bookingsInfoList.clear();
                    // Set data to the recycler view
                    setData(response, bookingsInfoList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getStackTrace();
                }
            });
        } catch (Exception e) {

        }
    }

    // Set data to the recycler view
    private void setData(String response, List<Booking> bookingsInfoList) {
        try {
            // Set data to json
            JSONObject jsonObject = new JSONObject(response);
            JSONArray trainBookings = jsonObject.getJSONArray("trains");
            Commons commons = new Commons();
            // Set data to train and set to recycler view
            for (int i = 0; i < trainBookings.length(); i++) {
                TrainInfo trainInfo = new TrainInfo();
                trainInfo.setTrainId(trainBookings.getJSONObject(i).getString("id"));
                trainInfo.setTrainNumber(trainBookings.getJSONObject(i).getString("number"));
                trainInfo.setDepartureStation(trainBookings.getJSONObject(i).getString("depatre_Station"));
                trainInfo.setArrivalStation(trainBookings.getJSONObject(i).getString("arrival_Station"));
                trainInfo.setDepartureTime(commons.formatDateTime(trainBookings.getJSONObject(i).getString("depatre_Time")));
                trainInfo.setArrivalTime(commons.formatDateTime(trainBookings.getJSONObject(i).getString("arrival_Time")));
                trainInfo.setStatus(trainBookings.getJSONObject(i).getString("status"));
                Booking booking = new Booking();
                booking.setTrainInfo(trainInfo);
                bookingsInfoList.add(booking);
            }
            if (trainBookings.length() > 0) {
                btnReservedCount.setText(trainBookings.length() + " Reserved Seats");
            } else {
                btnReservedCount.setText("No Reserved Seats");
            }
            commons.sortBookings(bookingsInfoList);
            bookingsAdapter.setBookingList(bookingsInfoList);
            recyclerView.setAdapter(bookingsAdapter);
        } catch (JSONException e) {

        }
    }
}
