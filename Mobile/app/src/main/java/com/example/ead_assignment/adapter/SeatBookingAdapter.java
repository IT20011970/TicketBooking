package com.example.ead_assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_assignment.R;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.model.TrainInfo;
import com.example.ead_assignment.ui.SeatBookingFragment;

import java.util.List;

public class SeatBookingAdapter extends RecyclerView.Adapter<SeatBookingAdapter.SeatBookingViewHolder> {

    private List<TrainInfo> seatsList;
    private LayoutInflater inflater;
    private Context context;
    private SeatBookingFragment fragment;
    private static TrainInfo staticTrainInfo;

    public SeatBookingAdapter(SeatBookingFragment fragment) {
        this.fragment = fragment;
    }

    public SeatBookingAdapter(List<TrainInfo> seatsList, Context context) {
        this.seatsList = seatsList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SeatBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set recycler view to layout
        View view = inflater.inflate(R.layout.recycler_seat_booking, parent, false);
        return new SeatBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeatBookingViewHolder holder, int position) {
        final TrainInfo trainInfo = seatsList.get(position);
        holder.getLblTrainDepart().setText(trainInfo.getDepartureStation());
        holder.getLblTrainArrival().setText(trainInfo.getArrivalStation());
        holder.getTxtTrainNumber().setText(trainInfo.getTrainNumber());
        holder.getTxtTrainDepartStation().setText(trainInfo.getDepartureStation());
        holder.getTxtTrainArrivalStation().setText(trainInfo.getArrivalStation());
        holder.getTxtTrainDepartTime().setText(trainInfo.getDepartureTime());
        holder.getTxtTrainArrivalTime().setText(trainInfo.getArrivalTime());
        holder.getTxtTrainStatus().setText(trainInfo.getStatus());

        // Set reserve and cancel buttons
        if (trainInfo.isReserved()) {
            holder.getBtnReserveSeat().setText("Cancel Booking");
            holder.getBtnReserveSeat().setBackgroundTintList(getContext().getResources().getColorStateList(R.color.btn_cancel));
            holder.getBtnReserveSeat().setTextColor(getContext().getResources().getColorStateList(R.color.btn_cancel_text));

            holder.getBtnReservedSeat().setText("Reserved");
            holder.getBtnReservedSeat().setBackgroundTintList(getContext().getResources().getColorStateList(R.color.btn_reserved));
            holder.getBtnReservedSeat().setTextColor(getContext().getResources().getColorStateList(R.color.btn_reserved_text));
        } else {
            // Set view if button is not available
            ViewGroup.LayoutParams params = holder.getLytSeatBookingReservedSeat().getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = 0;
            params.width = 0;
            holder.getLytSeatBookingReservedSeat().setLayoutParams(params);
        }

        // Set train status
        if (trainInfo.getStatus().equals("active")) {
            holder.getTxtTrainStatus().setText("Active");
            holder.getTxtTrainStatus().setTextColor(getContext().getResources().getColorStateList(R.color.btn_reserve_text));
        } else if (trainInfo.getStatus().equals("inactive")) {
            holder.getTxtTrainStatus().setText("Cancelled");
            holder.getTxtTrainStatus().setTextColor(getContext().getResources().getColorStateList(R.color.btn_cancel_text));
        }
        holder.getBtnReserveSeat().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trainInfo.getStatus().equals("active")) {
                    // Set navigation for reserve and not to navigate if seat count more than 4
                    if (trainInfo.isReserved()) {
                        Commons.setTrainInfo(trainInfo);// Set train object to summary page
                        Commons.setBookedCount(trainInfo.getSeatsCount());// Set seat count to summary page
                        Navigation.findNavController(view).navigate(R.id.nav_fragment_bookings_cancel_summary);
                    } else {
                        if (trainInfo.getSeatsCount() < 4) {
                            Commons.setTrainInfo(trainInfo);// Set train object to summary page
                            Commons.setBookedCount(trainInfo.getSeatsCount());// Set seat count to summary page
                            Navigation.findNavController(view).navigate(R.id.nav_fragment_seat_booking_summary);
                        } else {
                            Toast.makeText(getContext(), "Sorry, you are reached the maximum seat count", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Sorry, you cannot reserve", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return seatsList.size();
    }

    public List<TrainInfo> getSeatsList() {
        return seatsList;
    }

    public void setSeatsList(List<TrainInfo> seatsList) {
        this.seatsList = seatsList;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SeatBookingFragment getFragment() {
        return fragment;
    }

    public void setFragment(SeatBookingFragment fragment) {
        this.fragment = fragment;
    }

    public static TrainInfo getStaticSickness() {
        return staticTrainInfo;
    }

    public static void setStaticSickness(TrainInfo staticTrainInfo) {
        SeatBookingAdapter.staticTrainInfo = staticTrainInfo;
    }

    public class SeatBookingViewHolder extends RecyclerView.ViewHolder {

        private TextView lblTrainDepart, lblTrainArrival, txtTrainNumber, txtTrainDepartStation, txtTrainArrivalStation, txtTrainDepartTime, txtTrainArrivalTime, txtTrainStatus;
        private Button btnReserveSeat, btnReservedSeat;
        private LinearLayout lytSeatBookingReservedSeat;

        public SeatBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTrainDepart = itemView.findViewById(R.id.lbl_seat_booking_train_depart);
            lblTrainArrival = itemView.findViewById(R.id.lbl_seat_booking_train_arrival);
            txtTrainNumber = itemView.findViewById(R.id.txt_seat_booking_train_number);
            txtTrainDepartStation = itemView.findViewById(R.id.txt_seat_booking_train_depart_station);
            txtTrainArrivalStation = itemView.findViewById(R.id.txt_seat_booking_train_arrival_station);
            txtTrainDepartTime = itemView.findViewById(R.id.txt_seat_booking_train_depart_time);
            txtTrainArrivalTime = itemView.findViewById(R.id.txt_seat_booking_train_arrival_time);
            txtTrainStatus = itemView.findViewById(R.id.txt_seat_booking_train_status);
            btnReserveSeat = itemView.findViewById(R.id.btn_seat_booking_reserve_seat);
            btnReservedSeat = itemView.findViewById(R.id.btn_seat_booking_reserved_seat);
            lytSeatBookingReservedSeat = itemView.findViewById(R.id.lyt_seat_booking_reserved_seat);
        }

        public TextView getLblTrainDepart() {
            return lblTrainDepart;
        }

        public void setLblTrainDepart(TextView lblTrainDepart) {
            this.lblTrainDepart = lblTrainDepart;
        }

        public TextView getLblTrainArrival() {
            return lblTrainArrival;
        }

        public void setLblTrainArrival(TextView lblTrainArrival) {
            this.lblTrainArrival = lblTrainArrival;
        }

        public TextView getTxtTrainDepartStation() {
            return txtTrainDepartStation;
        }

        public void setTxtTrainDepartStation(TextView txtTrainDepartStation) {
            this.txtTrainDepartStation = txtTrainDepartStation;
        }

        public TextView getTxtTrainArrivalStation() {
            return txtTrainArrivalStation;
        }

        public void setTxtTrainArrivalStation(TextView txtTrainArrivalStation) {
            this.txtTrainArrivalStation = txtTrainArrivalStation;
        }

        public Button getBtnReserveSeat() {
            return btnReserveSeat;
        }

        public void setBtnReserveSeat(Button btnReserveSeat) {
            this.btnReserveSeat = btnReserveSeat;
        }

        public TextView getTxtTrainDepartTime() {
            return txtTrainDepartTime;
        }

        public void setTxtTrainDepartTime(TextView txtTrainDepartTime) {
            this.txtTrainDepartTime = txtTrainDepartTime;
        }

        public TextView getTxtTrainArrivalTime() {
            return txtTrainArrivalTime;
        }

        public void setTxtTrainArrivalTime(TextView txtTrainArrivalTime) {
            this.txtTrainArrivalTime = txtTrainArrivalTime;
        }

        public TextView getTxtTrainStatus() {
            return txtTrainStatus;
        }

        public void setTxtTrainStatus(TextView txtTrainStatus) {
            this.txtTrainStatus = txtTrainStatus;
        }

        public Button getBtnReservedSeat() {
            return btnReservedSeat;
        }

        public void setBtnReservedSeat(Button btnReservedSeat) {
            this.btnReservedSeat = btnReservedSeat;
        }

        public LinearLayout getLytSeatBookingReservedSeat() {
            return lytSeatBookingReservedSeat;
        }

        public void setLytSeatBookingReservedSeat(LinearLayout lytSeatBookingReservedSeat) {
            this.lytSeatBookingReservedSeat = lytSeatBookingReservedSeat;
        }

        public TextView getTxtTrainNumber() {
            return txtTrainNumber;
        }

        public void setTxtTrainNumber(TextView txtTrainNumber) {
            this.txtTrainNumber = txtTrainNumber;
        }
    }

}
