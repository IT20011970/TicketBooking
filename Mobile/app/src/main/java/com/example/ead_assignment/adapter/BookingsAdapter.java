package com.example.ead_assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_assignment.R;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.model.Booking;
import com.example.ead_assignment.ui.BookingsFragment;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder> {

    private List<Booking> bookingList;
    private LayoutInflater inflater;
    private Context context;
    private BookingsFragment fragment;
    private static Booking staticBooking;

    public BookingsAdapter(BookingsFragment fragment) {
        this.fragment = fragment;
    }

    public BookingsAdapter(List<Booking> bookingList, Context context) {
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
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set recycler view to layout
        View view = inflater.inflate(R.layout.recycler_bookings, parent, false);
        return new BookingsViewHolder(view);
    }

    // Set data to individual cards
    @Override
    public void onBindViewHolder(@NonNull final BookingsViewHolder holder, int position) {
        final Booking booking = bookingList.get(position);
        holder.getLblTrainDepart().setText(booking.getTrainInfo().getDepartureStation());
        holder.getLblTrainArrival().setText(booking.getTrainInfo().getArrivalStation());
        holder.getTxtTrainNumber().setText(booking.getTrainInfo().getTrainNumber());
        holder.getTxtTrainDepartStation().setText(booking.getTrainInfo().getDepartureStation());
        holder.getTxtTrainArrivalStation().setText(booking.getTrainInfo().getArrivalStation());
        holder.getTxtTrainDepartTime().setText(booking.getTrainInfo().getDepartureTime());
        holder.getTxtTrainArrivalTime().setText(booking.getTrainInfo().getArrivalTime());
        holder.getTxtTrainStatus().setText(booking.getTrainInfo().getStatus());

        // Set train status
        if (booking.getTrainInfo().getStatus().equals("active")) {
            holder.getTxtTrainStatus().setText("Active");
            holder.getTxtTrainStatus().setTextColor(getContext().getResources().getColorStateList(R.color.btn_reserve_text));
        } else if (booking.getTrainInfo().getStatus().equals("inactive")) {
            holder.getTxtTrainStatus().setText("Cancelled");
            holder.getTxtTrainStatus().setTextColor(getContext().getResources().getColorStateList(R.color.btn_cancel_text));
        }
        holder.getBtnCancelBooking().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set data to summary page and navigate to summary page
                Commons.setTrainInfo(booking.getTrainInfo());
                Commons.setBookedCount(bookingList.size());
                Navigation.findNavController(view).navigate(R.id.nav_fragment_bookings_cancel_summary);
            }
        });
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
        BookingsAdapter.staticBooking = staticBooking;
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

    public BookingsFragment getFragment() {
        return fragment;
    }

    public void setFragment(BookingsFragment fragment) {
        this.fragment = fragment;
    }

    public class BookingsViewHolder extends RecyclerView.ViewHolder {

        private TextView lblTrainDepart, lblTrainArrival, txtTrainNumber, txtTrainDepartStation, txtTrainArrivalStation, txtTrainDepartTime, txtTrainArrivalTime, txtTrainStatus;
        private Button btnCancelBooking;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            // Set elements to the variables
            lblTrainDepart = itemView.findViewById(R.id.lbl_bookings_train_depart);
            lblTrainArrival = itemView.findViewById(R.id.lbl_bookings_train_arrival);
            txtTrainNumber = itemView.findViewById(R.id.txt_bookings_train_number);
            txtTrainDepartStation = itemView.findViewById(R.id.txt_bookings_train_depart_station);
            txtTrainArrivalStation = itemView.findViewById(R.id.txt_bookings_train_arrival_station);
            txtTrainDepartTime = itemView.findViewById(R.id.txt_bookings_train_depart_time);
            txtTrainArrivalTime = itemView.findViewById(R.id.txt_bookings_train_arrival_time);
            txtTrainStatus = itemView.findViewById(R.id.txt_bookings_train_status);
            btnCancelBooking = itemView.findViewById(R.id.btn_bookings_cancel_reserve);
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

        public Button getBtnCancelBooking() {
            return btnCancelBooking;
        }

        public void setBtnCancelBooking(Button btnCancelBooking) {
            this.btnCancelBooking = btnCancelBooking;
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

        public TextView getTxtTrainNumber() {
            return txtTrainNumber;
        }

        public void setTxtTrainNumber(TextView txtTrainNumber) {
            this.txtTrainNumber = txtTrainNumber;
        }
    }
}
