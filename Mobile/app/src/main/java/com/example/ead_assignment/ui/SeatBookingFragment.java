package com.example.ead_assignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.R;
import com.example.ead_assignment.adapter.SeatBookingAdapter;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.db.VolleyRequest;
import com.example.ead_assignment.model.TrainInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SeatBookingFragment extends Fragment {

    private View view;
    private EditText txtTrainFrom;
    private RecyclerView recyclerView;
    private SeatBookingAdapter seatBookingAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Load the fragment
        view = inflater.inflate(R.layout.fragment_seat_booking, container, false);

        // Initialize the elements to variables
        txtTrainFrom = view.findViewById(R.id.lbl_seat_booking_train_depart);

        loadSchedules();

        return view;
    }

    // Load Schedules
    private void loadSchedules() {
        List<TrainInfo> trainInfoList = new ArrayList<>();
        // Set the recycler view
        recyclerView = view.findViewById(R.id.recycler_seat_booking);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        seatBookingAdapter = new SeatBookingAdapter(this);
        seatBookingAdapter.setContext(getContext());
        String trainSchedules = new VolleyRequest().getSQLiteDBData(getContext(), "train_schedules");
        if (trainSchedules != null) {
            setDataSchedules(trainSchedules, trainInfoList);
            // Get seat bookings data from embedded db
            String seatBookings = new VolleyRequest().getSQLiteDBData(getContext(), "seat_bookings");
            if (seatBookings != null) {
                setDataBookings(seatBookings, trainInfoList);
            }
        }
        new VolleyRequest().getData(getContext(), VolleyRequest.GET_SCHEDULES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Refresh the train schedules data in embedded db
                new VolleyRequest().updateSQLiteDBData(getContext(), "train_schedules", response);
                trainInfoList.clear();
                setDataSchedules(response, trainInfoList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void setReservedSeats(List<TrainInfo> trainInfoList) {
        try {
            // Get data from embedded db
            VolleyRequest volleyRequest = new VolleyRequest();
            String reply = volleyRequest.getSQLiteDBData(getContext(), "user_profile");
            JSONObject jsonObject = new JSONObject(reply);
            // Send request to the server
            volleyRequest.getData(getContext(), VolleyRequest.GET_BOOKINGS + "/" + URLEncoder.encode(jsonObject.getString("nic"), "UTF-8"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    setDataBookings(response, trainInfoList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } catch (Exception e) {

        }
    }

    // Set data train schedules
    private void setDataSchedules(String response, List<TrainInfo> trainInfoList) {
        try {
            // Set data to json
            JSONArray trainSchedules = new JSONArray(response);
            Commons commons = new Commons();
            // Set data to train and set to recycler view
            for (int i = 0; i < trainSchedules.length(); i++) {
                TrainInfo trainInfo = new TrainInfo();
                trainInfo.setTrainId(trainSchedules.getJSONObject(i).getString("id"));
                trainInfo.setTrainNumber(trainSchedules.getJSONObject(i).getString("number"));
                trainInfo.setDepartureStation(trainSchedules.getJSONObject(i).getString("depatre_Station"));
                trainInfo.setArrivalStation(trainSchedules.getJSONObject(i).getString("arrival_Station"));
                trainInfo.setDepartureTime(commons.formatDateTime(trainSchedules.getJSONObject(i).getString("depatre_Time")));
                trainInfo.setArrivalTime(commons.formatDateTime(trainSchedules.getJSONObject(i).getString("arrival_Time")));
                trainInfo.setStatus(trainSchedules.getJSONObject(i).getString("status"));
                trainInfoList.add(trainInfo);
            }
            commons.sortTrains(trainInfoList);
            setReservedSeats(trainInfoList);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Mark reserved seats and set data to the recycler view
    private void setDataBookings(String response, List<TrainInfo> trainInfoList) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray trainBookings = jsonObject.getJSONArray("trains");
            for (int i = 0; i < trainInfoList.size(); i++) {
                for (int j = 0; j < trainBookings.length(); j++) {
                    if (trainInfoList.get(i).getTrainId().equals(trainBookings.getJSONObject(j).getString("id"))) {
                        trainInfoList.get(i).setReserved(true);
                    }
                }
                trainInfoList.get(i).setSeatsCount(trainBookings.length());
            }
        } catch (JSONException e) {

        }
        seatBookingAdapter.setSeatsList(trainInfoList);
        recyclerView.setAdapter(seatBookingAdapter);
    }
}
