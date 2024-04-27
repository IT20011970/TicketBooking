package com.example.ead_assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_assignment.R;
import com.example.ead_assignment.model.Booking;
import com.example.ead_assignment.ui.BookingsHistoryFragment;

import java.util.List;

public class BookingsHistoryAdapter extends RecyclerView.Adapter<BookingsHistoryAdapter.BookingsHistoryViewHolder> {

    private List<Booking> bookingList;
    private LayoutInflater inflater;
    private Context context;
    private BookingsHistoryFragment fragment;
    private static Booking staticBooking;

    public BookingsHistoryAdapter(BookingsHistoryFragment fragment) {
        this.fragment = fragment;
    }

    public BookingsHistoryAdapter(List<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.inflater = LayoutInflater.from(context);
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingsHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set recycler view to layout
        View view = inflater.inflate(R.layout.recycler_bookings_history, parent, false);
        return new BookingsHistoryViewHolder(view);
    }

    // Set data to individual cards
    @Override
    public void onBindViewHolder(@NonNull final BookingsHistoryViewHolder holder, int position) {
        final Booking booking = bookingList.get(position);
        holder.getLblTrainDepart().setText(booking.getTrainInfo().getDepartureStation());
        holder.getLblTrainArrival().setText(booking.getTrainInfo().getArrivalStation());
        holder.getTxtTrainNumber().setText(booking.getTrainInfo().getTrainNumber());
        holder.getTxtTrainDepartStation().setText(booking.getTrainInfo().getDepartureStation());
        holder.getTxtTrainArrivalStation().setText(booking.getTrainInfo().getArrivalStation());
        holder.getTxtTrainDepartTime().setText(booking.getTrainInfo().getDepartureTime());
        holder.getTxtTrainArrivalTime().setText(booking.getTrainInfo().getArrivalTime());
        holder.getTxtTrainReservedTime().setText(booking.getTrainInfo().getReservedTime());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public List<Booking> getMedicationList() {
        return bookingList;
    }

    public void setMedicationList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public static Booking getStaticBooking() {
        return staticBooking;
    }

    public static void setStaticBooking(Booking staticBooking) {
        BookingsHistoryAdapter.staticBooking = staticBooking;
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

    public BookingsHistoryFragment getFragment() {
        return fragment;
    }

    public void setFragment(BookingsHistoryFragment fragment) {
        this.fragment = fragment;
    }

    public class BookingsHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView lblTrainDepart, lblTrainArrival, txtTrainNumber, txtTrainDepartStation, txtTrainArrivalStation, txtTrainDepartTime, txtTrainArrivalTime, txtTrainReservedTime;

        public BookingsHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTrainDepart = itemView.findViewById(R.id.lbl_bookings_history_train_depart);
            lblTrainArrival = itemView.findViewById(R.id.lbl_bookings_history_train_arrival);
            txtTrainNumber = itemView.findViewById(R.id.txt_bookings_history_train_number);
            txtTrainDepartStation = itemView.findViewById(R.id.txt_bookings_history_train_depart_station);
            txtTrainArrivalStation = itemView.findViewById(R.id.txt_bookings_history_train_arrival_station);
            txtTrainDepartTime = itemView.findViewById(R.id.txt_bookings_history_train_depart_time);
            txtTrainArrivalTime = itemView.findViewById(R.id.txt_bookings_history_train_arrival_time);
            txtTrainReservedTime = itemView.findViewById(R.id.txt_bookings_history_train_reserved_at);
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

        public TextView getTxtTrainReservedTime() {
            return txtTrainReservedTime;
        }

        public void setTxtTrainReservedTime(TextView txtTrainReservedTime) {
            this.txtTrainReservedTime = txtTrainReservedTime;
        }

        public TextView getTxtTrainNumber() {
            return txtTrainNumber;
        }

        public void setTxtTrainNumber(TextView txtTrainNumber) {
            this.txtTrainNumber = txtTrainNumber;
        }
    }
}
