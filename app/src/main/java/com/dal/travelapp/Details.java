package com.dal.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    RecyclerView recyclerView;
    RViewAdapterName rviewAdapterName;
    Button payment;
    int adult_count = 1, child_count, infant_count, LAUNCH_SECOND_ACTIVITY = 1;
    EditText first_name, last_name,eml;
    ArrayList<Traveller> travellers;
    private Booking selected_flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rview_booking_details);

        travellers = new ArrayList<Traveller>();

        payment = (Button) findViewById(R.id.payment);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewName);
        eml = (EditText)findViewById(R.id.email);

        if(Save.readStatus(getApplicationContext(),false))
        {
            eml.setText(Save.readEmail(getApplicationContext()));
        }


        Intent i = getIntent();
        adult_count = i.getIntExtra("adult_no", 0);
        child_count = i.getIntExtra("child_no", 0);
        infant_count = i.getIntExtra("infant_no", 0);
        selected_flight = (Booking) i.getSerializableExtra("flight_selected");

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (rviewAdapterName == null) {
            rviewAdapterName = new RViewAdapterName(Details.this, adult_count, child_count, infant_count);
            recyclerView.setAdapter(rviewAdapterName);
        }
        Log.v("v","vish");

        payment.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Log.e("clic1","gpt it");
                travellers.clear();
                for (int i=0;i<recyclerView.getChildCount();i++)
                {
                    View view = recyclerView.getChildAt(i);
//                    Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();
                    Log.e("pi1234",i+"");
                    System.out.println("pi1234"+" "+i+" cnt"+(adult_count+child_count+infant_count));
                    first_name = view.findViewById(R.id.first_name);
                    last_name = view.findViewById(R.id.last_name);

                    Traveller singleTraveller = new Traveller();
                    singleTraveller.first_name = first_name.getText().toString();
                    singleTraveller.last_name = last_name.getText().toString();

                    travellers.add(singleTraveller);
                }


                Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

                //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
                if(!logged_in)
                {
                    Toast.makeText(getApplicationContext(),"User not Logged In!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Details.this, Login.class);
                    startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                    //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                    //Log.d("bol",logged_in+"");
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Payment.class);
                    intent.putExtra("travellers",travellers);
                    intent.putExtra("selected_flight",selected_flight);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionlist_menu,menu);

        MenuItem itemLogInOut = menu.findItem(R.id.login_out);
        MenuItem username = menu.findItem(R.id.username);
        if (Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false"))) == true)
        {
            itemLogInOut.setTitle("Log Out");
            String name = Save.readEmail(getApplicationContext());
            username.setTitle("Hello "+name.split("@")[0]);
        }
        else
        {
            username.setTitle("Hello User");
            itemLogInOut.setTitle("Log In");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        this.invalidateOptionsMenu();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.login_out:
                if(item.getTitle().equals("Log In"))
                {
                    Intent intent = new Intent(this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    Save.delete(this);
                }
                return true;
            case R.id.my_tickets:
                Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

                //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
                if(!logged_in)
                {
                    Toast.makeText(this,"User not Logged In!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Details.this, Login.class);
                    startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                    //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                    //Log.d("bol",logged_in+"");
                }
                else
                {
                    Intent intent1 = new Intent(this,MyTickets.class);
                    startActivity(intent1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LAUNCH_SECOND_ACTIVITY)
        {
            Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

            if (resultCode == Activity.RESULT_OK && logged_in)
            {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                intent.putExtra("travellers",travellers);
                intent.putExtra("selected_flight",selected_flight);
                startActivity(intent);
            }
            else if (resultCode == Activity.RESULT_CANCELED || !logged_in)
            {
                Toast.makeText(this,"User not Logged In!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Details.this, Login.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
