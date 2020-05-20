package com.dal.travelapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {

    EditText email, password, conf_password, user_name, first_name, last_name;
    Button create;
    private RequestQueue queue;
    final int LAUNCH_OTP_ACTIVITY = 101;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        conf_password = (EditText) findViewById(R.id.conf_password);
        user_name = (EditText) findViewById(R.id.user_name);
        queue = Volley.newRequestQueue(getApplicationContext());

//        first_name = (EditText) findViewById(R.id.first_name);
//        last_name = (EditText) findViewById(R.id.last_name);
        create = (Button) findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://35.173.126.167:5000/api/register?email="+email.getText().toString()+"&username="+user_name.getText().toString()   +"&password="+password.getText().toString()+"&repeat_password="+conf_password.getText().toString();


                url = url.replace(" ", "%20");


//                Toast.makeText(getApplicationContext(), "signup URL: " + url, Toast.LENGTH_LONG).show();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

//                                    Toast.makeText(getApplicationContext(), "response: " + response.length(), Toast.LENGTH_LONG).show();

//                                    JSONObject jo = response.getJSONObject(); i
                                    if(!response.isNull("otp"))
                                    {
                                        otp = response.getString("otp");
                                    }

//                                    Toast.makeText(getApplicationContext(), " pass response: " + String.valueOf(!response.isNull("pass"))+" otp response: " + String.valueOf(!response.isNull("otp"))+" user response: " + String.valueOf(!response.isNull("user")), Toast.LENGTH_LONG).show();
//                                    Toast.makeText(getApplicationContext(),otp,Toast.LENGTH_LONG).show();

                                    if (!response.isNull("otp") && response.isNull("pass") && response.isNull("user")) {

                                        Intent i = new Intent(Signup.this, EnterOtp.class);
                                        i.putExtra("otp", otp);
                                        i.putExtra("email", email.getText().toString());
                                        i.putExtra("username", user_name.getText().toString());
                                        i.putExtra("password", password.getText().toString());
                                        i.putExtra("from","signup");
                                        startActivityForResult(i,LAUNCH_OTP_ACTIVITY);

//                                        //endpoint thi return karavva mate proper result par RESULT_OK lakhine java devu
//                                        //ahiya sign_up successful thai gayu atle return thase login par so RESULT_OK code sathe return
//                                        Intent returnIntent = new Intent();
//                                        setResult(Activity.RESULT_OK, returnIntent);
//                                        finish();

                                    } else if (!response.isNull("pass") && response.isNull("otp") && response.isNull("user")) {
                                        Toast.makeText(getApplicationContext(), "Password don't maych! Confirm password should be same as password.", Toast.LENGTH_LONG).show();
                                    } else if (!response.isNull("user") && response.isNull("otp") && response.isNull("pass")) {
                                        Toast.makeText(getApplicationContext(), "User already exist with same username. Kindly select unique username.", Toast.LENGTH_LONG).show();
                                    } else if (response.isNull("user") && response.isNull("otp") && response.isNull("pass")) {
                                        Toast.makeText(getApplicationContext(), "Error while performing Registration! TRY AGAIN.", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error Retriving Data", Toast.LENGTH_LONG).show();
                            }
                        }
                );
                queue.add(request);


                //Toast.makeText(getApplicationContext(),"email: "+email.getText().toString()+"\n"+"password: "+password.getText().toString()+"\n"+"conf_pass: "+conf_password.getText().toString()+"\n"+"first_name: "+first_name.getText().toString()+"\n"+"last_name: "+last_name.getText().toString(),Toast.LENGTH_LONG).show();
                //If SUCCESSFUL signup then
//                Save.save(getApplicationContext(), email.getText().toString());
//                Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_LONG).show();

                //return to Login.java

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_OTP_ACTIVITY)
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
                Toast.makeText(this,"User not Registered! TRY AGAIN.",Toast.LENGTH_LONG).show();
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
