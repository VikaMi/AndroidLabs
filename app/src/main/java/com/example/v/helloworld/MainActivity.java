package com.example.v.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnButonsClick();
    }

    public void OnButonsClick(){
        final EditText your_name = (EditText) findViewById(R.id.editText);
        Button say_hello_button = findViewById(R.id.button2);
        Button clear_button = findViewById(R.id.button);
        final TextView hello_you = findViewById(R.id.textView);

        say_hello_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hello_you.setText(getString(R.string.say_hello, your_name.getText().
                                toString().trim()));
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
