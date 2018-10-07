package com.example.v.helloworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v.helloworld.models.UserModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, yourPhone, password, confirmPasssword;
    Pattern pFirstName, pLastName, pEmail, pYourPhone, pPassword, pConfirmPassword;
    private Boolean name, surname, bEmail, bPhone, bPassword, bConfirmPassword;
    Button submitButton;
    TextView errorsList;

    public ArrayList<UserModel> userArrayList;

    protected TextView result;
    protected String text;

    protected Boolean validatorResult;
    protected Button list_users_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userArrayList = new ArrayList<>();
        setContentView(R.layout.activity_main);
        initButtons();
        list_users_button = findViewById(R.id.list_view_button);
        onSubmitButtonHandler();
        onListButtonHandler();
    }
    public void initButtons() {
        firstName = findViewById(R.id.editText);
        lastName = findViewById(R.id.editText2);
        email = findViewById(R.id.editText3);
        yourPhone = findViewById(R.id.editText4);
        password = findViewById(R.id.editText5);
        confirmPasssword = findViewById(R.id.editText6);
        submitButton = findViewById(R.id.submit_button);
        errorsList = findViewById(R.id.textView);
    }
    public void onListButtonHandler() {
        list_users_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent user_list = new Intent(getBaseContext(), UserListActivity.class);
                startActivity(user_list);
            }
        });
    }

    public void onSubmitButtonHandler() {

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                name = validateFieldToSave(firstName, pFirstName);
                surname = validateFieldToSave(lastName, pLastName);
                bEmail = validateFieldToSave(email, pEmail);
                bPassword = validateFieldToSave(password, pPassword);
                bConfirmPassword = validateFieldToSave(confirmPasssword, pConfirmPassword);
                bPhone = validateFieldToSave(yourPhone, pYourPhone);
                if (name && surname && bEmail && bPhone) {
                    Toast.makeText(getApplicationContext(),
                            "Save", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(),
                            "Please fill out the correct fields",
                            Toast.LENGTH_LONG).show();
                errorsList.setText(getString(R.string.say_hello, firstName.getText().
                        toString().trim()));
                validatorResult = true;
                validation();
                if (validatorResult) {
                    saveUser();
                }
            }
        });
    }
    private void validation() {
        pFirstName = Pattern.compile("[A-Z][a-z ,.'-]+$");
        pLastName = Pattern.compile("[A-Z][a-z ,.'-]+$");
        pEmail = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        pYourPhone = Pattern.compile("[0-9]{10,12}$");
        pPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$");
        pConfirmPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$");

        validateField(firstName, pFirstName);
        validateField(lastName, pLastName);
        validateField(email, pEmail);
        validateField(yourPhone, pYourPhone);
        validateField(password, pPassword);
        validateField(confirmPasssword, pConfirmPassword);
    }
    private void validateField(final EditText field, final Pattern pattern) {
        field.addTextChangedListener(new TextWatcher() {
            private boolean isValid(CharSequence s) {
                return pattern.matcher(s).matches();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValid(s)) {
                    field.setBackgroundResource(R.drawable.norm);
                } else field.setBackgroundResource(R.drawable.error);

                if (!password.getText().toString().equals(confirmPasssword.getText().toString()) ||
                        confirmPasssword.getText().toString().equals("")) {
                    confirmPasssword.setBackgroundResource(R.drawable.error);
                    password.setBackgroundResource(R.drawable.error);
                } else {
                    confirmPasssword.setBackgroundResource(R.drawable.norm);
                    password.setBackgroundResource(R.drawable.norm);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

        });
    }

    private boolean validateFieldToSave(EditText field, Pattern regex) {
        return field.getText().toString().matches(String.valueOf(regex));
    }
    @SuppressLint("SetTextI18n")
    public void stringValidator(EditText field_id, String regex, String field_name) {
        String value = String.valueOf(field_id.getText());
        String already_in_result = String.valueOf(result.getText());
        if (value.equals("")) {
            validatorResult = false;
            result.setText(already_in_result + "\nEmpty " + field_name);
            field_id.setError("Empty " + field_name);
        } else if (!(value.matches(regex))) {
            validatorResult = false;
            result.setText(already_in_result + "\nIncorrect " + field_name);
            field_id.setError("Incorrect " + field_name);
        }
    }



    public void saveUser(){
        String first_name_value = String.valueOf(firstName.getText());
        String last_name_value = String.valueOf(lastName.getText());
        String email_value = String.valueOf(email.getText());
        String phone_value = String.valueOf(yourPhone.getText());
        String password_value = String.valueOf(password.getText());

        UserModel user = new UserModel(first_name_value,
                last_name_value, email_value, phone_value, password_value);

        userArrayList.add(user);

        Gson gson = new Gson();
        String json = gson.toJson(userArrayList);
        Log.i("users", json);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                "user_list",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_list",json);
        editor.apply();
    }
}
