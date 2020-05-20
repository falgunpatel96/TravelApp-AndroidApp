package com.dal.travelapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText email,password;
    TextView signup;
    Button login;
    final int LAUNCH_SECOND_ACTIVITY = 1;
    final int LAUNCH_OTP_ACTIVITY = 100;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        signup = (TextView)findViewById(R.id.signup);
        queue = Volley.newRequestQueue(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"email: "+email.getText().toString()+"\n"+"password: "+password.getText().toString(),Toast.LENGTH_LONG).show();

                //
                //
                //Code for checking user password on cloud
                //
                //

                String url = "http://35.173.126.167:5000/api/login?username="+email.getText().toString()+"&password="+password.getText().toString();
                Log.d("url1",url);
                url = url.replace(" ", "%20");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

//                                    Toast.makeText(getApplicationContext(), "response: " + response.length(), Toast.LENGTH_LONG).show();

//                                    JSONObject jo = response.getJSONObject();
                                    Log.d("res1",response.toString());

//                                    String otp = response.getString("otp");
//                                    Log.d("otp1",otp);
//                                    Toast.makeText(getApplicationContext(), "response: " + response.toString(), Toast.LENGTH_LONG).show();

                                    if (!response.isNull("otp") && response.isNull("password_check") && response.isNull("user")) {

                                        String otp = response.getString("otp");
                                        Intent i = new Intent(Login.this,EnterOtp.class);
                                        i.putExtra("otp",otp);
                                        i.putExtra("email",email.getText().toString());
                                        i.putExtra("from","login");
                                        startActivityForResult(i,LAUNCH_OTP_ACTIVITY);

                                    } else if(!response.isNull("password_check")&& response.isNull("otp") && response.isNull("user")){
                                        Toast.makeText(getApplicationContext(), "Wrong password! Try Again.", Toast.LENGTH_LONG).show();
                                    } else if (!response.isNull("user")&& response.isNull("otp") && response.isNull("password_check"))
                                    {
                                        Toast.makeText(getApplicationContext(), "User doesn't exist. Kindly register first.", Toast.LENGTH_LONG).show();
                                    }else if (response.isNull("user") && response.isNull("otp") && response.isNull("password_check"))
                                    {
                                        Toast.makeText(getApplicationContext(), "Error while performing Login! TRY AGAIN.", Toast.LENGTH_LONG).show();
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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Signup.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

//                Intent returnIntent = new Intent();
//                setResult(Activity.RESULT_OK,returnIntent);
//                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(this,(resultCode)+" "+Save.readStatus(getApplicationContext(), new Boolean("false")),Toast.LENGTH_LONG).show();

        if (requestCode == LAUNCH_SECOND_ACTIVITY || requestCode == LAUNCH_OTP_ACTIVITY)
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
                Toast.makeText(this,"User not Logged In! TRY AGAIN.",Toast.LENGTH_LONG).show();
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
