package com.example.ead_assignment.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ead_assignment.R;
import com.example.ead_assignment.db.Commons;
import com.example.ead_assignment.db.VolleyRequest;
import com.example.ead_assignment.validator.Validator;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManageProfileFragment extends Fragment {

    private EditText txtNic, txtName, txtEmail, txtAddress, txtContact, txtPassword, txtConfPassword;
    private Button btnSave, btnDeactive;
    private LinearLayout txtNameErr, txtContactErr;
    private boolean validName = true, validAddress = true, validContact = true;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Load the fragment
        view = inflater.inflate(R.layout.fragment_manage_profile, container, false);

        // Initialize the elements to variables
        txtNic = view.findViewById(R.id.manage_profile_txt_nic);
        txtName = view.findViewById(R.id.manage_profile_txt_name);
        txtAddress = view.findViewById(R.id.manage_profile_txt_address);
        txtContact = view.findViewById(R.id.manage_profile_txt_contact_number);
        btnSave = view.findViewById(R.id.manage_profile_btn_save);
        btnDeactive = view.findViewById(R.id.manage_profile_btn_deactive);

        try {
            // Get user data from embedded db
            String reply = new VolleyRequest().getSQLiteDBData(getContext(), "user_profile");
            JSONObject jsonObject = new JSONObject(reply);
            if (jsonObject != null) {
                // Set data to variables
                txtNic.setText(jsonObject.getString("nic"));
                txtName.setText(jsonObject.getString("name"));
                txtAddress.setText(jsonObject.getString("address"));
                txtContact.setText(jsonObject.getString("contactNumber"));
            }
        } catch (Exception e) {

        }

        txtNameErr = view.findViewById(R.id.manage_profile_txt_name_err);
        txtContactErr = view.findViewById(R.id.manage_profile_txt_contact_number_err);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        btnDeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Alert view before deactivate account
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Manage Profile");
                alert.setMessage("Do you want to deactivate the account ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deactivateAccount();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        return view;
    }

    private void saveChanges() {
        // Check the all elements before proceed
        Validator validator = new Validator();
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

        if (validName && validAddress && validContact) {
            try {
                // Make json
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("nic", txtNic.getText());
                jsonBody.put("name", txtName.getText());
                jsonBody.put("address", txtAddress.getText());
                jsonBody.put("contactNumber", txtContact.getText());
                jsonBody.put("status", "active");
                jsonBody.put("isApprove", true);

                Map<String, String> queryParams = new HashMap<>();
                queryParams.put("id", txtNic.getText().toString());

                // Send request to the server
                new VolleyRequest().updateData(getContext(), VolleyRequest.MANAGE_PROFILE, queryParams, jsonBody, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Alert view before save changes
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Manage Profile");
                        alert.setMessage("Do you want to save changes ?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    // Update the embedded db and refresh the UI
                                    String reply = new VolleyRequest().updateSQLiteDBData(getContext(), "user_profile", response);
                                    JSONObject jsonObject = new JSONObject(reply);
                                    Commons.refreshNavView(jsonObject.getString("nic"), jsonObject.getString("name"));
                                    Toast.makeText(getContext(), "Saved the changes", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {

                                }
                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
            } catch (Exception e) {

            }
        } else {
//            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

    private void deactivateAccount() {
        try {
            // Make json
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nic", txtNic.getText());
            jsonBody.put("status", "inactive");
            jsonBody.put("isApprove", true);

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("id", txtNic.getText().toString());

            // Send request to the server (update)
            new VolleyRequest().updateData(getContext(), VolleyRequest.MANAGE_PROFILE, queryParams, jsonBody, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Navigate to logout
                    Navigation.findNavController(view).navigate(R.id.nav_logout);
                    Toast.makeText(getContext(), "Deactivated the account successfully", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } catch (Exception e) {

        }
    }
}
