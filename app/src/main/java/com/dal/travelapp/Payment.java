package com.dal.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    TextView email, flight_name, flight_code, depart_plc, depart_time, destination_plc, destination_time, pls_day, totl_hour, no_stops, price;
    ArrayList<Traveller> travellers;
    CreditCardEditText card_no;
    EditText name_card, expiry_date, cvv;
    Button pay;
    Booking selected_flight;
    ImageView compny_logo;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        compny_logo = (ImageView) findViewById(R.id.compny_logo);
        flight_name = (TextView) findViewById(R.id.flight_name);
        flight_code = (TextView) findViewById(R.id.flight_code);
        depart_plc = (TextView) findViewById(R.id.depart_plc);
        depart_time = (TextView) findViewById(R.id.depart_time);
        destination_plc = (TextView) findViewById(R.id.destination_plc);
        destination_time = (TextView) findViewById(R.id.destination_time);
        pls_day = (TextView) findViewById(R.id.pls_day);
        totl_hour = (TextView) findViewById(R.id.destination_time);
        no_stops = (TextView) findViewById(R.id.no_stops);
        price = (TextView) findViewById(R.id.price);

        email = (TextView) findViewById(R.id.email);

        card_no = (CreditCardEditText) findViewById(R.id.card_no);
        name_card = (EditText) findViewById(R.id.name_card);
        expiry_date = (EditText) findViewById(R.id.expiry_date);
        cvv = (EditText) findViewById(R.id.cvv);
        pay = (Button) findViewById(R.id.pay);

        email.setText(Save.readEmail(getApplicationContext()));

        Intent intent = getIntent();
        travellers = (ArrayList<Traveller>) intent.getSerializableExtra("travellers");
        selected_flight = (Booking) intent.getSerializableExtra("selected_flight");


        Glide.with(getApplicationContext()).setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.airplane24)).load(selected_flight.getFlight_logo()).into(compny_logo);
        //compny_logo.setImageResource(selected_flight.getFlight_logo());
        flight_name.setText(selected_flight.getFlight_name());
        flight_code.setText(selected_flight.getFlight_code());

        depart_plc.setText(selected_flight.getDepart_plc());
        depart_time.setText(selected_flight.getDepart_time());

        destination_plc.setText(selected_flight.getDestination_plc());
        destination_time.setText(selected_flight.getDestination_time());

        pls_day.setText(selected_flight.getPls_day());
        totl_hour.setText(selected_flight.getTotl_hour());
        no_stops.setText(selected_flight.getNo_stops());
        price.setText(selected_flight.getPrice());

        queue = Volley.newRequestQueue(getApplicationContext());

        String nameTravellers = new String();
        Traveller singleTraveller;

        for (int i = 0; i < travellers.size(); i++) {
            singleTraveller = travellers.get(i);
            nameTravellers = nameTravellers + singleTraveller.first_name + "\t" + singleTraveller.last_name + "\n";
        }

        //traveller name toast
        //Toast.makeText(this,nameTravellers,Toast.LENGTH_LONG).show();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card_no.getText().toString().length() == 16) {
                    String url = "http://54.159.158.177:5003/add";
                    url = url.replace(" ", "%20");


                    JSONArray traveller_name = new JSONArray();

                    for (int i = 0; i < travellers.size(); i++) {
                        JSONObject single_traveller = new JSONObject();
                        try {
                            single_traveller.put("fname", travellers.get(i).getFirst_name());
                            single_traveller.put("lname", travellers.get(i).getLast_name());
                            traveller_name.put(single_traveller);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONObject wholeJSON = new JSONObject();
                    JSONObject flight_details = new JSONObject();
                    try {
                        flight_details.put("flight_logo", selected_flight.getFlight_logo());

                        flight_details.put("flight_name", selected_flight.getFlight_name());
                        flight_details.put("flight_code", selected_flight.getFlight_code());

                        flight_details.put("destination_date", selected_flight.getDestination_date());
                        flight_details.put("destination_plc", selected_flight.getDestination_plc());
                        flight_details.put("destination_time", selected_flight.getDestination_time());

                        flight_details.put("departure_date", selected_flight.getDepart_date());
                        flight_details.put("departure_plc", selected_flight.getDepart_plc());
                        flight_details.put("departure_time", selected_flight.getDepart_time());

                        flight_details.put("pls_day", selected_flight.getPls_day());
                        flight_details.put("no_stops", selected_flight.getNo_stops());
                        flight_details.put("price", selected_flight.getPrice());
                        flight_details.put("total_hour", selected_flight.getTotl_hour());

                        JSONObject singlebooking = new JSONObject();
                        singlebooking.put("traveller_name", traveller_name);
                        singlebooking.put("flight_details", flight_details);

                        //calculate total amount
                        String priceStr = selected_flight.getPrice().split(" ")[1];
                        String curr = selected_flight.getPrice().split(" ")[0];
                        Integer priceInt = Integer.parseInt(priceStr);
                        Integer amount = priceInt * travellers.size();
                        singlebooking.put("amount", curr + " " + priceInt);

                        JSONArray bookings = new JSONArray();
                        bookings.put(singlebooking);

                        wholeJSON = new JSONObject();
                        wholeJSON.put("email_id", Save.readEmail(Payment.this));
                        wholeJSON.put("bookings", bookings);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, wholeJSON,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        Toast.makeText(getApplicationContext(), "response: " + response.length(), Toast.LENGTH_LONG).show();

//                                    JSONObject jo = response.getJSONObject();
                                        String code = response.getString("status_code");

                                        if (response.getString("status_code") != null && code.equals("200")) {
                                            Toast.makeText(Payment.this, "Ticket Booked! You can find them in MyTickets section.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Payment.this, "Error while booking ticket!", Toast.LENGTH_LONG).show();
                                        }

//
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
                else
                {
                    Toast.makeText(Payment.this, "Please Enter Valid Card Number", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}
