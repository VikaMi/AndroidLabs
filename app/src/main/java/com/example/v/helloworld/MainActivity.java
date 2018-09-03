package com.example.v.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView your_name;
    private Button say_hello_button;
    private Button clear_button;
    private TextView hello_you;
    private String yourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        your_name = (EditText) findViewById(R.id.editText);
        say_hello_button = findViewById(R.id.button2);
        clear_button = findViewById(R.id.button);
        hello_you = findViewById(R.id.textView);

        say_hello_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String yourName = your_name.getText().toString();
                        hello_you.setText("Hello " + yourName);
                    }

                });
        clear_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        your_name.setText("");
                    }
                }
        );
    }
}