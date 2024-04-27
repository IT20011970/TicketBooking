package com.example.ead_assignment;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.db.VolleyRequest;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the main UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.medi_blue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.medi_blue));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Assign the navigation bar
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nicView = (TextView) headerView.findViewById(R.id.nav_header_txt_nic);
        TextView nameView = (TextView) headerView.findViewById(R.id.nav_header_txt_name);
        Commons.setNavViewFields(nicView, nameView);

        try {
            // Set the nic and the name to the navigation bar
            String reply = new VolleyRequest().getSQLiteDBData(getApplicationContext(), "user_profile");
            JSONObject jsonObject = new JSONObject(reply);
            if (jsonObject != null) {
                nicView.setText(jsonObject.getString("nic"));
                nameView.setText(jsonObject.getString("name"));
            }
        } catch (Exception e) {

        }

        // Set navigation fields
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_fragment_seat_booking, R.id.nav_fragment_view_profile, R.id.nav_fragment_bookings, R.id.nav_fragment_bookings_history)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}