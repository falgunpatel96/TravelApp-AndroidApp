package com.dal.travelapp;

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

public class EnterOtp extends AppCompatActivity {

    Button submit;
    EditText otp;
    String otpEntered, email;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        submit = (Button) findViewById(R.id.submit);
        otp = (EditText) findViewById(R.id.otp);
        otpEntered = new String();
        email = new String();
        queue = Volley.newRequestQueue(getApplicationContext());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent i = getIntent();
                otpEntered = i.getStringExtra("otp");
                email = i.getStringExtra("email");
                String pass = i.getStringExtra("password");
                String username = i.getStringExtra("username");

                //Toast.makeText(getApplicationContext(), "otpEntered: "+otpEntered, Toast.LENGTH_LONG).show();

                String url = "http://35.173.126.167:5000/api/otp?otp="+otp.getText().toString()+"&email="+email+"&username="+username+"&password="+pass;
                url = url.replace(" ", "%20");

                Toast.makeText(getApplicationContext(), "otp URL: " + url, Toast.LENGTH_LONG).show();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    Toast.makeText(getApplicationContext(), "response otp: " + response.length(), Toast.LENGTH_LONG).show();

//                                    JSONObject jo = response.getJSONObject();
                                    String status = response.getString("status");

                                    Toast.makeText(getApplicationContext(), "status: " +status , Toast.LENGTH_LONG).show();

                                    if (status.equals("authenticated")) {

                                        //If user pasword match meaning successful login
                                        Save.save(getApplicationContext(), email);

//                                        if(i.getStringExtra("from").equals("login"))
//                                        {
                                            Toast.makeText(getApplicationContext(), "User Logged In!", Toast.LENGTH_LONG).show();
//                                        }
//                                        else if (i.getStringExtra("from").equals("signup"))
//                                        {
//                                            Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_LONG).show();
//                                        }

                                        //endpoint thi return karavva mate proper result par RESULT_OK lakhine java devu
                                        //ahiya otp successful match thai gayu atle return thase login or signup par jyathi avyu hoi tyathi. so RESULT_OK code sathe return
                                        Intent returnIntent1 = new Intent();
                                        setResult(Activity.RESULT_OK, returnIntent1);
                                        finish();
                                    }else if (status.equals("User Created."))
                                    {
                                        Save.save(getApplicationContext(), email);
//                                        else if (i.getStringExtra("from").equals("signup"))
//                                        {
                                        Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_LONG).show();
                                        Intent returnIntent = new Intent();
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        finish();
//                                        }
                                    }
                                    else if(status.equals("Wrong OTP entered. Please enter correct OTP")){
                                        Toast.makeText(getApplicationContext(), "Wrong OTP. TRY AGAIN.", Toast.LENGTH_LONG).show();
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



            }
        });
    }
}
