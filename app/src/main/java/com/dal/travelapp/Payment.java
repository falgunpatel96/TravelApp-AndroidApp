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

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    TextView email,flight_name, flight_code, depart_plc, depart_time, destination_plc, destination_time, pls_day, totl_hour, no_stops, price;
    ArrayList<Traveller> travellers;
    CreditCardEditText card_no;
    EditText name_card, expiry_date, cvv;
    Button pay;
    Booking selected_flight;
    ImageView compny_logo;

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

        email = (TextView)findViewById(R.id.email);

        card_no = (CreditCardEditText)findViewById(R.id.card_no);
        name_card = (EditText)findViewById(R.id.name_card);
        expiry_date = (EditText)findViewById(R.id.expiry_date);
        cvv = (EditText)findViewById(R.id.cvv);
        pay = (Button)findViewById(R.id.pay);

        email.setText(Save.readEmail(getApplicationContext()));

        Intent intent = getIntent();
        travellers = (ArrayList<Traveller>) intent.getSerializableExtra("travellers");
        selected_flight = (Booking) intent.getSerializableExtra("selected_flight");


        compny_logo.setImageResource(R.drawable.indigo_logo);
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

        String nameTravellers = new String();
        Traveller singleTraveller;
        for (int i=0;i<travellers.size();i++)
        {
            singleTraveller = travellers.get(i);
            nameTravellers = nameTravellers +singleTraveller.first_name+"\t"+singleTraveller.last_name+"\n";
        }

        //traveller name toast
        //Toast.makeText(this,nameTravellers,Toast.LENGTH_LONG).show();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Payment.this,"Payment Done!",Toast.LENGTH_LONG).show();
            }
        });

    }
}
