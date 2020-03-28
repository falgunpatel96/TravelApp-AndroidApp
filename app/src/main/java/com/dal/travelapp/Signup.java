package com.dal.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText email,password,conf_password,first_name,last_name;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        conf_password = (EditText)findViewById(R.id.password);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        create = (Button)findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"email: "+email.getText().toString()+"\n"+"password: "+password.getText().toString()+"\n"+"conf_pass: "+conf_password.getText().toString()+"\n"+"first_name: "+first_name.getText().toString()+"\n"+"last_name: "+last_name.getText().toString(),Toast.LENGTH_LONG).show();
                //If SUCCESSFUL signup then
                Save.save(getApplicationContext(),email.getText().toString());
                Toast.makeText(getApplicationContext(),"User Created!",Toast.LENGTH_LONG).show();

                //return to Login.java
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
