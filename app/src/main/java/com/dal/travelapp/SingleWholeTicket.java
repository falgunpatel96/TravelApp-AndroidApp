package com.dal.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
            }
        });
    }

    private void getTravllersInfo() {
        order_no.setText(selected_Ticket.getOrder_no());
        dateTime.setText(selected_Ticket.getDate_Time());
        amount.setText(selected_Ticket.getAmount());
        compny_logo.setImageResource(R.drawable.indigo_logo);
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
