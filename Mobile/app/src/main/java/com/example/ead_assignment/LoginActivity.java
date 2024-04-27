package com.example.ead_assignment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.db.SQLiteDBHelper;
import com.example.ead_assignment.db.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtSignUp;
    private EditText txtNic, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.medi_blue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.medi_blue));

        // assign buttons login and signup
        btnLogin = findViewById(R.id.login_btn_login);
        txtSignUp = findViewById(R.id.login_txt_signup);

        // assign text for nic and password
        txtNic = findViewById(R.id.login_txt_nic);
        txtNic.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        txtPassword = findViewById(R.id.login_txt_password);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // navigate to signup page
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Check whether the user previously login with the embedded db and proceed without login
        String jsonObject = new VolleyRequest().getSQLiteDBData(getApplicationContext(), "user_profile");
        if (jsonObject != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        // Do login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        // Check both username and password are not empty
        if (!txtNic.getText().toString().equals("") && !txtPassword.getText().toString().equals("")) {
            try {
                // Create json object for the vales
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("nic", txtNic.getText());
                jsonBody.put("password", txtPassword.getText());

                // Send request to the server
                new VolleyRequest().sendData(this, VolleyRequest.LOGIN, jsonBody, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // If login is true, the user is saving to the embedded db and proceed to the main activity
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("role").equals("traveller")) {
                                SQLiteDBHelper dbHelper = new SQLiteDBHelper(getApplicationContext());
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("nic", jsonObject.getString("nic"));
                                values.put("user_profile", jsonObject.toString());
                                db.insert("User", null, values);
                                db.close();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
