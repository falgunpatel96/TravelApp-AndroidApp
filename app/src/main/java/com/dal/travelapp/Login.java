package com.dal.travelapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText email,password;
    TextView signup;
    Button login;
    final int LAUNCH_SECOND_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        signup = (TextView)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"email: "+email.getText().toString()+"\n"+"password: "+password.getText().toString(),Toast.LENGTH_LONG).show();

                //
                //
                //Code for checking user password on cloud
                //
                //

                //If user pasword match meaning successful login
                Save.save(getApplicationContext(),email.getText().toString());
                Toast.makeText(getApplicationContext(),"User Logged In!",Toast.LENGTH_LONG).show();

                //return to MyBooking.java
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Signup.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY)
        {
            Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

            if (resultCode == Activity.RESULT_OK && logged_in)
            {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            else if (resultCode == Activity.RESULT_CANCELED || !logged_in)
            {
                //Intent intent = new Intent(MyBookings.this, Login.class);
                //startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                Toast.makeText(this,"User not Logged In!",Toast.LENGTH_LONG).show();
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
