package com.dal.travelapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyTickets extends AppCompatActivity implements RViewAdapterTicket.onClickListener {

    RecyclerView recyclerView;
    RViewAdapterTicket rviewAdapterTicket;
    ArrayList<Ticket> tickets;
    final int LAUNCH_SECOND_ACTIVITY = 1;
    final int DOWNLOAD_TICKET = 2;
    private Ticket selected_Ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rview_booking_option);

        tickets = new ArrayList<Ticket>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getBookings();
    }

    public void getBookings() {

        for (int i = 0; i < 5; i++) {
            Ticket singleTicket = new Ticket();

            singleTicket.setOrder_no("324632t47326");
            singleTicket.setDate_Time("1 dec 2019");
            singleTicket.setAmount("Rs. 45000");
            singleTicket.setFlight_name("IndiGo");
            singleTicket.setFlight_code("6E-171");
            singleTicket.setDepart_plc("New Delhi");
            singleTicket.setDestination_plc("Mumbai");
            singleTicket.setDepart_time("04:55");
            singleTicket.setDestination_time("07:05");
            singleTicket.setPls_day("");
            singleTicket.setTotl_hour("2h 10m");
            singleTicket.setNo_stops("Non Stop");
            singleTicket.setPrice("Rs. 4940");

            ArrayList<Traveller> travellerInfo = new ArrayList<Traveller>();
            travellerInfo.clear();
            for (int j = 0; j < 5; j++) {
                Traveller singleTraveller = new Traveller();
                singleTraveller.setFirst_name("firstname" + j);
                singleTraveller.setLast_name("lastname" + j);
                travellerInfo.add(singleTraveller);
            }
            singleTicket.setTravellersInfo(travellerInfo);

            tickets.add(singleTicket);
        }
        Log.e("bookings", "bookings Size:" + tickets.size());

        if (rviewAdapterTicket == null) {
            rviewAdapterTicket = new RViewAdapterTicket(MyTickets.this, tickets, MyTickets.this);
            recyclerView.setAdapter(rviewAdapterTicket);
        }
        if (tickets != null && !tickets.isEmpty()) {
            rviewAdapterTicket.doRefresh(tickets);
        }
    }

    @Override
    public void onItemClickListener(int position, Ticket singleTicket) {
        selected_Ticket = singleTicket;
        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

        //Toast.makeText(this, "" + logged_in, Toast.LENGTH_LONG).show();
        if (!logged_in) {

            Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MyTickets.this, Login.class);
            startActivityForResult(intent, DOWNLOAD_TICKET);
            //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
            //Log.d("bol", logged_in + "");
        } else {
            Toast.makeText(MyTickets.this, "Ticket Downloaded!", Toast.LENGTH_LONG).show();
            //code for downloading ticket
        }
    }

    @Override
    public void onLinerLayoutClick(int position, Ticket singleTicket) {
        selected_Ticket = singleTicket;
        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));
        
        if (!logged_in) {

            Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MyTickets.this, Login.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
        } else {
            Intent intent = new Intent(MyTickets.this, SingleWholeTicket.class);
            intent.putExtra("ticket_selected", singleTicket);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK && logged_in) {
                Intent intent = new Intent(MyTickets.this, SingleWholeTicket.class);
                intent.putExtra("ticket_selected", selected_Ticket);
                startActivity(intent);

            } else if (resultCode == Activity.RESULT_CANCELED || !logged_in) {
                Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyTickets.this, Login.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        } else if (requestCode == DOWNLOAD_TICKET) {
            if (resultCode == Activity.RESULT_OK && logged_in) {
                Toast.makeText(MyTickets.this, "Ticket Downloaded!", Toast.LENGTH_LONG).show();
                //code for downloading ticket
            } else if (resultCode == Activity.RESULT_CANCELED || !logged_in) {
                Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyTickets.this, Login.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        }
    }
}
