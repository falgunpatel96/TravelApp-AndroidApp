package com.dal.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyBookings extends AppCompatActivity implements RViewAdapter.onClickListener {

    RecyclerView recyclerView;
    RViewAdapter rviewAdapter;
    ArrayList<Booking> bookings;

    private Runnable runnable;
    final int LAUNCH_SECOND_ACTIVITY = 1;
    int adult_count = 1, child_count, infant_count;
    private Booking selected_Flight;
    String depart_plc, destin_plc, depart_date;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rview_booking_option);

        bookings = new ArrayList<Booking>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        queue = Volley.newRequestQueue(getApplicationContext());
        /*runnable = new Runnable() {
            @Override
            public void run() {
                getBookings();
            }
        };*/



        /*//retrieve data on separate thread
        Thread thread = new Thread(null, runnable, "background");
        thread.start();*/

//        rviewAdapter = new RViewAdapter(bookings);
        //thread.start();
        Intent i = getIntent();
        adult_count = i.getIntExtra("adult_no", 0);
        child_count = i.getIntExtra("child_no", 0);
        infant_count = i.getIntExtra("infant_no", 0);
        depart_plc = i.getStringExtra("depart_plc");
        destin_plc = i.getStringExtra("destin_plc");
        depart_date = i.getStringExtra("depart_date");


        getBookings();
    }

        /*rviewAdapter = new RViewAdapter(bookings);


//        recyclerView.setAdapter(rviewAdapter);

        //notifying RecyclerViewAdapter for change in data
//        rviewAdapter.notifyDataSetChanged();


    }

    public void getAllBookingsByUser() {
        //final TaskCompletionSource<String> task = new TaskCompletionSource<>();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

    /***************************************************************************************
     *    Title: JsonObjectRequest setup in getJokes() function
     *    Author: Bola Okesanjo, Archanaapriya Nallasivan & Deepan Shankar
     *    Date: 2019
     *    Code version: 1.0
     *    Availability: CSCI5708-CurrencyApp_Fall2019 [Slide 31], https://dal.brightspace.com/d2l/le/content/100143/viewContent/1488863/View?ou=100143
     ***************************************************************************************/

    /****************************************************************************************
     *    Title: chuck_norris_icon.png image
     *    Author: Bola Okesanjo, Archanaapriya Nallasivan & Deepan Shankar
     *    Date: 2019
     *    Code version: 1.0
     *    Availability: chuck_norris_icon.png from code.zip/code , https://dal.brightspace.com/d2l/le/content/100143/viewContent/1506439/View
     ***************************************************************************************/

    public void getBookings() {


//            String url = "54.236.253.176:5001/api/flight?source=Toronto&destination=Calgary";
            String url = "http://54.236.253.176:5001/api/flight?source="+depart_plc+"&destination="+destin_plc;
//        String url = "54.236.253.176:5001/api/flight";
        url = url.replace(" ", "%20");
        Log.e("API",url);
//            Booking singleBooking = new Booking();

//            //private String flight_name, flight_code, depart_plc, destination_plc, depart_time, destination_time, pls_day, totl_hour, no_stops, price;
//            singleBooking.setFlight_name("IndiGo");
//            singleBooking.setFlight_code("6E-171");
//            singleBooking.setDepart_plc("New Delhi");
//            singleBooking.setDestination_plc("Mumbai");
//            singleBooking.setDepart_time("04:55");
//            singleBooking.setDestination_time("07:05");
//            singleBooking.setPls_day("");
//            singleBooking.setTotl_hour("2h 10m");
//            singleBooking.setNo_stops("Non Stop");
//            singleBooking.setPrice("Rs. 4940");
//
//            bookings.add(singleBooking);

        JSONObject flightrequest = new JSONObject();
        JSONArray param = new JSONArray();
        try {
            flightrequest.put("source", depart_plc);
            flightrequest.put("destination", destin_plc);
            param.put(flightrequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            bookings.clear();
//                        Toast.makeText(getApplicationContext(),"response: "+response.length(),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);
                                Booking singleBooking = new Booking();

//                                    singleBooking.setId(jo.getInt("_id"));
//                                    singleBooking.setAttraction(jo.getString("name"));
//                                    singleBooking.setCity(jo.getString("city"));
//                                    singleBooking.setImageURL(jo.getString("image"));


                                singleBooking.setDepart_date(jo.getString("departure_date"));
                                singleBooking.setDepart_plc(jo.getString("departure_plc"));
                                singleBooking.setDepart_time(jo.getString("departure_time"));

                                singleBooking.setDestination_date(jo.getString("destination_date"));
                                singleBooking.setDestination_plc(jo.getString("destination_plc"));
                                singleBooking.setDestination_time(jo.getString("destination_time"));

                                singleBooking.setFlight_name(jo.getString("first_name"));
                                singleBooking.setFlight_code(jo.getString("flightcode"));
                                singleBooking.setFlight_logo(jo.getString("flight_logo"));

                                singleBooking.setNo_stops(jo.getString("no_stops"));
                                singleBooking.setPls_day(jo.getString("pls_day"));
                                singleBooking.setPrice(jo.getString("price"));
                                singleBooking.setPrice("CAD 500");
                                singleBooking.setTotl_hour(jo.getString("total_hour"));
//                                locationItem.setDesc(jo.getString("description"));
//                                locationItem.setImageURL(jo.getString("imageURL"));
                                bookings.add(singleBooking);
                            }
                            rviewAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Error Retriving Data", Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(request);
        Log.e("bookings", "bookings Size:" + bookings.size());

        if (rviewAdapter == null) {
            rviewAdapter = new RViewAdapter(MyBookings.this, bookings, MyBookings.this);
            recyclerView.setAdapter(rviewAdapter);
        }
        if (bookings != null && !bookings.isEmpty()) {
            rviewAdapter.doRefresh(bookings);
        }
    }


    @Override
    public void onItemClickListener(int position, Booking booking) {
        selected_Flight = booking;
        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

        //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
        if (!logged_in) {
//            Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MyBookings.this, Login.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
            //Log.d("bol",logged_in+"");
        } else {
            Intent intent = new Intent(this, Details.class);
            intent.putExtra("adult_no", adult_count);
            intent.putExtra("child_no", child_count);
            intent.putExtra("infant_no", infant_count);
            intent.putExtra("flight_selected", booking);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionlist_menu, menu);

        MenuItem itemLogInOut = menu.findItem(R.id.login_out);
        MenuItem username = menu.findItem(R.id.username);
        if (Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false"))) == true) {
            itemLogInOut.setTitle("Log Out");
            String name = Save.readEmail(getApplicationContext());
            username.setTitle("Hello " + name.split("@")[0]);
        } else {
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
        switch (item.getItemId()) {
            case R.id.login_out:
                if (item.getTitle().equals("Log In")) {
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                } else {
                    Save.delete(this);
                }
                return true;
            case R.id.my_tickets:
                Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

                //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
                if (!logged_in) {
                    Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MyBookings.this, Login.class);
                    startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
                    //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                    //Log.d("bol",logged_in+"");
                } else {
                    Intent intent1 = new Intent(this, MyTickets.class);
                    startActivity(intent1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

            if (resultCode == Activity.RESULT_OK && logged_in) {
                Intent intent = new Intent(this, Details.class);
                intent.putExtra("adult_no", adult_count);
                intent.putExtra("child_no", child_count);
                intent.putExtra("infant_no", infant_count);
                intent.putExtra("flight_selected", selected_Flight);
                startActivity(intent);
            } else if (resultCode == Activity.RESULT_CANCELED || !logged_in) {
                Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyBookings.this, Login.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
