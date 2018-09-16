package com.example.v.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.Buffer;
import java.util.regex.Pattern;

import static android.opengl.ETC1.isValid;


public class MainActivity extends AppCompatActivity {


    EditText firstName, lastName, email, yourPhone, password, confirmPasssword;
    Pattern pFirstName, pLastName, pEmail, pYourPhone, pPassword, pConfirmPassword;
    private Boolean name, surname, bEmail, bPhone, bPassword, bConfirmPassword;
    Button submitButton;
    TextView errorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        validation();
        onButtonsClick();

    }

    public void initButtons() {
        firstName = findViewById(R.id.editText);
        lastName = findViewById(R.id.editText2);
        email = findViewById(R.id.editText3);
        yourPhone = findViewById(R.id.editText4);
        password = findViewById(R.id.editText5);
        confirmPasssword = findViewById(R.id.editText6);
        submitButton = findViewById(R.id.button2);
        errorsList = findViewById(R.id.textView);
    }

    private boolean validate_field_to_save(EditText et_field, Pattern regex) {
        return et_field.getText().toString().matches(String.valueOf(regex));
    }

    private void validation() {
        pFirstName = Pattern.compile("[A-Z][a-z ,.'-]+$");
        pEmail = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        pYourPhone = Pattern.compile("\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})");
        pPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$");


        validate_field(firstName, pFirstName);
        validate_field(lastName, pLastName);
        validate_field(email, pEmail);
        validate_field(yourPhone, pYourPhone);
        validate_field(password, pPassword);
        validate_field(confirmPasssword, pConfirmPassword);
    }


    private void validate_field(final EditText et_field, final Pattern pattern) {
        et_field.addTextChangedListener(new TextWatcher() {

            private CharSequence mText;

            private boolean isValid(CharSequence s) {
                return pattern.matcher(s).matches();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValid(s)) {
                    et_field.setBackgroundResource(R.drawable.norm);

                } else et_field.setBackgroundResource(R.drawable.error);

                if (!password.getText().toString().equals(confirmPasssword.getText().toString()) || confirmPasssword.getText().toString().equals("")) {
                    confirmPasssword.setBackgroundResource(R.drawable.error);
                    password.setBackgroundResource(R.drawable.error);
                } else {
                    confirmPasssword.setBackgroundResource(R.drawable.norm);
                    password.setBackgroundResource(R.drawable.norm);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public void onButtonsClick(){
        initButtons();
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = validate_field_to_save(firstName, pFirstName);
                        surname = validate_field_to_save(lastName, pLastName);
                        bEmail = validate_field_to_save(email, pEmail);
                        bPassword = validate_field_to_save(password, pPassword);
                        bConfirmPassword = validate_field_to_save(confirmPasssword, pConfirmPassword);
                        bPhone= validate_field_to_save(yourPhone, pYourPhone);
                        if (name&&surname&&bEmail&&bPassword&&bConfirmPassword&&bPhone) {
                            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Please fill out the correct fields", Toast.LENGTH_LONG).show();
                        errorsList.setText(getString(R.string.say_hello, firstName.getText().
                                toString().trim()));
                    }

                });

    }
}
