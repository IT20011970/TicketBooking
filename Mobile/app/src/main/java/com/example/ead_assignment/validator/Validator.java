package com.example.ead_assignment.validator;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public boolean validateNIC(String nic) {
        String validator1 = "^[0-9]{9}[Vv]$";
        String validator2 = "^[0-9]{12}$";
        return (nic.matches(validator1) || nic.matches(validator2));
    }

    public boolean validateName(String name) {
        String validator1 = "^[A-Za-z. ]+$";
        return name.matches(validator1);
    }

    public boolean validateContactNumber(String number) {
        String validator1 = "^[0-9]{3}[-][0-9]{7}$";
        return number.matches(validator1);
    }

    public boolean chkPasswordEquality(String password1, String password2) {
        return password1.equals(password2);
    }

    public void setErr(LinearLayout lyt, boolean isSet) {
        ViewGroup.LayoutParams params = lyt.getLayoutParams();
        if (isSet) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            params.height = 0;
            params.width = 0;
        }
        lyt.setLayoutParams(params);
    }
}
