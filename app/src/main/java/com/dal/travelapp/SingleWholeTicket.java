package com.dal.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class SingleWholeTicket extends AppCompatActivity {

    LinearLayout outerll;
    TextView order_no, dateTime, amount, email, flight_name, flight_code, depart_plc, depart_time, destination_plc, destination_time, pls_day, totl_hour, no_stops, price;
    ImageView compny_logo;
    Button download;
    private Ticket selected_Ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_single_ticket);

        order_no = (TextView) findViewById(R.id.order_no);
        dateTime = (TextView) findViewById(R.id.dateTime);
        amount = (TextView) findViewById(R.id.amount);
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

        download = (Button) findViewById(R.id.download);
        outerll = (LinearLayout) findViewById(R.id.ll_traveller_info_singleTicket);


        Intent i = getIntent();
        selected_Ticket = (Ticket) i.getSerializableExtra("ticket_selected");

        for (int j = 0; j < selected_Ticket.getTravellersInfo().size(); j++) {

            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            outerll.addView(ll);

            TextView first_name = new TextView(this);
            first_name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
            //String id = "R.id.first_name"+j;
            first_name.setId((j*2));
            first_name.setGravity(Gravity.CENTER);
            ll.addView(first_name);

            TextView last_name = new TextView(this);
            last_name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
            last_name.setId((j*2)+1);
            last_name.setGravity(Gravity.CENTER);
            ll.addView(last_name);
        }
        getTravllersInfo();

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleWholeTicket.this, "Ticket Downloaded!", Toast.LENGTH_LONG).show();

                downloadTicket(Save.readEmail(SingleWholeTicket.this),order_no);
            }
        });
    }

    private void downloadTicket(String readEmail, final TextView order_no) {
        String mUrl= "http://127.0.0.1:5000/getPdf";
      JSONObject object = new JSONObject();
        try {
//            object.put("email_id",email);
//            object.put("booking_no",order_no);
            object.put("email_id","fan@gmail.com");
            object.put("booking_no","323030343031303035303438");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, mUrl,object,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response!=null) {

                                FileOutputStream outputStream;
                                String name="ticket"+order_no+".pdf";
                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                                outputStream.write(response);
                                outputStream.close();
                                Toast.makeText(SingleWholeTicket.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }

    private void getTravllersInfo() {
        order_no.setText(selected_Ticket.getOrder_no());
        dateTime.setText(selected_Ticket.getDate_Time());
        amount.setText(selected_Ticket.getAmount());
        Glide.with(SingleWholeTicket.this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.airplane24))
                .load(selected_Ticket.getFlight_logo())
                .into(compny_logo);
//        compny_logo.setImageResource(R.drawable.indigo_logo);
        flight_name.setText(selected_Ticket.getFlight_name());
        flight_code.setText(selected_Ticket.getFlight_code());
        depart_plc.setText(selected_Ticket.getDepart_plc());
        depart_time.setText(selected_Ticket.getDepart_time());
        destination_plc.setText(selected_Ticket.getDestination_plc());
        destination_time.setText(selected_Ticket.getDestination_time());
        pls_day.setText(selected_Ticket.getPls_day());
        totl_hour.setText(selected_Ticket.getTotl_hour());
        no_stops.setText(selected_Ticket.getNo_stops());
        price.setText(selected_Ticket.getPrice());

        ArrayList<Traveller> travellersInfo = selected_Ticket.getTravellersInfo();
        for (int i=0; i<travellersInfo.size(); i++)
        {
            System.out.println("iiiii "+i);
            Traveller singleTraveller = travellersInfo.get(i);

            TextView first_name = (TextView) findViewById((i*2));
            TextView last_name = (TextView) findViewById((i*2)+1);

            first_name.setText(singleTraveller.getFirst_name());
            last_name.setText(singleTraveller.getLast_name());
        }
    }
}
