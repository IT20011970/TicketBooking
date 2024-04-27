package com.example.ead_assignment.db;

import android.widget.TextView;

import com.example.ead_assignment.model.Booking;
import com.example.ead_assignment.model.TrainInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Commons {

    public static TextView nicView, nameView;
    private static TrainInfo trainInfo;
    private static int bookedCount;

    // Set fields of navigation bar
    public static void setNavViewFields(TextView nicViewA, TextView nameViewA) {
        nicView = nicViewA;
        nameView = nameViewA;
    }

    // Refresh Navigation bar
    public static void refreshNavView(String nic, String name) {
        if (nicView != null && nameView != null) {
            nicView.setText(nic);
            nameView.setText(name);
        }
    }

    // Format date and time for readable
    public String formatDateTime(String dateTimeP) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

            Date date = inputFormat.parse(dateTimeP);
            String outputTimestamp = outputFormat.format(date);

            return outputTimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing exception as needed
        }
        return null;
    }

    public String formatDateTimeReserved(String dateTimeP) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

            Date date = inputFormat.parse(dateTimeP);
            String outputTimestamp = outputFormat.format(date);

            return outputTimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing exception as needed
        }
        return null;
    }

    // Sort train cards by departure and arrival station for train schedules
    public void sortTrains(List<TrainInfo> trainInfoList) {
        Collections.sort(trainInfoList, new Comparator<TrainInfo>() {

            public int compare(TrainInfo o1, TrainInfo o2) {
                int value1 = o1.getDepartureStation().compareTo(o2.getDepartureStation());
                if (value1 == 0) {
                    return o1.getArrivalStation().compareTo(o2.getArrivalStation());
                }
                return value1;
            }
        });
    }

    // Sort train cards by departure and arrival station for bookings
    public void sortBookings(List<Booking> bookingList) {
        Collections.sort(bookingList, new Comparator<Booking>() {

            public int compare(Booking o1, Booking o2) {
                int value1 = o1.getTrainInfo().getDepartureStation().compareTo(o2.getTrainInfo().getDepartureStation());
                if (value1 == 0) {
                    return o1.getTrainInfo().getArrivalStation().compareTo(o2.getTrainInfo().getArrivalStation());
                }
                return value1;
            }
        });
    }

    public static int getBookedCount() {
        return bookedCount;
    }

    public static void setBookedCount(int bookedCount) {
        Commons.bookedCount = bookedCount;
    }

    public static TrainInfo getTrainInfo() {
        return trainInfo;
    }

    public static void setTrainInfo(TrainInfo trainInfo) {
        Commons.trainInfo = trainInfo;
    }
}
