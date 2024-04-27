package com.example.ead_assignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.R;
import com.example.ead_assignment.adapter.BookingsHistoryAdapter;
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

public class BookingsHistoryFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private BookingsHistoryAdapter bookingsHistoryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Load the fragment
        view = inflater.inflate(R.layout.fragment_bookings_history, container, false);

        loadBookings();

        return view;
    }

    //view all sicknesses
    private void loadBookings() {
        List<Booking> bookingsInfoList = new ArrayList<>();
        // Set the recycler view
        recyclerView = view.findViewById(R.id.recycler_bookings_history);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        bookingsHistoryAdapter = new BookingsHistoryAdapter(this);
        bookingsHistoryAdapter.setContext(getContext());
        VolleyRequest volleyRequest = new VolleyRequest();
        try {
            // Get user data from embedded db
            String reply = volleyRequest.getSQLiteDBData(getContext(), "user_profile");
            JSONObject jsonObject = new JSONObject(reply);
            // Get seat bookings data from embedded db
            String seatBookings = new VolleyRequest().getSQLiteDBData(getContext(), "bookings_history");
            if (seatBookings != null) {
                setData(seatBookings, bookingsInfoList);
            }
            // Send request to the server
            volleyRequest.getData(getContext(), VolleyRequest.GET_HISTORY + "/" + URLEncoder.encode(jsonObject.getString("nic"), "UTF-8"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new VolleyRequest().updateSQLiteDBData(getContext(), "bookings_history", response);
                    bookingsInfoList.clear();
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

    private void setData(String response, List<Booking> bookingsInfoList) {
        try {
            // Set data to json
            JSONArray trainBookings = new JSONArray(response);
            Commons commons = new Commons();
            // Set data to train and set to recycler view
            for (int i = 0; i < trainBookings.length(); i++) {
                TrainInfo trainInfo = new TrainInfo();
                trainInfo.setTrainId(trainBookings.getJSONObject(i).getString("id"));
                trainInfo.setTrainNumber(trainBookings.getJSONObject(i).getString("number"));
                trainInfo.setDepartureStation(trainBookings.getJSONObject(i).getString("departre_Station"));
                trainInfo.setArrivalStation(trainBookings.getJSONObject(i).getString("arrival_Station"));
                trainInfo.setDepartureTime(commons.formatDateTime(trainBookings.getJSONObject(i).getString("departre_Time")));
                trainInfo.setArrivalTime(commons.formatDateTime(trainBookings.getJSONObject(i).getString("arrival_Time")));
                String reservervedTime = trainBookings.getJSONObject(i).getString("reserverved_Time");
                trainInfo.setReservedTime(reservervedTime.split("T")[0]);
                trainInfo.setReservedTime(commons.formatDateTimeReserved(trainBookings.getJSONObject(i).getString("reserverved_Time")));
                Booking booking = new Booking();
                booking.setTrainInfo(trainInfo);
                bookingsInfoList.add(booking);
            }
            commons.sortBookings(bookingsInfoList);
            bookingsHistoryAdapter.setBookingList(bookingsInfoList);
            recyclerView.setAdapter(bookingsHistoryAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
