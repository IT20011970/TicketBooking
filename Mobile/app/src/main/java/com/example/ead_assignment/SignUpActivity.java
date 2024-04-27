package com.example.ead_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.db.VolleyRequest;
import com.example.ead_assignment.validator.Validator;

import org.json.JSONObject;

public class SignUpActivity extends Activity {

    private EditText txtNic, txtName, txtAddress, txtContact, txtPassword, txtConfPassword;
    private LinearLayout txtNicErr, txtNameErr, txtContactErr, txtConfPasswordErr;
    private boolean validNic = true, validName = true, validAddress = true, validContact = true, validPassword = true, validConfPassword = true;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.medi_blue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.medi_blue));

        // Assign UI elements to the variables
        txtNic = findViewById(R.id.sign_up_txt_nic);
        txtName = findViewById(R.id.sign_up_txt_name);
        txtAddress = findViewById(R.id.sign_up_txt_address);
        txtContact = findViewById(R.id.sign_up_txt_contact_number);
        txtPassword = findViewById(R.id.sign_up_txt_password);
        txtConfPassword = findViewById(R.id.sign_up_txt_confirm_password);
        btnSignUp = findViewById(R.id.sign_up_btn_signup);

        txtNicErr = findViewById(R.id.sign_up_txt_nic_err);
        txtNic.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        txtNameErr = findViewById(R.id.sign_up_txt_name_err);
        txtContactErr = findViewById(R.id.sign_up_txt_contact_number_err);
        txtConfPasswordErr = findViewById(R.id.sign_up_txt_confirm_password_err);
        txtNic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validator validator = new Validator();
                if (validator.validateNIC(editable.toString())) {
                    validator.setErr(txtNicErr, false);
                    validNic = true;
                    txtNic.setBackgroundResource(R.drawable.card_border_green);
                } else {
                    validator.setErr(txtNicErr, true);
                    validNic = false;
                    txtNic.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validator validator = new Validator();
                if (validator.validateName(editable.toString())) {
                    validator.setErr(txtNameErr, false);
                    validName = true;
                    txtName.setBackgroundResource(R.drawable.card_border_green);
                } else {
                    validator.setErr(txtNameErr, true);
                    validName = false;
                    txtName.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validator validator = new Validator();
                if (!editable.toString().isEmpty()) {
//                    validator.setErr(txtNameErr, false);
                    validAddress = true;
                    txtAddress.setBackgroundResource(R.drawable.card_border_green);
                } else {
//                    validator.setErr(txtNameErr, true);
                    validAddress = false;
                    txtAddress.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        txtContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3) {
                    txtContact.setText(txtContact.getText().insert(editable.length(), "-"));
                    txtContact.setSelection(4);
                }
                Validator validator = new Validator();
                if (validator.validateContactNumber(editable.toString())) {
                    validator.setErr(txtContactErr, false);
                    validContact = true;
                    txtContact.setBackgroundResource(R.drawable.card_border_green);
                } else {
                    validator.setErr(txtContactErr, true);
                    validContact = false;
                    txtContact.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    txtPassword.setBackgroundResource(R.drawable.card_border_green);
                } else {
                    txtPassword.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        txtConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validator validator = new Validator();
                if (validator.chkPasswordEquality(txtPassword.getText().toString(), editable.toString())) {
                    validator.setErr(txtConfPasswordErr, false);
                    validPassword = true;
                    txtConfPassword.setBackgroundResource(R.drawable.card_border_green);
                } else {
                    validator.setErr(txtConfPasswordErr, true);
                    validPassword = false;
                    txtConfPassword.setBackgroundResource(R.drawable.card_border_missed);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accSignup();
            }
        });
    }

    //sign up and create account
    private void accSignup() {
        // Check the all elements before proceed
        Validator validator = new Validator();
        if (txtNic.getText().toString().equals("")) {
            validator.setErr(txtNicErr, true);
            validNic = false;
            txtNic.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (txtName.getText().toString().equals("")) {
            validator.setErr(txtNameErr, true);
            validName = false;
            txtName.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (txtAddress.getText().toString().equals("")) {
//            validator.setErr(txtAddressErr, true);
            validAddress = false;
            txtAddress.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (txtContact.getText().toString().equals("")) {
            validator.setErr(txtContactErr, true);
            validContact = false;
            txtContact.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (txtPassword.getText().toString().equals("")) {
//            validator.setErr(txtPasswordErr, true);
            validPassword = false;
            txtPassword.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (txtConfPassword.getText().toString().equals("")) {
            validator.setErr(txtConfPasswordErr, true);
            validConfPassword = false;
            txtConfPassword.setBackgroundResource(R.drawable.card_border_missed);
        }

        if (validNic && validName && validAddress && validContact && validPassword) {
            try {
                // Make json
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("nic", txtNic.getText());
                jsonBody.put("name", txtName.getText());
                jsonBody.put("address", txtAddress.getText());
                jsonBody.put("role", "traveller");
                jsonBody.put("contactNumber", txtContact.getText());
                jsonBody.put("password", txtPassword.getText());
                jsonBody.put("status", "active");
                jsonBody.put("isApprove", true);

                // Send request to the server
                new VolleyRequest().sendData(this, VolleyRequest.SIGNUP, jsonBody, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Show the popup message and proceed to the login page
                        Toast.makeText(getApplicationContext(), "Sign Up Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            } catch (Exception e) {

            }
        } else {
//            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }
}

