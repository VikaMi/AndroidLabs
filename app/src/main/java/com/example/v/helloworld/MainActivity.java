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
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, yourPhone, password, confirmPasssword;
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

    private boolean validateFieldToSave(EditText field, Pattern regex) {
        return field.getText().toString().matches(String.valueOf(regex));
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
                        name = validateFieldToSave(firstName, pFirstName);
                        surname = validateFieldToSave(lastName, pLastName);
                        bEmail = validateFieldToSave(email, pEmail);
                        bPassword = validateFieldToSave(password, pPassword);
                        bConfirmPassword = validateFieldToSave(confirmPasssword, pConfirmPassword);
                        bPhone= validateFieldToSave(yourPhone, pYourPhone);
                        if (name&&surname&&bEmail&&bPhone) {
                            Toast.makeText(getApplicationContext(),
                                    "Save", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getApplicationContext(),
                                    "Please fill out the correct fields",
                                    Toast.LENGTH_LONG).show();
                        errorsList.setText(getString(R.string.say_hello, firstName.getText().
                                toString().trim()));
                    }
                });
    }
}
