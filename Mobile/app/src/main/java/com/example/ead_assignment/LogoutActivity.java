package com.example.ead_assignment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ead_assignment.db.SQLiteDBHelper;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Clear the embedded db before sign out
        SQLiteDBHelper dbHelper = new SQLiteDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("User", null, null);

        // Move to the login page
        Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
