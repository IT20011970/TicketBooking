package com.example.ead_assignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.R;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.db.VolleyRequest;
import com.example.ead_assignment.model.TrainInfo;

import org.json.JSONArray;
import org.json.JSONObject;

public class SeatBookingSummaryFragment extends Fragment {

    private View view;
    private Button btnReserveSeat;
    private TextView txtSeatBookingSummaryRemaining, txtSeatBookingSummaryTrain, txtTrainName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Load the fragment
        view = inflater.inflate(R.layout.fragment_seat_booking_summary, container, false);

        // Initialize the elements to variables
        btnReserveSeat = view.findViewById(R.id.btn_seat_booking_summary_reserve_seat);
        txtSeatBookingSummaryRemaining = view.findViewById(R.id.txt_seat_booking_summary_remaining);
        txtSeatBookingSummaryTrain = view.findViewById(R.id.txt_seat_booking_summary_train);
        txtTrainName = view.findViewById(R.id.txt_seat_booking_summary_train_name);

        int bookedCount = Commons.getBookedCount();
        TrainInfo trainInfo = Commons.getTrainInfo();
        txtTrainName.setText(trainInfo.getTrainNumber());
        // Set summarized texts
        txtSeatBookingSummaryTrain.setText(
                trainInfo.getDepartureStation() + " to " + trainInfo.getArrivalStation()
        );
        txtSeatBookingSummaryRemaining.setText(
                "You have " + (4 - bookedCount) + " remaining seats to reserve." +
                        "\n\nAfter you reserve the seat, " +
                        "you will have " + (4 - (bookedCount + 1)) + " remaining seats " +
                        "and " + (bookedCount + 1) + " reserved seats."
        );

        btnReserveSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Get nic from embedded db and set reserved train information to json
                    VolleyRequest volleyRequest = new VolleyRequest();
                    String reply = volleyRequest.getSQLiteDBData(getContext(), "user_profile");
                    JSONObject jsonObject = new JSONObject(reply);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("nic", jsonObject.getString("nic"));

                    JSONObject jo = new JSONObject();
                    jo.put("id", Commons.getTrainInfo().getTrainId());
                    JSONArray trains = new JSONArray();
                    trains.put(jo);

                    jsonBody.put("trains", trains);

                    // Set request to the server
                    volleyRequest.sendData(getContext(), VolleyRequest.MAKE_RESERVATION, jsonBody, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Navigate to the bookings page
                            Toast.makeText(getContext(), "Successfully reserved the seat", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.nav_fragment_bookings);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Sorry, you cannot reserve the seat", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
